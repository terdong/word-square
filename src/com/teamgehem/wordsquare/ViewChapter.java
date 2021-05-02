package com.teamgehem.wordsquare;

import android.app.Activity;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.LinearLayout;

import com.teamgehem.wordsquare.assets.AssetsAudio;
import com.teamgehem.wordsquarelite.R;

public class ViewChapter extends Activity implements OnTouchListener
{
    private static final String TAG = ViewChapter.class.getSimpleName();
    
    private LinearLayout chapter_btn[];
    
    private int chapterValue = 0;
    
    public ViewChapter()
    {
        // TODO Auto-generated constructor stub
    }
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_chapter);
        setVolumeControlStream(AudioManager.STREAM_MUSIC);
        
        chapter_btn = new LinearLayout[3];
        chapter_btn[0] = (LinearLayout) findViewById(R.id.chapter_btn_1);
        chapter_btn[1] = (LinearLayout) findViewById(R.id.chapter_btn_2);
        chapter_btn[2] = (LinearLayout) findViewById(R.id.chapter_btn_3);
        chapter_btn[0].setOnTouchListener(this);
        chapter_btn[1].setOnTouchListener(this);
        chapter_btn[2].setOnTouchListener(this);
        
        chapterValue = getIntent().getExtras().getInt("chapter");
        chapter_btn[chapterValue].setSelected(true);
        
        Button btnBack = (Button) findViewById(R.id.chapter_btn_back);
        btnBack.setOnTouchListener(new OnTouchListener() {
            
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                if (MotionEvent.ACTION_UP == event.getAction())
                {
                    finish();
                    overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                    AssetsAudio.touch.play(1);
                }
                return false;
            }
        });
        
    }
    
    @Override
    public boolean onTouch(View v, MotionEvent event)
    {
        if (event.getAction() == MotionEvent.ACTION_DOWN)
        {
            if (v.getId() == chapter_btn[0].getId())
            {
                chapter_btn[0].setSelected(true);
                chapter_btn[1].setSelected(false);
                chapter_btn[2].setSelected(false);
                chapterValue = 0;
            }
            else if (v.getId() == chapter_btn[1].getId())
            {
                chapter_btn[1].setSelected(true);
                chapter_btn[0].setSelected(false);
                chapter_btn[2].setSelected(false);
                chapterValue = 1;
            }
            else if (v.getId() == chapter_btn[2].getId())
            {
                chapter_btn[2].setSelected(true);
                chapter_btn[1].setSelected(false);
                chapter_btn[0].setSelected(false);
                chapterValue = 2;
            }
            // Log.d(TAG,"TOUCHED");
            Intent intent = new Intent(ViewChapter.this, ViewMenu.class);
            intent.putExtra("chapter", chapterValue);
            setResult(RESULT_OK, intent);
            finish();
            overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            AssetsAudio.touch.play(1);
        }
        return false;
    }
    
}// class

