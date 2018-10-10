package com.hrh.kmanual.modules.dto.form;/**
 * Created by Administrator on 2018/10/8 0008.
 */

import com.hrh.kmanual.modules.dao.entites.Knowledge;

import javax.validation.constraints.NotNull;

/**
 * @Description:
 * @Author: ren
 * @CreateTime: 2018-10-2018/10/8 0008 13:57
 */
public class KnowledgeUpdateForm extends KnowledgeAddForm {

    @NotNull(message = "ID不能为空")
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public Knowledge trainToEntity() {
        Knowledge knowledge = super.trainToEntity();
        knowledge.setId(id);
        return knowledge;
    }
}
