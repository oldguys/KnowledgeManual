package com.hrh.kmanual.modules.dto.form;/**
 * Created by Administrator on 2018/9/11 0011.
 */

import com.hrh.kmanual.commons.dto.Form;
import com.hrh.kmanual.modules.dao.entites.Knowledge;
import com.hrh.kmanual.modules.dao.entites.KnowledgeCollection;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description:
 * @Author: ren
 * @CreateTime: 2018-09-2018/9/11 0011 16:31
 */
public class KnowledgeCollectionAddForm implements Form<KnowledgeCollection> {

    @NotBlank(message = "文档集不能为空！")
    private String name;

    @NotBlank(message = "描述不能为空！")
    private String description;

    @NotNull(message = "类别不能为空！")
    private Integer type;

    private String knowledgeList;

    public String getKnowledgeList() {
        return knowledgeList;
    }

    public void setKnowledgeList(String knowledgeList) {
        this.knowledgeList = knowledgeList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Override
    public KnowledgeCollection trainToEntity() {

        KnowledgeCollection entity = new KnowledgeCollection();
        entity.setDescription(description);
        entity.setName(name);
        entity.setType(type);

        List<Knowledge> list = new ArrayList<>();
        if(StringUtils.isNotBlank(knowledgeList)){
            String[] arrays = knowledgeList.split(";");
            for(String item : arrays){
                if(StringUtils.isNotBlank(item)){
                    list.add(new Knowledge(Long.valueOf(item)));
                }
            }
        }
        entity.setKnowledgeList(list);

        return entity;
    }
}
