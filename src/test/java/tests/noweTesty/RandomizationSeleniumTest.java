package tests.noweTesty;

import org.junit.jupiter.api.Test;

import java.util.List;

import static io.qala.datagen.RandomShortApi.*;
import static io.qala.datagen.RandomString.Type.ALPHANUMERIC;
import static io.qala.datagen.RandomString.Type.NUMERIC;
import static io.qala.datagen.RandomValue.length;
import static io.qala.datagen.Repeater.repeat;

public class RandomizationSeleniumTest {

    @Test
    void randomizeTest() {
        String name = english(12);
        System.out.println("english(12) = " + name);

        String name2 = english(5, 13);
        System.out.println("english(5, 13) = " + name2);

        String name3 = alphanumeric(10);
        System.out.println("alphanumeric(10) = " + name3);

        int integer = integer(0, 121);
        System.out.println("integer(0, 121) = " + integer);

        List<String> actorList = List.of("Angelina", "Brad", "Salma");
        String sample = sample(actorList);
        System.out.println("sample(actorList) " + sample);

        String repeat = repeat(length(4), NUMERIC).string("-").times(4);
        System.out.println("repeat(length(4), NUMERIC).string('-').times(4) = " + repeat);

        String repeat2 = repeat(length(5), ALPHANUMERIC).string("_").times(4);
        System.out.println("repeat(length(4), NUMERIC).string('-').times(4) = " + repeat2);
    }
}
