package Lesson_7;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class FactorialTest {
    @Test
    void testFactorial() {
        assertEquals(120, Factorial.factorial(5));
        assertEquals(2, Factorial.factorial(2));
        assertEquals(1, Factorial.factorial(0));
        assertThrows(IllegalArgumentException.class, () -> Factorial.factorial(-1));
    }
}