package com.example.javafx.calculator

import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.scene.control.Button
import javafx.scene.control.TextField

class CalculatorController : Display {
    @FXML
    lateinit var display: TextField

    private val calc: Calculator

    init {
        calc = Calculator(this)
    }

    override var displayNumber: String
        get() = display.text
        set(displayNumber) {
            display.text = displayNumber
        }

    fun buttonBackSpaceClick(actionEvent: ActionEvent) {
        println("BackSpace clicked")
    }

    fun buttonDigitClick(actionEvent: ActionEvent) {
        val button = actionEvent.source as Button
        val digit = button.text
        calc.digit(digit)
    }

    fun buttonClearClick(actionEvent: ActionEvent) {
        calc.clear()
    }

    fun buttonCommaClick(actionEvent: ActionEvent) {
        calc.comma()
    }


    fun buttonNegateClick(actionEvent: ActionEvent) {
        calc.negate()
    }

    fun buttonSqrtClick(actionEvent: ActionEvent) {
        calc.sqrt()
    }

    fun buttonFractionOneClick(actionEvent: ActionEvent) {
        calc.fractionOne()
    }

    fun buttonPercentClick(actionEvent: ActionEvent) {
        calc.percent()
    }

    fun buttonOperatorClick(actionEvent: ActionEvent) {
        val button = actionEvent.source as Button
        val operator = button.text
        calc.operator(operator)
    }

    fun buttonEnterClick(actionEvent: ActionEvent) {
        calc.enter()
    }

    fun buttonClearEnteredClick(actionEvent: ActionEvent) {
        calc.clearEntered()
    }
}
