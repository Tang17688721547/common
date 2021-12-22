package cn.yuexiu.manage.modules.log.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class LogsModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
    private String id;

    @ApiModelProperty("日志id")
    private String logId;

    @ApiModelProperty("日志调用状态 1成功 2失败")
    private String status;

    @ApiModelProperty("日志类型名称")
    private String logTypeName;

    @ApiModelProperty("日志描述")
    private String logDescribe;

    @ApiModelProperty("目录编码")
    private String catalogCode;

    @ApiModelProperty("系统编码")
    private String sysCode;

    @ApiModelProperty("日志类型")
    private String logType;

    @ApiModelProperty("服务中文名")
    private String  serverCn;

    @ApiModelProperty("服务英文名")
    private String serverEn;

    @ApiModelProperty("服务api")
    private String serverApi;

    @ApiModelProperty("目录名称")
    private String catalogName;

    @ApiModelProperty("用户id")
    private String userId;

    @ApiModelProperty("创建人")
    private String createUser;

    @ApiModelProperty("创建时间")
    private String createTime;

    @ApiModelProperty("修改人")
    private String modifyUser;

    @ApiModelProperty("修改时间")
    private String modifyTime;
}
