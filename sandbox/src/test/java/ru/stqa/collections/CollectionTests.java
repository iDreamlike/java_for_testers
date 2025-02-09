package ru.stqa.collections;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class CollectionTests {

    @Test
    void arrayTests() {
        var array = new String[]{"a", "b", "c"};
//        var array = new String[3];
        Assertions.assertEquals(3, array.length);
        array[0] = "a";
        Assertions.assertEquals("a", array[0]);

        array[0] = "d";
        Assertions.assertEquals("d", array[0]);


    }

    @Test
    void listTests() {
//        var list = new ArrayList<String>();         // такой список нельзя сразу наполнить
//        var list = List.of("a", "b", "c");          // такой список нельзя модифицировать
//        var list = new ArrayList<>(List.of("a", "b", "c"));  //можно сразу наполнить и потом модифицировать

        var list = new ArrayList<>(List.of("a", "b", "c"));

        list.add("d");
        Assertions.assertEquals("a", list.get(0));

        list.set(0, "d");
        Assertions.assertEquals("d", list.get(0));

        Assertions.assertEquals(4, list.size());
    }
}
