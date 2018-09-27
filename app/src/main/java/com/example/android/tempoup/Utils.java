package com.example.android.tempoup;

import android.util.Log;

public class Utils {

    /*
     * In order to make life easier for music learners, these are the recommended tempo markings that
     * usually appear on a real metronome.
     */
    public static int getStandardMarkings(int index){
        switch (index){
            case 0:
                return 40;
            case 1:
                return 42;
            case 2:
                return 44;
            case 3:
                return 46;
            case 4:
                return 48;
            case 5:
                return 50;
            case 6:
                return 52;
            case 7:
                return 54;
            case 8:
                return 56;
            case 9:
                return 58;
            case 10:
                return 60;
            case 11:
                return 63;
            case 12:
                return 66;
            case 13:
                return 69;
            case 14:
                return 72;
            case 15:
                return 76;
            case 16:
                return 80;
            case 17:
                return 84;
            case 18:
                return 88;
            case 19:
                return 92;
            case 20:
                return 96;
            case 21:
                return 100;
            case 22:
                return 104;
            case 23:
                return 108;
            case 24:
                return 112;
            case 25:
                return 116;
            case 26:
                return 120;
            case 27:
                return 126;
            case 28:
                return 132;
            case 29:
                return 138;
            case 30:
                return 144;
            case 31:
                return 152;
            case 32:
                return 160;
            case 33:
                return 168;
            case 34:
                return 176;
            case 35:
                return 184;
            case 36:
                return 192;
            case 37:
                return 200;
            case 38:
                return 208;
            default:
                Log.d("Invalid Marking Index", "The input index is: " + index);
                return 0;
        }
    }

    public static int bpmToMilli(int bpm){
        return 6000 / bpm;
    }

}
