package com.hrh.kmanual.sys.dao.entities;

import com.hrh.kmanual.commons.dao.entities.BaseEntity;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * @author huangrenhao
 * @date 2018/8/15
 */
@Entity
public class Dictionary extends BaseEntity {

    /**
     *  字典类型
     */
    @NotBlank(message = "类型不能为空")
    private String type;

    /**
     *  值类型
     */
    @NotBlank(message = "值类型不能为空")
    private String valueType;

    @NotBlank(message = "值不能为空")
    @Column(columnDefinition = "TEXT")
    private String value;

    @NotBlank(message = "key不能为空")
    private String dictionaryKey;

    private Integer status;

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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getDictionaryKey() {
        return dictionaryKey;
    }

    public void setDictionaryKey(String dictionaryKey) {
        this.dictionaryKey = dictionaryKey;
    }
}
