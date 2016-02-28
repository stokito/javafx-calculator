package com.example.javafx.calculator

import spock.lang.Specification

class CalculatorSpec extends Specification {
    private Calculator calc = new Calculator(new DisplayStub())

    void getNumber() {
        given:
        calc.display.displayNumber = "0"
        when:
        double number = calc.getNumber()
        then:
        number == 0D
    }

    void setNumber() {
        given:
        double number = 42D
        when:
        calc.number = number
        then:
        calc.display.displayNumber == "42.0"
    }

    void digit() {
        given:
        assert calc.display.displayNumber == "0"
        when:
        calc.digit("1")
        then:
        calc.display.displayNumber == "1"
        when:
        calc.digit("2")
        then:
        calc.display.displayNumber == "12"
    }

    void clear() {
        given:
        calc.digit("1")
        calc.operator("+")
        calc.digit("3")
        calc.enter()
        when:
        calc.clear()
        then:
        !calc.lastButtonWasDigit
        calc.number == 0D
        calc.a == 0D
        calc.b == 0D
        calc.operator == "+"
    }

    void negate() {
        given:
        calc.digit("1")
        when:
        calc.negate()
        then:
        calc.number == -1D
    }

    void sqrt() {
        given:
        calc.digit("4")
        when:
        calc.sqrt()
        then:
        calc.number == 2D
    }

    void comma() {
        given:
        assert calc.display.displayNumber == "0"
        when:
        calc.comma()
        then:
        calc.display.displayNumber == "0,"
    }

    void commaMayBeUsedOnlyOnce() {
        given:
        calc.display.displayNumber = "0,3"
        when:
        calc.comma()
        then:
        calc.display.displayNumber == "0,3"
    }

    void fractionOne() {
        given:
        calc.digit("4")
        when:
        calc.fractionOne()
        then:
        calc.number == 0.25D
    }

    void percent() {
        when:
        calc.digit("6")
        calc.operator("+")
        calc.digit("50")
        calc.percent()
        then:
        calc.number == 9D
    }

    void operatorSetLastButtonWasDigitFalse() {
        given:
        calc.digit("2")
        when:
        calc.operator("+")
        then:
        !calc.lastButtonWasDigit
    }

    void operatorWasAfterOperator() {
        when:
        calc.digit("2")
        calc.operator("+")
        then:
        assertState(2D, 0D, 0D, "+", false)
        when:
        calc.operator("-")
        then:
        assertState(2D, 0D, 0D, "-", false)
    }

    void enter() {
        given:
        calc.digit("2")
        calc.operator("+")
        calc.digit("1")
        when:
        calc.enter()
        then:
        assertState(3D, 3D, 1D, "+", false)
        when:
        calc.enter()
        then:
        assertState(4D, 4D, 1D, "+", false)
        when:
        calc.enter()
        then:
        assertState(5D, 5D, 1D, "+", false)
    }

    private void assertState(double number, double a, double b, String operator, boolean lastButtonWasDigit) {
        assert calc.a == a
        assert calc.b == b
        assert calc.number == number
        assert calc.operator == operator
        assert calc.lastButtonWasDigit == lastButtonWasDigit
    }
}