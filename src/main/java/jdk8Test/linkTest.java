package jdk8Test;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author xhy
 * @Classname
 * @Description
 * @date 2021/1/14 - 9:32
 */
public class linkTest {
    public static void main(String[] args) throws ParseException {
        printTest();
    }
    public interface Converter<T1, T2> {
        void convert(int i);
    }

    private static void lamdaChangeFinal(){
        int num = 1;
        Converter<Integer, String> s = (param) -> System.out.println(String.valueOf(param + num));
        s.convert(2);
        //改变lambda中引用的变量会报错
       //num = 5;
    }

    //方法引用
    private static void printTest(){
        List names = new ArrayList();
        names.add("Google");
        names.add("Runoob");
        names.add("Taobao");
        names.add("Baidu");
        names.add("Sina");
        names.forEach(System.out::println);
    }

    private static void flatMapTest(){

        Stream<List<Integer>> inputStream = Stream.of(
                Arrays.asList(1),
                Arrays.asList(2, 3),
                Arrays.asList(4, 5, 6)
        );
        Stream<Integer> outputStream = inputStream.
                flatMap((childList) -> childList.stream()).map(n -> n * n);
        System.out.println(outputStream.collect(Collectors.toList()));
    }

    private static void fifterSimple(){

        Integer[] sixNums = {1, 2, 3, 4, 5, 6};
        Integer[] evens =
                Stream.of(sixNums).filter(n -> n%2 == 0).toArray(Integer[]::new);
        for (int i : evens){
            System.out.println(i);
        }
    }
}
