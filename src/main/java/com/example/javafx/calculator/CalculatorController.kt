package com.example.javafx.calculator;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class CalculatorController implements Display {
    @FXML
    private TextField display;

    private Calculator calc;

    public CalculatorController() {
        calc = new Calculator(this);
    }

    @Override
    public String getDisplayNumber() {
        return display.getText();
    }

    @Override
    public void setDisplayNumber(String displayNumber) {
        display.setText(displayNumber);
    }

    public void buttonBackSpaceClick(ActionEvent actionEvent) {
        System.out.println("BackSpace clicked");
    }

    public void buttonDigitClick(ActionEvent actionEvent) {
        Button button = (Button) actionEvent.getSource();
        String digit = button.getText();
        calc.digit(digit);
    }

    public void buttonClearClick(ActionEvent actionEvent) {
        calc.clear();
    }

    public void buttonCommaClick(ActionEvent actionEvent) {
        calc.comma();
    }


    public void buttonNegateClick(ActionEvent actionEvent) {
        calc.negate();
    }

    public void buttonSqrtClick(ActionEvent actionEvent) {
        calc.sqrt();
    }

    public void buttonFractionOneClick(ActionEvent actionEvent) {
        calc.fractionOne();
    }

    public void buttonPercentClick(ActionEvent actionEvent) {
        calc.percent();
    }

    public void buttonOperatorClick(ActionEvent actionEvent) {
        Button button = (Button) actionEvent.getSource();
        String operator = button.getText();
        calc.operator(operator);
    }

    public void buttonEnterClick(ActionEvent actionEvent) {
        calc.enter();
    }
}
