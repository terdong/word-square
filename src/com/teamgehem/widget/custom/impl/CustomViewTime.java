package com.teamgehem.widget.custom.impl;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import com.teamgehem.widget.custom.framework.CustomImageView;
import com.teamgehem.wordsquarelite.R;

public class CustomViewTime extends CustomImageView
{
    private static final String TAG = CustomViewTime.class.getSimpleName();
    
    private static final int INIT_COMBO_TIME = 31;
    
    private Drawable time_comma;
    
    private Rect thisRects[];
    
    public int second;
    
    private int mSecond;
    
    private Paint paint;
    
    private int tmpComboTime = -1;
    
    private boolean isTimeLimit;
    
    public CustomViewTime(Context context)
    {
        super(context);
    }
    
    public CustomViewTime(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }
    
    public CustomViewTime(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
    }
    
    @Override
    public void initClass()
    {
        
        time_comma = getResources().getDrawable(R.drawable.play_panel_comma);
        paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        
        second = 0;
        mSecond = 0;
        isTimeLimit = false;
    }
    
    @Override
    public void setObjectSize()
    {
        int w = getWidth();
        int h = getHeight();
        
        thisRects = new Rect[5];
        if (srcBmpRect != null)
        {
            int width = srcBmpRect[0].right * h / srcBmpRect[0].bottom;
            thisRect = new Rect(0, 0, width, h);
            thisRects[0] = thisRect;
            thisRects[1] = new Rect(width, 0, width * 2, h);
            int commaWidth = ((BitmapDrawable) time_comma).getBitmap().getWidth();
            thisRects[2] = new Rect(width * 2, 0, width * 2 + commaWidth, h);
            thisRects[3] = new Rect(width * 2 + commaWidth, 0, width * 3 + commaWidth, h);
            thisRects[4] = new Rect(width * 3 + commaWidth, 0, width * 4 + commaWidth, h);
        }
        else
        {
            for (int i = 0; i < thisRects.length; i++)
                thisRects[i] = new Rect(0, 0, w, h);
        }
        time_comma.setBounds(thisRects[2]);
    }
    
    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
        time_comma.draw(canvas);
        
        if (second < 10)
        {
            canvas.drawBitmap(srcBmp, srcBmpRect[0], thisRects[0], null);
            canvas.drawBitmap(srcBmp, srcBmpRect[second], thisRects[1], null);
        }
        else if (second < 100)
        {
            int num_10 = second / 10;
            int num_remain_1 = second - (num_10 * 10);
            canvas.drawBitmap(srcBmp, srcBmpRect[num_10], thisRects[0], null);
            canvas.drawBitmap(srcBmp, srcBmpRect[num_remain_1], thisRects[1], null);
        }
        
        if (mSecond < 10)
        {
            canvas.drawBitmap(srcBmp, srcBmpRect[0], thisRects[3], null);
            canvas.drawBitmap(srcBmp, srcBmpRect[mSecond], thisRects[4], null);
        }
        else if (mSecond < 100)
        {
            
            int num_10 = mSecond / 10;
            int num_remain_1 = mSecond - (num_10 * 10);
            
            canvas.drawBitmap(srcBmp, srcBmpRect[num_10], thisRects[3], null);
            canvas.drawBitmap(srcBmp, srcBmpRect[num_remain_1], thisRects[4], null);
        }
        
        // canvas.drawRect(0, 0, getWidth(), getHeight(), paint);
    }
    
    public boolean isOverNum()
    {
        if (second > 0)
        {
            mSecond += 12;
            if (mSecond > 99)
            {
                mSecond = 0;
                second--;
            }
            invalidate();
            return false;
        }
        return true;
    }
    
    public boolean isCombo()
    {
        
        if(tmpComboTime <= second){
            tmpComboTime = second-2;
            return true;
        }
        tmpComboTime = second-2;
        return false;
    }
    
    public boolean isTimeLimitWithSound(){
        if(second<3 && !isTimeLimit){
            isTimeLimit=true;
            return true;
        }
        return false;
    }
    
    public void setNum(int num)
    {
        this.second = num;
        this.mSecond = 0;
        initCombo();
        isTimeLimit = false;
    }
    
    public void initCombo(){
        tmpComboTime = INIT_COMBO_TIME;
    }
}// class
