package jdk8Test;

import entity.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * @author xhy
 * @Classname
 * @Description
 * @date 2021/3/17 - 13:16
 */
public class StreamTest {
    public static void main(String[] args) {
        test2();
    }

    /**
     * 过滤空字符串
     */
    private static void test(){
        List<String> strings = Arrays.asList("abc", "", "bc", "efg", "abcd", "", "jkl");
        //Arrays.asList得到的list不支持修改
     //   strings.add("ccc");
     //   strings.remove(3);
        List<String> filtered = strings.stream().filter(string -> !string.isEmpty()).collect(Collectors.toList());
        filtered.stream().forEach(System.out::println);
    }

    /**
     * 输出10个随机数
     */
    private static void test2(){
        Random random = new Random();
        random.ints().limit(100).sorted().forEach(System.out::println);
    }

    /**
     * 输出数的平方
     */

    private static void test3(){
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.stream().map(n -> n * n).collect(Collectors.toList()).forEach(System.out::println);
    }
    /**
     * 输出数组扁平化后的平方
     */

    private static void test4(){
        List<Integer> list1 = Arrays.asList(1, 4, 5);
        List<Integer> list2 = Arrays.asList(1, 2, 3);
        List<Integer> list3 = Arrays.asList(1, 7, 8);
        List<List<Integer>> lists = new ArrayList<>();
        lists.add(list1);
        lists.add(list2);
        lists.add(list3);
        lists.stream().flatMap(child -> child.stream().map(n -> n * n)).distinct().collect(Collectors.toList()).forEach(System.out::println);
    }

    /**
     * 统计集合符合条件的数量
     */
    private static void test5(){
        List<String> list = new ArrayList<>();
        list.add("xhy");
        list.add("");
        list.add("xxx");
        list.add("xxc");
        list.stream().filter(n -> !n.isEmpty()).forEach(System.out::println);
        System.out.println(list.stream().filter(n -> !n.isEmpty()).count());
        System.out.println("----------------------");
        list.forEach(System.out::println);
    }

    /**
     * peek和map的区别
     * peek参数实现consumer接口 无返回值 只能进行一些修改 打印的操作
     * map参数实现function接口 有返回值  可以修改对象的返回类型等操作
     */
    private static void test6(){
        List<String> list = new ArrayList<>();
        list.add("xhy");
        list.add("");
        list.add("xxx");
        list.add("xxc");
        List<String> list1 = list.stream().peek(n -> n="xx").collect(Collectors.toList());
        list1.forEach(System.out::println);
        List<Integer> list3 = new ArrayList<>();
        list3.add(1);
        list3.add(2);
        list3.add(3);
        list3.stream().peek(n -> n=7).collect(Collectors.toList()).forEach(System.out::println);
        System.out.println("---------------------------");
        list3.stream().map(n -> n=7).collect(Collectors.toList()).forEach(System.out::println);
        //修改user的名字
        List<User> list5 = Arrays.asList(new User(), new User(), new User());
        list5.stream().peek(n -> n.setUserName("xhy")).collect(Collectors.toList()).forEach(user -> System.out.println(user.getUserName()));
    }


}
