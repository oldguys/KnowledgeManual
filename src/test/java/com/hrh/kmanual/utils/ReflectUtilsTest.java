package com.hrh.kmanual.utils;

import com.hrh.kmanual.commons.utils.ReflectUtils;
import com.hrh.kmanual.modules.dao.entites.Menu;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author huangrenhao
 * @date 2018/8/28
 */
public class ReflectUtilsTest {

    @Test
    public void test() {

        Menu menu = new Menu();

        menu.setId(22222L);
        menu.setName("测试.....");
        menu.setType("sssss");

        List<Menu> menuList = new ArrayList<>();
        menuList.add(new Menu());
        menu.setSubMenu(menuList);

        Menu target = new Menu();

        target.setType("kkk");
        target.setCount(67887);

        System.out.println("menu:" + menu);
        System.out.println("target:" + target);
        System.out.println("sss");
        ReflectUtils.updateFieldByClass( menu, target);
        System.out.println("menu:" + menu);
        System.out.println("target:" + target);
    }
}
