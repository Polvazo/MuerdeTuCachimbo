package com.polvazo.perrovaca;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private BoardView boardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
