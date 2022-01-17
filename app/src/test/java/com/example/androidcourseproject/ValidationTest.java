package com.example.androidcourseproject;

import org.junit.Test;

import static org.junit.Assert.*;

public class ValidationTest {
    InputValidation inputValidation = new InputValidation();
    @Test
    public void ValidNumberOfNumbers() {
        assertTrue(inputValidation.isRowConsistsOfNumbers("3246524265"));
    }
    @Test
    public void ValidNumberOfLettert() {
        assertTrue(inputValidation.isRowConsistsOfLetters("asadfGSDgfdhgd"));
    }
    @Test
    public void ValidNumberOfNumbersAndLetters() {
        assertFalse(inputValidation.isRowConsistsOfLetters("Ug7378HSG8"));
        assertFalse(inputValidation.isRowConsistsOfNumbers("Ug7378HSG8"));
    }
}