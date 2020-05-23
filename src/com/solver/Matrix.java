package com.solver;

import java.io.*;
import java.util.Scanner;

public class Matrix {
    private final String filepath;
    private double[][] system;
    private int variables;// and equations

    public Matrix(String filepath) {
        this.filepath = filepath;
    }

    public void writeToMatrix() {
        File equations = new File(filepath);
        try (Scanner scan = new Scanner(equations)) {
            if (scan.hasNext()) {
                variables = scan.nextInt();
                system = new double[variables][variables + 1];
            }
            for (int i = 0; i < system.length; i++) {
                for (int k = 0; k < system[i].length; k++) {
                    system[i][k] = scan.nextDouble();
                    System.out.print(system[i][k] + "\t");
                }
                System.out.println();
            }
        } catch (FileNotFoundException e) {
            System.out.println("The element of the system is not found!");
        }
    }

    public void findOfValues() {
        //put the row with the first element not equal to zero
        // in the first place
        double firstElement;
        for (int i = 0; i < variables; i++) {
            //если элемент опорной строки, индекс которого равен номеру
            //опорной строки, равен нулю, то меняем все опорную строку
            //на первую попавшуюся строку снизу, в столбце которого нет нуля.
            if (system[i][i] == 0) {
                for (int z = i; z < variables; z++) {
                    if (system[z][i] > 0) {
                        double[] timeRow = system[i];
                        system[i] = system[z];
                        system[z] = timeRow;
                        break;
                    }
                }
            }

            //все элементы опорной строки делим на первый слева ненулевой
            //элемент этой строки.
            firstElement = system[i][i];
            for (int k = i; k < system[i].length; k++) {
                system[i][k] /= firstElement;
            }

            //из оставшихся снизу строк вычитаем опорную строку, умноженную
            //на элемент, индекс которого равен номеру опорной строки
            for (int n = i + 1; n < variables; n++) {
                firstElement = system[n][i];
                for (int m = i; m < system[i].length; m++) {
                    system[n][m] -= (system[i][m] * firstElement);
                }
            }
        }
        System.out.println();

        for (int i = 0; i < system.length; i++) {
            for (int k = 0; k < system[i].length; k++) {
                System.out.printf("%5.1f", system[i][k]);
            }
            System.out.println();
        }
        //в качестве опорной строки выбираем последнюю и
        //вычитаем из каждой строки выше опорную строку,
        //умноженную на элемент этой строки с индексом
        //равным номеру опорной строки
        for (int i = variables - 1; i >= 0; i--) {
            for (int n = i - 1; n >= 0; n--) {
                firstElement = system[n][i];
                for (int m = i; m < system[i].length; m++) {
                    system[n][m] -= (system[i][m] * firstElement);
                }
            }
        }
        System.out.println();

        for (int i = 0; i < system.length; i++) {
            for (int k = 0; k < system[i].length; k++) {
                System.out.printf("%5.1f", system[i][k]);
            }
            System.out.println();
        }
    }


    public void writeVariables(String out) throws IOException {
        File file = new File(out);
        try (PrintWriter printWriter = new PrintWriter(file)) {
            String result = "";
            for (int i = 0; i < variables; i++) {
                result = String.format("%.4f", system[i][variables]);
                printWriter.write(result + "\n");
            }
        } catch (IOException e) {
            System.out.println("tyty");
        }
    }
}





/*
System.out.println();

        for (int i = 0; i < system.length; i++) {
            for (int k = 0; k < system[i].length; k++) {
                System.out.print(system[i][k] + "\t");
            }
            System.out.println();
        }

 */