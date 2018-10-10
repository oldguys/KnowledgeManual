package com.hrh.kmanual.modules.services;

import com.hrh.kmanual.commons.utils.ReflectUtils;
import com.hrh.kmanual.modules.dao.entites.Attachment;
import com.hrh.kmanual.modules.dao.entites.Knowledge;
import com.hrh.kmanual.modules.dao.entites.KnowledgeCollection;
import com.hrh.kmanual.modules.dao.entites.Menu;
import com.hrh.kmanual.modules.dao.jpas.AttachmentRepository;
import com.hrh.kmanual.modules.dao.jpas.KnowledgeCollectionRepository;
import com.hrh.kmanual.modules.dao.jpas.KnowledgeRepository;
import com.hrh.kmanual.modules.dto.form.KnowledgeAddForm;
import com.hrh.kmanual.modules.dto.form.KnowledgeUpdateForm;
import com.hrh.kmanual.modules.dto.info.KnowledgeInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * @author huangrenhao
 * @date 2018/8/31
 */
@Service
public class KnowledgeService {

    @Autowired
    private KnowledgeRepository knowledgeRepository;
    @Autowired
    private AttachmentRepository attachmentRepository;
    @Autowired
    private MenuService menuService;
    @Autowired
    private KnowledgeCollectionRepository knowledgeCollectionRepository;

    public KnowledgeInfo getInfo(Long id) {

        KnowledgeInfo info = new KnowledgeInfo();
        Knowledge knowledge = knowledgeRepository.findOne(id);
        info.setKnowledge(knowledge);
        info.setParentMenuList(menuService.getParentMenuList(knowledge.getMenu()));

        KnowledgeCollection knowledgeCollection = knowledgeCollectionRepository.findByKnowledgeId(id);
        if (null != knowledgeCollection) {
            List<Knowledge> list = knowledgeCollection.getKnowledgeList();
            Collections.sort(list);
            knowledgeCollection.setKnowledgeList(list);
            info.setKnowledgeCollection(knowledgeCollection);
        }

        return info;
    }

    public Long persist(KnowledgeAddForm form) {
        Knowledge entity = form.trainToEntity();
        entity.setCreateTime(new Date());
        entity.setStatus(1);
        entity.setMenu(new Menu(form.getMenuId()));
        entity.setSortOrder(1);

        // 配置文档集合
        if (null != form.getKnowledgeCollection()) {
            KnowledgeCollection knowledgeCollection = knowledgeCollectionRepository.findOne(form.getKnowledgeCollection());
            if (null != knowledgeCollection) {
                knowledgeCollection.getKnowledgeList().add(entity);
            }
        }
        updateAttachments(form, entity);


        knowledgeRepository.save(entity);
        return entity.getId();
    }

    /**
     *  更新关联附件状态
     * @param form
     * @param entity
     */
    private void updateAttachments(KnowledgeAddForm form, Knowledge entity) {
        // 更新 附件关联
        if (!form.getAttachmentIds().isEmpty()) {
            List<Attachment> attachments = attachmentRepository.findByAsoKey(form.getKey());
            attachments.forEach(attachment -> {
                attachment.setKnowledge(entity);
                attachment.setStatus(1);
            });
        }
    }

    public Long modify(KnowledgeUpdateForm form) {
        Knowledge temp = form.trainToEntity();
        Knowledge entity = knowledgeRepository.findOne(temp.getId());
        ReflectUtils.updateFieldByClass(temp, entity);

        // 配置文档集合
        if (null != form.getKnowledgeCollection()) {
            KnowledgeCollection knowledgeCollection = knowledgeCollectionRepository.findOne(form.getKnowledgeCollection());
            if (null != knowledgeCollection) {
                knowledgeCollection.getKnowledgeList().add(entity);
            }
        }

        // 更新 附件关联
        updateAttachments(form, entity);

        knowledgeRepository.save(entity);
        return entity.getId();
    }
}
