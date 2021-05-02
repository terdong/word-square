package com.teamgehem.widget.custom.impl;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.teamgehem.widget.custom.framework.CustomButton;
import com.teamgehem.wordsquarelite.R;

public class CustomButtonPage extends CustomButton
{
    private static final String TAG = CustomButtonPage.class.getSimpleName();
    
    public static final int STATE_OFF = 0, STATE_ON = 1, STATE_SELECT = 2;
    
    public int state = STATE_OFF;
    
    private Bitmap bmp_Page_disable_num;
    
    private Bitmap bmp_Page_enable_num;
    
    private Bitmap bmp_Page_select_num;
    
    public CustomButtonPage(Context context, int id)
    {
        super(context);
        this.btnId = id;
    }

    @Override
    public void initClass()
    {
        setBackgroundResource(R.drawable.btn_page);
        setEnabled(true);
    }

    @Override
    public void setObjectSize()
    {
    }

    public void setPageNums(Bitmap enable, Bitmap disable, Bitmap select)
    {
        this.bmp_Page_enable_num = enable;
        this.bmp_Page_disable_num = disable;
        this.bmp_Page_select_num = select;
    }
    
    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
        if (isSelected())
        {
            canvas.drawBitmap(bmp_Page_select_num, srcBmpRect, thisRect, null);
        }
        else if (isEnabled())
        {
            canvas.drawBitmap(bmp_Page_enable_num, srcBmpRect, thisRect, null);
        }
        else if (!isEnabled())
        {
            canvas.drawBitmap(bmp_Page_disable_num, srcBmpRect, thisRect, null);
        }
    }
    
    public void setState(int state)
    {
        this.state = state;
        setImage();
    }
    
    private void setImage()
    {
        switch (state)
        {
            case STATE_OFF:
                setEnabled(false);
                setSelected(false);
                break;
            case STATE_ON:
                setEnabled(true);
                setSelected(false);
                break;
            case STATE_SELECT:
                setSelected(true);
                break;
            default:
                break;
        }
    }


}// class

