package Lesson_7;
import java.text.*;

public class AreaTriangle {
    public static double area(int side1, int side2, int side3){
        if((side1<=0)||(side2<=0)||(side3<=0)) throw new ArithmeticException("Sides cannot be zero");
        if (side1 + side2 <= side3 || side1 + side3 <= side2 || side2 + side3 <= side3) {
            throw new ArithmeticException("The sum of two sides cannot be less then third");
        }
        double perimetre = side1 + side2 + side3;
        double p = perimetre/2;
        double result = Math.sqrt(p*(p-side1)*(p-side2)*(p-side3));
        return Math.round(result * 100.0) / 100.0;
    }
}