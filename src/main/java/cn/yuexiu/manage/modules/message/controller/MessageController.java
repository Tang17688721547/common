package cn.yuexiu.manage.modules.message.controller;

import cn.yuexiu.manage.common.api.vo.Result;
import cn.yuexiu.manage.common.enums.InspectStatusEnum;
import cn.yuexiu.manage.common.enums.MsgStatusEnum;
import cn.yuexiu.manage.common.enums.SourceEnum;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import io.swagger.annotations.*;
import cn.yuexiu.manage.config.group.Save;
import cn.yuexiu.manage.modules.message.entity.Message;
import cn.yuexiu.manage.modules.message.entity.MessageInfo;
import cn.yuexiu.manage.modules.message.service.MessageInfoService;
import cn.yuexiu.manage.modules.message.service.MessageService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;

@RestController
@RequestMapping("/msg")
@Api("消息控制器")
public class MessageController {

    @Resource
    private MessageService messageService;

    @Resource
    private MessageInfoService messageInfoService;

    @ApiOperation("新增消息")
    @PostMapping("/save")
    public Result save(@Validated(value = {Save.class}) @RequestBody Message message) {
        messageService.saveMessage(message);
        return Result.ok();
    }

    @PostMapping("/selectUser")
    @ApiModelProperty("指定审批人")
    public Result selectUser(@NotBlank(message = "username不能为null") @RequestParam(value = "username") String username,
                             @NotBlank(message = "批次id不能为null") @RequestParam("batchId") String batchId) {
        UpdateWrapper<Message> messageUpdateWrapper = new UpdateWrapper<>();
        messageUpdateWrapper.eq("batch_id", batchId);
        messageUpdateWrapper.set("inspect_user",username);
        messageService.update(messageUpdateWrapper);
        return Result.ok();
    }

    @ApiOperation(value = "同步K2状态")
    @PutMapping("/callback")
    public Result callback(@NotBlank(message = "批次id不能为空")@ApiParam("批次id")@RequestParam("batchId") String batchId,
                          @NotBlank(message = "审核状态不能为空") @ApiParam("K2状态码")@RequestParam("inspectStatus") String inspectStatus) {
        messageService.callback(batchId,inspectStatus);
        return Result.ok();
    }


    @ApiOperation(value = "消息审核")
    @PostMapping("/inspect")
    public Result inspect(@NotBlank(message = "消息id不能为空") @ApiParam("消息id") @RequestParam("msgId") String msgId,
                          @ApiParam("消息状态") @RequestParam("inspectStatus") String inspectStatus,
                          @ApiParam("审核描述") @RequestParam("reason") String reason){
        messageService.inspectMessage(msgId,inspectStatus,reason);
        return Result.ok();
    }

    @ApiOperation(value = "更改代办状态")
    @PutMapping("/update")
    public Result updateMessage(@NotBlank(message = "消息id不能为空") @ApiParam("消息id") @RequestParam("msgId") String msgId){
        messageService.updateMessage(msgId);
        return Result.ok();
    }


    @ApiOperation("分页查询消息")
    @GetMapping("/list")
    public Result list(@ApiParam("页码（默认第一页）") @RequestParam(value = "pageNum", defaultValue = "1") Long pageNum,
                       @ApiParam("行码（默认20）") @RequestParam(value = "pageSize", defaultValue = "20") Long pageSize,
                       @ApiParam("事项名称") @RequestParam(value = "param", defaultValue = "") String param,
                       @ApiParam("来源") @RequestParam(value = "source", defaultValue = "") String source,
                       @ApiParam("消息类型") @RequestParam(value = "msgType", defaultValue = "") String msgType,
                       @ApiParam("用户id") @RequestParam(value = "userId", defaultValue = "") String userId,
                       @ApiParam("消息状态 （1待办  2已办") @RequestParam(value = "msgStatus", defaultValue = "") String msgStatus,
                       @ApiParam("类型 1申请,2审批") @RequestParam(value = "type", defaultValue = "") String type,
                       @ApiParam("审核状态 0发起，1通过，2不通过") @RequestParam(value = "inspectStatus", defaultValue = "") String inspectStatus,
                       @ApiParam("开始日期") @RequestParam(value = "startTime", defaultValue = "") String startTime,
                       @ApiParam("结束日期") @RequestParam(value = "endTime", defaultValue = "") String endTime
    ) {
        return Result.ok(messageService.getList(pageNum, pageSize, param, userId, msgStatus, type,inspectStatus, startTime, endTime, source, msgType));
    }

    @ApiOperation("查询全部消息")
    @GetMapping("/all")
    public Result all(@ApiParam("事项名称") @RequestParam(value = "param", defaultValue = "") String param,
                      @ApiParam("来源") @RequestParam(value = "source", defaultValue = "") String source,
                      @ApiParam("日志类型") @RequestParam(value = "msgType", defaultValue = "") String msgType,
                      @ApiParam("用户id") @RequestParam(value = "userId", defaultValue = "") String userId,
                      @ApiParam("类型 1申请,2审批") @RequestParam(value = "type", defaultValue = "") String type,
                      @ApiParam("消息状态 （1待办  2已办") @RequestParam(value = "msgStatus", defaultValue = "") String msgStatus,
                      @ApiParam("审核状态 0发起，1通过，2不通过") @RequestParam(value = "inspectStatus", defaultValue = "") String inspectStatus,
                      @ApiParam("开始日期") @RequestParam(value = "startTime", defaultValue = "") String startTime,
                      @ApiParam("结束日期") @RequestParam(value = "endTime", defaultValue = "") String endTime) {
        return Result.ok(messageService.getAll(param, userId, msgStatus, type,inspectStatus, startTime, endTime, source, msgType));
    }


    @ApiOperation("首页统计")
    @GetMapping("/count")
    public Result getCount(@ApiParam("用户id") @RequestParam(value = "userId", defaultValue = "") String userId) {
        return Result.ok(messageService.getCount(userId));
    }

    @ApiOperation("获取来源列表")
    @GetMapping("/getSource")
    public Result getSource() {
        return Result.ok(SourceEnum.getSourceEnums());
    }

    @ApiOperation("获取状态列表")
    @GetMapping("/getMsgStatus")
    public Result getMsgStatus() {
        return Result.ok(MsgStatusEnum.getMsgStatusEnums());
    }

    @ApiOperation("获取审核状态列表")
    @GetMapping("/InspectStatus")
    public Result getInspectStatus() {
        return Result.ok(InspectStatusEnum.getInspectStatusEnums());
    }
}
