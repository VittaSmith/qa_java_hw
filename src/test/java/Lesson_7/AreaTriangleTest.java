package Lesson_7;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AreaTriangleTest {
    @Test
    void testArea(){
        assertThrows(ArithmeticException.class, () -> AreaTriangle.area(3,4,0));
        assertEquals(2.83, AreaTriangle.area(3,3,2));
    }

}