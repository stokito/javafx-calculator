package com.example.javafx.calculator

import spock.lang.Specification
import spock.lang.Unroll

class CalculatorSpec extends Specification {
    private Calculator calc
    private Display display = new DisplayStub()

    void setup() {
        calc = new Calculator(display)
    }

    void getNumber() {
        given:
        display.displayNumber = "0"
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
        display.displayNumber == "42.0"
    }

    void digit() {
        given:
        assert display.displayNumber == "0"
        when:
        calc.digit("1")
        then:
        display.displayNumber == "1"
        when:
        calc.digit("2")
        then:
        display.displayNumber == "12"
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
        assert display.displayNumber == "0"
        when:
        calc.comma()
        then:
        display.displayNumber == "0,"
    }

    void commaMayBeUsedOnlyOnce() {
        given:
        display.displayNumber = "0,3"
        when:
        calc.comma()
        then:
        display.displayNumber == "0,3"
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


    @Unroll("#digit, #operator, #a, #b, #number, #opField, #lastButtonWasDigit")
    void operator(String digit, String operator, a, b, number, opField, lastButtonWasDigit) {
        given:
        if (digit) {
            calc.digit(digit)
        } else if (operator) {
            calc.operator(operator)
        } else {
            calc.enter()
        }
        expect:
        calc.a == a
        calc.b == b
        calc.number == number
        calc.operator == opField
        calc.lastButtonWasDigit == lastButtonWasDigit
        where:
        digit | operator | a  | b  | number | opField | lastButtonWasDigit
        "2"   | null     | 2D | 0D | 0D     | "+"     | true
        null  | "+"      | 2D | 2D | 0D     | "+"     | false
        "3"   | null     | 3D | 2D | 0D     | "+"     | true
        null  | "+"      | 5D | 2D | 3D     | "+"     | false
        "3"   | null     | 3D | 5D | 3D     | "+"     | true
        null  | "-"      | 8D | 5D | 3D     | "-"     | false
        "1"   | null     | 1D | 5D | 3D     | "-"     | true
        null  | null     | 7D | 8D | 1D     | "-"     | false
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

    //TODO make this test data driven as test for operator
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