package com.polvazo.perrovaca;

import java.security.PublicKey;

public class BoardControl {

    private int board[][];
    private int N = 10;

    public static final int NONE = 0;
    public static final int PERROVACA = 1;
    public static final int CACHIMBO = 2;
    public static final int WALL = 3;
    private int PERROVACA_ACTUAL_I = 4;
    private int PERROVACA_ACTUAL_j = 0;
    private int CACHIMBO_POSITION_I = 5;
    private int CACHIMBO_POSITION_J = 9;

    public int getCACHIMBO_POSITION_I() {
        return CACHIMBO_POSITION_I;
    }

    public int getCACHIMBO_POSITION_J() {
        return CACHIMBO_POSITION_J;
    }

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
        board[4][3] = WALL;
        board[5][3] = WALL;
        board[6][3] = WALL;
        board[5][4] = WALL;
        board[5][5] = WALL;
        board[4][5] = WALL;
        board[CACHIMBO_POSITION_I][CACHIMBO_POSITION_J] = CACHIMBO;

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
            if (board[i][j] == CACHIMBO) {
                return false;
            } else {
                if (Math.abs(PERROVACA_ACTUAL_I - i) == 1 && Math.abs(PERROVACA_ACTUAL_j - j) == 1) {
                    return true;
                } else {
                    if (PERROVACA_ACTUAL_I == i && Math.abs(PERROVACA_ACTUAL_j - j) == 1) {
                        return true;
                    } else if (PERROVACA_ACTUAL_j == j && Math.abs(PERROVACA_ACTUAL_I - i) == 1) {
                        return true;
                    }
                }
            }

        }
        return false;
    }

    public boolean isWinner(int i, int j) {
        if (board[i][j] == CACHIMBO) {
            return true;
        } else {
            return false;
        }


    }

}
