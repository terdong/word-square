package com.teamgehem.wordsquare;

import android.app.Activity;
import android.content.Intent;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;

import com.teamgehem.wordsquare.assets.AssetsAudio;
import com.teamgehem.wordsquarelite.R;

public class ViewPreMain extends Activity
{
    
    public ViewPreMain()
    {
    }
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_premain);
        setVolumeControlStream(AudioManager.STREAM_MUSIC);
        
        Button btnBuy, btnNext;
        btnBuy = (Button) findViewById(R.id.premain_btn_buy);
        btnNext = (Button) findViewById(R.id.premain_btn_next);
        
        btnBuy.setOnTouchListener(new OnTouchListener() {
            
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                if (event.getAction() == MotionEvent.ACTION_UP)
                {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
//                    intent.setData(Uri.parse("market://details?id=com.teamgehem.wordsquare"));
                    
                   intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                   intent.setClassName("com.skt.skaf.A000Z00040", "com.skt.skaf.A000Z00040.A000Z00040");
                   intent.setAction("COLLAB_ACTION");
                   intent.putExtra("com.skt.skaf.COL.URI", ("PRODUCT_VIEW/"+"0000281024"+"/0").getBytes());
                   intent.putExtra("com.skt.skaf.COL.REQUESTER", "A000Z00040");
                   
                    startActivity(intent);
                }
                return false;
            }
        });
        
        btnNext.setOnTouchListener(new OnTouchListener() {
            
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                if (event.getAction() == MotionEvent.ACTION_UP)
                {
                    changeView();
                    AssetsAudio.touch.play(1);
                }
                return false;
            }
        });
    }
    
    private void changeView(){
        Intent intent = new Intent(this, ViewMenu.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        overridePendingTransition(R.anim.fade,R.anim.hold);
        finish();
    }
    
}// class
