package Lesson_7;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ArithmeticTest {
    @Test
    void testArithmetic(){
        assertEquals(8, Arithmetic.add(5,3));
        assertEquals(-5,Arithmetic.sub(8,13));
        assertEquals(4, Arithmetic.mult(4,1));
        assertEquals(2,Arithmetic.div(6,3));
        assertThrows(ArithmeticException.class,() -> Arithmetic.div(12,0));
    }
}