package com.polvazo.perrovaca;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private BoardView boardView;
    mapaMatriz mapita;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //COMO SE ESTA TRABAJANDO EN HILOS, SOLO INSTANCIAMOS EL VIEW
        boardView = (BoardView) findViewById(R.id.board);



    }

    @Override
    protected void onResume() {
        super.onResume();
        boardView.resume();
    }


    @Override
    protected void onPause() {
        super.onPause();
        boardView.pause();

    }

}
