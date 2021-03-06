package jdk8Test;

import java.util.Optional;

/**
 * @author xhy
 * @Classname
 * @Description
 * @date 2021/3/17 - 15:26
 */
public class OptionalTest {
    public static void main(String args[]) {
        OptionalTest java8Tester = new OptionalTest();
        Integer value1 = null;
        Integer value2 = new Integer(10);
        // Optional.ofNullable - 允许传递为 null 参数
        Optional<Integer> a = Optional.ofNullable(value1);
        // Optional.of - 如果传递的参数是 null，抛出异常 NullPointerException
        Optional<Integer> b = Optional.of(value2);
        System.out.println(java8Tester.sum(a, b));


    }

    public Integer sum(Optional<Integer> a, Optional<Integer> b) {
        // Optional.isPresent - 判断值是否存在
        System.out.println("第一个参数值存在: " + a.isPresent());
        System.out.println("第二个参数值存在: " + b.isPresent());
        // Optional.orElse - 如果值存在，返回它，否则返回默认值
        Integer value1 = a.orElse(new Integer(0));
        //Optional.get - 获取值，值需要存在
        Integer value2 = b.get();
        Optional<Integer> c = b.map(s -> s + 20);
        //过滤后不存在就返回1
        Integer integer = c.filter(s -> s > 30).orElse(1);
        //过滤后不存在就调用默认值方法
        Integer integer1 = c.filter(s -> s > 30).orElseGet(() -> defaultR());
        return value1 + value2 + c.get() + integer + integer1;
    }

    private int defaultR(){
        return 2;
    }
}
