package ru.stqa.geometry.figures;

public record Rectangle(double a, double b) {


    public static void printRectangleArea(double a, double b) {
        var text = String.format("Площать прямоугольника со сторонами %f и %f = %f", a, b, area(a, b));
        System.out.println(text);
    }

    private static double area(double a, double b) {
        return a * b;
    }
}
