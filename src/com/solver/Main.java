package com.solver;


public class Main {

    public static void main(String[] args)  {
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
            Matrix matrix = new Matrix(in, out);
            matrix.getMatrix();
            Solver solver = new Solver(matrix);
            solver.solveMatrix();
        } else {
            System.out.println("You didn't input the filepath.");
        }
    }
}
