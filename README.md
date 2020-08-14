# Linear-Equations-Solver
The program  solves a system of linear equations using the Jordan-Gauss method. The system can be represented by both simple numbers and complex ones. 

Using the command line, the program receives data, processes them, solves the system and outputs the result to a file. Input data must be of the following format (4 arguments): 1) -in 2) path to file 3) –out 4) path to file.

## The course of action is as follows:
1. In the Matrix class, the input data is written into a common matrix. Then, after checking with regular expressions, the total matrix is rewritten into a matrix with simple numbers or into a matrix with complex numbers.
2. The derived classes of SimpleMatrix and ComplexMatrix have forward and backward methods according to the Jordan-Gauss method. The forward move method saves us from values below the main diagonal of the matrix, and the reverse move method - above.
3. After the forward move method has worked out, we check the matrix for possible solutions. The checkMatrix () method of the CheckingOfMatrix class is responsible for checking.
4. If the system has one solution, the reverse method is used, after which the result of the system solution is written to the file. If we do not have solutions or an infinite number of them, then we immediately proceed to writing the answer to the file.
