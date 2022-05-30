package com.tanran.login;

import java.util.ArrayList;
import java.util.stream.Collectors;

import org.assertj.core.util.Lists;
import org.junit.Test;

/**
 * TODO
 *
 * @author liuht26262@yunrong.cn
 * @version V3.0
 * @description
 * @since 2022/5/23 14:27
 */
public class test {

    @Test
    public void test2(){
        System.out.println(true||false||false);
        System.out.println(false||true||false);
        System.out.println(false||false||true);
    }

    @Test
    public void tests(){
        ArrayList<Integer> list =
            Lists.newArrayList();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);
        list.stream().filter(i-> i%2==0).collect(Collectors.toList());
        System.out.println(list);
    }
}
