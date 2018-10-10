package com.hrh.kmanual.modules.controllers;/**
 * Created by Administrator on 2018/9/11 0011.
 */

import com.hrh.kmanual.commons.utils.HttpJsonUtils;
import com.hrh.kmanual.commons.utils.ReflectUtils;
import com.hrh.kmanual.modules.dao.entites.Knowledge;
import com.hrh.kmanual.modules.dao.entites.KnowledgeCollection;
import com.hrh.kmanual.modules.dao.jpas.KnowledgeCollectionRepository;
import com.hrh.kmanual.modules.dto.form.KnowledgeCollectionAddForm;
import com.hrh.kmanual.modules.dto.form.KnowledgeCollectionUpdateForm;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

/**
 * @Description:
 * @Author: ren
 * @CreateTime: 2018-09-2018/9/11 0011 10:58
 */
@RestController
@RequestMapping("KnowledgeCollection")
public class KnowledgeCollectionController {

    @Autowired
    private KnowledgeCollectionRepository knowledgeCollectionRepository;

    @PostMapping("update")
    public Object update(@Valid KnowledgeCollectionUpdateForm form, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return HttpJsonUtils.buildError(bindingResult.getFieldError().getDefaultMessage());
        }

        KnowledgeCollection entity = knowledgeCollectionRepository.findOne(form.getId());
        if (null == entity) {
            return HttpJsonUtils.buildError("无效ID");
        }

        ReflectUtils.updateFieldByClass(form.trainToEntity(), entity);
        knowledgeCollectionRepository.save(entity);

        return HttpJsonUtils.RESULT_SUCCESS;
    }


    @PostMapping("addition")
    public Object addition(@Valid KnowledgeCollectionAddForm form, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return HttpJsonUtils.buildError(bindingResult.getFieldError().getDefaultMessage());
        }

        KnowledgeCollection entity = form.trainToEntity();
        entity.setStatus(1);
        entity.setCreateTime(new Date());
        knowledgeCollectionRepository.save(entity);

        return entity.getId() != null ? HttpJsonUtils.RESULT_SUCCESS : HttpJsonUtils.RESULT_FAILURE;
    }

    @GetMapping("type")
    public Object getType() {
        KnowledgeCollection.KnowledgeCollectionType[] types = KnowledgeCollection.KnowledgeCollectionType.values();
        Map<Integer, String> map = new HashMap<>(types.length);
        for (KnowledgeCollection.KnowledgeCollectionType type : types) {
            map.put(type.getCode(), type.getValue());
        }
        return map;
    }

    @GetMapping("{id}/info")
    public Object getInfo(@PathVariable("id") Long id) {
        return knowledgeCollectionRepository.findOne(id);
    }

    @GetMapping("list")
    public Object getList(String name) {

        if (StringUtils.isNoneBlank(name)) {
            return knowledgeCollectionRepository.findByNameContaining(name, new Sort(Sort.Direction.DESC, "id"));
        }

        return knowledgeCollectionRepository.findAll(new Sort(Sort.Direction.DESC, "id"));
    }

}
