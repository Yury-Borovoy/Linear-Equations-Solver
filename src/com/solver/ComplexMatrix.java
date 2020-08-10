package com.solver;


import java.math.BigDecimal;

public class ComplexMatrix extends Matrix{

    ComplexNumber[][] system;

    ComplexMatrix(String filepathOut, String [][] commonMatrix, int [] listOfColumns, int variables, int equations, String solution) {
        super(filepathOut, commonMatrix, listOfColumns, variables, equations, solution);
    }

    @Override
    void setMatrix() {
        system = new ComplexNumber[equations][variables + 1];

        for (int i = 0; i < commonMatrix.length; i++) {
            for (int k = 0; k < commonMatrix[i].length; k++) {
                system[i][k] = makeComplexElement(commonMatrix[i][k]);
            }
        }
    }

    ComplexNumber makeComplexElement(String element) {
        String fullComplexNumberRegex = "-?[0-9]+(\\.[0-9]+)?[+-][0-9]*(\\.[0-9]*)?i";
        String imaginaryPartRegex = "[+-]?[0-9]*(\\.[0-9]*)?i";

        if (element.matches("0")) {
            return new ComplexNumber(BigDecimal.valueOf(0), BigDecimal.valueOf(0));
        }else if (element.matches(fullComplexNumberRegex)) {
            String realPart = element.split(imaginaryPartRegex)[0];
            String imaginaryPart = element.split(realPart + "\\+?", 2)[1];
            imaginaryPart = editImaginaryPart(imaginaryPart);
            return new ComplexNumber(new BigDecimal(realPart), new BigDecimal(imaginaryPart));
        }else if (element.matches(imaginaryPartRegex)) {
            String imaginaryPart = editImaginaryPart(element);
            return new ComplexNumber(BigDecimal.valueOf(0), new BigDecimal(imaginaryPart));
        } else{
            String realPart = element.split(imaginaryPartRegex)[0];
            return new ComplexNumber(new BigDecimal(realPart), BigDecimal.valueOf(0));
        }
    }

    private static String editImaginaryPart(String imaginaryPart) {
        if (imaginaryPart.length() == 1) {
            return "1";
        } else if (imaginaryPart.length() == 2 && imaginaryPart.charAt(0) == '-') {
            return "-1";
        } else {
            return imaginaryPart.split("i")[0];
        }
    }

    void getMatrix() {

        for (int i = 0; i < system.length; i++) {
            for (int k = 0; k < system[i].length; k++) {
                if (system[i][k].realPart.signum() == 0) {
                    if (system[i][k].imaginaryPart.compareTo(BigDecimal.ONE) == 0) {
                        System.out.printf("%10s", "i");
                    } else if (system[i][k].imaginaryPart.compareTo(BigDecimal.ZERO) > 0) {
                        System.out.printf("%10.4f%s", system[i][k].imaginaryPart, "i");
                    }
                    else if (system[i][k].imaginaryPart.compareTo(BigDecimal.valueOf(-1)) == 0) {
                        System.out.printf("%10s", "-i");
                    }else if (system[i][k].imaginaryPart.compareTo(BigDecimal.ZERO) < 0) {
                        System.out.printf("%10.4f%s", system[i][k].imaginaryPart, "i");
                    } else {
                        System.out.printf("%10s", "0");
                    }

                } else {
                    if (system[i][k].imaginaryPart.compareTo(BigDecimal.ONE) == 0) {
                        System.out.printf("%10.4f+%s", system[i][k].realPart, "i");
                    }else if (system[i][k].imaginaryPart.compareTo(BigDecimal.ZERO) > 0) {
                        System.out.printf("%10.4f%+.4f%s", system[i][k].realPart, system[i][k].imaginaryPart, "i");
                    }else if (system[i][k].imaginaryPart.compareTo(BigDecimal.valueOf(-1)) == 0) {
                        System.out.printf("%10.4f-%s", system[i][k].realPart, "i");
                    }else if (system[i][k].imaginaryPart.compareTo(BigDecimal.ZERO) < 0) {
                        System.out.printf("%10.4f%.4f%s", system[i][k].realPart, system[i][k].imaginaryPart, "i");
                    } else {
                        System.out.printf("%10.4f", system[i][k].realPart);
                    }
                }
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
            if (system[i][i].realPart.compareTo(BigDecimal.ZERO) == 0 | system[i][i].imaginaryPart.compareTo(BigDecimal.ZERO) == 0) {
                for (int z = i + 1; z < variables; z++) {
                    lookForMaxElement(i, z);
                    if (system[i][z].realPart.compareTo(BigDecimal.ZERO) != 0 | system[i][z].imaginaryPart.compareTo(BigDecimal.ZERO) != 0) {
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

        ComplexNumber supportingElement = new ComplexNumber(BigDecimal.valueOf(0), BigDecimal.valueOf(0));
        int rowWithElement = 0;
        ComplexNumber[] timeRow;

        for (int a = i; a < equations; a++) {
            if(Math.abs(system[a][k].module().compareTo(supportingElement.module())) > 0){
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
        ComplexNumber timeElement;
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
        ComplexNumber firstElement;

        for (int i = 0; i < equations; i++) {
            firstElement = system[i][i];
            if (firstElement.realPart.compareTo(BigDecimal.ZERO) == 0 && firstElement.imaginaryPart.compareTo(BigDecimal.ZERO) == 0) {
                lookForMaxElement(i, i);
                firstElement = system[i][i];
            }

            //делим каждый елемент строки на первый элемент этой строки
            for (int k = i; k < system[i].length; k++) {
                //условие, которое предотвращает деление нуля на ноль
                if (system[i][k].realPart.compareTo(BigDecimal.ZERO) != 0 || system[i][k].imaginaryPart.compareTo(BigDecimal.ZERO) != 0) {
                    system[i][k] = system[i][k].divide(firstElement);
                }
            }

            //отнимаем от следующей строки предыдущую
            for (int n = i + 1; n < equations; n++) {
                firstElement = system[n][i];
                for (int m = i; m < system[i].length; m++) {
                    system[n][m] = system[n][m].subtract(system[i][m].multiply(firstElement));
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
        ComplexNumber firstElement;

        for (int i = variables - 1; i >= 0; i--) {
            for (int n = i - 1; n >= 0; n--) {
                firstElement = system[n][i];
                for (int m = i; m < system[i].length; m++) {
                    system[n][m] = system[n][m].subtract(system[i][m].multiply(firstElement));
                }
            }
        }
        swapColumnsBack();
    }

    public void swapColumnsBack() {
        ComplexNumber timeElement;

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
                if (system[i][k].realPart.abs().compareTo(BigDecimal.valueOf(0.0001)) < 0) {
                    system[i][k].realPart = BigDecimal.ZERO;
                }
                if (system[i][k].imaginaryPart.abs().compareTo(BigDecimal.valueOf(0.0001)) < 0) {
                    system[i][k].imaginaryPart = BigDecimal.ZERO;
                }
            }
        }
    }
}
