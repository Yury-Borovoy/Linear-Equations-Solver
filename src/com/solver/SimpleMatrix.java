package com.solver;

public class SimpleMatrix extends Matrix{

    double [][] system;

    SimpleMatrix(String filepathOut, String [][] commonMatrix, int [] listOfColumns, int variables, int equations, String solution) {
        super(filepathOut, commonMatrix, listOfColumns, variables, equations, solution);
    }

    @Override
    void setMatrix() {
        system = new double[equations][variables + 1];
        for (int i = 0; i < commonMatrix.length; i++) {
            for (int k = 0; k < commonMatrix[i].length; k++) {
                system[i][k] = Double.parseDouble(commonMatrix[i][k]);
            }
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

    void executeStraightRun() {
        //заполняем массив, который будем использовать
        //для перестановки столбцов
        for (int i = 0; i < variables; i++) {
            listOfColumns[i] = i;
        }

        //ищем максимальный элемент в столбце и переносим его
        //в нужное нам место на диагонали переменных
        for (int i = 0; i < equations; i++) {
            lookForMaxElement(i, i);
            if (system[i][i] == 0) {
                for (int z = i + 1; z < variables; z++) {
                    lookForMaxElement(i, z);
                    if (system[i][z] != 0) {
                        swapColumns(i, z);
                        break;
                    }
                }
            }
            lookForMaxElement(i, i);
        }
        divideAndSubtract();
    }

    //методы прямого хода

    //ищем больший элемент в указанном столбце и переставляем
    //строку с этим элементом на место строки, где должен быь
    // опорный элемент
    public void lookForMaxElement(int i, int k) {

        double supportingElement = 0.0;
        int rowWithElement = 0;
        double[] timeRow;

        for (int a = i; a < equations; a++) {
            if(Math.abs(system[a][k]) > supportingElement){
                supportingElement = system[a][k];
                rowWithElement = a;
            }
        }

        if (supportingElement != system[i][k]) {
            timeRow = system[i];
            system[i] = system[rowWithElement];
            system[rowWithElement] = timeRow;
        }
    }

    public void swapColumns(int i, int z) {
        double timeElement;
        int timeColumn = listOfColumns[i];
        listOfColumns[i] = listOfColumns[z];
        listOfColumns[z] = timeColumn;


        for (int k = 0; k < equations; k++) {
            timeElement = system[k][i];
            system[k][i] = system[k][z];
            system[k][z] = timeElement;
        }
    }

    //Все элементы опорной строки делим на первый слева ненулевой
    //элемент этой строки.
    //Затем из оставшихся снизу строк вычитаем опорную строку, умноженную
    //на элемент, индекс которого равен номеру опорной строки.
    public void divideAndSubtract() {
        double firstElement;

        for (int i = 0; i < equations; i++) {
            firstElement = system[i][i];
            if (firstElement == 0) {
                lookForMaxElement(i, i);
                firstElement = system[i][i];
            }

            //делим каждый елемент строки на первый элемент этой строки
            for (int k = i; k < system[i].length; k++) {
                //условие, которое предотвращает деление нуля на ноль
                if (system[i][k] != 0) {
                    system[i][k] /= firstElement;
                }
            }

            //отнимаем от следующей строки предыдущую
            for (int n = i + 1; n < equations; n++) {
                firstElement = system[n][i];
                for (int m = i; m < system[i].length; m++) {
                    system[n][m] -= (system[i][m] * firstElement);
                }
            }
        }
    }

    //методы обратного хода

    public void executeReturnRun() {

        if (!solution.equals("no")) {
            return;
        }
        //в качестве опорной строки выбираем последнюю и
        //вычитаем из каждой строки выше опорную строку,
        //умноженную на элемент этой строки с индексом
        //равным номеру опорной строки
        double firstElement;

        for (int i = variables - 1; i >= 0; i--) {
            for (int n = i - 1; n >= 0; n--) {
                firstElement = system[n][i];
                for (int m = i; m < system[i].length; m++) {
                    system[n][m] -= (system[i][m] * firstElement);
                }
            }
        }
        swapColumnsBack();
    }

    public void swapColumnsBack() {
        double timeElement;

        for (int i = 0; i < listOfColumns.length; i++) {
            if (listOfColumns[i] != i) {
                for (int z = i + 1; z < listOfColumns.length; z++) {
                    if (listOfColumns[z] == i) {
                        for (int k = 0; k < equations; k++) {
                            timeElement = system[k][i];
                            system[k][i] = system[k][z];
                            system[k][z] = timeElement;

                            int timeColumn = listOfColumns[i];
                            listOfColumns[i] = listOfColumns[z];
                            listOfColumns[z] = timeColumn;
                        }
                    }
                }
            }
        }
    }

    public void editResult() {
        for (int i = 0; i < system.length; i++) {
            for (int k = 0; k < system[i].length; k++) {
                //избавляемся от почти нулей
                if (Math.abs(system[i][k]) < 0.0001) {
                    system[i][k] = 0;
                }
                if (Math.abs(system[i][k]) < 0.0001) {
                    system[i][k] = 0;
                }
            }
        }
    }


}

