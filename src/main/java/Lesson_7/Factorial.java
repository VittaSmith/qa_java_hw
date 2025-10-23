package Lesson_7;
import java.util.*;

public class Factorial {
    public static int factorial(int number) {
        if (number < 0) throw new IllegalArgumentException("the number of factorial can be non-negative");
        int result = 1;
        for (int i = 1; i <= number; i++) result *= i;
        return result;
    }
}
