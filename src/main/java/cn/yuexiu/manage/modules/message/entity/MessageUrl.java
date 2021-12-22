package cn.yuexiu.manage.modules.message.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import cn.yuexiu.manage.config.group.Save;
import cn.yuexiu.manage.config.group.Update;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.io.Serializable;

@Data
@TableName("dm_message_url")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel("消息Url表")
public class MessageUrl implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
    @TableId
    @NotNull(message = "id不能为null",groups = {Update.class})
    @Null(message = "id为自动生成,不可携带",groups = {Save.class})
    private String id;

    @ApiModelProperty("来源")
    private String source;

    @ApiModelProperty("类型")
    private String type;

    @ApiModelProperty("方式")
    private String method;

    @ApiModelProperty("接口地址")
    private String url;
}
