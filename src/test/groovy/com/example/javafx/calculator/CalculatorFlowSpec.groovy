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
        digit | operator | number | a    | b    | opField | lastButtonWasDigit
        "2"   | null     | 2.0G   | 0.0G | 0.0G | "+"     | true
        null  | "+"      | 2.0G   | 2.0G | 2.0G | "+"     | false
        "3"   | null     | 3.0G   | 2.0G | 2.0G | "+"     | true
        null  | "+"      | 5.0G   | 5.0G | 3.0G | "+"     | false
        "3"   | null     | 3.0G   | 5.0G | 3.0G | "+"     | true
        null  | "-"      | 8.0G   | 8.0G | 3.0G | "-"     | false
        "1"   | null     | 1.0G   | 8.0G | 3.0G | "-"     | true
        null  | null     | 7.0G   | 7.0G | 1.0G | "-"     | false
    }

}