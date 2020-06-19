package com.solver;

public class ReturnRunCommand implements Command {
    Matrix matrix;

    ReturnRunCommand(Matrix matrix) {
        this.matrix = matrix;
    }

    @Override
    public void execute() {

        if (!matrix.getSolution().equals("")) {
            return;
        }
        //в качестве опорной строки выбираем последнюю и
        //вычитаем из каждой строки выше опорную строку,
        //умноженную на элемент этой строки с индексом
        //равным номеру опорной строки
        double firstElement;

        for (int i = matrix.getVariables() - 1; i >= 0; i--) {
            for (int n = i - 1; n >= 0; n--) {
                System.out.println(n);
                firstElement = matrix.system[n][i];
                for (int m = i; m < matrix.system[i].length; m++) {
                    System.out.println(m);
                    matrix.system[n][m] -= (matrix.system[i][m] * firstElement);
                }
            }
        }

        matrix.getMatrix();
        swapColumnsBack();
    }

    public void swapColumnsBack() {
        double timeElement;

        for (int i = 0; i < matrix.listOfColumns.length; i++) {
            if (matrix.listOfColumns[i] != i) {
                for (int z = i + 1; z < matrix.listOfColumns.length; z++) {
                    if (matrix.listOfColumns[z] == i) {
                        for (int k = 0; k < matrix.getEquations(); k++) {
                            timeElement = matrix.system[k][i];
                            matrix.system[k][i] = matrix.system[k][z];
                            matrix.system[k][z] = timeElement;

                            int timeColumn = matrix.listOfColumns[i];
                            matrix.listOfColumns[i] = matrix.listOfColumns[z];
                            matrix.listOfColumns[z] = timeColumn;
                        }
                    }
                }

            }
        }
    }
}