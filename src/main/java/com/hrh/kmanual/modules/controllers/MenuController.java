package com.hrh.kmanual.modules.controllers;

import com.hrh.kmanual.commons.utils.HttpJsonUtils;
import com.hrh.kmanual.modules.dao.entites.Knowledge;
import com.hrh.kmanual.modules.dao.entites.Menu;
import com.hrh.kmanual.modules.dao.jpas.MenuRepository;
import com.hrh.kmanual.modules.dto.form.MenuAddForm;
import com.hrh.kmanual.modules.dto.form.MenuUpdateForm;
import com.hrh.kmanual.modules.services.MenuService;
import com.hrh.kmanual.sys.config.DictionaryConfig;
import com.hrh.kmanual.sys.dao.jpas.DictionaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collections;
import java.util.List;

/**
 * @author huangrenhao
 * @date 2018/8/8
 */
@RestController
@RequestMapping("menus")
public class MenuController {

    @Autowired
    private MenuRepository menuRepository;
    @Autowired
    private MenuService menuService;
    @Autowired
    private DictionaryRepository dictionaryRepository;

    @PostMapping("update")
    public Object update(@Valid MenuUpdateForm form, BindingResult bindingResult) {
        if (bindingResult.hasFieldErrors()) {
            return HttpJsonUtils.buildError(bindingResult.getFieldError().getDefaultMessage());
        }
        menuService.update(form);
        return HttpJsonUtils.RESULT_SUCCESS;
    }

    @PostMapping("addition")
    public Object persist(@Valid MenuAddForm form, BindingResult bindingResult) {

        if (bindingResult.hasFieldErrors()) {
            return HttpJsonUtils.buildError(bindingResult.getFieldError().getDefaultMessage());
        }
        menuService.persist(form);
        return HttpJsonUtils.RESULT_SUCCESS;
    }

    @GetMapping("types")
    public Object getMenuTypes() {
        return dictionaryRepository.findByType(DictionaryConfig.DictionaryType.MENU_TYPES.getValue());
    }

    @GetMapping("{id}/info")
    public Object getOne(@PathVariable("id") Long id) {
        return menuRepository.findOne(id);
    }

    @GetMapping("bootstrap/list")
    public Object getBootstrapMenus(Boolean selectLeaf) {
        if (null != selectLeaf) {
            return menuService.bootstrapMenus(selectLeaf);
        }
        return menuService.bootstrapMenus();
    }

    @GetMapping("list")
    public Object getMenus() {
        List<Menu> list = menuService.getMenus();
        menuService.setKnowLedgeCount(list);
        return list;
    }

    @GetMapping("all")
    public Object getList(Long parentId) {

        if(null != parentId){
            return  menuService.getMenuListByParentId(parentId);
        }
        List menuList = menuRepository.findAll();
        menuService.setKnowLedgeCount(menuList);
        return menuList;
    }


}
