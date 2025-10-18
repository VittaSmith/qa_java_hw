package Lesson_4;
import java.util.*;
/* 
----Задание 2
Применяя интерфейсы написать программу расчета периметра и площади геометрических фигур: круг, прямоугольник, треугольник. 
Задать для каждой фигуры цвет заливки и цвет границы. 
Результат полученных характеристик [ Периметр, площадь, цвет фона, цвет границ ] по каждой фигуре вывести в консоль. 
Попробуйте реализовать базовые методы, такие как расчет периметра фигур, в качестве дефолтных методов в интерфейсе. */

interface InnerFigure {
    String getNameOfShape();
    String getColorFill();
    String getColorBorder();
    double perimetre();
    double area();
    default void printInfo(){
        System.out.println("\n\tInformation about "+ getNameOfShape() +":");
        System.out.println("1) "+ "perimetre = " + perimetre());
        System.out.println("2) "+ "area = " + area());
        System.out.println("3) "+ "color fill = " + getColorFill());
        System.out.println("4) "+ "color border = " + getColorBorder()); System.out.println();
    }
}

abstract public class Figure implements InnerFigure {
    String nameOfShape;
    String colorFill, colorBorder;
    public Figure(){}
    public Figure(String nameOfShape, String colorFill, String colorBorder){
        this.nameOfShape = nameOfShape;
        this.colorBorder = colorBorder;
        this.colorFill = colorFill;
    }
    @Override public String getNameOfShape(){return nameOfShape;}
    @Override public String getColorFill(){return colorFill;}
    @Override public String getColorBorder(){return colorBorder;}
    @Override abstract public double perimetre();
    @Override abstract public double area();
}

class Circl extends Figure{
    //String nameOfShape = "Circl";
    //String colorFill, colorBorder;
    private double radius;
    public Circl(){}
    public Circl(double radius, String colorFill, String colorBorder){
        super("Circl",colorFill, colorBorder);
        this.radius=radius;
    }
    @Override
    public double perimetre(){
        return 2*radius*Math.PI;
    }
    @Override
    public double area(){
        return Math.PI*Math.pow(radius,2);
    }
}

class Rectangle extends Figure {
    //String nameOfShape = "Rectangle";
    //String colorFill, colorBorder;
    private int side1, side2;
    public Rectangle(){}
    public Rectangle(int side1, int side2, String colorFill, String colorBorder){
        super("Rectangle",colorFill, colorBorder);
        this.side1=side1;
        this.side2=side2;
    }
    @Override
    public double perimetre(){
        return 2*(side1+side2);
    }
    @Override
    public double area(){
        return side1*side2;
    }
}

class Triangle extends Figure{
    //String nameOfShape = "Triangle";
    //String colorFill, colorBorder;
    private int side1, side2, side3;
    public Triangle(){}
    public Triangle(int side1, int side2, int side3, String colorFill, String colorBorder){
        super("Triangle",colorFill, colorBorder);
        this.side1=side1;
        this.side2=side2;
        this.side3=side3;
    }
    @Override
    public double perimetre(){
        return side1+side2+side3;
    }
    @Override
    public double area(){
        double p = perimetre()/2;
        return Math.sqrt(p*(p-side1)*(p-side2)*(p-side3));
    }
}