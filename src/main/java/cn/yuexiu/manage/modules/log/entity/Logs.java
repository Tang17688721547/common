package cn.yuexiu.manage.modules.log.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
@TableName("dm_logs")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel("日志表")
public class Logs implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
    @TableId
    @NotBlank(message = "id不能为null",groups = {Update.class})
    @Null(message = "id为自动生成,不可携带",groups = {Save.class})
    private String id;

    @ApiModelProperty("日志id")
    @NotBlank(message = "日志id不能为null",groups = {Save.class})
    private String logId;

    @ApiModelProperty("日志调用状态 1成功 2失败")
    private String status;

    @ApiModelProperty("日志描述")
    private String logDescribe;

    @ApiModelProperty("系统编码")
    private String sysCode;

    @ApiModelProperty("参数")
    private String param;

    @ApiModelProperty("用户id")
    private String userId;




    @ApiModelProperty("目录编码")
    private String catalogCode;

    @ApiModelProperty("日志类型(1.审核日志、2.操作日志、3.服务日志)")
    //@NotBlank(message = "日志类型不能为null",groups = {Save.class})
    private String logType;

   // @NotBlank(message = "服务中文名不能为null",groups = {Save.class})
    @ApiModelProperty("服务中文名")
    private String  serverCn;

  //  @NotBlank(message = "服务英文名不能为null",groups = {Save.class})
    @ApiModelProperty("服务英文名")
    private String serverEn;

   // @NotBlank(message = "服务api不能为null",groups = {Save.class})
    @ApiModelProperty("服务api")
    private String serverApi;



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
