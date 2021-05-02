package com.teamgehem.widget.custom.impl;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import com.teamgehem.widget.custom.framework.CustomButton;
import com.teamgehem.wordsquarelite.R;

public class CustomButtonStage extends CustomButton
{
    private static final String TAG = CustomButtonStage.class.getSimpleName();
    
    public static final int STATE_OFF = 0, STATE_ON = 1, STATE_STAR_01 = 2, STATE_STAR_02 = 3, STATE_STAR_03 = 4, STATE_CROWN = 5;
    
    private int state = STATE_OFF;
    
    private Drawable draw_lock;
    
    private Drawable draw_grade[];
    
    private Drawable current_draw_grade;
    
    public CustomButtonStage(Context context, int id)
    {
        super(context);
        this.btnId = id;
    }
    
    public CustomButtonStage(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }
    
    public CustomButtonStage(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
    }
    
    @Override
    public void initClass()
    {
        setBackgroundResource(R.drawable.btn_stage);
        draw_lock = getResources().getDrawable(R.drawable.stage_disable);
        
        draw_grade = new Drawable[4];
        draw_grade[0] = getResources().getDrawable(R.drawable.stage_grade_1);
        draw_grade[1] = getResources().getDrawable(R.drawable.stage_grade_2);
        draw_grade[2] = getResources().getDrawable(R.drawable.stage_grade_3);
        draw_grade[3] = getResources().getDrawable(R.drawable.stage_grade_4);
    }
    
    @Override
    public void setObjectSize()
    {
        draw_lock.setBounds(thisRect);
        for (int i = 0; i < draw_grade.length; i++)
            draw_grade[i].setBounds(thisRect);
    }
    
    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
        canvas.drawBitmap(srcBmp, srcBmpRect, thisRect, null);
        if (current_draw_grade != null) current_draw_grade.draw(canvas);
    }
    
    public void setState(int state)
    {
        this.state = state;
        switch (state)
        {
            case STATE_OFF:
                current_draw_grade = draw_lock;
                break;
            case STATE_ON:
                current_draw_grade = null;
                break;
            case STATE_STAR_01:
                current_draw_grade = draw_grade[0];
                break;
            case STATE_STAR_02:
                current_draw_grade = draw_grade[1];
                break;
            case STATE_STAR_03:
                current_draw_grade = draw_grade[2];
                break;
            case STATE_CROWN:
                current_draw_grade = draw_grade[3];
                break;
            
            default:
                break;
        }
        invalidate();
    }
    
    public boolean isUnlock()
    {
        return state > STATE_OFF;
    }
    
}// class

