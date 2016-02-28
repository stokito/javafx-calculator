package com.example.javafx.calculator;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

class CalculatorTest {
    private Calculator calc;
    private Display display = new DisplayStub();

    @Before
    void setUp()  {
        calc = new Calculator(display);
    }

    @Test
    void getNumber()  {
        //given
        display.setDisplayNumber("0");
        //when
        double number = calc.getNumber();
        //then
        assertEquals(0D, number, 0.000000001D);
    }

    @Test
    void setNumber()  {
        // given
        double number = 42D;
        // when
        calc.setNumber(number);
        // then
        assertEquals("42.0", display.getDisplayNumber());
    }

    @Test
    void digit()  {
        assertEquals("0", display.getDisplayNumber());
        calc.digit("1");
        assertEquals("1", display.getDisplayNumber());
        calc.digit("2");
        assertEquals("12", display.getDisplayNumber());
    }

    @Test
    void clear()  {
        // given
        calc.digit("1");
        calc.operator("+");
        calc.digit("3");
        calc.enter();
        // when
        calc.clear();
        // then
        assertFalse(calc.isLastButtonWasDigit());
        assertEquals(0D, calc.getNumber(), 0.000001D);
        assertEquals(0D, calc.getA(), 0.000001D);
        assertEquals(0D, calc.getB(), 0.000001D);
        assertEquals("+", calc.getOperator());
    }

    @Test
    void negate()  {
        // given
        calc.digit("1");
        // when
        calc.negate();
        // then
        assertEquals(-1D, calc.getNumber(), 0.00000001D);
    }

    @Test
    void sqrt()  {
        // given
        calc.digit("4");
        // when
        calc.sqrt();
        // then
        assertEquals(2D, calc.getNumber(), 0.00000001D);
    }

    @Test
    void comma()  {
        // given
        assertEquals("0", display.getDisplayNumber());
        //when
        calc.comma();
        // then
        assertEquals("0,",display.getDisplayNumber());
    }

    @Test
    void commaMayBeUsedOnlyOnce()  {
        // given
        display.setDisplayNumber("0,3");
        //when
        calc.comma();
        // then
        assertEquals("0,3",display.getDisplayNumber());
    }

    @Test
    void fractionOne()  {
        //given
        calc.digit("4");
        // when
        calc.fractionOne();
        // then
        assertEquals(0.25D, calc.getNumber(), 0.0000001D);
    }

    @Test
    void percent()  {
        // when
        calc.digit("6");
        calc.operator("+");
        calc.digit("50");
        calc.percent();
        // then
        assertEquals(9D, calc.getNumber(), 0.00000001D);
    }


    @Test
    void operator()  {
        calc.digit("2");
        assertState(2D, 0D, 0D, "+", true);
        calc.operator("+");
        assertState(2D, 2D, 0D, "+", false);
        calc.digit("3");
        assertState(3D, 2D, 0D, "+", true);
        calc.operator("+");
        assertState(5D, 2D, 3D, "+", false);
        calc.digit("3");
        assertState(3D, 5D, 3D, "+", true);
        calc.operator("-");
        assertState(8D, 5D, 3D, "-", false);
        calc.digit("1");
        assertState(1D, 5D, 3D, "-", true);
        calc.enter();
        assertState(7D, 8D, 1D, "-", false);
    }

    @Test
    void operatorSetLastButtonWasDigitFalse()  {
        calc.digit("2");
        calc.operator("+");
        assertFalse(calc.isLastButtonWasDigit());
    }

    @Test
    void operatorWasAfterOperator()  {
        calc.digit("2");
        calc.operator("+");
        assertState(2D, 0D, 0D, "+", false);
        calc.operator("-");
        assertState(2D, 0D, 0D, "-", false);
    }

    @Test
    void enter()  {
        calc.digit("2");
        calc.operator("+");
        calc.digit("1");
        calc.enter();
        assertState(3D, 2D, 1D, "+", false);
        calc.enter();
        assertState(4D, 3D, 1D, "+", false);
        calc.enter();
        assertState(5D, 4D, 1D, "+", false);
    }

    private void assertState(double number, double a, double b, String operator, boolean lastButtonWasDigit) {
        assertEquals(a, calc.getA(), 0.0000001D);
        assertEquals(b, calc.getB(), 0.0000001D);
        assertEquals(number, calc.getNumber(), 0.0000001D);
        assertEquals(operator, calc.getOperator());
        assertEquals(lastButtonWasDigit, calc.isLastButtonWasDigit());
    }

    private static class DisplayStub implements Display {
        String displayNumber = "0";
    }
}