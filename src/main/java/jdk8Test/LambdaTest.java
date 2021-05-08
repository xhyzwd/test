package jdk8Test;

import java.util.ArrayList;

/**
 * @author xhy
 * @Classname
 * @Description
 * @date 2021/4/14 - 8:45
 */
@FunctionalInterface
interface Foo {
    //void sayHello();
    int add(int a, int b);
    default int div(int a, int b) {
        return a / b;
    }

    static void sayDad(String str) {
        System.out.println(str);
    }
}
public class LambdaTest {
    public static void main(String[] args) {
//        Foo foo = new Foo() {
//            @Override
//            public void sayHello() {
//                System.out.println("hello");
//            }
//
//            @Override
//            public int add(int a, int b) {
//                return 0;
//            }
//        };
        Foo foo = (a, b) -> a + b;
        System.out.println(foo.add(1, 3));
        System.out.println(foo.div(10, 2));
        Foo.sayDad("xxxx");
       // foo.sayHello();
    }
}
