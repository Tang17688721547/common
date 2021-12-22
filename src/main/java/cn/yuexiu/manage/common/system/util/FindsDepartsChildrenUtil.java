package cn.yuexiu.manage.common.system.util;


import cn.yuexiu.manage.common.util.oConvertUtils;
import cn.yuexiu.manage.modules.log.entity.Catalog;
import cn.yuexiu.manage.modules.log.vo.CatalogIdModel;
import cn.yuexiu.manage.modules.log.vo.CatalogTreeModel;

import java.util.ArrayList;
import java.util.List;

/**
 * <P>
 * 对应部门的表,处理并查找树级数据
 * <P>
 * 
 * @Author: Steve
 * @Date: 2019-01-22
 */
public class FindsDepartsChildrenUtil {

	//目录树信息-树结构
	//private static List<SysDepartTreeModel> sysDepartTreeList = new ArrayList<SysDepartTreeModel>();
	
	//目录树id-树结构
    //private static List<DepartIdModel> idList = new ArrayList<>();



    public static List<CatalogTreeModel> wrapTreeDataToTreeList(List<Catalog> recordList) {
    	List<CatalogIdModel> idList = new ArrayList<CatalogIdModel>();
        List<CatalogTreeModel> records = new ArrayList<>();
        for (int i = 0; i < recordList.size(); i++) {
            Catalog depart = recordList.get(i);
            records.add(new CatalogTreeModel(depart));
        }
        List<CatalogTreeModel> tree = findChildren(records, idList);
        setEmptyChildrenAsNull(tree);
        return tree;
    }

    /**
     * 获取 DepartIdModel
     * @param recordList
     * @return
     */
    public static List<CatalogIdModel> wrapTreeDataToDepartIdTreeList(List<Catalog> recordList) {
        // 在该方法每请求一次,都要对全局list集合进行一次清理
        //idList.clear();
        List<CatalogIdModel> idList = new ArrayList<CatalogIdModel>();
        List<CatalogTreeModel> records = new ArrayList<>();
        for (int i = 0; i < recordList.size(); i++) {
            Catalog catalog = recordList.get(i);
            records.add(new CatalogTreeModel(catalog));
        }
        findChildren(records, idList);
        return idList;
    }

    /**
     * queryTreeList的子方法 ====2=====
     * 该方法是找到并封装顶级父类的节点到TreeList集合
     */
    private static List<CatalogTreeModel> findChildren(List<CatalogTreeModel> recordList,
                                                         List<CatalogIdModel> departIdList) {

        List<CatalogTreeModel> treeList = new ArrayList<>();
        for (int i = 0; i < recordList.size(); i++) {
            CatalogTreeModel branch = recordList.get(i);
            if (oConvertUtils.isEmpty(branch.getParentId())||branch.getParentId().equals("0")) {
                treeList.add(branch);
                CatalogIdModel departIdModel = new CatalogIdModel().convert(branch);
                departIdList.add(departIdModel);
            }
        }
        getGrandChildren(treeList,recordList,departIdList);
        
        //idList = departIdList;
        return treeList;
    }

    /**
     * queryTreeList的子方法====3====
     *该方法是找到顶级父类下的所有子节点集合并封装到TreeList集合
     */
    private static void getGrandChildren(List<CatalogTreeModel> treeList,List<CatalogTreeModel> recordList,List<CatalogIdModel> idList) {

        for (int i = 0; i < treeList.size(); i++) {
            CatalogTreeModel model = treeList.get(i);
            CatalogIdModel idModel = idList.get(i);
            for (int i1 = 0; i1 < recordList.size(); i1++) {
                CatalogTreeModel m = recordList.get(i1);
                if (m.getParentId()!=null && m.getParentId().equals(model.getId())) {
                    model.getChildren().add(m);
                    CatalogIdModel dim = new CatalogIdModel().convert(m);
                    idModel.getChildren().add(dim);
                }
            }
            getGrandChildren(treeList.get(i).getChildren(), recordList, idList.get(i).getChildren());
        }

    }
    

    /**
     * queryTreeList的子方法 ====4====
     * 该方法是将子节点为空的List集合设置为Null值
     */
    private static void setEmptyChildrenAsNull(List<CatalogTreeModel> treeList) {

        for (int i = 0; i < treeList.size(); i++) {
            CatalogTreeModel model = treeList.get(i);
            if (model.getChildren().size() == 0) {
                model.setChildren(null);
                model.setIsLeaf(true);
            }else{
                setEmptyChildrenAsNull(model.getChildren());
                model.setIsLeaf(false);
            }
        }
        // sysDepartTreeList = treeList;
    }
}
