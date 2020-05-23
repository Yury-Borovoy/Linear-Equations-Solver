package com.solver;


import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
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
            Matrix matrix = new Matrix(in);
            //write the values to the matrix from the file
            matrix.writeToMatrix();
            matrix.findOfValues();
            matrix.writeVariables(out);
        }else {
            System.out.println("You didn't input the filepath.");
        }
    }
}
