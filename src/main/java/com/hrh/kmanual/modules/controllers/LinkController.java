package com.hrh.kmanual.modules.controllers;

import com.hrh.kmanual.commons.utils.HttpJsonUtils;
import com.hrh.kmanual.commons.utils.ReflectUtils;
import com.hrh.kmanual.modules.dao.entites.Link;
import com.hrh.kmanual.modules.dao.jpas.LinkRepository;
import com.hrh.kmanual.modules.dto.form.LinkAddForm;
import com.hrh.kmanual.modules.dto.form.LinkUpdateForm;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;

/**
 * @author huangrenhao
 * @date 2018/9/7
 */
@RestController
@RequestMapping("link")
public class LinkController {

    @Autowired
    private LinkRepository linkRepository;

    @GetMapping("tags/info")
    public Object getTagsWithCount() {
        return linkRepository.findCountByTags();
    }

    @GetMapping("{id}/info")
    public Object getOne(@PathVariable("id") Long id) {
        return linkRepository.findOne(id);
    }

    @PostMapping("/{id}/{status}")
    public Object updateStatus(@PathVariable("id") Long id, @PathVariable("status") Integer status) {

        Link entity = linkRepository.findOne(id);
        if (null == entity) {
            return HttpJsonUtils.buildError("无效ID");
        }
        entity.setStatus(status);
        linkRepository.save(entity);
        return HttpJsonUtils.SUCCESS;
    }

    @PostMapping("update")
    public Object update(@Valid LinkUpdateForm form, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            HttpJsonUtils.buildError(bindingResult.getFieldError().getDefaultMessage());
        }

        Link entity = linkRepository.findOne(form.getId());
        if (null == entity) {
            return HttpJsonUtils.buildError("无效ID");
        }

        ReflectUtils.updateFieldByClass(form.trainToEntity(), entity);
        linkRepository.save(entity);

        return HttpJsonUtils.SUCCESS;
    }

    @PostMapping("addition")
    public Object addition(@Valid LinkAddForm form, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            HttpJsonUtils.buildError(bindingResult.getFieldError().getDefaultMessage());
        }
        Link link = form.trainToEntity();
        link.setStatus(1);
        link.setCreateTime(new Date());
        linkRepository.save(link);

        return link.getId() == null ? HttpJsonUtils.RESULT_FAILURE : HttpJsonUtils.RESULT_SUCCESS;
    }

    @GetMapping("list")
    public Object getList(String tag) {

        if (!StringUtils.isBlank(tag)) {
            return linkRepository.findByTagsContaining(tag, new Sort(Sort.Direction.DESC, "id"));
        }

        return linkRepository.findAll(new Sort(Sort.Direction.DESC, "id"));
    }
}
