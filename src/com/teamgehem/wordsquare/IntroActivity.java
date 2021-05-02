package com.teamgehem.wordsquare;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.Window;

import com.badlogic.androidgames.framework.Audio;
import com.badlogic.androidgames.framework.impl.AndroidAudio;
import com.teamgehem.wordsquare.assets.AssetsAudio;
import com.teamgehem.wordsquarelite.R;

public class IntroActivity extends Activity
{
    private static final int DELAY_TO_NEXT_VIEW = 3000;
    private Audio wsaAudio;
    private boolean isTouch;
    public IntroActivity()
    {
    }
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        overridePendingTransition(R.anim.fade,R.anim.fade);
        setContentView(R.layout.view_intro);
        handler.sendEmptyMessageDelayed(0, DELAY_TO_NEXT_VIEW);
        initClass();
    }
    
    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        super.onTouchEvent(event);
        
        if(event.getAction() == MotionEvent.ACTION_DOWN){
            isTouch = true;
            AssetsAudio.touch.play(1);
            changeView();
        }
        return true;
    }
    
    Handler handler = new Handler()
    {
        public void handleMessage(Message msg)
        {
            if(msg.what==0 && !isTouch){
                changeView();
            }
        }
    };
    
    private void changeView(){
        Intent intent = new Intent(this, ViewPreMain.class);
        startActivity(intent);
        overridePendingTransition(R.anim.fade,R.anim.hold);
        finish();
    }
    
    public void initClass(){
        wsaAudio = new AndroidAudio(this);
        AssetsAudio.combo = wsaAudio.newSound("combo.ogg");
        AssetsAudio.touch = wsaAudio.newSound("touch.ogg");
        AssetsAudio.touch_incorrect = wsaAudio.newSound("touch_incorrect.ogg");
        AssetsAudio.time_limit = wsaAudio.newSound("time_limit.wav");
        AssetsAudio.whistle = wsaAudio.newSound("whistle.ogg");
        AssetsAudio.stamp = wsaAudio.newSound("throw.ogg");
        
        isTouch = false;
    }
    
}// class








