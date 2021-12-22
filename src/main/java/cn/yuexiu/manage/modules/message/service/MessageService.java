package cn.yuexiu.manage.modules.message.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import cn.yuexiu.manage.modules.message.entity.Message;

import java.util.List;
import java.util.Map;

public interface MessageService extends IService<Message> {

    Page<Message> getList(Long pageNum, Long pageSize, String param, String userId,  String msgStatus,String type,String inspectStatus,String startTime,String endTime,String source,String msgType);

    List<Message> getAll(String param, String userId, String msgStatus,String type,String inspectStatus,String startTime,String endTime,String source,String msgType);

    Map<String,Integer> getCount(String userId);

    void inspect(String msgId);

    void deleteById(String id);

    void saveMessage(Message message);

    void callback(String batchId, String inspectStatus);

    void dataInspect(String msgId, String inspectStatus,String reason);

    void inspectMessage(String msgId, String inspectStatus, String reason);

    void updateMessage(String msgId);

//    void createMessage(Message message);
}
