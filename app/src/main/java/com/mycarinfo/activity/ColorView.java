package com.mycarinfo.activity;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by HX_CHEN on 2017/4/29.
 * TIME: ${YESR}${MONTY} 29 1752
 */

public class ColorView extends View {
    public ColorView(Context context) {
        super(context);
    }

    public ColorView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ColorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public ColorView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

        private float x;                //  圆心横坐标
        private float y;                //  圆心纵坐标
        private float radius;            //  半径
        private int color;            //  画笔的颜色

        public float getX()
        {
            return x;
        }
        public void setX(float x)
        {
            this.x = x;
        }
        public float getY()
        {
            return y;
        }
        public void setY(float y)
        {
            this.y = y;
        }
        public float getRadius()
        {
            return radius;
        }
        public void setRadius(float radius)
        {
            this.radius = radius;
        }
        public int getColor()
        {
            return color;
        }
        public void setColor(int color)
        {
            this.color = color;
            invalidate();
        }

    @Override
    protected void onDraw(Canvas canvas) {

        int verticalCenter    =  getHeight() / 2;
        Paint paint = new Paint();
        paint.setAntiAlias(true);//抗锯齿
        paint.setColor(color);
        canvas.drawCircle( 10,verticalCenter , 10, paint);;
        super.onDraw(canvas);
    }

}
