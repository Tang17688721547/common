package cn.yuexiu.manage.modules.message.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.yuexiu.manage.modules.message.entity.NoticeMessage;
import cn.yuexiu.manage.modules.message.mapper.NoticeMessageMapper;
import cn.yuexiu.manage.modules.message.service.NoticeMessageService;
import org.springframework.stereotype.Service;

@Service
public class NoticeMessageServiceImpl extends ServiceImpl<NoticeMessageMapper, NoticeMessage> implements NoticeMessageService {
}
