package com.solver;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Matrix {

    private String filepathIn;
    protected String filepathOut;
    protected String[][] commonMatrix;
    protected int [] listOfColumns;
    protected int variables;
    protected int equations;
    boolean complexNumbers = false;
    protected String solution = "no";

    Matrix(String filepathIn, String filepathOut) {
        this.filepathIn = filepathIn;
        this.filepathOut = filepathOut;
        setMatrix();
    }

    //конструктор для ComplexMatrix и SimpleMatrix
    public Matrix(String filepathOut, String [][] commonMatrix, int [] listOfColumns, int variables, int equations, String solution) {
        this.filepathOut = filepathOut;
        this.commonMatrix = commonMatrix;
        this.listOfColumns = listOfColumns;
        this.variables = variables;
        this.equations = equations;
        this.solution = solution;
    }


    void setMatrix() {
        File equationsFromFile = new File(filepathIn);

        try (Scanner scan = new Scanner(equationsFromFile)) {
            String [] input = scan.nextLine().split(" ");
            variables = Integer.parseInt(input[0]);
            equations = Integer.parseInt(input[1]);
            listOfColumns = new int[variables];
            commonMatrix = new String[equations][variables + 1];

            for (int i = 0; i < commonMatrix.length; i++) {
                input = scan.nextLine().split(" ");
                for (int k = 0; k < commonMatrix[i].length; k++) {
                    commonMatrix[i][k] = input[k];
                    if (commonMatrix[i][k].matches("-?[0-9]+(\\.[0-9]+)?[+-][0-9]*(\\.[0-9]*)?i")) {
                        complexNumbers = true;
                    } else if (commonMatrix[i][k].matches("[+-]?[0-9]*(\\.[0-9]*)?i")) {
                        complexNumbers = true;
                    }
                }
            }
        }catch (FileNotFoundException e) {
            System.out.println("The element of the system is not found!");
        }
    }



    void solveComplexMatrix() {
        ComplexMatrix complexMatrix = new ComplexMatrix(filepathOut, commonMatrix, listOfColumns, variables, equations, solution);
        complexMatrix.setMatrix();
        complexMatrix.getMatrix();

        complexMatrix.executeStraightRun();
        CheckingOfMatrix checkingOfMatrix = new CheckingOfMatrix(complexMatrix);
        checkingOfMatrix.checkMatrix();
        complexMatrix.executeReturnRun();
        complexMatrix.editResult();
        complexMatrix.getMatrix();

        WritingToFile writingToFile = new WritingToFile(complexMatrix);
        writingToFile.writeToFile();
    }

    void solveSimpleMatrix() {
        SimpleMatrix simpleMatrix = new SimpleMatrix(filepathOut, commonMatrix, listOfColumns, variables, equations, solution);
        simpleMatrix.setMatrix();
        simpleMatrix.getMatrix();

        simpleMatrix.executeStraightRun();
        CheckingOfMatrix checkingOfMatrix = new CheckingOfMatrix(simpleMatrix);
        checkingOfMatrix.checkMatrix();
        simpleMatrix.executeReturnRun();
        simpleMatrix.editResult();
        simpleMatrix.getMatrix();

        WritingToFile writingToFile = new WritingToFile(simpleMatrix);
        writingToFile.writeToFile();
    }
}
