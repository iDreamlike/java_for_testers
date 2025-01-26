package ru.stqa.geometry.figures;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TriangleTests {

    @Test
    void cannotCreateTriangleWithNegativeSide() {
        try {
            new Triangle(-5.0, 3.0, 4.0);
            Assertions.fail();
        } catch (IllegalArgumentException exception) {
            //ok
        }
    }

    @Test
    void cannotCreateTriangleWithWrongSides() {
        try {
            new Triangle(10, 20, 40);
            Assertions.fail();
        } catch (IllegalArgumentException exception) {
            //ok;
        }
    }

    @Test
    void canCalculatePerimeterTriangle() {
        Assertions.assertEquals(15.0, new Triangle(5.0, 5.0, 5.0).perimeter());
    }

    @Test
    void canCalculateAreaTriangle() {
        Assertions.assertEquals(10.825317547305483, new Triangle(5.0, 5.0, 5.0).area());
    }

    @Test
    void testEquality() {
        var r1 = new Triangle(5.0, 4.0, 3.0);
        var r2 = new Triangle(5.0, 4.0, 3.0);
        Assertions.assertEquals(r1, r2);
    }

    @Test
    void testEquality2() {
        var r1 = new Triangle(3.0, 4.0, 5.0);
        var r2 = new Triangle(5.0, 4.0, 3.0);
        Assertions.assertEquals(r1, r2);
    }
}
