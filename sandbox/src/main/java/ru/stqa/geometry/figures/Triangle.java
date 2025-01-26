package ru.stqa.geometry.figures;

public record Triangle(double a, double b, double c) {

    public Triangle {
        if (a < 0 || b < 0 || c < 0) {
            throw new IllegalArgumentException("Сторона треугольника не может быть меньше нуля");
        }
        if ((a + b) < c || (a + c) < b || (b + c) < a) {
            throw new IllegalArgumentException("Сумма сторон должна быть больше или равна третьей");
        }
    }

    public double perimeter() {
        return this.a + this.b + this.c;
    }

    public double area() {
        double p = 0.5 * (this.a + this.b + this.c);
        return Math.sqrt(p * (p - this.a) * (p - this.b) * (p - this.c));
    }
}
