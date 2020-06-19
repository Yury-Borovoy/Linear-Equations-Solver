package com.solver;

import java.util.Arrays;

public class StraightRunCommand implements Command{
    Matrix matrix;

    StraightRunCommand(Matrix matrix) {
        this.matrix = matrix;
    }

    @Override
    public void execute() {
        //создаем массив, который будем использовать
        //для перестановки столбцов
        matrix.listOfColumns  = new int[matrix.getVariables()];

        for (int i = 0; i < matrix.getVariables(); i++) {
            matrix.listOfColumns[i] = i;
        }

        for (int i = 0; i < matrix.getEquations(); i++) {
            lookForMaxElement(i, i);
            if (matrix.system[i][i] == 0) {
                for (int z = i + 1; z < matrix.getVariables(); z++) {
                    lookForMaxElement(i, z);
                    if (matrix.system[i][z] != 0) {
                        swapColumns(i, z);
                        break;
                    }
                }
            }
            lookForMaxElement(i, i);
        }
        matrix.getMatrix();
        divideAndSubtract();

        matrix.getMatrix();
        System.out.println(Arrays.toString(matrix.listOfColumns));
    }

    //ищем больший элемент в указанном столбце и переставляем
    //строку с этим элементом на место строки, где должен быь
    // опорный элемент
    public void lookForMaxElement(int i, int k) {

        double supportingElement = 0.0;
        int rowWithElement = 0;
        double[] timeRow;

        for (int a = i; a < matrix.getEquations(); a++) {
            if(Math.abs(matrix.system[a][k]) > supportingElement){
                supportingElement = matrix.system[a][k];
                rowWithElement = a;
            }
        }

        if (supportingElement != matrix.system[i][k]) {
            timeRow = matrix.system[i];
            matrix.system[i] = matrix.system[rowWithElement];
            matrix.system[rowWithElement] = timeRow;
        }
    }

    //Все элементы опорной строки делим на первый слева ненулевой
    //элемент этой строки.
    //Затем из оставшихся снизу строк вычитаем опорную строку, умноженную
    //на элемент, индекс которого равен номеру опорной строки.
    public void divideAndSubtract() {
        double firstElement;

        for (int i = 0; i < matrix.getEquations(); i++) {
            firstElement = matrix.system[i][i];
            if (firstElement == 0) {
                lookForMaxElement(i, i);
                firstElement = matrix.system[i][i];
            }

            for (int k = i; k < matrix.system[i].length; k++) {
                //условие, которое предотвращает деление нуля на ноль
                if (matrix.system[i][k] != 0) {
                    matrix.system[i][k] /= firstElement;
                }
            }


            for (int n = i + 1; n < matrix.getEquations(); n++) {
                firstElement = matrix.system[n][i];
                for (int m = i; m < matrix.system[i].length; m++) {
                    matrix.system[n][m] -= (matrix.system[i][m] * firstElement);
                }
            }

        }
    }

    public void swapColumns(int i, int z) {
        double timeElement;
        int timeColumn = matrix.listOfColumns[i];
        matrix.listOfColumns[i] = matrix.listOfColumns[z];
        matrix.listOfColumns[z] = timeColumn;


        for (int k = 0; k < matrix.getEquations(); k++) {
            timeElement = matrix.system[k][i];
            matrix.system[k][i] = matrix.system[k][z];
            matrix.system[k][z] = timeElement;
        }
    }
}
