package ir.mctab.java32.hw10.repositories;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class MyTest {
    public static void main(String[] args) {

            List<Integer> integers = new ArrayList<>();
        for (int i = 100; i >0 ; i--) {
            integers.add(i);
        }

            integers.stream().parallel().sorted().forEach(System.out::println);

    }

}
