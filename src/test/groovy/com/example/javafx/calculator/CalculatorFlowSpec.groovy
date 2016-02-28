package com.example.javafx.calculator

import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Unroll

class CalculatorFlowSpec extends Specification {
    @Shared
    private Calculator calc = new Calculator(new DisplayStub())

    @Unroll("operator(#digit, #operator, #a, #b, #number, #opField, #lastButtonWasDigit)")
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
        digit | operator | number | a  | b  | opField | lastButtonWasDigit
        "2"   | null     | 2D     | 0D | 0D | "+"     | true
        null  | "+"      | 2D     | 2D | 0D | "+"     | false
        "3"   | null     | 3D     | 2D | 0D | "+"     | true
        null  | "+"      | 5D     | 2D | 3D | "+"     | false
        "3"   | null     | 3D     | 5D | 3D | "+"     | true
        null  | "-"      | 8D     | 5D | 3D | "-"     | false
        "1"   | null     | 1D     | 5D | 3D | "-"     | true
        null  | null     | 7D     | 8D | 1D | "-"     | false
    }

}