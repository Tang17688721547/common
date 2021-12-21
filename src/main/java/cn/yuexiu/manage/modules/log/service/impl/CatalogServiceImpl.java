package cn.yuexiu.manage.modules.log.service.impl;

import cn.yuexiu.manage.common.util.FillRuleUtil;
import cn.yuexiu.manage.common.util.StringUtil;
import cn.yuexiu.manage.common.util.YouBianCodeUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.yuexiu.manage.common.exception.JeecgBootException;
import cn.yuexiu.manage.common.system.util.FindsDepartsChildrenUtil;
import cn.yuexiu.manage.modules.log.entity.Catalog;
import cn.yuexiu.manage.modules.log.entity.Logs;
import cn.yuexiu.manage.modules.log.mapper.CatalogMapper;
import cn.yuexiu.manage.modules.log.service.CatalogService;
import cn.yuexiu.manage.modules.log.service.LogsService;
import cn.yuexiu.manage.modules.log.vo.CatalogIdModel;
import cn.yuexiu.manage.modules.log.vo.CatalogTreeModel;
import com.seaboxdata.auth.api.controller.IOauthRoleController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class CatalogServiceImpl extends ServiceImpl<CatalogMapper, Catalog> implements CatalogService {




    @Resource
    private LogsService logsService;

    @Override
    public Catalog saveCatalog(Catalog catalog) {
        // 先判断该对象有无父级ID,有则意味着不是最高级,否则意味着是最高级
        // 获取父级ID
        checkBySysCode(catalog.getSysCode());
        String code = FillRuleUtil.execute(catalog.getParentId());
        catalog.setCatalogCode(code);
        if (StringUtil.isEmpty(catalog.getParentId())) {
            catalog.setParentId("0");
        }
        this.save(catalog);
        return catalog;
    }

    @Override
    public List<CatalogTreeModel> searhBy(String catalogName) {
        QueryWrapper<Catalog> query = new QueryWrapper<>();
        List<CatalogTreeModel> newList = new ArrayList<>();
        query.like("catalog_name", "%" + catalogName + "%");
        query.eq("status", 1);
        CatalogTreeModel model = new CatalogTreeModel();
        List<Catalog> departList = this.list(query);
        if (departList.size() > 0) {
            for (Catalog depart : departList) {
                model = new CatalogTreeModel(depart);
                model.setChildren(null);
                newList.add(model);
            }
            return newList;
        }
        return null;
    }

    @Override
    public List<CatalogTreeModel> queryTreeList() {
        QueryWrapper<Catalog> query = new QueryWrapper<>();
        query.orderByDesc("catalog_order");
        List<Catalog> list = this.list(query);
        // 调用wrapTreeDataToTreeList方法生成树状数据
        List<CatalogTreeModel> listResult = FindsDepartsChildrenUtil.wrapTreeDataToTreeList(list);
        return listResult;
    }

    @Override
    public List<CatalogIdModel> queryTreeIdList() {
        QueryWrapper<Catalog> query = new QueryWrapper<>();
        query.orderByDesc("catalog_order");
        List<Catalog> list = this.list(query);
        // 调用wrapTreeDataToTreeList方法生成树状数据
        List<CatalogIdModel> listResult = FindsDepartsChildrenUtil.wrapTreeDataToDepartIdTreeList(list);
        return listResult;
    }

    @Override
    public void updateCatalog(Catalog catalog) {
        Catalog oldCatalog = getById(catalog.getId());

        if (!oldCatalog.getParentId().equals(catalog.getParentId())) {
            String code = execute(catalog.getParentId());
            catalog.setCatalogCode(code);
            if (StringUtil.isEmpty(catalog.getParentId())) {
                catalog.setParentId("0");
            }


            //查询指定目录下所以子目录
            QueryWrapper<Catalog> query = new QueryWrapper<>();
            query.apply("catalog_code like {0}",oldCatalog.getCatalogCode()+"%");
            List<Catalog> catalogs = list(query);

            //解除指定目录下所有目录关系
            UpdateWrapper<Catalog> catalogUpdateWrapper = new UpdateWrapper<>();
            catalogUpdateWrapper.apply("catalog_code like {0}",oldCatalog.getCatalogCode()+"%");
            catalogUpdateWrapper.set("parent_id","0");
            update(catalogUpdateWrapper);
            //更改修改的目录信息
            updateById(catalog);
            UpdateWrapper<Logs> logsUpdateWrapper = new UpdateWrapper<>();
            logsUpdateWrapper.eq("catalog_code",oldCatalog.getCatalogCode());
            logsUpdateWrapper.set("catalog_code",catalog.getCatalogCode());
            logsService.update(logsUpdateWrapper);

            if(!StringUtil.isEmpty(catalogs)) {
                updateCatalogs(catalog.getId(), catalogs);
            }
        }else{
            updateById(catalog);
        }
        //  String oldCatalogCode = catalog.getCatalogCode();
    }

    private  String execute(String parentId) {
        LambdaQueryWrapper<Catalog> query = new LambdaQueryWrapper<Catalog>();
        LambdaQueryWrapper<Catalog> query1 = new LambdaQueryWrapper<Catalog>();
        // 创建一个List集合,存储查询返回的所有Catalog对象
        List<Catalog> catalogs = new ArrayList<>();

        // 定义新编码字符串
        String newOrgCode = "";
        // 定义旧编码字符串
        String oldOrgCode = "";


        //如果是最高级,则查询出同级的code, 调用工具类生成编码并返回
        if (StringUtil.isEmpty(parentId)) {
            // 线判断数据库中的表是否为空,空则直接返回初始编码
            query1.eq(Catalog::getParentId, "0");
            query1.orderByDesc(Catalog::getCatalogCode);
            catalogs = list(query1);
            if (catalogs == null || catalogs.size() == 0) {
                return YouBianCodeUtil.getNextYouBianCode(null);
            } else {
                Catalog catalog = catalogs.get(0);
                oldOrgCode = catalog.getCatalogCode();
                newOrgCode = YouBianCodeUtil.getNextYouBianCode(oldOrgCode);
            }
        } else {//反之则查询出所有同级的目录,获取结果后有两种情况,有同级和没有同级
            // 封装查询同级的条件
            query.eq(Catalog::getParentId, parentId);
            // 降序排序
            query.orderByDesc(Catalog::getCatalogCode);
            // 查询出同级目录的集合
            List<Catalog> parentList = list(query);
            // 查询出父级目录
            Catalog catalog = getById(parentId);
            // 获取父级目录的Code
            String parentCode = catalog.getCatalogCode();
            // 处理同级目录为null的情况
            if (parentList == null || parentList.size() == 0) {
                // 直接生成当前的目录编码并返回
                newOrgCode = YouBianCodeUtil.getSubYouBianCode(parentCode, null);
            } else { //处理有同级目录的情况
                // 获取同级目录的编码,利用工具类
                String subCode = parentList.get(0).getCatalogCode();
                // 返回生成的当前目录编码
                newOrgCode = YouBianCodeUtil.getSubYouBianCode(parentCode, subCode);
            }
        }
        // 返回最终封装了目录编码
        return newOrgCode;
    }

    private void updateCatalogs(String id,List<Catalog> catalogs){
        for (Catalog catalog : catalogs) {
            if(id.equals(catalog.getParentId())){
                Catalog oldCatalog = getById(catalog.getId());
                String code = execute(id);
                catalog.setCatalogCode(code);
                catalog.setParentId(id);
                updateById(catalog);

                UpdateWrapper<Logs> logsUpdateWrapper = new UpdateWrapper<>();
                logsUpdateWrapper.eq("catalog_code",oldCatalog.getCatalogCode());
                logsUpdateWrapper.set("catalog_code",catalog.getCatalogCode());
                logsService.update(logsUpdateWrapper);
                updateCatalogs(catalog.getId(),catalogs);
            }
        }
    }

    @Override
    public void deleteCatalog(String catalogCode) {
        QueryWrapper<Logs> logsQueryWrapper = new QueryWrapper<>();
        logsQueryWrapper.like("catalog_code", catalogCode + "%");
        Integer count = logsService.count(logsQueryWrapper);
        if (count > 0) {
            throw new JeecgBootException("目录下存在日志无法删除");
        }
        UpdateWrapper<Catalog> catalogUpdateWrapper = new UpdateWrapper<>();

        catalogUpdateWrapper.like("catalog_code", catalogCode + "%");

        remove(catalogUpdateWrapper);
    }

    @Override
    public void checkBySysCode(String sysCode) {
        QueryWrapper<Catalog> catalogQueryWrapper = new QueryWrapper<>();
        catalogQueryWrapper.eq("sys_code", sysCode);
        List<Catalog> catalogs = list(catalogQueryWrapper);
        if (!StringUtil.isEmpty(catalogs)) {
            throw new JeecgBootException("系统编码已存在,请重新输入。");
        }
    }


}
