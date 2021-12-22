package cn.yuexiu.manage.modules.log.controller;

import cn.yuexiu.manage.common.api.vo.Result;
import cn.yuexiu.manage.common.enums.LogsTypeEnum;
import cn.yuexiu.manage.common.enums.MsgStatusEnum;
import com.dfjinxin.commons.auth.utlis.OnlineUserUtils;
import com.seaboxdata.auth.api.controller.IOauthRoleController;
import com.seaboxdata.auth.api.enums.RoleEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import cn.yuexiu.manage.config.group.Save;
import cn.yuexiu.manage.modules.log.entity.Logs;
import cn.yuexiu.manage.modules.log.service.LogsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/logs")
@Api("日志控制器")
public class LogsController {
    @Resource
    private LogsService logsService;

    @Autowired
    private IOauthRoleController iOauthRoleController;

    @ApiOperation("保存日志")
    @PostMapping("/save")
    public Result save(@Validated(value = {Save.class}) @RequestBody Logs logs, HttpServletRequest servletRequest){
        logsService.saveLogs(logs);
        return Result.ok();
    }

    @ApiOperation("分页查询日志")
    @GetMapping("/list")
    public Result list(@ApiParam("页码 默认第一页") @RequestParam(value = "pageNum",defaultValue = "1")Long pageNum,
                       @ApiParam("行码 默认20行")@RequestParam(value = "pageSize",defaultValue = "20") Long pageSize,
                       @ApiParam("日志类型")@RequestParam(value = "logType",defaultValue = "") String logType,
                       @ApiParam("服务中/英文/api查询")@RequestParam(value = "param",defaultValue = "")String param,
                       @ApiParam("用户id")@RequestParam(value = "userId",defaultValue = "")String userId,
                       @ApiParam("目录编码")@RequestParam(value = "catalogCode",defaultValue = "")String catalogCode,
                       @ApiParam("日志状态 1成功 2失败")@RequestParam(value = "status",defaultValue = "")String status,
                       @ApiParam("开始时间")@RequestParam(value = "startTime",defaultValue = "")String startTime,
                       @ApiParam("结束时间")@RequestParam(value = "endTime",defaultValue = "")String endTime
                       ){
        return Result.ok(logsService.getList(pageNum,pageSize,param,startTime,endTime,logType,userId,catalogCode,status));
    }

    @ApiOperation("查询日志")
    @GetMapping("/all")
    public Result all(
                      @ApiParam("服务中/英文/api查询")@RequestParam(value = "param",defaultValue = "")String param,
                      @ApiParam("开始时间")@RequestParam(value = "startTime",defaultValue = "")String startTime,
                      @ApiParam("结束时间")@RequestParam(value = "endTime",defaultValue = "")String endTime,
                      @ApiParam("目录编码")@RequestParam(value = "catalogCode",defaultValue = "")String catalogCode,
                      @ApiParam("日志状态 1成功 2失败")@RequestParam(value = "status",defaultValue = "")String status,
                      @ApiParam("用户id")@RequestParam(value = "userId",defaultValue = "")String userId,
                      @ApiParam("日志类型")@RequestParam(value = "logType",defaultValue = "") String logType){
        return Result.ok (logsService.getAll(param,startTime,endTime,logType,userId,catalogCode,status));
    }


    @ApiOperation("获取日志类型列表")
    @GetMapping("/getLogTypes")
    public Result getLogTypes(){
        return Result.ok (LogsTypeEnum.getLogsTypeEnums());
    }


    @ApiOperation("test")
    @PostMapping("/test")
    public Result getTest(){
        return Result.ok();
       //
      //  return Result.ok(iOauthRoleController.decideUserRole(OnlineUserUtils.userId(), RoleEnum.SYSMANAGER));
    }

}
