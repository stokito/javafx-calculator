package com.example.javafx.calculator;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class CalculatorController implements Display {
    @FXML
    private TextField display;

    private Calculator calculator;

    public CalculatorController() {
        calculator = new Calculator(this);
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
        calculator.digit(digit);
    }

    public void buttonClearClick(ActionEvent actionEvent) {
        calculator.clear();
    }

    public void buttonCommaClick(ActionEvent actionEvent) {
        calculator.comma();
    }


    public void buttonNegateClick(ActionEvent actionEvent) {
        calculator.negate();
    }

    public void buttonSqrtClick(ActionEvent actionEvent) {
        calculator.sqrt();
    }
}
