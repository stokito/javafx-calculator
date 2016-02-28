package com.example.javafx.calculator

import org.junit.Before
import org.junit.Test

class CalculatorTest {
    private Calculator calc
    private Display display = new DisplayStub()

    @Before
    void setUp() {
        calc = new Calculator(display)
    }

    @Test
    void getNumber() {
        //given
        display.setDisplayNumber("0")
        //when
        double number = calc.getNumber()
        //then
        assert number == 0D
    }

    @Test
    void setNumber() {
        // given
        double number = 42D
        // when
        calc.setNumber(number)
        // then
        assert display.getDisplayNumber() == "42.0"
    }

    @Test
    void digit() {
        assert display.getDisplayNumber() == "0"
        calc.digit("1")
        assert display.getDisplayNumber() == "1"
        calc.digit("2")
        assert display.getDisplayNumber() == "12"
    }

    @Test
    void clear() {
        // given
        calc.digit("1")
        calc.operator("+")
        calc.digit("3")
        calc.enter()
        // when
        calc.clear()
        // then
        assert !calc.isLastButtonWasDigit()
        assert calc.getNumber() == 0D
        assert calc.getA() == 0D
        assert calc.getB() == 0D
        assert calc.getOperator() == "+"
    }

    @Test
    void negate() {
        // given
        calc.digit("1")
        // when
        calc.negate()
        // then
        assert calc.getNumber() == -1D
    }

    @Test
    void sqrt() {
        // given
        calc.digit("4")
        // when
        calc.sqrt()
        // then
        assert calc.getNumber() == 2D
    }

    @Test
    void comma() {
        // given
        assert display.getDisplayNumber() == "0"
        //when
        calc.comma()
        // then
        assert display.getDisplayNumber() == "0,"
    }

    @Test
    void commaMayBeUsedOnlyOnce() {
        // given
        display.setDisplayNumber("0,3")
        //when
        calc.comma()
        // then
        assert display.getDisplayNumber() == "0,3"
    }

    @Test
    void fractionOne() {
        //given
        calc.digit("4")
        // when
        calc.fractionOne()
        // then
        assert calc.getNumber() == 0.25D
    }

    @Test
    void percent() {
        // when
        calc.digit("6")
        calc.operator("+")
        calc.digit("50")
        calc.percent()
        // then
        assert calc.getNumber() == 9D
    }


    @Test
    void operator() {
        calc.digit("2")
        assertState(2D, 0D, 0D, "+", true)
        calc.operator("+")
        assertState(2D, 2D, 0D, "+", false)
        calc.digit("3")
        assertState(3D, 2D, 0D, "+", true)
        calc.operator("+")
        assertState(5D, 2D, 3D, "+", false)
        calc.digit("3")
        assertState(3D, 5D, 3D, "+", true)
        calc.operator("-")
        assertState(8D, 5D, 3D, "-", false)
        calc.digit("1")
        assertState(1D, 5D, 3D, "-", true)
        calc.enter()
        assertState(7D, 8D, 1D, "-", false)
    }

    @Test
    void operatorSetLastButtonWasDigitFalse() {
        calc.digit("2")
        calc.operator("+")
        assert !calc.isLastButtonWasDigit()
    }

    @Test
    void operatorWasAfterOperator() {
        calc.digit("2")
        calc.operator("+")
        assertState(2D, 0D, 0D, "+", false)
        calc.operator("-")
        assertState(2D, 0D, 0D, "-", false)
    }

    @Test
    void enter() {
        calc.digit("2")
        calc.operator("+")
        calc.digit("1")
        calc.enter()
        assertState(3D, 2D, 1D, "+", false)
        calc.enter()
        assertState(4D, 3D, 1D, "+", false)
        calc.enter()
        assertState(5D, 4D, 1D, "+", false)
    }

    private void assertState(double number, double a, double b, String operator, boolean lastButtonWasDigit) {
        assert calc.getA() == a
        assert calc.getB() == b
        assert calc.getNumber() == number
        assert calc.getOperator() == operator
        assert calc.isLastButtonWasDigit() == lastButtonWasDigit
    }

    private static class DisplayStub implements Display {
        String displayNumber = "0"
    }
}