package ru.stqa.geometry.figures;

public record Triangle(double a, double b, double c) {


    public double perimeter() {
        return this.a + this.b + this.c;
    }

    public double area() {
        double p = 0.5 * (this.a + this.b + this.c);
        return Math.sqrt(p * (p - this.a) * (p - this.b) * (p - this.c));
    }
}
