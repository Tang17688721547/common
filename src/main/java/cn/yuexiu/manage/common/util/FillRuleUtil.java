package cn.yuexiu.manage.common.util;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import cn.yuexiu.manage.modules.log.entity.Catalog;
import cn.yuexiu.manage.modules.log.service.CatalogService;

import java.util.ArrayList;
import java.util.List;


/**
 * 规则值自动生成工具类
 *
 * @author qinfeng
 * @举例： 自动生成订单号；自动生成当前日期
 */
@Slf4j
public class FillRuleUtil {

    /**
     * @param ruleCode ruleCode
     * @return
     */
    @SuppressWarnings("unchecked")
    public static String execute(String parentId) {
        CatalogService catalogService = (CatalogService) SpringContextUtils.getBean("catalogServiceImpl");

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
            catalogs = catalogService.list(query1);
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
            List<Catalog> parentList = catalogService.list(query);
            // 查询出父级目录
            Catalog catalog = catalogService.getById(parentId);
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
}
