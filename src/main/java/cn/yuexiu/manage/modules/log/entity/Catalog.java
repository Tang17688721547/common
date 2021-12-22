package cn.yuexiu.manage.modules.log.entity;


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
@TableName("dm_catalog")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel("目录表")
public class Catalog implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
    @TableId
    @NotBlank(message = "id不能为null",groups = {Update.class})
    @Null(message = "id为自动生成,不可携带",groups = {Save.class})
    private String id;

    @ApiModelProperty(value ="父级id")
    private String parentId;

    @ApiModelProperty(value ="目录名")
    private String catalogName;

    @ApiModelProperty(value ="系统编码")
    private String sysCode;

    @ApiModelProperty(value ="目录编码")
    private String catalogCode;

    @ApiModelProperty(value ="目录序号")
    private Integer catalogOrder;

    @ApiModelProperty(value = "Api接口")
    private String infoUrl;

    @ApiModelProperty(value ="状态（1启用 0禁用）")
    private String status;

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
