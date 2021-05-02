package com.teamgehem.wordsquare;

import android.app.Activity;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.teamgehem.ad.ManagerAd;
import com.teamgehem.engine.util.ExtractDrawable;
import com.teamgehem.widget.custom.impl.CustomViewNumber;
import com.teamgehem.wordsquare.assets.AssetsAudio;
import com.teamgehem.wordsquare.assets.AssetsGetWord;
import com.teamgehem.wordsquare.assets.AssetsState;
import com.teamgehem.wordsquare.db.WordSquareProvider;
import com.teamgehem.wordsquarelite.R;

public class ViewResult extends Activity
{
    private static final String TAG = ViewResult.class.getSimpleName();
    
    private static final String KEY_DATA = "getword";
    
    private static final int GRADE_NUM[] = new int[] { 400, 700, 1000, 2000 };
    
    private static final long GRADE_ANI_DELAY = 1000L;
    
    private int resultNum[];
    
    private Animation gradeAni;
    
    private ImageView grade;
    
    private WordSquareProvider wsp;
    
    private AssetsState assetsState;
    
    private int totalScore;
    
    private ManagerAd mManagerAd;
    
    private Button resultMenu;
    private Button resultReplay;
    private Button resultNext;
    
    public ViewResult()
    {
        // TODO Auto-generated constructor stub
    }
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_result);
        setVolumeControlStream(AudioManager.STREAM_MUSIC);
        initClass();
    }
    
    private void initClass()
    {
        FrameLayout ad_layout = (FrameLayout) findViewById(R.id.result_ad_layout);
        
        mManagerAd = new ManagerAd(this, ad_layout);
        
        wsp = WordSquareProvider.getInstance(this);
        
        assetsState = ViewMenu.mAssetsState;
        
        resultNum = getIntent().getExtras().getIntArray("result");
        
        ExtractDrawable bitmap_rect_score_num = new ExtractDrawable(this, R.drawable.result_score_num, 10);
        
        final CustomViewNumber cvnCount = (CustomViewNumber) findViewById(R.id.result_image_count_num);
        cvnCount.setSrcBmp(bitmap_rect_score_num.bitmap);
        cvnCount.setSrcBmpRect(bitmap_rect_score_num.bounds);
        cvnCount.setNum(resultNum[0]);
        cvnCount.setAlign(1);
        
        final CustomViewNumber cvnCombo = (CustomViewNumber) findViewById(R.id.result_image_combo_num);
        cvnCombo.setSrcBmp(bitmap_rect_score_num.bitmap);
        cvnCombo.setSrcBmpRect(bitmap_rect_score_num.bounds);
        cvnCombo.setNum(resultNum[1]);
        cvnCombo.setAlign(1);
        
        final CustomViewNumber cvnScore[] = new CustomViewNumber[4];
        
        cvnScore[0] = (CustomViewNumber) findViewById(R.id.result_image_score01);
        cvnScore[1] = (CustomViewNumber) findViewById(R.id.result_image_score02);
        cvnScore[2] = (CustomViewNumber) findViewById(R.id.result_image_score03);
        cvnScore[3] = (CustomViewNumber) findViewById(R.id.result_image_score04);
        
        for (int i = 0; i < cvnScore.length; i++)
        {
            cvnScore[i].setSrcBmp(bitmap_rect_score_num.bitmap);
            cvnScore[i].setSrcBmpRect(bitmap_rect_score_num.bounds);
            cvnScore[i].setNum(0);
        }
        
        final int tmpChapter = assetsState.current_stateChapter;
        final int tmpPage = assetsState.current_statePage;
        final int tmpStage = assetsState.current_stateStage;
        
        totalScore = resultNum[0] * resultNum[1];
//         totalScore = resultNum[0] * 200;
        
        if (totalScore < 10)
        {
            cvnScore[3].num = totalScore;
        }
        else if (totalScore < 100)
        {
            int num_10 = totalScore / 10;
            int num_1 = totalScore - (num_10 * 10);
            
            cvnScore[2].num = num_10;
            cvnScore[3].num = num_1;
            
        }
        else if (totalScore < 1000)
        {
            
            int num_100 = totalScore / 100;
            int num_100_remain = totalScore - (num_100 * 100);
            int num_10 = num_100_remain / 10;
            int num_1 = num_100_remain - (num_10 * 10);
            
            cvnScore[1].num = num_100;
            cvnScore[2].num = num_10;
            cvnScore[3].num = num_1;
            
        }
        else if (totalScore < 10000)
        {
            int num_1000 = totalScore / 1000;
            int num_1000_remain = totalScore - (num_1000 * 1000);
            int num_100 = num_1000_remain / 100;
            int num_100_remain = num_1000_remain - (num_100 * 100);
            int num_10 = num_100_remain / 10;
            int num_1 = num_100_remain - (num_10 * 10);
            
            cvnScore[0].num = num_1000;
            cvnScore[1].num = num_100;
            cvnScore[2].num = num_10;
            cvnScore[3].num = num_1;
        }
        
        resultMenu = (Button) findViewById(R.id.result_btn_menu);
        resultReplay = (Button) findViewById(R.id.result_btn_replay);
        resultNext = (Button) findViewById(R.id.result_btn_next);
        
        resultMenu.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                if (MotionEvent.ACTION_UP == event.getAction())
                {
                    finish();
                    AssetsAudio.touch.play(1);
                }
                return false;
            }
        });
        
        resultReplay.setOnTouchListener(new OnTouchListener() {
            
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                if (event.getAction() == MotionEvent.ACTION_UP)
                {
                    assetsState.current_stateChapter = tmpChapter;
                    assetsState.current_statePage = tmpPage;
                    assetsState.current_stateStage = tmpStage;
                    
                    Intent intent = new Intent(ViewResult.this, ViewGame.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                    intent.putExtra(KEY_DATA, getIntent().getExtras().getParcelableArray(KEY_DATA));
                    startActivity(intent);
                    overridePendingTransition(R.anim.fade, R.anim.hold);
                    finish();
                    AssetsAudio.touch.play(1);
                }
                return false;
            }
        });
        
        resultNext.setOnTouchListener(new OnTouchListener() {
            
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                if (event.getAction() == MotionEvent.ACTION_UP)
                {
                    Intent intent;
                    if(assetsState.current_stateStage > 29){
                        assetsState.current_stateStage=0;
                        intent = new Intent(ViewResult.this, ViewPreMain.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.fade, R.anim.hold);
                        finish();
                    }else{
                        intent = new Intent(ViewResult.this, ViewGame.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                        AssetsGetWord[] agw = wsp.getSelectWord(assetsState.current_stateChapter + 1, assetsState.current_statePage + 1, assetsState.current_stateStage + 1);
                        intent.putExtra(KEY_DATA, agw);
                        startActivity(intent);
                        overridePendingTransition(R.anim.fade, R.anim.hold);
                        finish();
                    }
                    AssetsAudio.touch.play(1);
                }
                return false;
            }
        });
        
        grade = (ImageView) findViewById(R.id.result_image_grade);
        if (totalScore < GRADE_NUM[0])
        {
            grade.setImageDrawable(getResources().getDrawable(R.drawable.result_failed));
            if(!assetsState.isOpenNextState())
                resultNext.setVisibility(View.INVISIBLE);
                
        }
        else if (totalScore < GRADE_NUM[1])
        {
            grade.setImageDrawable(getResources().getDrawable(R.drawable.result_grade_1));
            assetsState.setStageGrade(2);
        }
        else if (totalScore < GRADE_NUM[2])
        {
            grade.setImageDrawable(getResources().getDrawable(R.drawable.result_grade_2));
            assetsState.setStageGrade(3);
        }
        else if (totalScore < GRADE_NUM[3])
        {
            grade.setImageDrawable(getResources().getDrawable(R.drawable.result_grade_3));
            assetsState.setStageGrade(4);
        }
        else if (totalScore >= GRADE_NUM[3])
        {
            grade.setImageDrawable(getResources().getDrawable(R.drawable.result_grade_4));
            assetsState.setStageGrade(5);
        }
        
        gradeAni = AnimationUtils.loadAnimation(this, R.anim.zoom_exit);
        
        handler.sendEmptyMessageDelayed(1, GRADE_ANI_DELAY);
        
        if (totalScore >= GRADE_NUM[0])
        {
            assetsState.nextStageUp();
            handler.sendEmptyMessageDelayed(2, 2000);
        }
        
        mManagerAd.ShowAd(0);
    }
    
    Handler handler = new Handler() {
        
        @Override
        public void handleMessage(Message msg)
        {
            if (msg.what == 1)
            {
                AssetsAudio.stamp.play(1);
                grade.startAnimation(gradeAni);
                grade.setVisibility(View.VISIBLE);
                handler.sendEmptyMessageDelayed(3, 1000);
            }
            else if (msg.what == 2)
            {
            }else if(msg.what==3){
                resultMenu.setClickable(true);
                resultNext.setClickable(true);
                resultReplay.setClickable(true);
            }
        }
        
    };
    
    @Override
    protected void onResume()
    {
        super.onResume();
        
        // AssetsAudio.whistle.play(1);
    }
    
    @Override
    protected void onDestroy()
    {
        mManagerAd.dispose();
        super.onDestroy();
    }
    
}// class

