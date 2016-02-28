package com.example.javafx.calculator;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class CalculatorController {
    @FXML
    private TextField display;
    private boolean lastButtonWasDigit;


    public String getDisplayNumber() {
        return display.getText();
    }

    public void setDisplayNumber(String displayNumber) {
        display.setText(displayNumber);
    }

    public double getNumber() {
        return Double.parseDouble(getDisplayNumber());
    }

    public void setNumber(double number) {
        setDisplayNumber(String.valueOf(number));
    }

    public void buttonBackSpaceClick(ActionEvent actionEvent) {
        System.out.println("BackSpace clicked");
    }

    public void buttonDigitClick(ActionEvent actionEvent) {
        Button button = (Button) actionEvent.getSource();
        String digit = button.getText();
        if (lastButtonWasDigit) {
            setDisplayNumber(getDisplayNumber() + digit);
        } else {
            setDisplayNumber(digit);
        }
        lastButtonWasDigit = true;
    }

    public void buttonClearClick(ActionEvent actionEvent) {
        setNumber(0);
        lastButtonWasDigit = false;
    }

    public void buttonCommaClick(ActionEvent actionEvent) {
        if (!getDisplayNumber().contains(",")) {
            setDisplayNumber(getDisplayNumber() + ",");
        }
        lastButtonWasDigit = true;
    }

    public void buttonNegateClick(ActionEvent actionEvent) {
        double newNumber = getNumber() * -1;
        setNumber(newNumber);
    }

    public void buttonSqrtClick(ActionEvent actionEvent) {
        double newNumber = Math.sqrt(getNumber());
        setNumber(newNumber);
    }
}
