package com.hrh.kmanual.dao.jpa;

import com.hrh.kmanual.commons.dao.entities.BaseEntity;
import com.hrh.kmanual.commons.utils.JsonUtils;
import com.hrh.kmanual.modules.dao.entites.Menu;
import com.hrh.kmanual.modules.dao.jpas.MenuRepository;

import com.hrh.kmanual.modules.services.MenuService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author huangrenhao
 * @date 2018/8/7
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class MenuTest {

    @Autowired
    private MenuRepository menuRepository;
    @Autowired
    private MenuService menuService;

    @Test
    public void testFindByParentId(){
       List<Menu> menus =  menuRepository.findByParentId(null);
        System.out.println(menus.size());
    }

    @Test
    public void testPersist(){
        Menu menu = new Menu();
        menu.setName("测试。。。。");
        menu.setType("ssss");
        menu.setParentId(222L);
        menu.setSortOrder(11);
        menu.setCreateTime(new Date());
        menu.setStatus(1);
    }

    @Test
    public void testFindAllByStatus() {
        List<Menu> menus = menuRepository.findAllByStatus(1);
        System.out.println(JsonUtils.getInstance().toJson(menus.get(0)));
                System.out.println(menus.size());
        for (Menu menu : menus) {
            System.out.println(menu);
        }

    }

    @Test
    public void testGetMenuIdSet() {
        System.out.println(menuService.getMenuIdSet(3L));
    }

    /**
     * 测试DEMO
     */
    @Test
    public void testAppend() {

        Menu parent = menuRepository.findOne(5L);

        Menu menu = new Menu();
        menu.setCreateTime(new Date());
        menu.setName("测试7");
        menu.setSortOrder(3);
        menu.setType("1");

        parent.getSubMenu().add(menu);
        /**
         * 不设置级联更新，则需要先调用持久化，设置则不需要。
         */
        //        menuRepository.save(menu);
        /**
         *  必须更新，才能将游离态转换为持久态
         */
        menuRepository.save(parent);
    }

    @Test
    public void testSave() {

        List<Menu> menuList = new ArrayList<>(4);
        Menu menu1 = new Menu();
        menu1.setCreateTime(new Date());
        menu1.setName("首页");
        menu1.setSortOrder(1);
        menu1.setType("1");

        Menu menu2 = new Menu();
        menu2.setCreateTime(new Date());
        menu2.setName("知识手册");
        menu2.setSortOrder(2);
        menu2.setType("1");

        menuList.add(menu1);
        menuList.add(menu2);

        testSubMenu(menuList);

        menuRepository.save(menuList);

    }

    private void testSubMenu(List<Menu> menuList) {

        Menu menu3 = new Menu();
        menu3.setName("测试目录3");
        menu3.setCreateTime(new Date());
        menu3.setSortOrder(3);
        menu3.setType("1");

        menuList.add(menu3);


        List<Menu> subMenu1 = new ArrayList<>(2);
        menu3.setSubMenu(subMenu1);

        Menu menu1 = new Menu();
        menu1.setCreateTime(new Date());
        menu1.setName("测试1");
        menu1.setSortOrder(1);
        menu1.setType("1");

        subMenu1.add(menu1);

        Menu menu2 = new Menu();
        menu2.setCreateTime(new Date());
        menu2.setName("测试2");
        menu2.setSortOrder(2);
        menu2.setType("1");
        subMenu1.add(menu2);

        List<Menu> subMenu2 = new ArrayList<>(2);
        menu2.setSubMenu(subMenu2);

        Menu menu4 = new Menu();
        menu4.setCreateTime(new Date());
        menu4.setName("测试4");
        menu4.setSortOrder(1);
        menu4.setType("1");
        subMenu2.add(menu4);

        Menu menu5 = new Menu();
        menu5.setCreateTime(new Date());
        menu5.setName("测试5");
        menu5.setSortOrder(2);
        menu5.setType("1");
        subMenu2.add(menu5);

        menuList.add(menu1);
        menuList.add(menu2);
        menuList.add(menu3);
        menuList.add(menu4);
        menuList.add(menu5);
    }


}
