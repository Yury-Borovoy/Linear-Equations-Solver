package com.solver;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;

public class WritingToFile {

    ComplexMatrix complexMatrix;
    SimpleMatrix simpleMatrix;
    String matrix;

    public WritingToFile(ComplexMatrix complexMatrix) {
        this.complexMatrix = complexMatrix;
        matrix = "complex";
    }
    public WritingToFile(SimpleMatrix simpleMatrix) {
        this.simpleMatrix = simpleMatrix;
        matrix = "simple";
    }

    public void writeToFile() {
        if (matrix.equals("complex")) {
            writeSolutionForComplex(complexMatrix.filepathOut, complexMatrix.solution);
        } else {
            writeSolutionForSimple(simpleMatrix.filepathOut, simpleMatrix.solution);
        }
    }

    public void writeSolutionForComplex(String out, String solution) {
        File file = new File(out);
        try (PrintWriter printWriter = new PrintWriter(file)) {
            if (solution.equals("no")) {
                for (int i = 0; i < complexMatrix.variables; i++) {
                    if (complexMatrix.system[i][complexMatrix.variables].realPart.signum() == 0) {
                        if (complexMatrix.system[i][complexMatrix.variables].imaginaryPart.compareTo(BigDecimal.ONE) == 0) {
                            solution = String.format("%s", "i");
                        } else if (complexMatrix.system[i][complexMatrix.variables].imaginaryPart.compareTo(BigDecimal.ZERO) > 0) {
                            solution = String.format("%.4f%s", complexMatrix.system[i][complexMatrix.variables].imaginaryPart, "i");
                        } else if (complexMatrix.system[i][complexMatrix.variables].imaginaryPart.compareTo(BigDecimal.valueOf(-1)) == 0) {
                            solution = String.format("%s", "-i");
                        } else if (complexMatrix.system[i][complexMatrix.variables].imaginaryPart.compareTo(BigDecimal.ZERO) < 0) {
                            solution = String.format("%.4f%s", complexMatrix.system[i][complexMatrix.variables].imaginaryPart, "i");
                        } else {
                            solution = String.format("%s", "0");
                        }

                    } else {
                        if (complexMatrix.system[i][complexMatrix.variables].imaginaryPart.compareTo(BigDecimal.ONE) == 0) {
                            solution = String.format("%.4f+%s", complexMatrix.system[i][complexMatrix.variables].realPart, "i");
                        } else if (complexMatrix.system[i][complexMatrix.variables].imaginaryPart.compareTo(BigDecimal.ZERO) > 0) {
                            solution = String.format("%.4f%+.4f%s", complexMatrix.system[i][complexMatrix.variables].realPart, complexMatrix.system[i][complexMatrix.variables].imaginaryPart, "i");
                        } else if (complexMatrix.system[i][complexMatrix.variables].imaginaryPart.compareTo(BigDecimal.valueOf(-1)) == 0) {
                            solution = String.format("%.4f-%s", complexMatrix.system[i][complexMatrix.variables].realPart, "i");
                        } else if (complexMatrix.system[i][complexMatrix.variables].imaginaryPart.compareTo(BigDecimal.ZERO) < 0) {
                            solution = String.format("%.4f%.4f%s", complexMatrix.system[i][complexMatrix.variables].realPart, complexMatrix.system[i][complexMatrix.variables].imaginaryPart, "i");
                        } else {
                            solution = String.format("%.4f", complexMatrix.system[i][complexMatrix.variables].realPart);
                        }
                    }
                    System.out.println(solution);
                    printWriter.write(solution + "\n");
                }
            } else {
                printWriter.write(solution);
            }
        } catch (IOException e) {
            System.out.println("error");
        }
    }

    public void writeSolutionForSimple(String out, String solution) {
        File file = new File(out);
        try (PrintWriter printWriter = new PrintWriter(file)) {
            if (solution.equals("no")) {
                for (int i = 0; i < simpleMatrix.variables; i++) {
                    solution = String.format("%.4f", simpleMatrix.system[i][simpleMatrix.variables]);
                    System.out.println(solution);
                    printWriter.write(solution + "\n");
                }
            } else {
                printWriter.write(solution);
                System.out.println(solution);
            }
        } catch (IOException e) {
            System.out.println("error");
        }
    }
}

