package com.solver;

public class CheckCommand implements Command{
    Matrix matrix;
    int zerosInRow = 0;
    int emptyRow = 0;
    int zerosInColumns = 0;

    CheckCommand(Matrix matrix) {
        this.matrix = matrix;
    }

    @Override
    public void execute() {
        checkForZeroSolutions();
        if (matrix.getSolution().equals("")) {
            checkForManySolutions();
        }
    }

    public void checkForZeroSolutions() {
        //ищем строки, в которых на месте всех переменных нули,
        //а на месте последнего значения не ноль
        for (int i = 0; i < matrix.getEquations(); i++) {
            for (int z = 0; z <= matrix.getVariables(); z++) {
                if (matrix.system[i][z] == 0) {
                    zerosInRow++;
                    System.out.println(zerosInRow);
                }
            }
            if (zerosInRow == matrix.getVariables() && matrix.system[i][matrix.getVariables()] != 0) {
                matrix.setSolution("No solutions");
                return;
            } else if (zerosInRow > matrix.getVariables()){
                emptyRow++;
                zerosInRow = 0;
            } else{
                zerosInRow = 0;
            }
        }

        //если количество переменных меньше количества не пустых уравнений,
        //то система также не имеет решений
        if ((matrix.getVariables() ) < (matrix.getEquations() - emptyRow)) {
            matrix.setSolution("No solutions");
        }

    }

    public void checkForManySolutions() {
        //если количество переменных меньше количества не пустых уравнений,
        //то система также не имеет решений
        if ((matrix.getVariables() ) > (matrix.getEquations() - emptyRow)) {
            matrix.setSolution("Infinitely many solutions");
            return;
        }

        //если в системе есть полностью нулевые столбцы с переменными,
        //то система имеет множество решений
        for (int i = 0; i < matrix.getVariables(); i++) {
            for (int z = 0; z < matrix.getEquations(); z++) {
                if (matrix.system[i][z] == 0) {
                    zerosInColumns++;
                }
            }
            if (zerosInColumns == matrix.getEquations()) {
                matrix.setSolution("Infinitely many solutions");
                System.out.println(matrix.getFilepathOut());
                return;
            }else{
                zerosInColumns = 0;
            }
        }
    }
}
