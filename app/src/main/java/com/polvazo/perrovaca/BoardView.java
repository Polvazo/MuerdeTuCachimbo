package com.polvazo.perrovaca;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;


public class BoardView extends SurfaceView implements Runnable {

    private int N = 10;
    private Thread thread;
    private Canvas canvas;
    private int cellSize;
    private int size;
    private boolean canDraw;
    private SurfaceHolder surfaceHolder;


    private BoardControl boardControl;
    private long tiempoanteriortoast;
    //imagenes
    private Bitmap perrovaca;
    private Bitmap cachimbo;
    private Bitmap wall;

    //pinceles
    private Paint blackPaint;
    private Paint redPaint;
    private int countPlay = 1;

    public BoardView(Context context) {
        super(context);
        init(null, 0);
    }

    public BoardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public BoardView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        surfaceHolder = getHolder();
        boardControl = new BoardControl();
    }

    private void prepareBrushes() {
        blackPaint = new Paint();
        blackPaint.setStyle(Paint.Style.FILL_AND_STROKE);

        redPaint = new Paint();
        redPaint.setStyle(Paint.Style.STROKE);
        redPaint.setStrokeWidth(3.0f);
        redPaint.setColor(Color.RED);

    }

    public void resume() {
        canDraw = true;
        thread = new Thread(this);
        thread.start();
    }

    public void pause() {
        canDraw = false;
        while (true) {
            try {
                thread.join();
                break;
            } catch (InterruptedException ignored) {
            }
        }
        thread = null;
    }

    @Override
    public void run() {
        prepareBrushes();
        while (canDraw) {
            if (!surfaceHolder.getSurface().isValid()) {
                continue;
            }
            canvas = surfaceHolder.lockCanvas();

            canvas.drawColor(Color.WHITE);
            drawGrid();
            drawSelectionPerroVaca();
            drawPieces();


            surfaceHolder.unlockCanvasAndPost(canvas);
            try {
                Thread.sleep(25);
            } catch (InterruptedException ignores) {
            }

        }

    }

    private void drawSelectionPerroVaca() {

        int i = boardControl.getPERROVACA_ACTUAL_I();
        int j = boardControl.getPERROVACA_ACTUAL_j();

        int x, y;
        for (int k = -1; k < 2; k++) {
            for (int l = -1; l < 2; l++) {
                x = (j + k) * cellSize;
                y = (i + l) * cellSize;
                canvas.drawRect(x, y, x + cellSize, y + cellSize, redPaint);
            }

        }

    }

    private void drawPieces() {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                int piece = boardControl.getItemAt(i, j);
                drawPieceAt(piece, i, j);
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            int j = (int) (event.getX() / cellSize);
            int i = (int) (event.getY() / cellSize);
            if (i >= 0 && i <= (N - 1) && j >= 0 && j <= (N - 1)) {
                selectPiece(i, j);

            }

        }

        return true;
    }

    private void selectPiece(int i, int j) {
       /* int pieceSelect = boardControl.getItemAt(i,j);
        if(pieceSelect== BoardControl.PERROVACA){
            para varios jugadores
        }*/
        if (boardControl.isMovedValido(i, j)) {
            boardControl.movePiece(i, j);
            drawPieceAt(BoardControl.NONE, i, j);
            countPlay++;

        } else {
            if (boardControl.isWinner(i, j) == true) {
                boardControl.movePiece(i, j);
                Toast.makeText(getContext(), "ganaste tio en " + countPlay + " jugadas", Toast.LENGTH_SHORT).show();

            } else {
                long actual = System.currentTimeMillis();
                Log.i("tiempo acutal", String.valueOf(actual));
                if (actual - tiempoanteriortoast > 2000) {
                    Toast toast = Toast.makeText(getContext(), "Moviento Invalido", Toast.LENGTH_SHORT);
                    tiempoanteriortoast = actual;
                    toast.show();
                }
            }

        }


    }

    private void drawPieceAt(int piece, int i, int j) {
        Bitmap pieceBmp = null;
        switch (piece) {
            case BoardControl.PERROVACA:
                pieceBmp = perrovaca;
                break;
            case BoardControl.CACHIMBO:
                pieceBmp = cachimbo;
                break;
            case BoardControl.NONE:

                break;
            case BoardControl.WALL:
                pieceBmp = wall;
                break;
        }

        if (pieceBmp != null) {
            canvas.drawBitmap(pieceBmp, j * cellSize, i * cellSize, null);
        }
    }

    private void drawGrid() {
        for (int i = 0; i <= N; i++) {
            canvas.drawLine(0, i * cellSize, size, i * cellSize, blackPaint);
        }
        for (int j = 0; j <= N; j++) {
            canvas.drawLine(j * cellSize, 0, j * cellSize, size, blackPaint);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        size = Math.min(getMeasuredWidth(), getMeasuredHeight());
        cellSize = size / N;
        setMeasuredDimension(size, size);
        loadImages();
    }

    private void loadImages() {
        if (cellSize > 0) {
            Resources resources = getResources();

            Bitmap pv = BitmapFactory.decodeResource(resources, R.drawable.perrovaca);
            perrovaca = Bitmap.createScaledBitmap(pv, cellSize, cellSize, true);

            Bitmap ch = BitmapFactory.decodeResource(resources, R.drawable.gonzalo);
            cachimbo = Bitmap.createScaledBitmap(ch, cellSize, cellSize, true);

            wall = Bitmap.createBitmap(cellSize, cellSize, Bitmap.Config.ARGB_8888);
            wall.eraseColor(Color.GREEN);

            if (pv != null) {
                pv.recycle();
            }
            if (ch != null) {
                ch.recycle();
            }
        }
    }

}
