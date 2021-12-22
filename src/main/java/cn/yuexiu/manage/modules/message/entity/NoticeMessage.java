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
@TableName("dm_notice_message")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel("通知消息表")
public class NoticeMessage implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键",hidden = true)
    @TableId
    @NotBlank(message = "id不能为null",groups = {Update.class})
    @Null(message = "id为自动生成,不可携带",groups = {Save.class})
    private String id;

    @ApiModelProperty(value = "消息id")
    private String msgId;

    @ApiModelProperty(value = "状态 1未读  2已读")
    private String status;

    @ApiModelProperty(value = "1通过  2不通过")
    private String inspectStatus;

    @ApiModelProperty(value = "通知用户")
    private String userId;

    @ApiModelProperty(value = "通知文本")
    private String text;

    @ApiModelProperty(value = "通知类型")
    private String type;

    @ApiModelProperty(value = "通知账号")
    private String account;

    @ApiModelProperty(value = "通知状态 1成功  2失败")
    private String noticeStatus;

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
}
