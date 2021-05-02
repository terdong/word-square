package com.teamgehem.widget.custom.impl;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.teamgehem.widget.custom.framework.CustomButton;
import com.teamgehem.wordsquare.assets.AssetsGetWord;
import com.teamgehem.wordsquarelite.R;

public class CustomButtonWord extends CustomButton 
{
    private static final String TAG = CustomButtonWord.class.getSimpleName();
    
    private static final String DEFAULT_MSG = "Olligodang";
    
    private boolean isAnswer;
    
    private Paint paintText;
    
    private float textWidth, textHeight;
    
    private AssetsGetWord assetWord;
    
    private String textMsg;
    
    private int lightYellow;
    
    
    public CustomButtonWord(Context context)
    {
        super(context);
    }
    
    
    public CustomButtonWord(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }
    
    public CustomButtonWord(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
    }
    
    @Override
    public void initClass()
    {
        paintText = new Paint();
        paintText.setColor(Color.BLACK);
        paintText.setAntiAlias(true);
        paintText.setTextSize(25F);
        paintText.setTypeface(Typeface.DEFAULT_BOLD);
        textMsg = DEFAULT_MSG;
        isAnswer = false;
        lightYellow = getResources().getColor(R.color.light_yellow);
    }

    @Override
    public void setObjectSize()
    {
        Rect bounds = new Rect();
        paintText.getTextBounds(textMsg, 0, textMsg.length(), bounds);
        textWidth = ((float) getWidth() - bounds.width()) / 2.0F;
        textHeight = ((float) (getHeight() - 4) + bounds.height()) / 2.0F - 1.0F;
    }
    
    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        super.onTouchEvent(event);
        
        int action = event.getAction();
        
        switch (action)
        {
            case MotionEvent.ACTION_DOWN:
                if (isSelected())
                {
                    paintText.setColor(lightYellow);
                    String str = assetWord.mean; 
                    textMsg = str!=null?str:textMsg;
                    setObjectSize();
                }
                else
                {
                    paintText.setColor(Color.WHITE);
                    setSelected(true);
                }
                break;
            case MotionEvent.ACTION_UP:
                // paintText.setColor(Color.BLACK);
                break;
            default:
                // paintText.setColor(Color.BLACK);
                break;
        }
        
        invalidate();
        return true;
    }
    
    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
        
        canvas.drawText(textMsg, textWidth, textHeight, paintText);
    }
    
    public void setAssetWord(AssetsGetWord assetWord)
    {
        this.assetWord = assetWord;
        textMsg = assetWord.word;
        setObjectSize();
        setSelected(false);
        paintText.setColor(Color.BLACK);
        isAnswer = false;
        invalidate();
    }
    
    public int getIdMatch()
    {
        return assetWord.getId_match();
    }
    
    public boolean isAnswer()
    {
        return isAnswer;
    }
    
    public void setAnswer(boolean isAnswer)
    {
        this.isAnswer = isAnswer;
    }
    
    public String getEngWord(){
        return assetWord.word;
    }
    
}// class

