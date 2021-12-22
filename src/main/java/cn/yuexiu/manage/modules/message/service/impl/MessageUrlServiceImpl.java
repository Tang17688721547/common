package cn.yuexiu.manage.modules.message.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.yuexiu.manage.modules.message.entity.MessageUrl;
import cn.yuexiu.manage.modules.message.mapper.MessageUrlMapper;
import cn.yuexiu.manage.modules.message.service.MessageUrlService;
import org.springframework.stereotype.Service;

@Service
public class MessageUrlServiceImpl extends ServiceImpl<MessageUrlMapper, MessageUrl> implements MessageUrlService {
}
