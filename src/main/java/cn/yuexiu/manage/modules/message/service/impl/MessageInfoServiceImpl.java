package cn.yuexiu.manage.modules.message.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.yuexiu.manage.modules.message.entity.MessageInfo;
import cn.yuexiu.manage.modules.message.mapper.MessageInfoMapper;
import cn.yuexiu.manage.modules.message.service.MessageInfoService;
import org.springframework.stereotype.Service;

@Service
public class MessageInfoServiceImpl extends ServiceImpl<MessageInfoMapper, MessageInfo> implements MessageInfoService {
}
