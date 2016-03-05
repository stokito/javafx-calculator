package com.example.javafx.calculator

import java.math.BigDecimal

import java.math.BigDecimal.ZERO

class Calculator(private val display: Display) {

    var isLastButtonWasDigit: Boolean = false
    var operator = "+"

    var a = ZERO
    var b = ZERO

    var number: BigDecimal
        get() {
            return BigDecimal(display.displayNumber.replace(',', '.'))
        }
        set(number) {
            display.displayNumber = number.toString()
        }

    fun digit(digit: String) {
        if (isLastButtonWasDigit) {
            display.displayNumber = display.displayNumber + digit
        } else {
            display.displayNumber = digit
        }
        isLastButtonWasDigit = true
    }

    fun clear() {
        number = ZERO
        isLastButtonWasDigit = false
        operator = "+"
        a = ZERO
        b = ZERO
    }

    fun negate() {
        val newNumber = number.negate()
        number = newNumber
    }


    fun sqrt() {
        val newNumber = Math.sqrt(number.toDouble())
        number = BigDecimal(newNumber)
    }

    fun comma() {
        if (!display.displayNumber.contains(",")) {
            display.displayNumber = display.displayNumber + ","
        }
        isLastButtonWasDigit = true
    }

    fun fractionOne() {
        //TODO to implement number = 1 / number
    }

    fun percent() {
        //TODO implement me
    }

    fun operator(operator: String) {
        if (isLastButtonWasDigit) {
            b = number
            calc()
        }
        this.operator = operator
    }

    fun enter() {
        if (isLastButtonWasDigit) {
            b = number
        }
        calc()
    }

    private fun calc() {
        when (operator) {
            "+" -> number = a.add(b)
            "-" -> number = a.subtract(b)
            "*" -> number = a.multiply(b)
            "/" -> number = a.divide(b)
            else -> throw IllegalStateException("Unknown operator " + operator)
        }
        a = number
        isLastButtonWasDigit = false
    }

}
