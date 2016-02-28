package com.example.javafx.calculator;

public class Calculator {
    private final Display display;

    private boolean lastButtonWasDigit;

    public Calculator(Display display) {
        this.display = display;
    }

    public boolean isLastButtonWasDigit() {
        return lastButtonWasDigit;
    }

    public double getNumber() {
        return Double.parseDouble(display.getDisplayNumber());
    }

    public void setNumber(double number) {
        display.setDisplayNumber(String.valueOf(number));
    }

    public void digit(String digit) {
        if (lastButtonWasDigit) {
            display.setDisplayNumber(display.getDisplayNumber() + digit);
        } else {
            display.setDisplayNumber(digit);
        }
        lastButtonWasDigit = true;
    }

    public void clear() {
        setNumber(0);
        lastButtonWasDigit = false;
    }

    public void negate() {
        double newNumber = getNumber() * -1;
        setNumber(newNumber);
    }


    public void sqrt() {
        double newNumber = Math.sqrt(getNumber());
        setNumber(newNumber);
    }

    public void comma() {
        if (!display.getDisplayNumber().contains(",")) {
            display.setDisplayNumber(display.getDisplayNumber() + ",");
        }
        lastButtonWasDigit = true;
    }
}
