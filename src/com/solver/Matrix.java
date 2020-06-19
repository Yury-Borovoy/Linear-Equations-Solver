package com.solver;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Matrix {

    private final String filepathIn;
    private final String filepathOut;
    double [][] system;
    int [] listOfColumns;  //будем использовать listOfColumns для фиксации перестановок столбцов
    private int variables;
    private int equations;
    private String solution = "";

    Matrix(String filepathIn, String filepathOut) {
        this.filepathIn = filepathIn;
        this.filepathOut = filepathOut;
        setMatrix();
    }

    void setMatrix() {
        File equationsFromFile = new File(filepathIn);
        try (Scanner scan = new Scanner(equationsFromFile)) {
            if (scan.hasNext()) {
                variables = scan.nextInt();
                equations = scan.nextInt();
                listOfColumns = new int[variables];
                system = new double[equations][variables + 1];
            }

            for (int i = 0; i < system.length; i++) {
                for (int k = 0; k < system[i].length; k++) {
                    system[i][k] = scan.nextDouble();
                }
            }
        }catch (FileNotFoundException e) {
            System.out.println("The element of the system is not found!");
        }
    }

    void getMatrix() {
        for (double[] i : system) {
            for (double k : i) {
                System.out.printf("%5.1f", k);
            }
            System.out.println();
        }
        System.out.println();
    }

    int getVariables() {
        return variables;
    }

    int getEquations() {
        return equations;
    }

    String getFilepathOut() {
        return filepathOut;
    }

    String getSolution() {
        return solution;
    }

    void setSolution(String solution) {
        this.solution = solution;
    }
}