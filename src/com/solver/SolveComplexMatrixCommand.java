package com.solver;

public class SolveComplexMatrixCommand implements Command{

    Matrix matrix;

    public SolveComplexMatrixCommand(Matrix matrix) {
        this.matrix = matrix;
    }

    @Override
    public void execute() {
        matrix.solveComplexMatrix();
    }
}