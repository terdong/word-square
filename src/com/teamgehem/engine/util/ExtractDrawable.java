package com.teamgehem.engine.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;

public class ExtractDrawable
{
    private BitmapDrawable bd;
    public Bitmap bitmap;
    public Rect bounds [] ;
    public ExtractDrawable(Context context, int id, int num)
    {
        bd = (BitmapDrawable)context.getResources().getDrawable(id);
        bitmap = bd.getBitmap();
        int width = bitmap.getWidth()/num;
        int height = bitmap.getHeight();
        bounds = new Rect[num];
        int target_width=0;
        int target_height=0;
        for(int i=0; i<num; i++){
            bounds[i] = new Rect(target_width, target_height,target_width+width,height);
            target_width+=width;
        }
    }
    
}// class
