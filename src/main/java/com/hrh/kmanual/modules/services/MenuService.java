package com.hrh.kmanual.modules.services;

import com.hrh.kmanual.commons.dto.BootstrapTreeViewNode;
import com.hrh.kmanual.commons.utils.ReflectUtils;
import com.hrh.kmanual.modules.dao.entites.Menu;
import com.hrh.kmanual.modules.dao.jpas.MenuRepository;
import com.hrh.kmanual.modules.dto.form.MenuAddForm;
import com.hrh.kmanual.modules.dto.form.MenuUpdateForm;
import com.hrh.kmanual.sys.config.DictionaryConfig;
import com.hrh.kmanual.sys.config.StaticValueConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import javax.transaction.Transactional;
import java.util.*;

/**
 * @author huangrenhao
 * @date 2018/8/9
 */
@Service
public class MenuService {

    @Autowired
    private MenuRepository menuRepository;


    /**
     * 获取指定目录的所有父级目录
     * 并且按照父级层次排序
     *
     * @param menu
     * @return
     */
    public List<Menu> getParentMenuList(Menu menu) {

        if (null == menu) {
            return new ArrayList<>();
        }
        LinkedList<Menu> parentMenuList = new LinkedList<>();
        Menu parentNode = appendParentMenu(menu, getMenus(), parentMenuList);
        if(null != parentNode){
            parentNode.setSubMenu(Collections.emptyList());
            parentMenuList.add(parentNode);
        }

        return parentMenuList;
    }

    private Menu appendParentMenu(Menu menu, List<Menu> menus, List<Menu> parentMenuList) {

        for (Menu node : menus) {
            if (node.getId().equals(menu.getId())) {
                return node;
            }
            if (!node.getSubMenu().isEmpty()) {
                Menu temp = appendParentMenu(menu, node.getSubMenu(), parentMenuList);
                if (null != temp) {
                    temp.setSubMenu(Collections.emptyList());
                    parentMenuList.add(temp);
                    return node;
                }
            }
        }
        return null;
    }


    /**
     * 获取目录信息
     *
     * @return
     */
    public List<Menu> getMenus() {
        List<Menu> menus = menuRepository.findMenus();
        // 目录排序
        sortMenu(menus);
        return menus;
    }

    /**
     * 目录排序
     *
     * @param menus
     */
    private void sortMenu(List<Menu> menus) {
        Collections.sort(menus);
        for (Menu menu : menus) {
            if (!menu.getSubMenu().isEmpty()) {
                sortMenu(menu.getSubMenu());
            }
        }
    }

    /**
     * 获取指定节点及以下所有的子节点的ID集合
     *
     * @param id
     * @return
     */
    public List<Long> getMenuIdSet(Long id) {

        List<Long> IdSet = new ArrayList<>();
        Menu menu = menuRepository.findOne(id);

        if (null != menu) {
            IdSet.add(menu.getId());
            if (!menu.getSubMenu().isEmpty()) {
                appendMenuId(menu.getSubMenu(), IdSet);
            }
        }
        return IdSet;
    }

    /**
     * 填充ID
     *
     * @param subMenu
     * @param idSet
     */
    private void appendMenuId(List<Menu> subMenu, List<Long> idSet) {
        for (Menu menu : subMenu) {
            idSet.add(menu.getId());
            if (!menu.getSubMenu().isEmpty()) {
                appendMenuId(menu.getSubMenu(), idSet);
            }
        }
    }

    public List<BootstrapTreeViewNode> bootstrapMenus() {
        return bootstrapMenus(false);
    }

    /**
     * @param flag 是否开启设置 非叶子节点不可选
     * @return
     */
    public List<BootstrapTreeViewNode> bootstrapMenus(boolean flag) {
        List<Menu> menus = getMenus();
        setKnowLedgeCount(menus);

        List<BootstrapTreeViewNode> bootstrapTreeViewNodes = new ArrayList<>(menus.size());

        // 不可选 非叶子节点时，不需要显示所有的 根节点选项
        if (!flag) {
            BootstrapTreeViewNode all = new BootstrapTreeViewNode("" + 0, " 所有");
            all.setIcon(DictionaryConfig.getStaticValueMap().get(StaticValueConfig.BOOTSTRAP_NODE_ICON_KEY));
            bootstrapTreeViewNodes.add(all);
        }
        circleAppend(menus, bootstrapTreeViewNodes, flag);
        return bootstrapTreeViewNodes;
    }

    /**
     * 注入 知识点统计数
     *
     * @param menus
     */
    public void setKnowLedgeCount(List<Menu> menus) {

        List<Menu> countMenus = menuRepository.findAllByStatus(1);

        Map<Long, Integer> countMap = new HashMap<>(countMenus.size());
        Map<Long, Long> parentIdMap = new HashMap<>(countMenus.size());
        Map<Long, String> parentNameMap = new HashMap<>(countMenus.size());

        for (Menu menu : countMenus) {
            countMap.put(menu.getId(), menu.getCount());
            parentIdMap.put(menu.getId(), menu.getParentId());
            parentNameMap.put(menu.getId(), menu.getParentNodeName());
        }

        setCircleKnowledge(menus, countMap, parentIdMap, parentNameMap);
    }

    /**
     *  循环设置 父级和统计 信息
     * @param menus
     * @param countMap
     * @param parentIdMap
     * @param parentNameMap
     */
    private void setCircleKnowledge(List<Menu> menus, Map<Long, Integer> countMap, Map<Long, Long> parentIdMap, Map<Long, String> parentNameMap) {
        for (Menu menu : menus) {
            menu.setCount(countMap.get(menu.getId()));
            menu.setParentId(parentIdMap.get(menu.getId()));
            menu.setParentNodeName(parentNameMap.get(menu.getId()));
            if (!menu.getSubMenu().isEmpty()) {
                setKnowLedgeCount(menu.getSubMenu(), countMap, parentIdMap, parentNameMap);
            }
        }
    }

    /**
     * 递归注入
     *
     * @param subMenu
     * @param countMap
     */
    private void setKnowLedgeCount(List<Menu> subMenu, Map<Long, Integer> countMap, Map<Long, Long> parentIdMap, Map<Long, String> parentNameMap) {
        setCircleKnowledge(subMenu, countMap, parentIdMap, parentNameMap);
    }

    /**
     * 填充节点
     *
     * @param subMenu
     * @param parent
     */
    private void appendNodes(List<Menu> subMenu, BootstrapTreeViewNode parent, boolean flag) {
        List<BootstrapTreeViewNode> bootstrapTreeViewNodes = new ArrayList<>(subMenu.size());
        circleAppend(subMenu, bootstrapTreeViewNodes, flag);
        parent.setNodes(bootstrapTreeViewNodes);
    }

    private void circleAppend(List<Menu> subMenu, List<BootstrapTreeViewNode> bootstrapTreeViewNodes, boolean flag) {
        for (Menu menu : subMenu) {
            BootstrapTreeViewNode node = new BootstrapTreeViewNode(String.valueOf(menu.getId()), " " + menu.getName());

            if (menu.getCount() != null && menu.getCount() > 0) {
                node.getTags().add(menu.getCount() + "");
            }

            bootstrapTreeViewNodes.add(node);
            if (!menu.getSubMenu().isEmpty()) {
                // 设置 非 叶子节点不可选中
                if (flag) {
                    node.setSelectable(false);
                }
                appendNodes(menu.getSubMenu(), node, flag);
            } else {
                // 叶子节点设置前置图标
                node.setIcon(DictionaryConfig.getStaticValueMap().get(StaticValueConfig.BOOTSTRAP_NODE_ICON_KEY));
            }
        }
    }

    /**
     * 持久化
     *
     * @param form
     * @return
     */
    public boolean persist(MenuAddForm form) {

        Menu entity = form.trainToEntity();
        entity.setCreateTime(new Date());
        entity.setStatus(1);

        Menu parentNode = menuRepository.findOne(entity.getParentId());

        if (null != parentNode) {
            return setMenuOrderAndPersist(form, entity, parentNode.getSubMenu(), parentNode);
        }

        if (null == parentNode) {
            List<Menu> menus = menuRepository.findMenus();
            return setMenuOrderAndPersist(form, entity, menus, null);
        }

        return false;
    }

    @Transactional(rollbackOn = RuntimeException.class)
    private boolean setMenuOrderAndPersist(MenuAddForm form, Menu entity, List<Menu> menus, Menu parentNode) {
        if (menus.isEmpty()) {
            menus = new ArrayList<>();
            entity.setSortOrder(1);
        } else {
            Collections.sort(menus);
            // 节点放置队尾
            if (form.getAfterMenuId() == 0L) {
                entity.setSortOrder(menus.get(menus.size() - 1).getSortOrder() + 1);
            } else {
                // 节点放置于队列中间
                boolean sortFlag = false;
                int sortOrder = 1;
                for (Menu menu : menus) {
                    // 若被更新实体出现在当前集合中，则跳过修改
                    if (menu.getId().equals(entity.getId())) {
                        continue;
                    }
                    // 找到排序位置，更新需要插入节点的位置，并开启之后队列往后挪1位排序。
                    if (menu.getId().equals(form.getAfterMenuId())) {
                        entity.setSortOrder(sortOrder);
                        sortOrder++;
                        sortFlag = true;
                    }
                    if (sortFlag) {
                        menu.setSortOrder(sortOrder);
                    }
                    menu.setSortOrder(sortOrder);
                    sortOrder++;
                }
            }
        }
        menus.add(entity);
        menuRepository.save(entity);
        // 更新关联关系并持久化
        if (parentNode != null) {
            parentNode.setSubMenu(menus);
            menuRepository.save(parentNode);
        }
        return true;
    }

    @Transactional(rollbackOn = RuntimeException.class)
    public boolean update(MenuUpdateForm form) {

        // 转换更新的值
        Menu entity = menuRepository.findOne(form.getId());
        Menu temp = form.trainToEntity();
        ReflectUtils.updateFieldByClass(temp, entity);

        Menu parentNode = menuRepository.findOne(form.getParentId());

        if (null != parentNode) {
            return setMenuOrderAndPersist(form, entity, parentNode.getSubMenu(), parentNode);
        }

        if (null == parentNode) {
            menuRepository.updateParentIdById(null, entity.getId());
            List<Menu> menus = menuRepository.findMenus();
            return setMenuOrderAndPersist(form, entity, menus, null);
        }

        return true;
    }

    public List<Menu> getMenuListByParentId(Long parentId) {

        List<Menu> resultSet = new ArrayList<>();

        Menu parentNode = menuRepository.findOne(parentId);
        resultSet.add(parentNode);

        if (parentNode.getSubMenu().isEmpty()) {
            parentNode.setLeaf(true);
        } else {
            appendSubMenu(parentNode.getSubMenu(), resultSet);
        }

        setKnowLedgeCount(resultSet);
        return resultSet;
    }

    private void appendSubMenu(List<Menu> subMenu, List<Menu> resultSet) {
        for (Menu menu : subMenu) {
            resultSet.add(menu);
            if (menu.getSubMenu().isEmpty()) {
                menu.setLeaf(true);
            } else {
                appendSubMenu(menu.getSubMenu(), resultSet);
            }
        }
    }
}
