package com.solver;


import java.math.BigDecimal;
import java.math.MathContext;

public class ComplexNumber {

    protected BigDecimal realPart;
    protected BigDecimal imaginaryPart;

    ComplexNumber(BigDecimal realPart, BigDecimal imaginaryPart) {
        this.realPart = realPart;
        this.imaginaryPart = imaginaryPart;
    }

    public ComplexNumber add(ComplexNumber b) {
        ComplexNumber a = this;
        BigDecimal realPart = a.realPart.add(b.realPart);
        BigDecimal imaginaryPart = a.imaginaryPart.add(b.imaginaryPart);

        return new ComplexNumber(realPart, imaginaryPart);
    }

    public ComplexNumber subtract(ComplexNumber b) {
        ComplexNumber a = this;
        BigDecimal realPart = a.realPart.subtract(b.realPart);
        BigDecimal imaginaryPart = a.imaginaryPart.subtract(b.imaginaryPart);

        return new ComplexNumber(realPart, imaginaryPart);
    }

    public ComplexNumber multiply(ComplexNumber b) {
        ComplexNumber a = this;
        BigDecimal realPart = (a.realPart.multiply(b.realPart)).subtract(a.imaginaryPart.multiply(b.imaginaryPart));
        BigDecimal imaginaryPart = (a.realPart.multiply(b.imaginaryPart)).add(a.imaginaryPart.multiply(b.realPart));

        return new ComplexNumber(realPart, imaginaryPart);
    }

    private ComplexNumber reciprocal() {
        BigDecimal scale = (this.realPart.multiply(this.realPart)).add(this.imaginaryPart.multiply(this.imaginaryPart));
        if (scale.equals(BigDecimal.valueOf(0))) {
            return new ComplexNumber(BigDecimal.valueOf(0), BigDecimal.valueOf(0));
        }
        return new ComplexNumber(realPart.divide(scale, MathContext.DECIMAL32),
                (imaginaryPart.multiply(BigDecimal.valueOf(-1))).divide(scale, MathContext.DECIMAL32));
    }

    public ComplexNumber divide(ComplexNumber b) {
        return this.multiply(b.reciprocal());
    }

    public BigDecimal module() {
        return realPart.multiply(realPart).add(imaginaryPart.multiply(imaginaryPart));
    }
}
