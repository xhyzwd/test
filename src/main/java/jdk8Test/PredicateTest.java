package jdk8Test;

import java.util.function.Predicate;

/**
 * @author xhy
 * @Classname
 * @Description
 * @date 2021/3/17 - 11:04
 */
public class PredicateTest {
    public static void main(String[] args) {
        test();
    }

    public static void test(){
        Predicate<Integer> predicate = n-> n%2 == 0;
        Predicate<Integer> predicate2 = n-> n%2 != 0;
        //test
        System.out.println(predicate.test(2));
        System.out.println(predicate2.test(2));
        //与运算
        System.out.println(predicate.and(predicate2).test(2));
        //或运算
        System.out.println(predicate.or(predicate2).test(2));
        //取反
        System.out.println(predicate.negate().test(2));
    }
}
