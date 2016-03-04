package com.example.javafx.calculator;

import java.math.BigDecimal;

import static java.math.BigDecimal.ZERO;

public class Calculator {
    private final Display display;

    private boolean lastButtonWasDigit;
    private String operator = "+";

    private BigDecimal a = ZERO;
    private BigDecimal b = ZERO;

    public Calculator(Display display) {
        this.display = display;
    }

    public BigDecimal getA() {
        return a;
    }

    public BigDecimal getB() {
        return b;
    }

    public boolean isLastButtonWasDigit() {
        return lastButtonWasDigit;
    }

    public String getOperator() {
        return operator;
    }

    public BigDecimal getNumber() {
        return new BigDecimal(display.getDisplayNumber().replace(',', '.'));
    }

    public void setNumber(BigDecimal number) {
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
        setNumber(ZERO);
        lastButtonWasDigit = false;
        operator  = "+";
        a = ZERO;
        b = ZERO;
    }

    public void negate() {
        BigDecimal newNumber = getNumber().negate();
        setNumber(newNumber);
    }


    public void sqrt() {
        double newNumber = Math.sqrt(getNumber().doubleValue());
        setNumber(new BigDecimal(newNumber));
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
            b = getNumber();
            calc();
        }
        this.operator = operator;
    }

    public void enter() {
        if (lastButtonWasDigit) {
            b = getNumber();
        }
        calc();
    }

    private void calc() {
        switch (operator) {
            case "+":
                setNumber(a.add(b));
                break;
            case "-":
                setNumber(a.subtract(b));
                break;
            case "*":
                setNumber(a.multiply(b));
                break;
            case "/":
                setNumber(a.divide(b));
                break;
            default:
                throw new IllegalStateException("Unknown operator " + operator);
        }
        a = getNumber();
        lastButtonWasDigit = false;
    }

}
