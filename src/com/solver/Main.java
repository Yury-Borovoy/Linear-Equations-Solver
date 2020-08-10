package com.solver;


public class Main {

    public static void main(String[] args) {
        //check and read a file
        String in = "";
        String out = "";

        if (args.length > 0) {
            for (int i = 0; i < args.length; i++) {
                if (args[i].matches("-in")) {
                    in = args[i + 1];
                } else if (args[i].matches("-out")) {
                    out = args[i + 1];
                }
            }
            Controller controller = new Controller();
            Matrix matrix = new Matrix(in, out);
            if (matrix.complexNumbers) {
                Command solveComplexMatrix = new SolveComplexMatrixCommand(matrix);
                controller.setCommand(solveComplexMatrix);
                controller.executeCommand();
            }else{
                Command solveSimpleMatrix = new SolveSimpleMatrixCommand(matrix);
                controller.setCommand(solveSimpleMatrix);
                controller.executeCommand();
            }

        } else {
            System.out.println("You didn't input the filepath.");
        }
    }
}
