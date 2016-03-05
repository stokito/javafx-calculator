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
        def number = calc.getNumber()
        then:
        number == 0.0G
    }

    void getNumberDecimalSeparator() {
        given:
        calc.display.displayNumber = "0,1"
        when:
        def number = calc.getNumber()
        then:
        number == 0.1G
    }

    @Ignore
    void setNumber() {
        given:
        def number = 42.0G
        when:
        calc.number = number
        then:
        calc.display.displayNumber == "42"
    }

    void setNumberScaleRounding() {
        given:
        def number = 42.5G
        when:
        calc.number = number
        then:
        calc.display.displayNumber == "42.5"
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
        calc.number == 0.0G
        calc.a == 0.0G
        calc.b == 0.0G
        calc.operator == "+"
    }

    def "clearEntered() removes last inputted number"(){
        given:
        calc.digit("1")
        calc.digit("2")
        when:
        calc.clearEntered()
        then:
        calc.number == 0.0G
        calc.display.displayNumber == "0"
    }

    void negate() {
        given:
        calc.digit("1")
        when:
        calc.negate()
        then:
        calc.number == -1.0G
    }

    void sqrt() {
        given:
        calc.digit("4")
        when:
        calc.sqrt()
        then:
        calc.number == 2.0G
    }

    void comma() {
        given:
        assert calc.display.displayNumber == "0"
        when:
        calc.comma()
        then:
        calc.display.displayNumber == "0,"
    }

    void "comma() may be used only once"() {
        given:
        calc.display.displayNumber = "0,3"
        when:
        calc.comma()
        then:
        calc.display.displayNumber == "0,3"
    }

    @Ignore
    void "comma() works with both decimal separators"() {
        given:
        calc.display.displayNumber = "0.3"
        when:
        calc.comma()
        then:
        calc.display.displayNumber == "0.3"
    }

    @Ignore
    void fractionOne() {
        given:
        calc.digit("4")
        when:
        calc.fractionOne()
        then:
        calc.number == 0.25G
    }

    @Ignore
    void percent() {
        when:
        calc.digit("6")
        calc.operator("+")
        calc.digit("50")
        calc.percent()
        then:
        calc.number == 9.0G
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
        assertState(2.0G, 2.0G, 2.0G, "+", false)
        when:
        calc.operator("-")
        then:
        assertState(2.0G, 2.0G, 2.0G, "-", false)
    }

    void enter() {
        given:
        calc.digit("2")
        calc.operator("+")
        calc.digit("1")
        when:
        calc.enter()
        then:
        assertState(3.0G, 3.0G, 1.0G, "+", false)
        when:
        calc.enter()
        then:
        assertState(4.0G, 4.0G, 1.0G, "+", false)
        when:
        calc.enter()
        then:
        assertState(5.0G, 5.0G, 1.0G, "+", false)
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
        a    | b    | operator | number
        2.0G | 3.0G | "+"      | 5.0G
        2.0G | 3.0G | "-"      | -1.0G
        2.0G | 3.0G | "*"      | 6.0G
        6.0G | 2.0G | "/"      | 3.0G
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

    private void assertState(number, a, b, String operator, boolean lastButtonWasDigit) {
        assert calc.a == a
        assert calc.b == b
        assert calc.number == number
        assert calc.operator == operator
        assert calc.lastButtonWasDigit == lastButtonWasDigit
    }
}