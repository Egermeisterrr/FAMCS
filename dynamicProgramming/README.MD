# Dynamic Programming Task:
Given a sequence of matrices. We need to find the order in which they should be multiplied so that the number of atomic multiplication operations is minimal.
The first line of the input file contains the number of matrices, the next - the number of rows and columns.
The output file contains the minimum number of operations.

INPUT FORMAT:
The first line contains the number s of matrices (2 ≤ s ≤ 100). In the following s rows, the sizes of the matrices are set: row i + 1 contains, separated by a space, the number of ni rows and the number of mi columns of the matrix Ai (1 ≤ ni, mi ≤ 100). It is guaranteed that mi coincides with ni + 1 for all indices i from 1 to s − 1.

OUTPUT FORMAT:
Print the minimum number of atomic multiplication operations required to multiply s matrices.

EXAMPLE:

![image](https://user-images.githubusercontent.com/102251036/174600031-a0ef2616-12ac-4428-b8c8-f3aaa40e55f3.png)
