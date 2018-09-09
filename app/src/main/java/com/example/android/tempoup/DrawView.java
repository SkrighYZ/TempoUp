package com.example.android.tempoup;

import android.util.AttributeSet;
import android.view.View;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

public class DrawView extends View {

    private Paint paint = new Paint();

    private void init() {
        paint.setColor(getResources().getColor(R.color.colorLineWhite));
    }

    public DrawView(Context context) {
        super(context);
        init();
    }

    public DrawView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DrawView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    @Override
    public void onDraw(Canvas canvas){
        int width = canvas.getWidth();
        int height = canvas.getHeight();
        drawScale(canvas, width, height);
        drawPole(canvas, width, height);
        super.onDraw(canvas);
    }

    private void drawScale(Canvas canvas, int width, int height){

        paint.setColor(getResources().getColor(R.color.colorLineWhite));
        paint.setStrokeWidth(4);
        int shortLineLength = 50;
        int midLineLength = 80;
        int longLineLength = 100;

        for(int i = 0; i < 17; i++){
            if(i == 0 || i == 16){
                canvas.drawLine(width / 6 + width * 4 / 6 / 16 * i,
                        (height - longLineLength) / 2,
                        width / 6 + width * 4 / 6 / 16 * i,
                        (height - longLineLength) / 2 + longLineLength,
                        paint);
            }
            if(i == 4 || i == 8 || i == 12){
                canvas.drawLine(width / 6 + width * 4 / 6 / 16 * i,
                        (height - midLineLength) / 2,
                        width / 6 + width * 4 / 6 / 16 * i,
                        (height - midLineLength) / 2 + midLineLength,
                        paint);
            }
            canvas.drawLine(width / 6 + width * 4 / 6 / 16 * i,
                    (height - shortLineLength) / 2,
                    width / 6 + width * 4 / 6 / 16 * i,
                    (height - shortLineLength) / 2 + shortLineLength,
                    paint);
        }
    }

    private void drawPole(Canvas canvas, int width, int height){
        paint.setColor(getResources().getColor(R.color.colorLineBlue));
        paint.setStrokeWidth(6);
        int poleLength = 150;
        canvas.drawLine(width / 2 - 6,
                (height - poleLength) / 2,
                width / 2 - 6,
                (height - poleLength) / 2 + poleLength,
                paint);
    }
}
