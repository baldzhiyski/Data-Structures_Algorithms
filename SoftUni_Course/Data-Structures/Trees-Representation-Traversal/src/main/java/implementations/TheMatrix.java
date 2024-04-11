package implementations;

import java.util.ArrayDeque;
import java.util.Deque;

public class TheMatrix {
    private char[][] matrix;
    private char fillChar;
    private char toBeReplaced;
    private int startRow;
    private int startCol;

    public TheMatrix(char[][] matrix, char fillChar, int startRow, int startCol) {
        this.matrix = matrix;
        this.fillChar = fillChar;
        this.startRow = startRow;
        this.startCol = startCol;
        this.toBeReplaced = this.matrix[this.startRow][this.startCol];
    }

    public void solve() {
        fillMatrixWithStack();
    }

    private void fillMatrixWithStack() {
        Deque<int[]> coordinates = new ArrayDeque<>();

        coordinates.add(new int[]{startRow,startCol});

        while (!coordinates.isEmpty()){
            int[] position = coordinates.poll();

            int row = position[0];
            int col = position[1];

            this.matrix[row][col] = fillChar;

            if(isInBounds(row+1,col) && this.matrix[row+1][col]== this.toBeReplaced){
                coordinates.offer(new int[]{row+1,col});
            }

            if(isInBounds(row,col+1) && this.matrix[row][col+1]== this.toBeReplaced){
                coordinates.offer(new int[]{row,col+1});
            }

            if(isInBounds(row,col-1) && this.matrix[row][col-1]== this.toBeReplaced){
                coordinates.offer(new int[]{row,col-1});
            }

            if(isInBounds(row-1,col) && this.matrix[row-1][col]== this.toBeReplaced){
                coordinates.offer(new int[]{row-1,col});
            }
        }
    }

    private boolean isInBounds(int row, int col) {
        return !isOutOfBounds(row,col);
    }

    private void fillMatrixRecursive(int row, int col) {
        if(isOutOfBounds(row,col) || this.matrix[row][col] != this.toBeReplaced){
            return;
        }

        this.matrix[row][col] = this.fillChar;

        System.out.println(this.toOutputString());
        System.out.println();

        // Now we need to see where we can go
        this.fillMatrixRecursive(row + 1,col);
        this.fillMatrixRecursive(row,col+1);
        this.fillMatrixRecursive(row-1,col);
        this.fillMatrixRecursive(row,col-1);


    }

    private boolean isOutOfBounds(int row, int col) {
        return row <0 || row >= this.matrix.length || col < 0 || col >=this.matrix[row].length;
    }

    public String toOutputString() {
        StringBuilder builder = new StringBuilder();
        for (int row = 0; row < matrix.length; row++) {
            for (int col = 0; col < matrix[row].length; col++) {
                builder.append(this.matrix[row][col]);
            }
            builder.append(System.lineSeparator());
        }

        return builder.toString().trim();
    }
}
