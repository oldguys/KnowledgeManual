package com.hrh.kmanual.modules.dto.form;

import com.hrh.kmanual.modules.dao.entites.Menu;

import javax.validation.constraints.NotNull;

/**
 * @author huangrenhao
 * @date 2018/8/28
 */
public class MenuUpdateForm extends MenuAddForm {

    @NotNull(message = "ID不能为空！")
    private Long id;

    @NotNull(message = "状态不能为空！")
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
    public Menu trainToEntity() {
        Menu entity = super.trainToEntity();
        entity.setId(id);
        entity.setStatus(status);
        return entity;
    }
}
