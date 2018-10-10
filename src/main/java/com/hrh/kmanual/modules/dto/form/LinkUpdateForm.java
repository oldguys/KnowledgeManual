package com.hrh.kmanual.modules.dto.form;/**
 * Created by Administrator on 2018/9/9 0009.
 */

import com.hrh.kmanual.modules.dao.entites.Link;

import javax.validation.constraints.NotNull;

/**
 * @Description:
 * @Author: ren
 * @CreateTime: 2018-09-2018/9/9 0009 17:44
 */
public class LinkUpdateForm extends LinkAddForm {

    @NotNull(message = "ID不能为空！")
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public Link trainToEntity() {
        Link entity = super.trainToEntity();
        entity.setId(id);
        return entity;
    }
}
