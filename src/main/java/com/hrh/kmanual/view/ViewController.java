package com.hrh.kmanual.view;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author huangrenhao
 * @date 2018/8/9
 */
@Controller
@RequestMapping("view")
public class ViewController {

    @RequestMapping("{id}/preview")
    public String preview(@PathVariable("id") Long id, Model model) {
        model.addAttribute("attachment", id);
        return "km/sys/attachment_view";
    }

    @RequestMapping("search")
    public String search(String query, Model model) {
        model.addAttribute("queryText", query);
        return "km/knowledge_search";
    }

    @RequestMapping({"", "/index"})
    public String index() {
        return "km/index";
    }

    @RequestMapping("attachments/table")
    public String attachment() {
        return "km/sys/attachment_table";
    }

    @RequestMapping("knowledge/tree")
    public String manual() {
        return "km/knowledge_tree";
    }

    @RequestMapping("knowledge/{id}")
    public String knowledgeView(@PathVariable("id") Long id, Model model) {
        model.addAttribute("id", id);
        return "km/knowledge_info";
    }

    @RequestMapping("knowledge/tags")
    public String knowledgeTags() {
        return "km/knowledge_tags";
    }

    @RequestMapping("knowledge/add")
    public String knowledgeAdd() {
        return "km/knowledge_add";
    }

    @RequestMapping("knowledge/{id}/edit")
    public String knowledgeEdit(@PathVariable("id") Long id, Model model) {
        model.addAttribute("id", id);
        return "km/knowledge_edit";
    }

    @RequestMapping("knowledge/collections")
    public String knowledgeCollections() {
        return "km/knowledge_collections";
    }

    @RequestMapping("dictionary/table")
    public String dictionaryTable() {
        return "km/sys/dictionary_table";
    }

    @RequestMapping("menu/table")
    public String menu() {
        return "km/sys/menu_tree";
    }

    @RequestMapping("link/tree")
    public String linkTree() {
        return "km/link_tree";
    }

}
