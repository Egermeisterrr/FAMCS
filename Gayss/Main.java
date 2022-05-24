package com.company;
public class Main {
    public static int accuracy  = 12;
    public static int NumOfDecimals = 2;
    public static int SignificOfDecimals = (int) Math.pow(10, NumOfDecimals);
    public static double MIN = Math.pow(0.1, 18);

    //умножение матриц, для нахождения f и проверки обратной матрицы
    public static float[][] multiple(float[][] firstMatrix, float[][] secondMatrix) {
        float[][] matrix = new float[firstMatrix.length][secondMatrix[0].length];
        for(int i = 0; i < firstMatrix.length; ++i)
            for(int c = 0; c < secondMatrix[0].length; ++c)
                for(int r = 0; r < secondMatrix.length; ++r)
                    matrix[i][c] += firstMatrix[i][r] * secondMatrix[r][c];
        return matrix;
    }
    //округление
    public static float rounding(float num) {
        float t = (float) Math.round(num * Main.SignificOfDecimals);
        return  (t / Main.SignificOfDecimals);
    }
    //Вывод матрицы
    public static void printMatrix(boolean x, float[][] matrix) {
        if(x)
            NumOfDecimals = Main.accuracy;
        else
            NumOfDecimals = Main.NumOfDecimals;
        int max = Math.abs((int) matrix[0][0]);
        int size = String.valueOf(max).length() + 4 + Main.NumOfDecimals;
        String Str;
        Str = "% " + size + "." + com.company.Main.NumOfDecimals + "f";
        for (int i = 0; i < matrix.length; ++i) {
            System.out.print('|');
            for (int j = 0; j < matrix[0].length - 1; ++j)
                System.out.print(String.format(Str, Math.abs(matrix[i][j]) < com.company.Main.MIN ? 0 : matrix[i][j]));
            System.out.print(String.format(Str, Math.abs(matrix[i][matrix[0].length - 1]) < com.company.Main.MIN ? 0 : matrix[i][matrix[0].length - 1]));
            System.out.println(" |\n");
        }
    }
    //Метод Гаусса прямой ход
    public static void MethodOfGauss(float[][] matrix) {
        float q;
        int length = matrix.length;
        for(int col = 0; col < matrix.length; ++col) {
            for(int i = col + 1; i < matrix.length; ++i) {
                if(matrix[i][col] != 0) { //прямой ход Метода Гаусса
                    q = matrix[i][col] / matrix[col][col];
                    for (int j = col; j < matrix.length; ++j)
                        matrix[i][j] -= matrix[col][j] * q;
                }
            }
        }
    }
    //Нахождение определителя
    public static float determinant(float[][] matrix) {
        float[][] copyMtrx = new float[matrix.length][matrix.length];
        for(int i = 0; i < matrix.length; ++i)
            for(int j = 0; j < matrix.length; ++j)
                copyMtrx[i][j] = matrix[i][j];
        MethodOfGauss(copyMtrx);
        float det = 1;
        for(int i = 0; i < matrix.length; ++i)
            det *= copyMtrx[i][i];
        return det;
    }

    //Нахождение обратной матрицы
    public static float[][] InverseMatrix(float[][] mtrx) {
        int size = mtrx.length;
        float[][] inverseMtrx = new float[size][size];
        float[][] copyMatrix = new float[size][size];
        for(int i = 0; i < mtrx.length; ++i)
            for(int j = 0; j < mtrx.length; ++j)
                copyMatrix[i][j] = mtrx[i][j];
        float[][] slae = new float[size + 1][size + 1];
        for(int i = 0; i < mtrx.length; ++i)
            for(int j = 0; j < mtrx.length; ++j)
                slae[i][j] = mtrx[i][j];
        for(int i = 0; i < mtrx.length; ++i) {
            if(i != 0)
                slae[i - 1][slae.length - 1] = 0;
            slae[i][slae.length - 1] = 1;
            float[] solution = solution(slae);
            for(int k = 0; k < solution.length; ++k)
                inverseMtrx[k][i] = solution[k];
        }
        return inverseMtrx;
    }

    //Метод Гаусса обратный ход
    public static float[] solution(float[][] SLAE) {
        float[] answer = new float[SLAE.length - 1];
        float[][] copySLAE = new float[SLAE.length][SLAE.length];
        for(int i = 0; i < SLAE.length; ++i)
            for(int j = 0; j < SLAE.length; ++j)
                copySLAE[i][j] = SLAE[i][j];
        MethodOfGauss(copySLAE);
        for(int i = SLAE.length - 2; i >= 0; --i) { //обратный ход метода Гаусса
            answer[i] = copySLAE[i][SLAE.length - 1];
            for(int j = SLAE.length - 2; j > i; --j)
                answer[i] -= copySLAE[i][j] * answer[j];
            answer[i] /= copySLAE[i][i];
        }
        return answer;
    }
    public static void main(String[] args) {
        int size = 11, limit1 = -100, limit2= 100;
        float[][] mtrx = new float[size][size];

        //рандомим матрицу
        System.out.println("Исходная матрица: ");
        for (int i = 0; i < mtrx.length; i++) {
            for (int j = 0; j < mtrx[i].length; j++)
                mtrx[i][j] = rounding((float) ((Math.random() * (limit2 - limit1)) + limit1));
        }

        printMatrix(false, mtrx);

        //элементы х
        float[][] exactX = new float[size][1];
        for (int i = 0; i < size; ++i)
            exactX[i][0] = (int) Math.round(((Math.random() * (limit2 - limit1)) + limit1));

        //находим f
        float[][] temp = multiple(mtrx, exactX);

        float[][] SLAE = new float[size + 1][size + 1];
        for (int i = 0; i < size; ++i) {
            SLAE[i][size] = temp[i][0];
            for (int j = 0; j < size; ++j) {
                SLAE[i][j] = mtrx[i][j];
            }
        }
        //находим х ~
        float[] answer = solution(SLAE);

        //вывод результатов
        System.out.println(" A * x  = f \n         x                       x~ ");
        for (int i = 0; i < answer.length; ++i) {
            System.out.printf("%." + Main.accuracy + "e\t\t%." + Main.accuracy + "e\n",exactX[i][0], answer[i]);
        }
        System.out.println();
        System.out.println(" A * A^(-1) = ");
        printMatrix(true,  multiple(mtrx, InverseMatrix(mtrx)));
        System.out.println("Определитель A = " + determinant(mtrx));
    }
}