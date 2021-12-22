package cn.yuexiu.manage.modules.message.service.impl;

import cn.yuexiu.manage.common.enums.InspectStatusEnum;
import cn.yuexiu.manage.common.enums.MsgStatusEnum;
import cn.yuexiu.manage.common.enums.SourceEnum;
import cn.yuexiu.manage.common.exception.JeecgBootException;
import cn.yuexiu.manage.common.util.DateUtils;
import cn.yuexiu.manage.common.util.HttpUtils;
import cn.yuexiu.manage.common.util.StringUtil;
import cn.yuexiu.manage.modules.message.entity.MessageUrl;
import cn.yuexiu.manage.modules.message.service.MessageUrlService;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dfjinxin.commons.auth.utlis.OnlineUserUtils;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import cn.yuexiu.manage.modules.log.service.LogsService;
import cn.yuexiu.manage.modules.message.entity.Message;
import cn.yuexiu.manage.modules.message.entity.MessageInfo;
import cn.yuexiu.manage.modules.message.mapper.MessageMapper;
import cn.yuexiu.manage.modules.message.service.MessageInfoService;
import cn.yuexiu.manage.modules.message.service.MessageService;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;

@Service
@Slf4j
public class MessageServiceImpl extends ServiceImpl<MessageMapper, Message> implements MessageService {

    @Resource
    private MessageMapper messageMapper;

    @Resource
    private LogsService logsService;

    @Resource
    private MessageInfoService messageInfoService;

    @Resource
    private MessageUrlService messageUrlService;

    @Override
    public Page<Message> getList(Long pageNum, Long pageSize, String param, String userId, String msgStatus, String type, String inspectStatus, String startTime, String endTime, String source, String msgType) {

        //获取用户id
        userId = "admin";

        // Long user = OnlineUserUtils.userId();

        // System.out.println(user);
        //获取管理员id列表
        List<String> userIds = new ArrayList<>();
        Page<Message> messagePage = new Page<>(pageNum, pageSize);
        QueryWrapper<Message> wrapper = new QueryWrapper();
        wrapper.eq(!StringUtil.isEmpty(msgType), "msg_type", msgType);
        wrapper.ge(!StringUtils.isEmpty(startTime), "create_time", startTime);
        wrapper.le(!StringUtils.isEmpty(endTime), "create_time", endTime);
        wrapper.eq(!StringUtils.isEmpty(source), "msg_source", source);
        wrapper.like(!StringUtils.isEmpty(param), "msg_name", param + "%");
        wrapper.eq(!StringUtil.isEmpty(msgStatus), "msg_status", msgStatus);
        wrapper.eq(!StringUtil.isEmpty(inspectStatus), "inspect_status", inspectStatus);
        wrapper.orderByDesc("create_time");

        if ("1".equals(type)) {
            if (!userIds.contains(userId)) {
                wrapper.eq("apply_user", userId);
            }
        } else if ("2".equals(type)) {
            if (!userIds.contains(userId)) {
                wrapper.eq("inspect_user", userId);
            }
        }
        page(messagePage, wrapper);

        for (Message record : messagePage.getRecords()) {
            if (!StringUtil.isEmpty(record.getMsgStatus())) {
                MsgStatusEnum msgStatusEnum = MsgStatusEnum.getByCode(record.getMsgStatus());
                if (!StringUtil.isEmpty(msgStatusEnum)) {
                    record.setMsgStatusLabel(msgStatusEnum.getLabel());
                }
            }
            if (!StringUtil.isEmpty(record.getMsgSource())) {
                SourceEnum sourceEnum = SourceEnum.getByCode(record.getMsgSource());
                if (!StringUtil.isEmpty(sourceEnum)) {
                    record.setMsgSourceLabel(sourceEnum.getLabel());
                }
            }

            if (!StringUtil.isEmpty(record.getInspectStatus())) {
                InspectStatusEnum inspectStatusEnum = InspectStatusEnum.getByCode(record.getInspectStatus());
                if (!StringUtil.isEmpty(inspectStatusEnum)) {

                    record.setInspectStatusName(inspectStatusEnum.getLabel());
                }
            }
        }
        return messagePage;
    }

    @Override
    public List<Message> getAll(String param, String userId, String msgStatus, String type, String inspectStatus, String startTime, String endTime, String source, String msgType) {
        //获取用户id
        userId = "admin";

        //获取管理员id列表

        List<Message> messages = new ArrayList<>();
        QueryWrapper<Message> wrapper = new QueryWrapper();
        wrapper.eq(!StringUtil.isEmpty(msgType), "msg_type", msgType);
        wrapper.ge(!StringUtils.isEmpty(startTime), "create_time", startTime);
        wrapper.le(!StringUtils.isEmpty(endTime), "create_time", endTime);
        wrapper.eq(!StringUtils.isEmpty(source), "msg_source", source);
        wrapper.like(!StringUtils.isEmpty(param), "msg_name", param + "%");
        wrapper.eq(!StringUtil.isEmpty(msgStatus), "msg_status", msgStatus);
        wrapper.eq(!StringUtil.isEmpty(inspectStatus), "inspect_status", inspectStatus);
        messages = list(wrapper);
        if (!StringUtil.isEmpty(messages)) {
            for (Message message : messages) {
                if (!StringUtil.isEmpty(message.getMsgStatus())) {
                    MsgStatusEnum msgStatusEnum = MsgStatusEnum.getByCode(message.getMsgStatus());
                    if (!StringUtil.isEmpty(msgStatusEnum)) {

                        message.setMsgStatusLabel(msgStatusEnum.getLabel());
                    }
                }
                if (!StringUtil.isEmpty(message.getMsgSource())) {
                    SourceEnum sourceEnum = SourceEnum.getByCode(message.getMsgSource());
                    if (!StringUtil.isEmpty(sourceEnum)) {

                        message.setMsgSourceLabel(sourceEnum.getLabel());
                    }
                }

                if (!StringUtil.isEmpty(message.getInspectStatus())) {
                    InspectStatusEnum inspectStatusEnum = InspectStatusEnum.getByCode(message.getInspectStatus());
                    if (!StringUtil.isEmpty(inspectStatusEnum)) {

                        message.setInspectStatusName(inspectStatusEnum.getLabel());
                    }
                }
            }
        }
        return messages;
    }

    @Override
    public Map<String, Integer> getCount(String userId) {

        Long user = OnlineUserUtils.userId();

        Map<String, Integer> map = new HashMap<>();
        if (userId == null) {
            //获取用户id
        }
        //申请
        QueryWrapper<Message> wrapper = new QueryWrapper();
        wrapper.eq("apply_user", userId);
        Integer applyCount = count(wrapper);
        map.put("apply", applyCount);

        //审批
        wrapper = new QueryWrapper();
        wrapper.eq("inspect_user", userId);
        Integer inspectCount = count(wrapper);
        map.put("inspect", inspectCount);

        //未做
        wrapper = new QueryWrapper();
        wrapper.eq("inspect_user", userId);
        wrapper.eq("msg_status", 1);
        Integer pendingCount = count(wrapper);
        map.put("pending", pendingCount);

        //已做
        wrapper = new QueryWrapper();
        wrapper.eq("inspect_user", userId);
        wrapper.eq("msg_status", 2);
        Integer doneCount = count(wrapper);
        map.put("already", doneCount);
        return map;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void inspect(String msgId) {
        HttpServletRequest request = ((ServletRequestAttributes) (RequestContextHolder.currentRequestAttributes())).getRequest();
        UpdateWrapper<Message> messageUpdateWrapper = new UpdateWrapper<>();
        messageUpdateWrapper.eq("id", msgId);
        messageUpdateWrapper.set("msg_status", 2);
        update(messageUpdateWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteById(String id) {
        removeById(id);
        UpdateWrapper<MessageInfo> messageInfoUpdateWrapper = new UpdateWrapper<>();
        messageInfoUpdateWrapper.eq("msg_id", id);
        messageInfoService.remove(messageInfoUpdateWrapper);

    }

    @Override
    public void saveMessage(Message message) {
        message.setApplyTime(DateUtils.now());
        message.setMsgStatus("1");
        message.setInspectStatus("0");
        message.setInspectUser("admin");
        save(message);

//        for (MessageInfo messageInfo : message.getMessageInfos()) {
//            messageInfo.setMsgId(message.getId());
//        }
//        messageInfoService.saveBatch(message.getMessageInfos());

    }

    @Override
    public void callback(String batchId, String inspectStatus) {
        UpdateWrapper<Message> messageUpdateWrapper = new UpdateWrapper<>();
        messageUpdateWrapper.eq("batch_id", batchId);
        messageUpdateWrapper.set("inspect_status", inspectStatus);
        messageUpdateWrapper.set("inspect_time", DateUtils.now());
        update(messageUpdateWrapper);
    }

    @Override
    public void dataInspect(String msgId, String inspectStatus, String reason) {
        UpdateWrapper<Message> messageUpdateWrapper = new UpdateWrapper<>();
        messageUpdateWrapper.eq("id", msgId);
        messageUpdateWrapper.set("inspect_time", DateUtils.now());
        messageUpdateWrapper.set("msg_status", 2);
        messageUpdateWrapper.set("inspect_status", inspectStatus);
        messageUpdateWrapper.set(!StringUtil.isEmpty(reason), "reason", reason);

        Message message = getById(msgId);
        QueryWrapper<MessageUrl> messageUrlQueryWrapper = new QueryWrapper<>();
        messageUrlQueryWrapper.eq("source", message.getMsgSource());
        messageUrlQueryWrapper.eq("type", message.getMsgType());
        messageUrlQueryWrapper.eq("api_type", 2);
        MessageUrl messageUrl = messageUrlService.getOne(messageUrlQueryWrapper);
        if (Objects.nonNull(messageUrl)) {
            String url = messageUrl.getUrl();
            url = url.replace("{id}", message.getBatchId()).replace("{status}", inspectStatus);
            if(StringUtils.isNotBlank(reason)){
                url = url +"&reason="+reason;
            }
            if (StringUtils.isNotBlank(message.getParam())) {
                url = url + "&" + message.getParam();
            }
            CloseableHttpResponse response = null;

            try {
                response = HttpUtils.doGet(url, "", messageUrl.getMethod(), new HashMap<>(), new HashMap<>());
                String result = HttpUtils.read(response.getEntity().getContent(), "utf-8");
                log.info("result == "+result.toString());
                if(!StringUtil.isEmpty(result)){

                    JSONObject object =  JSONObject.parseObject(result);
                    //object.getString("code")
                    Integer code = object.getInteger("code");
                    if(200!=code){
                        throw new JeecgBootException(object.getString("message"));
                    }
                }
            } catch (IOException e) {
                log.info(e.toString());
                throw new JeecgBootException("获取数据安全api响应失败", e);
            } catch (Exception e) {
                log.info(e.toString());
                throw new JeecgBootException("执行数据安全api失败", e);
            }
        }
        update(messageUpdateWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void inspectMessage(String msgId, String inspectStatus, String reason) {
       Message message = getById(msgId);
       if(StringUtil.isEmpty(message)){
           throw  new JeecgBootException("msg不存在   msgId:"+msgId);
       }
       if(SourceEnum.SJAN.getKey().equals(message.getMsgSource())){
           dataInspect(msgId,inspectStatus,reason);
       }else if(SourceEnum.FWZX.getKey().equals(message.getMsgSource())){
            if(message.getMsgType().equals("DS_DEFAULT")){
                inspect(msgId);
            }else{

            }
       }
    }

    @Override
    public void updateMessage(String msgId) {
        UpdateWrapper<Message> messageUpdateWrapper = new UpdateWrapper<>();
        messageUpdateWrapper.eq("id", msgId);
        messageUpdateWrapper.set("msg_status", 2);
        update(messageUpdateWrapper);
    }

//    @Override
//    public void createMessage(Message message) {
//        message.setApplyTime(DateUtils.now());
//        save(message);
//        for (MessageInfo messageInfo : message.getMessageInfos()) {
//            messageInfo.setMsgId(message.getId());
//        }
//        messageInfoService.saveBatch(message.getMessageInfos());
//    }
}
