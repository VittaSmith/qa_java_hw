package Lesson_7;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EqualsTest {
    @Test
    void testEquals(){
        assertEquals(true,Equals.equalTwoNum(3,3));
        assertEquals(false,Equals.equalTwoNum(3,-3));
        assertEquals(false,Equals.equalTwoNum(-6,6));
        assertEquals(true,Equals.equalTwoNum(-5,-5));
    }

}