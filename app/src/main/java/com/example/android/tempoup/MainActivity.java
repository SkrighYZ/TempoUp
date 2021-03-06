package com.example.android.tempoup;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity {

    LinearLayout canvasLayout;
    DrawView drawView;
    TextView tempoTextView;
    CircularSeekBar seekBar;
    ToggleButton toggle;
    SoundThread sound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        canvasLayout = (LinearLayout) findViewById(R.id.canvasLayout);
        drawView = new DrawView(getApplicationContext());
        drawView.setBpm(40);
        canvasLayout.addView(drawView);

        tempoTextView = (TextView) findViewById(R.id.tempoText);
        tempoTextView.setBackgroundColor(getResources().getColor(R.color.colorBackgroundWhite));

        seekBar = (CircularSeekBar) findViewById(R.id.seekBar);
        seekBar.setBarWidth(5);
        seekBar.setMaxProgress(38);
        seekBar.setProgress(0);
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
                    int tempo = getTempoFromProgress(seekBar.getProgress());
                    Toast.makeText(getApplicationContext(), "Tempo: " + tempo, Toast.LENGTH_SHORT).show();
                    sound = new SoundThread(getApplicationContext(), tempo);
                    sound.play();
                    drawView.setBpm(tempo);
                    drawView.resume();
                }
                else{
                    Toast.makeText(getApplicationContext(), "Stopped", Toast.LENGTH_SHORT).show();
                    sound.pause();
                    drawView.pause();
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
