package cn.yuexiu.manage.modules.log.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import cn.yuexiu.manage.modules.log.entity.Catalog;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 部门表 封装树结构的部门的名称的实体类
 * <p>
 * 
 * @Author Steve
 * @Since 2019-01-22 
 *
 */
@Data
public class CatalogIdModel implements Serializable {

    private static final long serialVersionUID = 1L;

    // 主键ID
    private String key;

    // 主键ID
    private String value;

    // 目录名称
    private String title;

    // Api接口
    private String infoUrl;

    List<CatalogIdModel> children = new ArrayList<>();
    
    /**
     * 将SysDepartTreeModel的部分数据放在该对象当中
     * @param treeModel
     * @return
     */
    public CatalogIdModel convert(CatalogTreeModel treeModel) {
        this.key = treeModel.getKey();
        this.value = treeModel.getValue();
        this.title = treeModel.getCatalogName();
        this.infoUrl = treeModel.getInfoUrl();
        return this;
    }
    
    /**
     * 该方法为用户部门的实现类所使用
     * @param catalog
     * @return
     */
    public CatalogIdModel convertByUserDepart(Catalog catalog) {
        this.key = catalog.getId();
        this.value = catalog.getId();
        this.title = catalog.getCatalogName();
        this.infoUrl = catalog.getInfoUrl();
        return this;
    } 

    public List<CatalogIdModel> getChildren() {
        return children;
    }

    public void setChildren(List<CatalogIdModel> children) {
        this.children = children;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
