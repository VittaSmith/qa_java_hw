package Lesson_7;

import org.testng.annotations.Test;
import static org.testng.Assert.*;

public class ArithmeticTest {
    @Test
    public void arithmetic_basic_ops() {
        assertEquals(Arithmetic.add(5, 3), 8);
        assertEquals(Arithmetic.sub(8, 13), -5);
        assertEquals(Arithmetic.mult(4, 1), 4);
        assertEquals(Arithmetic.div(6, 3), 2.0); // div возвращает double
    }
    @Test(expectedExceptions = ArithmeticException.class)
    public void division_by_zero_throws() {
        Arithmetic.div(12, 0);
    }
}