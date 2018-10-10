package com.hrh.kmanual.sys.controllers;

import com.hrh.kmanual.commons.utils.HttpJsonUtils;
import com.hrh.kmanual.sys.config.DictionaryConfig;
import com.hrh.kmanual.sys.dao.entities.Dictionary;
import com.hrh.kmanual.sys.dao.jpas.DictionaryRepository;
import com.hrh.kmanual.sys.dto.form.DictionaryAddForm;
import com.hrh.kmanual.sys.dto.form.DictionaryUpdateForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;

/**
 * @author huangrenhao
 * @date 2018/8/17
 */
@RequestMapping("dictionary")
@RestController
public class DictionaryController {

    @Autowired
    private DictionaryRepository dictionaryRepository;

    @PostMapping("update")
    public Object update(@Valid DictionaryUpdateForm form, BindingResult bindingResult){

        if (bindingResult.hasFieldErrors()) {
            return HttpJsonUtils.buildError(bindingResult.getFieldError().getDefaultMessage());
        }
        dictionaryRepository.save(form.trainToEntity());
        return HttpJsonUtils.RESULT_SUCCESS ;
    }

    @PostMapping("addition")
    public Object addition(@Valid DictionaryAddForm form, BindingResult bindingResult) {

        if (bindingResult.hasFieldErrors()) {
            return HttpJsonUtils.buildError(bindingResult.getFieldError().getDefaultMessage());
        }
        Dictionary dictionary = form.trainToEntity();
        dictionary.setCreateTime(new Date());
        dictionary.setStatus(1);
        dictionaryRepository.save(dictionary);

        return dictionary.getId() != null ? HttpJsonUtils.RESULT_SUCCESS : HttpJsonUtils.RESULT_FAILURE;
    }

    @GetMapping("knowledge/types")
    public Object getDictionaryTypes() {
        return DictionaryConfig.getKnowledgeTypeMap();
    }

    @GetMapping("{id}/info")
    public Object getOne(@PathVariable("id") Long id) {
        return dictionaryRepository.findOne(id);
    }

    @GetMapping("list")
    public Object getList() {
        return dictionaryRepository.findAll(new Sort(Sort.Direction.DESC, "createTime"));
    }
}
