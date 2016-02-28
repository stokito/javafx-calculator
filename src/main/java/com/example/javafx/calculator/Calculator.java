package com.example.javafx.calculator;

public class Calculator {
    private final Display display;

    private boolean lastButtonWasDigit;
    private String operator = "+";

    private double a;
    private double b;

    public Calculator(Display display) {
        this.display = display;
    }

    public double getA() {
        return a;
    }

    public double getB() {
        return b;
    }

    public boolean isLastButtonWasDigit() {
        return lastButtonWasDigit;
    }

    public String getOperator() {
        return operator;
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
        operator  = "+";
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

    public void fractionOne() {
        //TODO to implement number = 1 / number
    }

    public void percent() {
        //TODO implement me
    }

    public void operator(String operator) {
        if (lastButtonWasDigit){
            if (this.operator.equals("+")) {
                setNumber(a + b);
            } else if (this.operator.equals("-")) {
                setNumber(a - b);
            } else if (this.operator.equals("*")) {
                setNumber(a * b);
            } else if (this.operator.equals("/")) {
                setNumber(a / b);
            } else {
                throw new IllegalStateException("Unknown operator " + this.operator);
            }
        }
        this.operator = operator;
        lastButtonWasDigit = false;
    }

    public void enter() {
        //TODO
    }

}
