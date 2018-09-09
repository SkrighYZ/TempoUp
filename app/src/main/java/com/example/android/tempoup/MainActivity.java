package com.example.android.tempoup;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    DrawView drawView;
    TextView tempoTextView;
    CircularSeekBar seekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawView = (DrawView) findViewById(R.id.drawView);
        drawView.setBackgroundColor(getResources().getColor(R.color.colorBackgroundGrey));

        tempoTextView = (TextView) findViewById(R.id.tempoText);
        tempoTextView.setBackgroundColor(getResources().getColor(R.color.colorBackgroundGrey));

        seekBar = (CircularSeekBar) findViewById(R.id.seekBar);
        seekBar.setBarWidth(5);
        seekBar.setMaxProgress(100);
        seekBar.setProgress(50);
        seekBar.invalidate();
        seekBar.setSeekBarChangeListener(new CircularSeekBar.OnSeekChangeListener() {
            @Override
            public void onProgressChange(CircularSeekBar view, int newProgress) {
                Log.d("Welcome", "Progress:" + view.getProgress() + "/" + view.getMaxProgress());
            }
        });
        seekBar.setBackgroundColor(getResources().getColor(R.color.colorBackgroundGrey));
        seekBar.setRingBackgroundColor(getResources().getColor(R.color.colorBackgroundWhite));
    }
}
