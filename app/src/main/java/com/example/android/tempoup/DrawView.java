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
    private int speed;
    private int timeInterval;
    private int countTime;

    private int screenWidth = 0;
    private int screenHeight = 0;




    private void init() {
        scalePaint.setColor(getResources().getColor(R.color.colorLineBlack));
        scalePaint.setStrokeWidth(4);
        polePaint.setColor(getResources().getColor(R.color.colorLineBlue));
        polePaint.setStrokeWidth(6);

        surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);

        speed = 1;
        timeInterval = 0;
        countTime = 0;
    }

    private void initPoleX(){
        poleX = screenWidth / 2 - 8;
        Log.d("DrawView", "poleX = " + poleX);
    }

    public DrawView(Context context) {
        super(context);
        init();
    }

    private void initCanvas(){
        initPoleX();
        canvas = surfaceHolder.lockCanvas();
        canvas.drawColor(getResources().getColor(R.color.colorBackgroundWhite));
        drawScale(canvas);
        drawPole(canvas);
        surfaceHolder.unlockCanvasAndPost(canvas);
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
        while(canDraw) {
            long startTime = System.currentTimeMillis();

            canvas = surfaceHolder.lockCanvas();

            if (canvas == null) {
                try {
                    Thread.sleep(1);
                    continue;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            update();
            draw();

            long endTime = System.currentTimeMillis();
            long deltaTime = endTime - startTime;

            if (deltaTime < timeInterval) {
                try {
                    Thread.sleep(timeInterval - deltaTime);
                } catch (InterruptedException e) {
                    Log.e("DrawView", e.getMessage());
                }

            }

            surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }

    private void update(){
        timeInterval = Utils.bpmToMilli(bpm) / 10;
        if(speed == 1){
            if(countTime >= 9){
                poleX = screenWidth * 5 / 6 - 15;
                countTime = 0;
                speed = -1;
                return;
            }
            poleX += (screenWidth * 5 / 6 - poleX) / (10 - countTime);
        } else if(speed == -1){
            if(countTime >= 9){
                poleX = screenWidth / 6;
                countTime = 0;
                speed = 1;
                return;
            }
            poleX -= (poleX - screenWidth / 6) / (10 - countTime);
        }
        countTime++;
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

        initPoleX();
        initCanvas();

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
        initCanvas();
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        if(thread != null){
            pause();
        }
    }

    public void setBpm(int bpm){
        this.bpm = bpm;
    }

    public int getBpm(){
        return bpm;
    }

}
