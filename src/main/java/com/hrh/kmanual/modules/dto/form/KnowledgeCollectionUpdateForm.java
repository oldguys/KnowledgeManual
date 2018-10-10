package com.hrh.kmanual.modules.dto.form;/**
 * Created by Administrator on 2018/9/12 0012.
 */

import com.hrh.kmanual.modules.dao.entites.KnowledgeCollection;

import javax.validation.constraints.NotNull;

/**
 * @Description:
 * @Author: ren
 * @CreateTime: 2018-09-2018/9/12 0012 11:18
 */
public class KnowledgeCollectionUpdateForm extends KnowledgeCollectionAddForm {

    @NotNull(message = "ID不能为空！")
    private Long id;

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
    public KnowledgeCollection trainToEntity() {
        KnowledgeCollection entity = super.trainToEntity();
        entity.setId(id);
        entity.setStatus(status);
        return entity;
    }
}
