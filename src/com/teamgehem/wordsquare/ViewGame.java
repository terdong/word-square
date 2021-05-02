package com.teamgehem.wordsquare;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.os.Vibrator;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

import com.teamgehem.ad.ManagerAd;
import com.teamgehem.engine.util.ExtractDrawable;
import com.teamgehem.widget.custom.impl.CustomButtonWord;
import com.teamgehem.widget.custom.impl.CustomViewNumber;
import com.teamgehem.widget.custom.impl.CustomViewTime;
import com.teamgehem.wordsquare.assets.AssetsAudio;
import com.teamgehem.wordsquare.assets.AssetsGetWord;
import com.teamgehem.wordsquarelite.R;

public class ViewGame extends Activity
{
    private static final String TAG = ViewGame.class.getSimpleName();
    
    private static final String KEY_DATA = "getword";
    
    private static final int BUTTON_NUMBER = 8;
    
    private static final long VIBRATOR_NO = 100L;
    
    private static final long TIME_REVISION = 12L;
    
    private static final int TIME_COUNT = 30;
    
    private Button btnBack;
    
    private Toast finishToast;
    
    private boolean isFinish = false;
    
    private AssetsGetWord[] agw;
    
    private CustomButtonWord[] mBtnWord;
    
    private ArrayList<Integer> al;
    
    private Vibrator vibrator;
    
    private CustomViewNumber gameCount;
    
    private CustomViewNumber gameCombo;
    
    private CustomViewNumber gameScore;
    
    private CustomViewTime gameTime;
    
    private ImageView gameImgCombo;
    
    private Timer timer;
    
    private TimerTask timerTask;
    
    private TableLayout table;
    
    private TableRow tablerow1, tablerow2, tablerow3;
    
    private Animation aniAppearStartBoard[];
    
    private ProgressBar mProgressBar;
    
    private ManagerAd mManagerAd;
    
    private Animation aniCombo_in;
    
    private Animation aniCombo_out;
    
    private ImageView gameImgComboEffect = null;
    
    public ViewGame()
    {
    }
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_game);
        setVolumeControlStream(AudioManager.STREAM_MUSIC);
        initClass();
        
    }
    
    private void initTimer()
    {
        gameTime.setNum(TIME_COUNT - 1);
        timerTask = new TimerTask() {
            
            @Override
            public void run()
            {
                handlerTimer.sendEmptyMessage(1);
            }
        };
        timer = new Timer();
        timer.schedule(timerTask, 100, 10 * TIME_REVISION);
    }
    
    private void initGame()
    {
        gameCount.initNumCount();
        gameCombo.initNumCount();
        gameScore.initNumCount();
        gameTime.setNum(TIME_COUNT);
        gameImgCombo.setVisibility(View.INVISIBLE);
        mProgressBar.setProgress(TIME_COUNT);
    }
    
    private void initWord()
    {
        al.clear();
        for (int i = 0; i < 7; i += 2)
            al.add(i);
        
        Collections.shuffle(al);
        
        for (int i = 0; i < 4; i++)
        {
            mBtnWord[i].setAssetWord(agw[al.get(i)]);
        }
        
        al.clear();
        for (int i = 1; i < 8; i += 2)
            al.add(i);
        
        Collections.shuffle(al);
        
        for (int i = 4; i < 8; i++)
        {
            mBtnWord[i].setAssetWord(agw[al.get(i - 4)]);
        }
        
    }
    
    private void initClass()
    {
        aniCombo_in = AnimationUtils.loadAnimation(this, R.anim.zoom_in);
        aniCombo_out = AnimationUtils.loadAnimation(this, R.anim.fade_out);
        
        FrameLayout ad_layout = (FrameLayout) findViewById(R.id.game_ad_layout);
        
        mManagerAd = new ManagerAd(this, ad_layout);
        
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        
        al = new ArrayList<Integer>(8);
        
        aniAppearStartBoard = new Animation[2];
        aniAppearStartBoard[0] = AnimationUtils.loadAnimation(this, R.anim.push_top_out);
        aniAppearStartBoard[1] = AnimationUtils.loadAnimation(this, R.anim.push_top_in);
        
        ExtractDrawable bitmap_rect_time_num = new ExtractDrawable(this, R.drawable.play_time, 10);
        
        gameCount = (CustomViewNumber) findViewById(R.id.game_count_num);
        gameCombo = (CustomViewNumber) findViewById(R.id.game_combo_num);
        gameScore = (CustomViewNumber) findViewById(R.id.game_score_num);
        gameTime = (CustomViewTime) findViewById(R.id.game_time_num);
        
        gameImgCombo = (ImageView) findViewById(R.id.game_combo);
        
        gameCount.setSrcBmp(bitmap_rect_time_num.bitmap);
        gameCount.setSrcBmpRect(bitmap_rect_time_num.bounds);
        gameCombo.setSrcBmp(bitmap_rect_time_num.bitmap);
        gameCombo.setSrcBmpRect(bitmap_rect_time_num.bounds);
        gameScore.setSrcBmp(bitmap_rect_time_num.bitmap);
        gameScore.setSrcBmpRect(bitmap_rect_time_num.bounds);
        gameTime.setSrcBmp(bitmap_rect_time_num.bitmap);
        gameTime.setSrcBmpRect(bitmap_rect_time_num.bounds);
        
        mBtnWord = new CustomButtonWord[BUTTON_NUMBER];
        mBtnWord[0] = (CustomButtonWord) findViewById(R.id.game_btn_word_00);
        mBtnWord[1] = (CustomButtonWord) findViewById(R.id.game_btn_word_02);
        mBtnWord[2] = (CustomButtonWord) findViewById(R.id.game_btn_word_04);
        mBtnWord[3] = (CustomButtonWord) findViewById(R.id.game_btn_word_06);
        mBtnWord[4] = (CustomButtonWord) findViewById(R.id.game_btn_word_01);
        mBtnWord[5] = (CustomButtonWord) findViewById(R.id.game_btn_word_03);
        mBtnWord[6] = (CustomButtonWord) findViewById(R.id.game_btn_word_05);
        mBtnWord[7] = (CustomButtonWord) findViewById(R.id.game_btn_word_07);
        
        Bundle bundle = getIntent().getExtras();
        Parcelable[] parcelable = bundle.getParcelableArray(KEY_DATA);
        agw = new AssetsGetWord[parcelable.length];
        for (int i = 0; i < parcelable.length; i++)
            agw[i] = (AssetsGetWord) parcelable[i];
        
        mProgressBar = (ProgressBar) findViewById(R.id.game_progressbar);
        
        for (int i = 0; i < mBtnWord.length; i++)
        {
            mBtnWord[i].btnId = i;
            mBtnWord[i].setOnTouchListener(new OnTouchListener() {
                
                @Override
                public boolean onTouch(View v, MotionEvent event)
                {
                    if (MotionEvent.ACTION_UP == event.getAction())
                    {
                        if (timer == null && !table.isClickable()) initTimer();
                        
                        if(!gameScore.isShown()){
                            gameScore.setVisibility(View.VISIBLE);
                        }
                        
                        CustomButtonWord cbw = (CustomButtonWord) v;
                        int id_match = cbw.getIdMatch();
                        int id = cbw.btnId;
                        if (!cbw.isAnswer())
                        {
                            for (int i = 0; i < 8; i++)
                            {
                                if (mBtnWord[i].isSelected() && !mBtnWord[i].isAnswer() && id != mBtnWord[i].btnId)
                                {
                                    if (mBtnWord[i].getIdMatch() == id_match)
                                    {
                                        cbw.setAnswer(true);
                                        mBtnWord[i].setAnswer(true);
                                        
                                        gameCount.addNum();
                                        
                                        if (gameTime.isCombo() && !table.isClickable())
                                        {
                                            
                                            gameCombo.addNum();
                                            gameImgCombo.startAnimation(aniCombo_in);
                                            gameImgCombo.setVisibility(View.VISIBLE);
                                            gameImgComboEffect.startAnimation(aniCombo_in);
                                            gameImgComboEffect.setVisibility(View.VISIBLE);
                                            
                                            handlerTimer.sendEmptyMessageDelayed(2, 1000);
                                            AssetsAudio.combo.play(1f);
                                        }
                                    }
                                    else
                                    {
                                        vibrator.vibrate(VIBRATOR_NO);
                                        initWord();
                                        
                                        gameTime.initCombo();
                                        gameCount.subNum();
                                        
                                        AssetsAudio.touch_incorrect.play(1);
                                        
                                    }
                                    gameScore.setNum(gameCount.num * gameCombo.num);
                                }
                                
                            }
                            
                        }
                        int compliteCount = 0;
                        int length = mBtnWord.length;
                        for (int i = 0; i < length; i++)
                        {
                            if (mBtnWord[i].isAnswer()) compliteCount++;
                        }
                        if (compliteCount == length)
                        {
                            initWord();
//                            gameTime.initCombo();
                        }
                        AssetsAudio.touch.play(1);
                    }
                    return false;
                }
            });
        }
        
        finishToast = Toast.makeText(ViewGame.this, R.string.game_back_msg, Toast.LENGTH_SHORT);
        finishToast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM, 0, 0);
        
        btnBack = (Button) findViewById(R.id.game_btn_back);
        btnBack.setOnTouchListener(new OnTouchListener() {
            
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                int action = event.getAction();
                if (MotionEvent.ACTION_UP == action)
                {
                    eventFinish();
                }
                return false;
            }
        });
        
        table = (TableLayout) findViewById(R.id.game_table_page);
        tablerow1 = (TableRow) findViewById(R.id.game_back_board1);
        tablerow2 = (TableRow) findViewById(R.id.game_back_board2);
        tablerow3 = (TableRow) findViewById(R.id.game_back_board3);
        tablerow1.setVisibility(View.INVISIBLE);
        tablerow2.setVisibility(View.INVISIBLE);
        tablerow3.setVisibility(View.INVISIBLE);
        table.setClickable(true);
        table.setOnTouchListener(new OnTouchListener() {
            
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                if (table.isClickable())
                {
                    initGame();
                    for (int i = 0; i < mBtnWord.length; i++)
                        if (mBtnWord[i].isSelected())
                        {
                            initWord();
                            break;
                        }
                    
                    table.setClickable(false);
                    table.setBackgroundResource(0);
                    tablerow1.setVisibility(View.VISIBLE);
                    tablerow2.setVisibility(View.VISIBLE);
                    tablerow3.setVisibility(View.VISIBLE);
                    AssetsAudio.whistle.play(1);
                }
                return false;
            }
        });
        
        initWord();
        initGame();
        
        mManagerAd.ShowAd(0);
        
    }
    
    @Override
    public void onWindowFocusChanged(boolean hasFocus)
    {
        super.onWindowFocusChanged(hasFocus);
        
        if (gameImgComboEffect == null)
        {
            RelativeLayout game_layout_table_page = (RelativeLayout) findViewById(R.id.game_layout_table_page);
            int height = game_layout_table_page.getHeight();
            
            gameImgComboEffect = (ImageView) findViewById(R.id.game_combo_effect);
            gameImgComboEffect.getLayoutParams().height = height;
        }
    }
    
    
    
    @Override
    protected void onDestroy()
    {
        finishToast.cancel();
        calcelTimer();
        mManagerAd.dispose();
        super.onDestroy();
    }
    
    private Handler handlerFinish = new Handler() {
        @Override
        public void handleMessage(Message msg)
        {
            if (msg.what == 0)
            {
                isFinish = false;
            }
        }
    };
    
    private Handler handlerTimer = new Handler() {
        
        @Override
        public void handleMessage(Message msg)
        {
            int key = msg.what;
            if (key == 1)
            {
                if (!gameTime.isOverNum())
                {
                    mProgressBar.setProgress(gameTime.second);
                }
                else
                {
                    if (!isFinish)
                    {
                        calcelTimer();
                        nextView();
                    }
                    isFinish = true;
                }
                
                if (gameTime.isTimeLimitWithSound())
                {
                    AssetsAudio.time_limit.play(1);
                }
                
            }
            else if (key == 2)
            {
                gameImgCombo.startAnimation(aniCombo_out);
                gameImgCombo.setVisibility(View.INVISIBLE);
                gameImgComboEffect.startAnimation(aniCombo_out);
                gameImgComboEffect.setVisibility(View.INVISIBLE);
            }
        }
        
    };
    
    private void nextView()
    {
        Intent intent = new Intent(ViewGame.this, ViewResult.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        intent.putExtra("result", new int[] { gameCount.num, gameCombo.num });
        intent.putExtra(KEY_DATA, agw);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.push_top_in, R.anim.hold);
        AssetsAudio.whistle.play(1);
    }
    
    private void calcelTimer()
    {
        if (timerTask != null)
        {
            timerTask.cancel();
            timerTask = null;
        }
        if (timer != null)
        {
            timer.cancel();
            timer = null;
        }
    }
    
    @Override
    public void onBackPressed()
    {
        // super.onBackPressed();
        eventFinish();
    }
    
    private void eventFinish()
    {
        if (!isFinish)
        {
            calcelTimer();
            
            finishToast.show();
            isFinish = true;
            handlerFinish.sendEmptyMessageDelayed(0, 1000 * 2);
            initWord();
            initGame();
            
            tablerow1.setVisibility(View.INVISIBLE);
            tablerow2.setVisibility(View.INVISIBLE);
            tablerow3.setVisibility(View.INVISIBLE);
            table.setClickable(true);
            table.setBackgroundResource(R.drawable.play_start);
            
        }
        else
        {
            finish();
        }
        AssetsAudio.touch.play(1);
    }
    
}// class

