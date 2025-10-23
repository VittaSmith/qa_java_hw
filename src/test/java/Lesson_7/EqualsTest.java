package Lesson_7;

import org.testng.annotations.Test;
import static org.testng.Assert.*;

public class EqualsTest {

    @Test
    public void equals_cases() {
        assertTrue(Equals.equalTwoNum(3, 3));
        assertFalse(Equals.equalTwoNum(3, -3));
        assertFalse(Equals.equalTwoNum(-6, 6));
        assertTrue(Equals.equalTwoNum(-5, -5));
    }
}