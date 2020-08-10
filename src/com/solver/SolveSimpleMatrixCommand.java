package com.solver;

public class SolveSimpleMatrixCommand implements Command{

    Matrix matrix;

    public SolveSimpleMatrixCommand(Matrix matrix) {
        this.matrix = matrix;
    }

    @Override
    public void execute() {
        matrix.solveSimpleMatrix();
    }
}
