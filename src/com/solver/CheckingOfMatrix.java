package com.solver;

import java.math.BigDecimal;

public class CheckingOfMatrix{

    ComplexMatrix complexMatrix;
    SimpleMatrix simpleMatrix;
    private int zerosInRow = 0;
    private int emptyRow = 0;
    private int zerosInColumns = 0;
    String matrix;


    public CheckingOfMatrix(ComplexMatrix complexMatrix) {
        this.complexMatrix = complexMatrix;
        matrix = "complex";
    }
    public CheckingOfMatrix(SimpleMatrix simpleMatrix) {
        this.simpleMatrix = simpleMatrix;
        matrix = "simple";
    }

    public void checkMatrix() {
        if (matrix.equals("complex")) {
            checkForZeroSolutionsForComplex();
            if (complexMatrix.solution.equals("no")) {
                checkForManySolutionsForComplex();
            }
        } else {
            checkForZeroSolutionsForSimple();
            if (simpleMatrix.solution.equals("no")) {
                checkForManySolutionsForSimple();
            }
        }
    }

    public void checkForZeroSolutionsForComplex() {



        //ищем строки, в которых на месте всех переменных нули,
        //а на месте последнего значения не ноль
        for (int i = 0; i < complexMatrix.equations; i++) {
            for (int z = 0; z <= complexMatrix.variables; z++) {
                if (complexMatrix.system[i][z].realPart.compareTo(BigDecimal.ZERO) == 0 && complexMatrix.system[i][z].imaginaryPart.compareTo(BigDecimal.ZERO) == 0) {
                    zerosInRow++;
                }
            }
            if (zerosInRow == complexMatrix.variables && (complexMatrix.system[i][complexMatrix.variables].realPart.compareTo(BigDecimal.ZERO) != 0 || complexMatrix.system[i][complexMatrix.variables].imaginaryPart.compareTo(BigDecimal.ZERO) != 0)) {
                complexMatrix.solution = "No solutions";
                return;
            } else if (zerosInRow > complexMatrix.variables){
                emptyRow++;
                zerosInRow = 0;
            } else{
                zerosInRow = 0;
            }
        }

        //если количество переменных меньше количества не пустых уравнений,
        //то система также не имеет решений
        if ((complexMatrix.variables ) < (complexMatrix.equations - emptyRow)) {
            complexMatrix.solution = "No solutions";
        }

    }

    public void checkForManySolutionsForComplex() {

        //если количество переменных меньше количества не пустых уравнений,
        //то система также не имеет решений
        if (complexMatrix.variables  > (complexMatrix.equations - emptyRow)) {
            complexMatrix.solution = "Infinitely many solutions";
            return;
        }

        //если в системе есть полностью нулевые столбцы с переменными,
        //то система имеет множество решений
        for (int i = 0; i < complexMatrix.variables; i++) {
            for (int z = 0; z < complexMatrix.equations; z++) {
                if (complexMatrix.system[i][z].realPart.compareTo(BigDecimal.ZERO) == 0 && complexMatrix.system[i][z].imaginaryPart.compareTo(BigDecimal.ZERO) == 0) {
                    zerosInColumns++;
                }
            }
            if (zerosInColumns == complexMatrix.equations) {
                complexMatrix.solution = "Infinitely many solutions";
                return;
            }else{
                zerosInColumns = 0;
            }
        }
    }


    public void checkForZeroSolutionsForSimple() {
        //ищем строки, в которых на месте всех переменных нули,
        //а на месте последнего значения не ноль
        for (int i = 0; i < simpleMatrix.equations; i++) {
            for (int z = 0; z <= simpleMatrix.variables; z++) {
                if (simpleMatrix.system[i][z] == 0) {
                    zerosInRow++;
                }
            }
            if (zerosInRow == simpleMatrix.variables && simpleMatrix.system[i][simpleMatrix.variables] != 0 ) {
                simpleMatrix.solution = "No solutions";
                return;
            } else if (zerosInRow > simpleMatrix.variables){
                emptyRow++;
                zerosInRow = 0;
            } else{
                zerosInRow = 0;
            }
        }

        //если количество переменных меньше количества не пустых уравнений,
        //то система также не имеет решений
        if ((simpleMatrix.variables ) < (simpleMatrix.equations - emptyRow)) {
            simpleMatrix.solution = "No solutions";
        }

    }

    public void checkForManySolutionsForSimple() {
        //если количество переменных меньше количества не пустых уравнений,
        //то система также не имеет решений
        if (simpleMatrix.variables  > (simpleMatrix.equations - emptyRow)) {
            simpleMatrix.solution = "Infinitely many solutions";
            return;
        }

        //если в системе есть полностью нулевые столбцы с переменными,
        //то система имеет множество решений
        for (int i = 0; i < simpleMatrix.variables; i++) {
            for (int z = 0; z < simpleMatrix.equations; z++) {
                if (simpleMatrix.system[i][z] == 0) {
                    zerosInColumns++;
                }
            }
            if (zerosInColumns == simpleMatrix.equations) {
                simpleMatrix.solution = "Infinitely many solutions";
                return;
            }else{
                zerosInColumns = 0;
            }
        }
    }
}