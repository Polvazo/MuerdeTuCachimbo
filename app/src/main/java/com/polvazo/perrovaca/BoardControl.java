package com.polvazo.perrovaca;

public class BoardControl {

    private int board[][];
    private int N = 10;

    public static final int NONE = 0;
    public static final int PERROVACA = 1;
    public static final int CACHIMBO = 2;
    public static final int WALL = 3;
    private int PERROVACA_ACTUAL_I;
    private int PERROVACA_ACTUAL_j;

    public int getPERROVACA_ACTUAL_I() {
        return PERROVACA_ACTUAL_I;
    }

    public int getPERROVACA_ACTUAL_j() {
        return PERROVACA_ACTUAL_j;
    }

    public BoardControl() {
        board = new int[N][N];
        board[PERROVACA_ACTUAL_I][PERROVACA_ACTUAL_j] = PERROVACA;
        board[3][3] = WALL;
    }

    public int getItemAt(int i, int j) {
        return board[i][j];
    }

    public void movePiece(int finalInicialX, int finalInicialY) {

        board[PERROVACA_ACTUAL_I][PERROVACA_ACTUAL_j] = NONE;
        PERROVACA_ACTUAL_I = finalInicialX;
        PERROVACA_ACTUAL_j = finalInicialY;
        board[finalInicialX][finalInicialY] = PERROVACA;

    }

    public boolean isMovedValido(int i, int j) {
        if (board[i][j] == WALL) {
            return false;
        } else {
            if (Math.abs(PERROVACA_ACTUAL_I - i) == 1 && Math.abs(PERROVACA_ACTUAL_j - j) == 1) {
                return true;
            } else {
                if (PERROVACA_ACTUAL_I == i && Math.abs(PERROVACA_ACTUAL_j - j) == 1) {
                    return true;
                } else if (PERROVACA_ACTUAL_j==j && Math.abs(PERROVACA_ACTUAL_I-i)==1){
                    return true;
                }
                return false;
            }
        }

    }
}
