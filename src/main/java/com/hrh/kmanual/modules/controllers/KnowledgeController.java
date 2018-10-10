package com.hrh.kmanual.modules.controllers;

import com.hrh.kmanual.commons.utils.HttpJsonUtils;
import com.hrh.kmanual.modules.dao.entites.Knowledge;
import com.hrh.kmanual.modules.dao.jpas.KnowledgeRepository;
import com.hrh.kmanual.modules.dto.form.KnowledgeAddForm;
import com.hrh.kmanual.modules.dto.form.KnowledgeUpdateForm;
import com.hrh.kmanual.modules.services.KnowledgeService;
import com.hrh.kmanual.modules.services.MenuService;
import com.hrh.kmanual.sys.config.DictionaryConfig;
import com.hrh.kmanual.sys.dao.jpas.DictionaryRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collections;
import java.util.List;

/**
 * @author huangrenhao
 * @date 2018/8/14
 */
@RestController
@RequestMapping("knowledge")
public class KnowledgeController {

    @Autowired
    private KnowledgeRepository knowledgeRepository;
    @Autowired
    private MenuService menuService;
    @Autowired
    private KnowledgeService knowledgeService;
    @Autowired
    private DictionaryRepository dictionaryRepository;

    @PostMapping("modify")
    public Object modify(@Valid KnowledgeUpdateForm form , BindingResult bindingResult){
        if (bindingResult.hasFieldErrors()) {
            return HttpJsonUtils.buildError(bindingResult.getFieldError().getDefaultMessage());
        }
        return HttpJsonUtils.buildSuccess("更新成功！", knowledgeService.modify(form));
    }

    @PostMapping("sort")
    public Object updateSort(@RequestParam("list") List<Long> list) {

        for (int i = 0; i < list.size(); i++) {
            knowledgeRepository.updateSort(list.get(i), i + 1);
        }

        return HttpJsonUtils.RESULT_SUCCESS;
    }

    @GetMapping("tags/info")
    public Object getTagsWithCount() {
        return knowledgeRepository.findCountByTags();
    }

    @GetMapping("{id}/info")
    public Object getOne(@PathVariable("id") Long id) {
        return knowledgeService.getInfo(id);
    }

    @GetMapping("tags")
    public Object getTags() {
        return dictionaryRepository.findByType(DictionaryConfig.DictionaryType.KNOWLEDGE_TAGS.getValue());
    }

    @PostMapping("addition")
    public Object addition(@Valid KnowledgeAddForm form, BindingResult bindingResult) {

        if (bindingResult.hasFieldErrors()) {
            return HttpJsonUtils.buildError(bindingResult.getFieldError().getDefaultMessage());
        }
        return HttpJsonUtils.buildSuccess("保存成功！", knowledgeService.persist(form));
    }


    @GetMapping("list")
    public Object getKnowledgeList(Long menuId, String tag, Integer status) {

        List<Knowledge> list = null;
        if (null != status) {
            return knowledgeRepository.findByStatus(status);
        }
        if (null != menuId) {
            list = knowledgeRepository.findAllByMenuIds(menuService.getMenuIdSet(menuId));
            Collections.sort(list);
            return list;
        }
        if (!StringUtils.isBlank(tag)) {
            return knowledgeRepository.findByTagsContaining(tag);
        }
        return knowledgeRepository.findAll();
    }

    @GetMapping("page")
    public Object page(String queryText, Integer offset, Integer size) {
        offset = offset == null ? 0 : (offset > 0 ? offset - 1 : offset);
        size = size == null ? 30 : size;
        queryText = StringUtils.isBlank(queryText) ? "" : queryText;

        return knowledgeRepository.findByStringTextPage(queryText, new PageRequest(offset, size));
    }
}
