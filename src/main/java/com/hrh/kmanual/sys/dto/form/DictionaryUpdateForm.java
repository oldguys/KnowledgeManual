package com.hrh.kmanual.sys.dto.form;

import com.hrh.kmanual.commons.dto.Form;
import com.hrh.kmanual.sys.dao.entities.Dictionary;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.util.Date;


/**
 * @author huangrenhao
 * @date 2018/8/19
 */
public class DictionaryUpdateForm extends DictionaryAddForm{

    @NotNull(message = "ID不能为空")
    private Long id ;

    @NotNull(message = "状态不能为空")
    private Integer status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public Dictionary trainToEntity() {
        Dictionary dictionary = super.trainToEntity();
        dictionary.setId(id);
        dictionary.setStatus(status);
        return dictionary;
    }
}
