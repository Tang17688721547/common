package cn.yuexiu.manage.modules.log.service;

import com.baomidou.mybatisplus.extension.service.IService;
import cn.yuexiu.manage.modules.log.entity.Catalog;
import cn.yuexiu.manage.modules.log.vo.CatalogIdModel;
import cn.yuexiu.manage.modules.log.vo.CatalogTreeModel;

import java.util.List;

public interface CatalogService extends IService<Catalog>    {

    public Catalog saveCatalog(Catalog catalog);


    public List<CatalogTreeModel> searhBy(String catalogName);
    /**
     *
     * @return 查询全部目录信息树形结构
     */
    public List<CatalogTreeModel> queryTreeList();

    /**
     *
     * @return 查询全部目录Id树形结构
     */
    public List<CatalogIdModel> queryTreeIdList();


    public void updateCatalog(Catalog catalog);


    public void deleteCatalog(String catalogCode);


    void checkBySysCode(String sysCode);
}
