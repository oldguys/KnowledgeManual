package com.hrh.kmanual.modules.dto.form;

import com.hrh.kmanual.commons.dto.Form;
import com.hrh.kmanual.modules.dao.entites.Menu;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * @author huangrenhao
 * @date 2018/8/24
 */
public class MenuAddForm implements Form<Menu> {

    @NotBlank(message = "目录标题不能为空")
    private String name;

    @NotNull(message = "父级目录ID不能为空")
    private Long afterMenuId;

    @NotBlank(message = "类别不能为空")
    private String type;

    @NotNull(message = "父级ID不能为空")
    private Long parentId;

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

    public Long getAfterMenuId() {
        return afterMenuId;
    }

    public void setAfterMenuId(Long afterMenuId) {
        this.afterMenuId = afterMenuId;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    @Override
    public Menu trainToEntity() {

        Menu menu = new Menu();
        menu.setName(name);
        menu.setType(type);
        menu.setParentId(parentId);

        return menu;
    }
}
