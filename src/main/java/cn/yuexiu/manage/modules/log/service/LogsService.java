package cn.yuexiu.manage.modules.log.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import cn.yuexiu.manage.modules.log.entity.Logs;
import cn.yuexiu.manage.modules.log.vo.LogsModel;

import java.util.List;

public interface LogsService  extends IService<Logs> {
    IPage<LogsModel> getList(Long pageNum, Long pageSize, String param, String startTime, String endTime, String logType, String userId, String catalogCode, String status);

    List<LogsModel> getAll(String param, String startTime, String endTime,String logType,String userId,String catalogCode,String status);

    void saveLogs(Logs logs);
}
