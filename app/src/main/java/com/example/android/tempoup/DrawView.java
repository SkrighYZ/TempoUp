package com.example.android.tempoup;

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
    private Canvas canvas;

    private int poleX;
    private int bpm;
    private int speed = 10;

    private int screenWidth = 0;
    private int screenHeight = 0;


    private void init() {
        scalePaint.setColor(getResources().getColor(R.color.colorLineBlack));
        scalePaint.setStrokeWidth(4);
        polePaint.setColor(getResources().getColor(R.color.colorLineBlue));
        polePaint.setStrokeWidth(6);

        surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);

        initPoleX();
    }

    private void initPoleX(){
        poleX = screenWidth / 2;
        Log.d("DrawView", "poleX = " + poleX);
    }

    public DrawView(Context context) {
        super(context);
        init();
    }

    private void draw(){
        canvas.drawColor(getResources().getColor(R.color.colorBackgroundWhite));
        drawScale(canvas);
        drawPole(canvas);
    }

    private void drawScale(Canvas canvas){

        int width = screenWidth;
        int height = screenHeight;
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
        int height = screenHeight;
        int poleLength = 150;
        canvas.drawLine(poleX,
                (height - poleLength) / 2,
                poleX,
                (height - poleLength) / 2 + poleLength,
                polePaint);
    }

    @Override
    public void run() {
        long startTime = System.currentTimeMillis();
        while(canDraw) {

            canvas = surfaceHolder.lockCanvas();
            if (canvas == null) {
                try {
                    Thread.sleep(1);
                    continue;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            if(poleX == 0){
                poleX = screenWidth / 2;
            }
            draw();
            poleX += speed;
            long endTime = System.currentTimeMillis();
            long deltaTime = endTime - startTime;

            if (deltaTime < 200) {
                try {
                    Thread.sleep(200 - deltaTime);
                } catch (InterruptedException e) {
                    Log.e("DrawView", e.getMessage());
                }

            }

            surfaceHolder.unlockCanvasAndPost(canvas);
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
        screenWidth = getWidth();
        screenHeight = getHeight();
        resume();
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        pause();
    }

    public void setBpm(int bpm){
        this.bpm = bpm;
    }

    public int getBpm(){
        return bpm;
    }

}
