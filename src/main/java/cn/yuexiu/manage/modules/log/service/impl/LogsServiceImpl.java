package cn.yuexiu.manage.modules.log.service.impl;

import cn.yuexiu.manage.common.enums.LogsTypeEnum;
import cn.yuexiu.manage.common.util.StringUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.yuexiu.manage.common.exception.JeecgBootException;
import cn.yuexiu.manage.modules.log.entity.Catalog;
import cn.yuexiu.manage.modules.log.entity.Logs;
import cn.yuexiu.manage.modules.log.mapper.LogsMapper;
import cn.yuexiu.manage.modules.log.service.CatalogService;
import cn.yuexiu.manage.modules.log.service.LogsService;
import cn.yuexiu.manage.modules.log.vo.LogsModel;
import com.dfjinxin.commons.auth.utlis.OnlineUserUtils;
import com.seaboxdata.auth.api.controller.IOauthRoleController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class LogsServiceImpl extends ServiceImpl<LogsMapper, Logs> implements LogsService {


    @Resource
    private LogsMapper logsMapper;

    @Resource
    private CatalogService catalogService;

    @Override
    public IPage<LogsModel> getList(Long pageNum, Long pageSize, String param, String startTime, String endTime, String logType, String userId, String catalogCode, String status) {
        Page<LogsModel> logsIPage = new Page<>(pageNum, pageSize);
        if (!StringUtil.isEmpty(catalogCode)) {
            catalogCode = catalogCode + "%";
        }
        if (!StringUtil.isEmpty(param)) {
            param = "%" + param + "%";

        }
        logsMapper.getList(logsIPage, logType, userId, status, catalogCode, startTime, endTime, param);
        for (LogsModel record : logsIPage.getRecords()) {
            if (!StringUtil.isEmpty(record.getLogType())) {
                LogsTypeEnum logsTypeEnum = LogsTypeEnum.getByCode(record.getLogType());
                record.setLogTypeName(logsTypeEnum.getLabel());
            }

        }
        return logsIPage;
    }

    @Override
    public List<LogsModel> getAll(String param, String startTime, String endTime, String logType, String userId, String catalogCode, String status) {
        Long user = OnlineUserUtils.userId();
        System.out.println(user);
        String userName = OnlineUserUtils.username(user);
        System.out.println(userName);
        List<LogsModel> logsModels = logsMapper.getAll(logType, userId, status, catalogCode, startTime, endTime, param);
        if (!StringUtil.isEmpty(logsModels)) {
            for (LogsModel record : logsModels) {
                if (!StringUtil.isEmpty(record.getLogType())) {
                    LogsTypeEnum logsTypeEnum = LogsTypeEnum.getByCode(record.getLogType());
                    record.setLogTypeName(logsTypeEnum.getLabel());
                }

            }
        }


        return logsModels;
    }


    @Override
    public void saveLogs(Logs logs) {
        QueryWrapper<Catalog> catalogQueryWrapper = new QueryWrapper<>();
        catalogQueryWrapper.eq("sys_code", logs.getSysCode());
        Catalog catalog = catalogService.getOne(catalogQueryWrapper);
        if (StringUtil.isEmpty(catalog)) throw new JeecgBootException("系统编码不存在");
        logs.setCatalogCode(catalog.getCatalogCode());
        save(logs);
    }
}
