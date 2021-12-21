package cn.yuexiu.manage.modules.log.vo;

import cn.yuexiu.manage.modules.log.entity.Catalog;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 部门表 存储树结构数据的实体类
 * <p>
 *
 * @Author Steve
 * @Since 2019-01-22
 */
@Data
public class CatalogTreeModel implements Serializable{

    private static final long serialVersionUID = 1L;

    /** 对应SysDepart中的id字段,前端数据树中的key*/
    private String key;

    /** 对应SysDepart中的id字段,前端数据树中的value*/
    private String value;

    /** 对应depart_name字段,前端数据树中的title*/
    private String title;

    private boolean isLeaf;
    // 以下所有字段均与SysDepart相同

    private String id;

    private String parentId;

    private String catalogName;

    private Integer catalogOrder;

    private String catalogCode;

    private String status;

    private String infoUrl;

    private List<CatalogTreeModel> children = new ArrayList<>();

    private Integer deleted;

    private String createUser;

    private String createTime;

    private String modifyUser;

    private String modifyTime;

    private String sysCode;

    /**
     * 将SysDepart对象转换成SysDepartTreeModel对象
     * @param sysDepart
     */
	public CatalogTreeModel(Catalog catalog) {
		this.key = catalog.getCatalogCode();
        this.value = catalog.getCatalogCode();
        this.title = catalog.getCatalogName();
        this.id = catalog.getId();
        this.parentId = catalog.getParentId();
        this.catalogName = catalog.getCatalogName();
        this.catalogOrder = catalog.getCatalogOrder();
        this.catalogCode = catalog.getCatalogCode();
        this.status = catalog.getStatus();
        this.deleted = catalog.getDeleted();
        this.sysCode = catalog.getSysCode();
        this.createUser = catalog.getCreateUser();
        this.createTime = catalog.getCreateTime();
        this.modifyUser = catalog.getModifyUser();
        this.modifyTime = catalog.getModifyTime();
        this.infoUrl = catalog.getInfoUrl();
    }

    public boolean getIsLeaf() {
        return isLeaf;
    }

    public void setIsLeaf(boolean isleaf) {
         this.isLeaf = isleaf;
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


	public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<CatalogTreeModel> getChildren() {
        return children;
    }

    public void setChildren(List<CatalogTreeModel> children) {
        if (children==null){
            this.isLeaf=true;
        }
        this.children = children;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }


    public boolean isLeaf() {
        return isLeaf;
    }

    public void setLeaf(boolean leaf) {
        isLeaf = leaf;
    }

    public String getCatalogName() {
        return catalogName;
    }

    public void setCatalogName(String catalogName) {
        this.catalogName = catalogName;
    }

    public Integer getCatalogOrder() {
        return catalogOrder;
    }

    public void setCatalogOrder(Integer catalogOrder) {
        this.catalogOrder = catalogOrder;
    }

    public String getCatalogCode() {
        return catalogCode;
    }

    public void setCatalogCode(String catalogCode) {
        this.catalogCode = catalogCode;
    }

    public Integer getDeleted() {
        return deleted;
    }

    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getModifyUser() {
        return modifyUser;
    }

    public void setModifyUser(String modifyUser) {
        this.modifyUser = modifyUser;
    }

    public String getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(String modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }



    public CatalogTreeModel() { }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CatalogTreeModel that = (CatalogTreeModel) o;
        return isLeaf == that.isLeaf &&
                Objects.equals(key, that.key) &&
                Objects.equals(value, that.value) &&
                Objects.equals(title, that.title) &&
                Objects.equals(id, that.id) &&
                Objects.equals(parentId, that.parentId) &&
                Objects.equals(catalogName, that.catalogName) &&
                Objects.equals(catalogOrder, that.catalogOrder) &&
                Objects.equals(catalogCode, that.catalogCode) &&
                Objects.equals(status, that.status) &&
                Objects.equals(children, that.children) &&
                Objects.equals(deleted, that.deleted) &&
                Objects.equals(createUser, that.createUser) &&
                Objects.equals(createTime, that.createTime) &&
                Objects.equals(modifyUser, that.modifyUser) &&
                Objects.equals(modifyTime, that.modifyTime) &&
                Objects.equals(sysCode, that.sysCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key, value, title, isLeaf, id, parentId, catalogName, catalogOrder, catalogCode, status, children, deleted, createUser, createTime, modifyUser, modifyTime, sysCode);
    }
}
