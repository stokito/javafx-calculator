package com.example.javafx.calculator

import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Unroll

class CalculatorFlowSpec extends Specification {
    @Shared
    private Calculator calc = new Calculator(new DisplayStub())

    @Unroll("operator(#digit, #operator, #a, #b, #number, #opField, #lastButtonWasDigit)")
    void operator(String digit, String operator, number, a, b, opField, lastButtonWasDigit) {
        given:
        if (digit) {
            calc.digit(digit)
        } else if (operator) {
            calc.operator(operator)
        } else {
            calc.enter()
        }
        expect:
        calc.number == number
        calc.a == a
        calc.b == b
        calc.operator == opField
        calc.lastButtonWasDigit == lastButtonWasDigit
        where:
        digit | operator | number | a  | b  | opField | lastButtonWasDigit
        "2"   | null     | 2D     | 0D | 0D | "+"     | true
        null  | "+"      | 2D     | 2D | 2D | "+"     | false
        "3"   | null     | 3D     | 2D | 2D | "+"     | true
        null  | "+"      | 5D     | 5D | 3D | "+"     | false
        "3"   | null     | 3D     | 5D | 3D | "+"     | true
        null  | "-"      | 8D     | 8D | 3D | "-"     | false
        "1"   | null     | 1D     | 8D | 3D | "-"     | true
        null  | null     | 7D     | 7D | 1D | "-"     | false
    }

}