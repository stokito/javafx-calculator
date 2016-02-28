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
        display.displayNumber = "0"
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
        calc.number = number
        // then
        assert display.displayNumber == "42.0"
    }

    @Test
    void digit() {
        assert display.displayNumber == "0"
        calc.digit("1")
        assert display.displayNumber == "1"
        calc.digit("2")
        assert display.displayNumber == "12"
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
        assert !calc.lastButtonWasDigit
        assert calc.number == 0D
        assert calc.a == 0D
        assert calc.b == 0D
        assert calc.operator == "+"
    }

    @Test
    void negate() {
        // given
        calc.digit("1")
        // when
        calc.negate()
        // then
        assert calc.number == -1D
    }

    @Test
    void sqrt() {
        // given
        calc.digit("4")
        // when
        calc.sqrt()
        // then
        assert calc.number == 2D
    }

    @Test
    void comma() {
        // given
        assert display.displayNumber == "0"
        //when
        calc.comma()
        // then
        assert display.displayNumber == "0,"
    }

    @Test
    void commaMayBeUsedOnlyOnce() {
        // given
        display.displayNumber = "0,3"
        //when
        calc.comma()
        // then
        assert display.displayNumber == "0,3"
    }

    @Test
    void fractionOne() {
        //given
        calc.digit("4")
        // when
        calc.fractionOne()
        // then
        assert calc.number == 0.25D
    }

    @Test
    void percent() {
        // when
        calc.digit("6")
        calc.operator("+")
        calc.digit("50")
        calc.percent()
        // then
        assert calc.number == 9D
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
        assert !calc.lastButtonWasDigit
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
        assert calc.a == a
        assert calc.b == b
        assert calc.number == number
        assert calc.operator == operator
        assert calc.lastButtonWasDigit == lastButtonWasDigit
    }

    private static class DisplayStub implements Display {
        String displayNumber = "0"
    }
}