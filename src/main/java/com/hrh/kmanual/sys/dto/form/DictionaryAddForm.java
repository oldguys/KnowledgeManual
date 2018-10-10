package com.hrh.kmanual.sys.dto.form;

import com.hrh.kmanual.commons.dto.Form;
import com.hrh.kmanual.sys.dao.entities.Dictionary;
import org.hibernate.validator.constraints.NotBlank;


/**
 * @author huangrenhao
 * @date 2018/8/19
 */
public class DictionaryAddForm implements Form<Dictionary>{

    @NotBlank(message = "标签名不能为空")
    private String name;

    @NotBlank(message = "类型不能为空")
    private String type;

    @NotBlank(message = "值类型不能为空")
    private String valueType;

    @NotBlank(message = "值不能为空")
    private String value;

    @NotBlank(message = "key不能为空")
    private String dictionaryKey;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValueType() {
        return valueType;
    }

    public void setValueType(String valueType) {
        this.valueType = valueType;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDictionaryKey() {
        return dictionaryKey;
    }

    public void setDictionaryKey(String dictionaryKey) {
        this.dictionaryKey = dictionaryKey;
    }

    @Override
    public Dictionary trainToEntity() {

        Dictionary dictionary = new Dictionary();
        dictionary.setName(name);
        dictionary.setValueType(valueType);
        dictionary.setValue(value);
        dictionary.setDictionaryKey(dictionaryKey);
        dictionary.setType(type);
        return dictionary;
    }
}
