package cn.yuexiu.manage.modules.log.controller;


import cn.yuexiu.manage.common.api.vo.Result;
//import com.dfjinxin.commons.auth.utlis.OnlineUserUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import cn.yuexiu.manage.modules.log.entity.Catalog;
import cn.yuexiu.manage.modules.log.service.CatalogService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;

@RestController
@RequestMapping("/catalog")
@Api("日志目录控制器")
public class CatalogController {

    @Resource
    private CatalogService catalogService;

    @ApiOperation("新增目录")
    @PostMapping("/sava")
    public Result sava(@RequestBody Catalog catalog) {
        return Result.ok(catalogService.saveCatalog(catalog));
    }

    @ApiOperation("目录搜索")
    @GetMapping("/searhBy")
    public Result searhBy(@ApiParam(value = "目录名称",required = true) @NotBlank(message = "目录名称不能为空")@RequestParam("catalogName") String catalogName) {
        return Result.ok(catalogService.searhBy(catalogName));
    }
    /**
     * @return 查询全部目录信息树形结构
     */
    @ApiOperation("获取目录树信息")
    @GetMapping("/queryTreeList")
    public Result queryTreeList() {
        return Result.ok(catalogService.queryTreeList());
    }

    /**
     * @return 查询全部目录Id树形结构
     */
    @ApiOperation("获取目录树id")
    @GetMapping("/queryTreeIdList")
    public Result queryTreeIdList() {
        return Result.ok(catalogService.queryTreeIdList());
    }

    @ApiOperation("编辑目录")
    @PutMapping("/edit")
    public Result edit(@RequestBody Catalog catalog) {
        catalogService.updateCatalog(catalog);
        return Result.ok();
    }

    @ApiOperation("删除目录")
    @DeleteMapping("/del")
    public Result del(@ApiParam(value = "目录编码",required = true) @NotBlank(message = "目录编码不能为空") @RequestParam("catalogCode") String catalogCode) {
        catalogService.deleteCatalog(catalogCode);
        return Result.ok();
    }

    @ApiOperation("验证系统编码是否重复")
    @GetMapping("/checkBySysCode")
    public Result checkBySysCode(@ApiParam(value = "系统编码",required = true) @NotBlank(message = "系统编码不能为空")@RequestParam("sysCode") String sysCode) {
        catalogService.checkBySysCode(sysCode);
        return Result.ok();
    }

    @ApiOperation("获取目录信息")
    @GetMapping("/getByInfo")
    public Result getByInfo(@ApiParam(value = "目录id",required = true) @NotBlank(message = "目录id不能为空")@RequestParam("id") String id) {
        return Result.ok(catalogService.getById(id));
    }
}
