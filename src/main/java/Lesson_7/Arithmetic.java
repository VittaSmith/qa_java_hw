package Lesson_7;

public class Arithmetic {
    public static int add(int a, int b){return a+b;}
    public static int sub(int a, int b){return a-b;}
    public static double div(int a, int b){
        if (b==0) throw new ArithmeticException("Division by zero");
        return (double) a/b;
    }
    public static int mult(int a, int b){return a*b;}
}
