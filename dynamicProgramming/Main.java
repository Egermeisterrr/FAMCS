import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Scanner;

public class Main {

    public static class Matrix {
        int rows;
        int cols;

        public Matrix(int r, int c) {
            rows = r;
            cols = c;
        }
    }

    public static int countOperations(Matrix[] arr) {
        int size = arr.length;
        int[][] table = new int[size][size];

        for(int l = 1; l < size; l++) {
            for(int i = 0; i < size - l; i++) {
                int j = i + l;
                table[i][j] = Integer.MAX_VALUE;
                for(int k = i; k < j; k++) {
                    if((table[i][k] + table[k + 1][j] + arr[i].rows * arr[k + 1].rows * arr[j].cols) < table[i][j]) {
                        table[i][j] = table[i][k] + table[k + 1][j] + arr[i].rows * arr[k + 1].rows * arr[j].cols;
                    }
                }
            }
        }
        return table[0][size - 1];
    }

    public static void main(String[] args) {
        try {
            Scanner in = new Scanner(new File("input.txt"));
            PrintStream p = new PrintStream(new FileOutputStream("output.txt"));
            int count = in.nextInt();
            int rows, cols, k = 0;
            Matrix arr[] = new Matrix[count];
            while(in.hasNext()) {
                rows = in.nextInt();
                cols = in.nextInt();
                arr[k] = new Matrix(rows, cols);
                k++;
            }
            int answer = countOperations(arr);
            p.println(answer);
        }
        catch(IOException e) {
            System.err.println(e);
        }
    }
}