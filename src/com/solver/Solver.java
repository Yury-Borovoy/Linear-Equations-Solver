package com.solver;

public class Solver {
    Matrix matrix;

    Solver(Matrix matrix) {
        this.matrix = matrix;
    }

    public void solveMatrix() {
        Command straightRun = new StraightRunCommand(matrix);
        straightRun.execute();
        Command checkTheSystem = new CheckCommand(matrix);
        checkTheSystem.execute();
        Command returnRun = new ReturnRunCommand(matrix);
        returnRun.execute();
        Command writeToFile = new WriteToFileCommand(matrix);
        writeToFile.execute();
    }
}
