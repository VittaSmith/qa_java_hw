package Lesson_7;

import org.testng.annotations.Test;
import static org.testng.Assert.*;

public class FactorialTest {
    @Test
    public void factorial_valid_values() {
        assertEquals(Factorial.factorial(5), 120);
        assertEquals(Factorial.factorial(2), 2);
        assertEquals(Factorial.factorial(0), 1);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void factorial_negative_throws() {
        Factorial.factorial(-1);
    }
}