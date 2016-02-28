package com.example.javafx.calculator;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Objects;

import static org.junit.Assert.*;

public class CalculatorTest {
    private Calculator calculator;
    private Display display = new DisplayStub();

    @Before
    public void setUp() throws Exception {
        calculator = new Calculator(display);
    }

    @Test
    public void getNumber() throws Exception {
        //given
        display.setDisplayNumber("0");
        //when
        double number = calculator.getNumber();
        //then
        assertEquals(0D, number, 0.000000001D);
    }

    @Test
    public void setNumber() throws Exception {
        // given
        double number = 42D;
        // when
        calculator.setNumber(number);
        // then
        assertEquals("42.0", display.getDisplayNumber());
    }

    @Test
    public void digit() throws Exception {
        assertEquals("0", display.getDisplayNumber());
        calculator.digit("1");
        assertEquals("1", display.getDisplayNumber());
        calculator.digit("2");
        assertEquals("12", display.getDisplayNumber());
    }

    @Test
    public void clear() throws Exception {
        // given
        calculator.digit("1");
        assertTrue(calculator.isLastButtonWasDigit());
        // when
        calculator.clear();
        // then
        assertFalse(calculator.isLastButtonWasDigit());
        assertEquals(0D, calculator.getNumber(), 0.000001D);
    }

    @Test
    public void negate() throws Exception {
        // given
        calculator.digit("1");
        // when
        calculator.negate();
        // then
        assertEquals(-1D, calculator.getNumber(), 0.00000001D);
    }

    @Test
    public void sqrt() throws Exception {
        // given
        calculator.digit("4");
        // when
        calculator.sqrt();
        // then
        assertEquals(2D, calculator.getNumber(), 0.00000001D);
    }

    @Test
    public void comma() throws Exception {
        // given
        assertEquals("0", display.getDisplayNumber());
        //when
        calculator.comma();
        // then
        assertEquals("0,",display.getDisplayNumber());
    }

    @Test
    public void commaMayBeUsedOnlyOnce() throws Exception {
        // given
        display.setDisplayNumber("0,3");
        //when
        calculator.comma();
        // then
        assertEquals("0,3",display.getDisplayNumber());
    }

    private static class DisplayStub implements Display {
        private String displayNumber = "0";
        @Override
        public String getDisplayNumber() {
            return displayNumber;
        }

        @Override
        public void setDisplayNumber(String displayNumber) {
            this.displayNumber = displayNumber;
        }
    }
}