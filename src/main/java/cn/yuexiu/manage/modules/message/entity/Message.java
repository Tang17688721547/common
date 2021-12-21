package cn.yuexiu.manage.modules.message.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import cn.yuexiu.manage.config.group.Save;
import cn.yuexiu.manage.config.group.Update;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;
import java.io.Serializable;

@Data
@TableName("dm_message")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel("消息表")
public class Message implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键",hidden = true)
    @TableId
    @NotBlank(message = "id不能为null",groups = {Update.class})
    @Null(message = "id为自动生成,不可携带",groups = {Save.class})
    private String id;

    @NotBlank(message = "批次id不能为null",groups = {Save.class})
    @ApiModelProperty("批次id")
    private String batchId;

    @NotBlank(message = "事项名称不能为null",groups = {Save.class})
    @ApiModelProperty("事项名称")
    private String msgName;

    @NotBlank(message = "消息来源不能为null",groups = {Save.class})
    @ApiModelProperty("消息来源（1.服务中心,2数据安全）")
    private String msgSource;

    @NotBlank(message = "消息类型不能为null",groups = {Save.class})
    @ApiModelProperty("消息类型(0默认)")
    private String msgType;

    @ApiModelProperty("消息描述")
    private String  msgDescribe;

    @ApiModelProperty(value = "申请用户",required = false)
    @NotBlank(message = "申请用户不能为null",groups = {Save.class})
    private String applyUser;

    @ApiModelProperty("申请时间")
    private String applyTime;

  //  @NotBlank(message = "审核人不能为null",groups = {Save.class})
    @ApiModelProperty(value = "审核人",required = false)
    private String inspectUser;

    @ApiModelProperty(value = "参数")
    private String param;

    @ApiModelProperty("审核时间")
    private String inspectTime;

  //  @NotNull(message = "审核状态不能为null",groups = {Save.class})
    @ApiModelProperty("状态 0发起 1通过 2不通过")
    private String inspectStatus;

    @TableField(exist = false)
    @ApiModelProperty("审核状态名称")
    private String inspectStatusName;

 //   @NotBlank(message = "消息状态不能为空",groups = {Save.class})
    @ApiModelProperty("1待办  2已办")
    private String msgStatus;


    @ApiModelProperty("拒绝理由")
    private String reason;

    @ApiModelProperty(value = "逻辑删除 0正常  1删除",hidden = true)
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private Integer deleted;


    @ApiModelProperty(value ="创建人",hidden = true)
    private String createUser;

    @ApiModelProperty(value = "创建时间",hidden = true)
    @TableField(fill = FieldFill.INSERT)
    private String createTime;

    @ApiModelProperty(value = "修改人",hidden = true)
    private String modifyUser;

    @ApiModelProperty(value = "修改时间",hidden = true)
    @TableField(fill = FieldFill.UPDATE)
    private String modifyTime;


    @ApiModelProperty(value = "消息状态中文名",hidden = true)
    @TableField(exist = false)
    private String msgStatusLabel;


    @ApiModelProperty(value = "来源中文名",hidden = true)
    @TableField(exist = false)
    private String msgSourceLabel;

}
