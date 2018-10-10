package com.hrh.kmanual.modules.dto.info;

import com.hrh.kmanual.modules.dao.entites.Knowledge;
import com.hrh.kmanual.modules.dao.entites.KnowledgeCollection;
import com.hrh.kmanual.modules.dao.entites.Menu;

import java.util.List;

/**
 * @author huangrenhao
 * @date 2018/9/5
 */
public class KnowledgeInfo {

    private Knowledge knowledge;

    private List<Menu> parentMenuList;

    private KnowledgeCollection knowledgeCollection;

    public KnowledgeCollection getKnowledgeCollection() {
        return knowledgeCollection;
    }

    public void setKnowledgeCollection(KnowledgeCollection knowledgeCollection) {
        this.knowledgeCollection = knowledgeCollection;
    }

    public Knowledge getKnowledge() {
        return knowledge;
    }

    public void setKnowledge(Knowledge knowledge) {
        this.knowledge = knowledge;
    }

    public List<Menu> getParentMenuList() {
        return parentMenuList;
    }

    public void setParentMenuList(List<Menu> parentMenuList) {
        this.parentMenuList = parentMenuList;
    }
}
