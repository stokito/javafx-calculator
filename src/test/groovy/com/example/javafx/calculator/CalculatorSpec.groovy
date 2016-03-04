package com.example.javafx.calculator

import spock.lang.Ignore
import spock.lang.Specification
import spock.lang.Unroll

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

    void getNumberDecimalSeparator() {
        given:
        calc.display.displayNumber = "0,1"
        when:
        double number = calc.getNumber()
        then:
        number == 0.1D
    }

    void setNumber() {
        given:
        double number = 42D
        when:
        calc.number = number
        then:
        calc.display.displayNumber == "42"
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

    @Ignore
    void fractionOne() {
        given:
        calc.digit("4")
        when:
        calc.fractionOne()
        then:
        calc.number == 0.25D
    }

    @Ignore
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
        assertState(2D, 2D, 2D, "+", false)
        when:
        calc.operator("-")
        then:
        assertState(2D, 2D, 2D, "-", false)
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

    @Unroll("calc(#a, #b, #operator, #number)")
    void calc(a, b, operator, number) {
        given:
        calc.a = a
        calc.b = b
        calc.operator = operator
        calc.calc()
        expect:
        calc.number == number
        where:
        a  | b  | operator | number
        2D | 3D | "+"      | 5D
        2D | 3D | "-"      | -1D
        2D | 3D | "*"      | 6D
        6D | 2D | "/"      | 3D
    }

    void "calc() division on 0 throws ArithmeticException"() {
        given:
        calc.a = 42
        calc.b = 0
        calc.operator = "/"
        when:
        calc.calc()
        then:
        thrown(ArithmeticException)
    }

    private void assertState(double number, double a, double b, String operator, boolean lastButtonWasDigit) {
        assert calc.a == a
        assert calc.b == b
        assert calc.number == number
        assert calc.operator == operator
        assert calc.lastButtonWasDigit == lastButtonWasDigit
    }
}