package cn.yuexiu.manage.common.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum InspectStatusEnum {

    FQ("0", "发起"),
    TG("1", "通过"),
    BTG("2", "不通过");

    private InspectStatusEnum( String key , String label ){
        this.key = key ;
        this.label = label ;
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


    public static InspectStatusEnum getByCode(String key){
        for (InspectStatusEnum value : values()) {
            if (value.getKey().equals(key)) {
                return value;
            }
        }
        return null;

    }

    public static List<Map<String,String>> getInspectStatusEnums(){

        List<Map<String,String>> list = new ArrayList<>();

        for (InspectStatusEnum value : InspectStatusEnum.values()) {

            Map<String,String> map = new HashMap<>();
            map.put("key",value.key);
            map.put("label",value.label);
            list.add(map);
        }
        return list;
    }

}
