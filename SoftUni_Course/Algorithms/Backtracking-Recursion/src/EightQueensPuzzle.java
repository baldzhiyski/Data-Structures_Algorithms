public class EightQueensPuzzle {
    public static char[][] chesBoard = {
            {'-','-','-','-','-','-','-','-'},
            {'-','-','-','-','-','-','-','-'},
            {'-','-','-','-','-','-','-','-'},
            {'-','-','-','-','-','-','-','-'},
            {'-','-','-','-','-','-','-','-'},
            {'-','-','-','-','-','-','-','-'},
            {'-','-','-','-','-','-','-','-'},
            {'-','-','-','-','-','-','-','-'}
    };

    public static boolean[][] freePositions =new boolean[8][8];
    public static void main(String[] args) {
        findQueenPositions(0);
    }

    private static void findQueenPositions(int row) {
        if (row == 8) {
            printMatrix();
            return;
        }

        for (int col = 0; col < 8; col++) {
            if (canPlaceQueen(row, col)) {
                putQueen(row, col);
                findQueenPositions(row + 1);
                removeQueen(row, col);
            }
        }
    }

    private static void removeQueen(int row, int col) {
        chesBoard[row][col] = '-';
    }

    private static void putQueen(int row, int col) {
        chesBoard[row][col] = 'Q';
    }

    // Method to check if a queen can be placed at a position
    private static boolean canPlaceQueen(int row, int col) {
        // Check if there is a queen in the same row
        for (int c = 0; c < 8; c++) {
            if (chesBoard[row][c] == 'Q') {
                return false;
            }
        }

        // Check if there is a queen in the same column
        for (int r = 0; r < 8; r++) {
            if (chesBoard[r][col] == 'Q') {
                return false;
            }
        }

        // Check if there is a queen in the diagonals
        int r, c;

        // Check left up diagonal
        r = row;
        c = col;
        while (r >= 0 && c >= 0) {
            if (chesBoard[r--][c--] == 'Q') {
                return false;
            }
        }

        // Check left down diagonal
        r = row;
        c = col;
        while (r < 8 && c >= 0) {
            if (chesBoard[r++][c--] == 'Q') {
                return false;
            }
        }

        // Check right up diagonal
        r = row;
        c = col;
        while (r >= 0 && c < 8) {
            if (chesBoard[r--][c++] == 'Q') {
                return false;
            }
        }

        // Check right down diagonal
        r = row;
        c = col;
        while (r < 8 && c < 8) {
            if (chesBoard[r++][c++] == 'Q') {
                return false;
            }
        }

        // If no queen found in the same row, column, or diagonals, the position is valid
        return true;
    }
    public static  void printMatrix(){
        for (char[] chars : chesBoard) {
            for (char symbol : chars) {
                System.out.print(symbol + " ");
            }
            System.out.println();
        }
        System.out.println();
    }
}
