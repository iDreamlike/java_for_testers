package ru.stqa.geometry.figures;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TriangleTests {

    @Test
    void canCalculatePerimeterTriangle() {
        Assertions.assertEquals(15.0, new Triangle(5.0, 5.0, 5.0).perimeter());
    }

    @Test
    void canCalculateAreaTriangle() {
        Assertions.assertEquals(10.825317547305483, new Triangle(5.0, 5.0, 5.0).area());
    }
}
