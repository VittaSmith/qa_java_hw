package Lesson_7;

import org.testng.annotations.Test;
import static org.testng.Assert.*;

public class AreaTriangleTest {

    @Test(expectedExceptions = ArithmeticException.class)
    public void area_throws_on_zero_side() {
        AreaTriangle.area(3, 4, 0);
    }

    @Test
    public void area_valid_triangle() {
        assertEquals(AreaTriangle.area(3, 3, 2), 2.83, "Площадь должна быть 2.83");
    }

    @Test(expectedExceptions = ArithmeticException.class)
    public void area_throws_on_triangle_inequality() {
        AreaTriangle.area(2, 3, 10);
    }
}