package org.d3ifcool.jagosholat.Controller.KompasFragmentContent;

/*
 * Created by Bryan on 3/20/2018.
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import org.d3ifcool.jagosholat.Controller.MainContent.KompasFragment;
import org.d3ifcool.jagosholat.R;

public class KompasRose extends View {

    // ---------------------------------------------------------------------------------------------
    private int width = 240;
    private int height = 240;
    private float directionNorth = 0;
    private float directionQibla = 0;
    private float centre_x = width * 0.5f;
    private float centre_y = height * 0.5f;
    // ---------------------------------------------------------------------------------------------
    private Matrix rotateNeedle = new Matrix();
    private Bitmap compassBackground;
    private Bitmap compassNeedle;
    // ---------------------------------------------------------------------------------------------

    public KompasRose(Context context) {
        super(context);
        initCompassView();
    }
    public KompasRose(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initCompassView();
    }
    public KompasRose(Context context, AttributeSet attrs) {
        super(context, attrs);
        initCompassView();
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(width, height);
    }
    private void initCompassView() {

        // -----------------------------------------------------------------------------------------
        // Membuat Kompas
        compassNeedle = BitmapFactory.decodeResource(getResources(), R.drawable.kompas_front);
        compassBackground = BitmapFactory.decodeResource(getResources(), R.drawable.kompas_back);
        // -----------------------------------------------------------------------------------------
        width = compassBackground.getWidth()*2;
        height = compassBackground.getHeight()*2;
        // -----------------------------------------------------------------------------------------
        centre_x = width  * 0.5f;
        centre_y = height * 0.5f;
        // -----------------------------------------------------------------------------------------
        rotateNeedle.postTranslate(centre_x - compassNeedle.getWidth()/2, centre_y - compassNeedle.getHeight()/2);
        invalidate();
    }

    public void setDirections(float directionsNorth, float directionsQibla) {
        // -----------------------------------------------------------------------------------------
        this.directionNorth = directionsNorth;
        this.directionQibla = directionsQibla;
        // -----------------------------------------------------------------------------------------
        rotateNeedle = new Matrix();
        float degree = (float) KompasFragment.degree;
        // -----------------------------------------------------------------------------------------
        rotateNeedle.postRotate(degree, compassNeedle.getWidth()/2, compassNeedle.getHeight()/2);
        rotateNeedle.postTranslate(centre_x - compassNeedle.getWidth()/2, centre_y - compassNeedle.getHeight()/2);
        invalidate();
        // -----------------------------------------------------------------------------------------
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Paint p = new Paint();
        canvas.rotate(-directionNorth, centre_x, centre_y);
        canvas.drawBitmap(compassBackground, compassBackground.getWidth()/2, compassBackground.getHeight()/2, p);
        canvas.drawBitmap(compassNeedle, rotateNeedle, p);
    }
}