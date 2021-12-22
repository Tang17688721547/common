package cn.yuexiu.manage.common.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author tangjian
 */

public enum MsgStatusEnum {

    DB("1", "待办"),
    YB("2", "已办");


    private MsgStatusEnum( String key , String label ){
        this.key = key ;
        this.label = label;

//        Map<String,String> msgStatus = new HashMap<>();
//        msgStatus.put("key",key);
//        msgStatus.put("label",label);
//        msgStatusEnums.add(msgStatus);
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    /**
     * key 标识
     */
    private String key;

    /**
     * label 中文名
     */
    private String label;


    public static MsgStatusEnum getByCode(String key){
        for (MsgStatusEnum value : values()) {
            if (value.getKey().equals(key)) {
                return value;
            }
        }
        return null;

    }

    public static List<Map<String,String>> getMsgStatusEnums(){

        List<Map<String,String>> list = new ArrayList<>();

        for (MsgStatusEnum value : MsgStatusEnum.values()) {

            Map<String,String> map = new HashMap<>();
            map.put("key",value.key);
            map.put("label",value.label);
            list.add(map);
        }
        return list;
    }

}
