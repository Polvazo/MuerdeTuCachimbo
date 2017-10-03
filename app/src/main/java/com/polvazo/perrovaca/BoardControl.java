package com.polvazo.perrovaca;

import java.security.PublicKey;

public class BoardControl {

    private int board[][];
    private int N = 10;

    public static final int NONE = 0;
    public static final int PERROVACA = 1;
    public static final int CACHIMBO = 2;
    public static final int WALL = 3;
    private int PERROVACA_ACTUAL_I = Jashir.posicionPERROVACAx;
    private int PERROVACA_ACTUAL_j = Jashir.posicionPERROVACAy;



    public int getPERROVACA_ACTUAL_I() {
        return PERROVACA_ACTUAL_I;
    }

    public int getPERROVACA_ACTUAL_j() {
        return PERROVACA_ACTUAL_j;
    }

    public BoardControl() {
        board = new int[N][N];
        board[Jashir.posicionPERROVACAx][Jashir.posicionPERROVACAy] = PERROVACA;
        board[3][3] = WALL;
        board[4][3] = WALL;
        board[5][3] = WALL;
        board[6][3] = WALL;
        board[5][4] = WALL;
        board[5][5] = WALL;
        board[4][5] = WALL;
        board[Jashir.posicionCACHIMBOx][Jashir.positicionCACHIMBOy] = CACHIMBO;

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


    //En esta parta estoy validando las jugadas permitidas del perro vaca
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
