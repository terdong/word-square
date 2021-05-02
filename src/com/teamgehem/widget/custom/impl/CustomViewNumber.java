package com.teamgehem.widget.custom.impl;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;

import com.teamgehem.widget.custom.framework.CustomImageView;

public class CustomViewNumber extends CustomImageView
{
    public CustomViewNumber(Context context)
    {
        super(context);
    }
    
    public CustomViewNumber(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }
    
    public CustomViewNumber(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
    }
    
    private static final String TAG = CustomViewNumber.class.getSimpleName();
    
    private Rect thisRect2;
    
    private Rect thisRect3;
    
    private Rect thisRect4;
    
    public int num = 0;
    
    private Paint paint;
    
    /**
     * The align. 0 = 왼쪽 기본 정렬, 1 = 중앙, 2 = 오른쪽
     */
    private int align = 0;
    
    @Override
    public void initClass()
    {
        paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);
    }
    
    @Override
    public void setObjectSize()
    {
        int w = getWidth();
        int h = getHeight();
        
        if (srcBmpRect != null)
        {
            int width = srcBmpRect[0].right * h / srcBmpRect[0].bottom;
            if (align == 0)
            {
                thisRect = new Rect(0, 0, width, h);
                thisRect2 = new Rect(width, 0, width * 2, h);
                thisRect3 = new Rect(width * 2, 0, width * 3, h);
                thisRect4 = new Rect(width * 3, 0, width * 4, h);
            }
            else if (align == 1)
            {
                int halfW = w / 2;
                int halfWidth = width / 2;
                if (num < 10) 
                    thisRect = new Rect(halfW - halfWidth, 0, halfW + halfWidth, h);
                else if (num < 100)
                {
                    thisRect = new Rect(halfW - width, 0, halfW, h);
                    thisRect2 = new Rect(halfW, 0, halfW + width, h);
                }
            }
        }
        else
        {
            thisRect = new Rect(0, 0, w, h);
            thisRect2 = new Rect(0, 0, w, h);
            thisRect3 = new Rect(0, 0, w, h);
            thisRect4 = new Rect(0, 0, w, h);
        }
    }
    
    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
        
        if (num < 10)
            canvas.drawBitmap(srcBmp, srcBmpRect[num], thisRect,  null);
        else if (num < 100)
        {
            int num_10 = num / 10;
            int num_remain_1 = num - (num_10 * 10);
            canvas.drawBitmap(srcBmp, srcBmpRect[num_10], thisRect,  paint);
            canvas.drawBitmap(srcBmp, srcBmpRect[num_remain_1], thisRect2,  paint);
        }
        else if (num < 1000)
        {
            int num_100 = num / 100;
            int num_remain_10 = num - (num_100 * 100);
            int num_10 = num_remain_10 / 10;
            int num_remain_1 = num_remain_10 - (num_10 * 10);
            canvas.drawBitmap(srcBmp, srcBmpRect[num_100], thisRect, null);
            canvas.drawBitmap(srcBmp, srcBmpRect[num_10], thisRect2, null);
            canvas.drawBitmap(srcBmp, srcBmpRect[num_remain_1], thisRect3, null);
        }
        else if (num < 10000)
        {
            int num_1000 = num / 1000;
            int num_remain_1000 = num - (num_1000 * 1000);
            int num_100 = num_remain_1000 / 100;
            int num_remain_100 = num_remain_1000 - (num_100 * 100);
            int num_10 = num_remain_100 / 10;
            int num_remain_1 = num_remain_100 - (num_10 * 10);
            canvas.drawBitmap(srcBmp, srcBmpRect[num_1000], thisRect, null);
            canvas.drawBitmap(srcBmp, srcBmpRect[num_100], thisRect2, null);
            canvas.drawBitmap(srcBmp, srcBmpRect[num_10], thisRect3, null);
            canvas.drawBitmap(srcBmp, srcBmpRect[num_remain_1], thisRect4, null);
        }
//        canvas.drawRect(0, 0, getWidth(), getHeight(), paint);
    }
    
    public void initNumCount()
    {
        num = 0;
    }
    
    public void setNum(int num)
    {
        this.num = num;
        invalidate();
    }
    
    public void addNum()
    {
        this.num++;
        invalidate();
    }
    
    public void subNum()
    {
        if (num > 0)
            this.num--;
        else
            this.num = 0;
        invalidate();
    }

    public void setAlign(int align)
    {
        this.align = align;
    }
    
}// class

