package com.cloud.example.client;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author ; lidongdong
 * @Description Lambda用例
 * @Date 2019-10-28
 */
public class LambdaClient {


    /**
     * java标签的使用
     */
    public static void labeledFor() {
        label:
        for (int i = 0; i < 10; i++) {
            outer:
            for (int j = 0; j < 10; j++) {
                bgm:
                for (int m = 0; m < 10; m++) {
                    if (m > 6) {
                        System.out.println(i + "++" + j + "++" + m);
                        break outer;
                    }
                    System.out.println(i + "--" + j + "--" + m);
                }
            }
        }
    }


    public static void test1() {
        List<String> list1 = new ArrayList<String>();
        list1.add("1");
        list1.add("2");
        list1.add("3");
        list1.add("5");
        list1.add("6");

        List<String> list2 = new ArrayList<String>();
        list2.add("2");
        list2.add("3");
        list2.add("7");
        list2.add("8");

        // 交集
        List<String> intersection = list1.stream().filter(item -> list2.contains(item)).collect(Collectors.toList());
        System.out.println("---交集 intersection---");
        intersection.parallelStream().forEach(System.out::println);

        // 差集 (list1 - list2)
        List<String> reduce1 = list1.stream().filter(item -> !list2.contains(item)).collect(Collectors.toList());
        System.out.println("---差集 reduce1 (list1 - list2)---");
        reduce1.parallelStream().forEach(System.out::println);

        // 差集 (list2 - list1)
        List<String> reduce2 = list2.stream().filter(item -> !list1.contains(item)).collect(Collectors.toList());
        System.out.println("---差集 reduce2 (list2 - list1)---");
        reduce2.parallelStream().forEach(System.out::println);

        // 并集
        List<String> listAll = list1.parallelStream().collect(Collectors.toList());
        List<String> listAll2 = list2.parallelStream().collect(Collectors.toList());
        listAll.addAll(listAll2);
        System.out.println("---并集 listAll---");
        listAll.parallelStream().forEachOrdered(System.out::println);

        // 去重并集
        List<String> listAllDistinct = listAll.stream().distinct().collect(Collectors.toList());
        System.out.println("---得到去重并集 listAllDistinct---");
        listAllDistinct.parallelStream().forEachOrdered(System.out::println);

        System.out.println("---原来的List1---");
        list1.parallelStream().forEachOrdered(System.out::println);
        System.out.println("---原来的List2---");
        list2.parallelStream().forEachOrdered(System.out::println);

    }

    public static void main(String[] args) {
        test1();

        labeledFor();
    }

}
