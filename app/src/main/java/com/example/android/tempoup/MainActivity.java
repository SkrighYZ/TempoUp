package com.example.android.tempoup;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity {

    DrawView drawView;
    TextView tempoTextView;
    CircularSeekBar seekBar;
    ToggleButton toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawView = (DrawView) findViewById(R.id.drawView);

        tempoTextView = (TextView) findViewById(R.id.tempoText);
        tempoTextView.setBackgroundColor(getResources().getColor(R.color.colorBackgroundWhite));

        seekBar = (CircularSeekBar) findViewById(R.id.seekBar);
        seekBar.setBarWidth(5);
        seekBar.setMaxProgress(38);
        seekBar.setProgress(21);
        seekBar.invalidate();
        seekBar.setSeekBarChangeListener(new CircularSeekBar.OnSeekChangeListener() {
            @Override
            public void onProgressChange(CircularSeekBar seekBar, int newProgress) {
                Log.d("Welcome", "Progress:" + seekBar.getProgress() + "/" + seekBar.getMaxProgress());
                int tempo = getTempoFromProgress(seekBar.getProgress());
                tempoTextView.setText(tempo + " BPM");
            }
        });
        seekBar.setBackgroundColor(getResources().getColor(R.color.colorBackgroundWhite));
        seekBar.setRingBackgroundColor(getResources().getColor(R.color.colorLineGrey));

        toggle = (ToggleButton) findViewById(R.id.toggle);
        ToggleButton toggle = (ToggleButton) findViewById(R.id.toggle);
        toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked){
                    Toast.makeText(getApplicationContext(), "Toggle is on", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getApplicationContext(), "Toggle is off", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     *
     * @param progress seekBar value
     * @return  corresponding tempo marking
     */
    private int getTempoFromProgress(int progress){
        return Utils.getStandardMarkings(progress);
    }

}
