package com.solver;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

public class WriteToFileCommand implements Command{
    Matrix matrix;

    WriteToFileCommand(Matrix matrix) {
        this.matrix = matrix;
    }

    //Проверяем, какие решения имеет матрица
    //и записываем их в файл
    @Override
    public void execute() {
        writeSolution(matrix.getFilepathOut(), matrix.getSolution());
    }

    public void writeSolution(String out, String solution) {
        File file = new File(out);
        try (PrintWriter printWriter = new PrintWriter(file)) {
            if (solution.equals("")) {
                for (int i = 0; i < matrix.getVariables(); i++) {
                    solution = String.format("%.4f", matrix.system[i][matrix.getVariables()]);
                    System.out.println(solution);
                    printWriter.write(solution + "\n");
                }
            } else {
                printWriter.write(solution);
            }
        } catch (IOException e) {
            System.out.println("error");
        }
        System.out.println(solution);
    }
}