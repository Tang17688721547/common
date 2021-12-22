package cn.yuexiu.manage.modules.message.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import cn.yuexiu.manage.modules.message.entity.MessageInfo;

@Mapper
public interface MessageInfoMapper extends BaseMapper<MessageInfo> {

    Integer getApplyUserCount(String applyName);

    Integer getInspectUserCount(String inspectUser);
}
