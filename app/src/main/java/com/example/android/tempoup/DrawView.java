package com.example.android.tempoup;

import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

public class DrawView extends SurfaceView implements Runnable, SurfaceHolder.Callback {

    private Paint scalePaint = new Paint();
    private Paint polePaint = new Paint();

    private Thread thread = null;
    private boolean canDraw = false;
    private SurfaceHolder surfaceHolder;

    private int speed = 10;
    private int poleX;


    private void init() {
        scalePaint.setColor(getResources().getColor(R.color.colorLineBlack));
        scalePaint.setStrokeWidth(4);
        polePaint.setColor(getResources().getColor(R.color.colorLineBlue));
        polePaint.setStrokeWidth(6);

        surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);

        poleX = -1;
    }

    private void initPoleX(int canvasWidth){
        poleX = canvasWidth / 2 - 10;
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

    private void drawOn(Canvas canvas){
        drawScale(canvas);
        drawPole(canvas);
    }

    private void drawScale(Canvas canvas){

        int width = canvas.getWidth();
        int height = canvas.getHeight();
        int shortLineLength = 50;
        int midLineLength = 80;
        int longLineLength = 100;

        for(int i = 0; i < 17; i++){
            if(i == 0 || i == 16){
                canvas.drawLine(width / 6 + width * 4 / 6 / 16 * i,
                        (height - longLineLength) / 2,
                        width / 6 + width * 4 / 6 / 16 * i,
                        (height - longLineLength) / 2 + longLineLength,
                        scalePaint);
            }
            if(i == 4 || i == 8 || i == 12){
                canvas.drawLine(width / 6 + width * 4 / 6 / 16 * i,
                        (height - midLineLength) / 2,
                        width / 6 + width * 4 / 6 / 16 * i,
                        (height - midLineLength) / 2 + midLineLength,
                        scalePaint);
            }
            canvas.drawLine(width / 6 + width * 4 / 6 / 16 * i,
                    (height - shortLineLength) / 2,
                    width / 6 + width * 4 / 6 / 16 * i,
                    (height - shortLineLength) / 2 + shortLineLength,
                    scalePaint);
        }
    }

    private void drawPole(Canvas canvas){
        int height = canvas.getHeight();
        int poleLength = 150;
        canvas.drawLine(poleX,
                (height - poleLength) / 2,
                poleX,
                (height - poleLength) / 2 + poleLength,
                polePaint);
    }

    @Override
    public void run() {
        Canvas canvas = null;
        while(canDraw){

            try {
                canvas = surfaceHolder.lockCanvas();
                if (canvas == null) {
                    Thread.sleep(1);
                    continue;
                }
                int width = canvas.getWidth();
                if (poleX == -1) initPoleX(width);
                drawOn(canvas);
                poleX += speed;

            } catch(InterruptedException e){
                Log.d("Thread", "Thread cannot sleep!!");
                e.printStackTrace();

            } finally{
                if(canvas != null){
                    surfaceHolder.unlockCanvasAndPost(canvas);
                }
            }

        }
    }

    public void pause(){
        canDraw = false;

        while(true){
            try {
                thread.join();
                break;
            } catch (InterruptedException e) {
                Log.d("Thread", "Thread joining failed.");
            }
        }

        thread = null;

    }

    public void resume(){
        canDraw = true;
        thread = new Thread(this);
        thread.start();
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        resume();
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        pause();
    }

}
