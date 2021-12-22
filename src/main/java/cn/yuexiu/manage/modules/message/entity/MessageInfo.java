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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.io.Serializable;

@Data
@TableName("dm_message_info")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel("消息详情表")
public class MessageInfo implements Serializable {

    @ApiModelProperty("主键")
    @TableId
    @NotNull(message = "id不能为null",groups = {Update.class})
    @Null(message = "id为自动生成,不可携带",groups = {Save.class})
    private String id;

    @NotNull(message = "批次id不能为null",groups = {Save.class})
    @ApiModelProperty("批次id")
    private String msgId;

    @NotNull(message = "操作id不能为null",groups = {Save.class})
    @ApiModelProperty("操作id")
    private String actionId;

    @NotNull(message = "消息状态不能为null",groups = {Save.class})
    @ApiModelProperty("状态 0通过 1不通过")
    private String status;

    @ApiModelProperty("消息内容")
    private String msgBody;

    @NotBlank(message = "实体id不能为空",groups = {Save.class})
    @ApiModelProperty("实体id")
    private String entityId;

    @NotBlank(message = "用户id不能为空",groups = {Save.class})
    @ApiModelProperty("用户id")
    private String userId;

    @ApiModelProperty("逻辑删除 0正常  1删除")
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private Integer deleted;

    @ApiModelProperty("创建人")
    private String createUser;

    @ApiModelProperty("创建时间")
    @TableField(fill = FieldFill.INSERT)
    private String createTime;

    @ApiModelProperty("修改人")
    private String modifyUser;

    @ApiModelProperty("修改时间")
    @TableField(fill = FieldFill.UPDATE)
    private String modifyTime;
}
