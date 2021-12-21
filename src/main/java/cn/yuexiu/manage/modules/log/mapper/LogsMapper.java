package cn.yuexiu.manage.modules.log.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import cn.yuexiu.manage.modules.log.entity.Logs;
import cn.yuexiu.manage.modules.log.vo.LogsModel;

import java.util.List;

@Mapper
public interface LogsMapper extends BaseMapper<Logs> {
    Page<LogsModel> getList(Page<LogsModel> logsIPage, String logType, String userId, String status, String catalogCode, String startTime, String endTime, String param);

    List<LogsModel> getAll(String logType, String userId, String status, String catalogCode, String startTime, String endTime, String param);
}
