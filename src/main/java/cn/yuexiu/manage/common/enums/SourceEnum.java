package cn.yuexiu.manage.common.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author tangjian
 */

public enum SourceEnum {

    FWZX("1", "服务中心"),
    SJAN("2", "数据安全");

    private SourceEnum( String key , String label ){
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


    public static SourceEnum getByCode(String key){
        for (SourceEnum value : values()) {
            if (value.getKey().equals(key)) {
                return value;
            }
        }
        return null;

    }

    public static List<Map<String,String>> getSourceEnums(){

        List<Map<String,String>> list = new ArrayList<>();

        for (SourceEnum value : SourceEnum.values()) {
            Map<String,String> map = new HashMap<>();
            map.put("key",value.key);
            map.put("label",value.label);
            list.add(map);
        }
        return list;
    }

}
