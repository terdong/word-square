package com.teamgehem.widget.custom.framework;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.ImageView;

public abstract class CustomImageView extends ImageView implements Widget
{
    protected Bitmap srcBmp;
    
    protected Rect srcBmpRect[];
    
    protected Rect thisRect;
    
    public CustomImageView(Context context)
    {
        super(context);
        initClass();
    }
    
    public CustomImageView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        initClass();
    }
    
    public CustomImageView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        initClass();
    }
    
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh)
    {
        super.onSizeChanged(w, h, oldw, oldh);
//        thisRect = new Rect(0,0,w,h);
        setObjectSize();
    }
    
    public abstract void initClass();
    
    public abstract void setObjectSize();
    
    public void setSrcBmp(Bitmap srcBmp)
    {
        this.srcBmp = srcBmp;
    }
    
    public void setSrcBmpRect(Rect[] srcBmpRect)
    {
        this.srcBmpRect = srcBmpRect;
    }
    
    public void setThisRect(Rect thisRect)
    {
        this.thisRect = thisRect;
    }
    
}// class
