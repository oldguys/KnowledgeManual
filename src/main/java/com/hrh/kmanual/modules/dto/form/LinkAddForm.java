package com.hrh.kmanual.modules.dto.form;/**
 * Created by Administrator on 2018/9/9 0009.
 */

import com.hrh.kmanual.commons.dto.Form;
import com.hrh.kmanual.modules.dao.entites.Link;
import org.hibernate.validator.constraints.NotBlank;

/**
 * @Description:
 * @Author: ren
 * @CreateTime: 2018-09-2018/9/9 0009 17:15
 */
public class LinkAddForm implements Form<Link> {

    @NotBlank(message = "名称不能为空！")
    private String name;

    @NotBlank(message = "标识不能为空！")
    private String tags;

    @NotBlank(message = "链接不能为空！")
    private String href;

    private String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public Link trainToEntity() {

        Link entity = new Link();
        entity.setName(name);
        entity.setTags(tags);
        entity.setHref(href);
        entity.setDescription(description);

        return entity;
    }
}
