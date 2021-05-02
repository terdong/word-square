package com.teamgehem.wordsquare;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

import com.badlogic.androidgames.framework.FileIO;
import com.badlogic.androidgames.framework.impl.AndroidFileIO;
import com.teamgehem.engine.util.ExtractDrawable;
import com.teamgehem.widget.custom.impl.CustomButtonPage;
import com.teamgehem.widget.custom.impl.CustomButtonStage;
import com.teamgehem.wordsquare.assets.AssetsAudio;
import com.teamgehem.wordsquare.assets.AssetsGetWord;
import com.teamgehem.wordsquare.assets.AssetsState;
import com.teamgehem.wordsquare.db.WordSquareProvider;
import com.teamgehem.wordsquarelite.R;

public class ViewMenu extends Activity
{
    private static final String TAG = ViewMenu.class.getSimpleName();
    
    private static final String KEY_DATA = "getword";
    
    private static final String FILE_EXTENSION_STATE = "data.ws";
    
    private static final int CALLER_REQUEST = 1;
    
    private CustomButtonPage[] btn_Page;
    
    private CustomButtonStage[] btn_Stage;
    
    private WordSquareProvider wsp;
    
    public static AssetsState mAssetsState;
    
    private FileIO mFileIo;
    
    private Toast finishToast;
    
    private boolean isFinish = false;
    
    public ViewMenu()
    {
    }
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_menu);
        setVolumeControlStream(AudioManager.STREAM_MUSIC);
        initClass();
    }
    
    private void initClass()
    {
        mFileIo = new AndroidFileIO(getAssets(), this.getExternalFilesDir(null).getPath());
        wsp = WordSquareProvider.getInstance(this);
        mAssetsState = (AssetsState) loadObject(mFileIo, FILE_EXTENSION_STATE);
        
        finishToast = Toast.makeText(ViewMenu.this, R.string.menu_back_msg, Toast.LENGTH_SHORT);
        
        Button btnChapter = (Button) findViewById(R.id.menu_btn_chapter);
        btnChapter.setOnTouchListener(new OnTouchListener() {
            
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                if (MotionEvent.ACTION_UP == event.getAction())
                {
                    Intent intent = new Intent(ViewMenu.this, ViewChapter.class);
                    intent.putExtra("chapter", mAssetsState.current_stateChapter);
                    startActivityForResult(intent, CALLER_REQUEST);
                    overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
                    
                    AssetsAudio.touch.play(1);
                }
                return false;
            }
        });
        
        Button btnBack = (Button) findViewById(R.id.menu_btn_back);
        btnBack.setOnTouchListener(new OnTouchListener() {
            
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                if (MotionEvent.ACTION_UP == event.getAction())
                {
                    if (!isFinish)
                    {
                        finishToast.show();
                        isFinish = true;
                        handler.sendEmptyMessageDelayed(0, 1000 * 2);
                    }
                    else
                    {
                        finish();
                    }
                    AssetsAudio.touch.play(1);
                }
                return false;
            }
        });
        
        TableRow tr1 = (TableRow) findViewById(R.id.menu_back_board1);
        TableRow tr2 = (TableRow) findViewById(R.id.menu_back_board2);
        TableLayout tl = (TableLayout) findViewById(R.id.menu_table_stage);
        TableRow tr;
        TableRow.LayoutParams trParams = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
        trParams.gravity = Gravity.CENTER_HORIZONTAL;
        
        ExtractDrawable[] bitmap_Page_num = new ExtractDrawable[3];
        ExtractDrawable bitmap_Stage_num10 = new ExtractDrawable(this, R.drawable.stage_number_1_10, 10);
        ExtractDrawable bitmap_Stage_num20 = new ExtractDrawable(this, R.drawable.stage_number_11_20, 10);
        ExtractDrawable bitmap_Stage_num30 = new ExtractDrawable(this, R.drawable.stage_number_21_30, 10);
        
        bitmap_Page_num[0] = new ExtractDrawable(this, R.drawable.page_able_num, 10);
        bitmap_Page_num[1] = new ExtractDrawable(this, R.drawable.page_disable_num, 10);
        bitmap_Page_num[2] = new ExtractDrawable(this, R.drawable.page_select_num, 10);
        
        int state_page[][] = mAssetsState.statePage;
        btn_Page = new CustomButtonPage[10];
        for (int i = 0; i < btn_Page.length; i++)
        {
            btn_Page[i] = new CustomButtonPage(this, i);
            btn_Page[i].setState(state_page[mAssetsState.current_stateChapter][i]);
            btn_Page[i].setPageNums(bitmap_Page_num[0].bitmap, bitmap_Page_num[1].bitmap, bitmap_Page_num[2].bitmap);
            btn_Page[i].setSrcBmpRect(bitmap_Page_num[0].bounds[i]);
            btn_Page[i].setOnTouchListener(new OnTouchListener() {
                
                @Override
                public boolean onTouch(View v, MotionEvent event)
                {
                    int action = event.getAction();
                    switch (action)
                    {
                        case MotionEvent.ACTION_DOWN:
                            CustomButtonPage cbp = (CustomButtonPage) v;
                            if (!v.isSelected() && cbp.state > CustomButtonPage.STATE_OFF)
                            {
                                if(cbp.btnId==1){
                                    Intent intent = new Intent(ViewMenu.this, ViewPreMain.class);
                                    startActivity(intent);
                                    overridePendingTransition(R.anim.fade, R.anim.hold);
                                    AssetsAudio.touch.play(1);
                                    return false;
                                }
                                    
                                for (int i = 0; i < btn_Page.length; i++)
                                {
                                    if (mAssetsState.statePage[mAssetsState.current_stateChapter][i] >= CustomButtonPage.STATE_ON)
                                    {
                                        mAssetsState.statePage[mAssetsState.current_stateChapter][i] = CustomButtonPage.STATE_ON;
                                        btn_Page[i].setState(CustomButtonPage.STATE_ON);
                                    }
                                }
                                cbp.setState(CustomButtonPage.STATE_SELECT);
                                
                                mAssetsState.current_statePage = cbp.btnId;
                                mAssetsState.statePage[mAssetsState.current_stateChapter][cbp.btnId] = CustomButtonPage.STATE_SELECT;
                                int state_stage[][][] = mAssetsState.stateStage;
                                for (int i = 0; i < btn_Stage.length; i++)
                                {
                                    btn_Stage[i].setState(state_stage[mAssetsState.current_stateChapter][mAssetsState.current_statePage][i]);
                                }
                            }
                            AssetsAudio.touch.play(1);
                            break;
                        case MotionEvent.ACTION_UP:
                            
                            break;
                        default:
                            break;
                    }
                    return false;
                }
            });
            if (i < 5)
                tr1.addView(btn_Page[i]);
            else
                tr2.addView(btn_Page[i]);
        }
        btn_Page[0].setSelected(true);
        
        String service = Context.LAYOUT_INFLATER_SERVICE;
        LayoutInflater li = (LayoutInflater) getSystemService(service);
        
        int state_stage[][][] = mAssetsState.stateStage;
        int itemCount = 0;
        btn_Stage = new CustomButtonStage[30];
        for (int i = 0; i < 6; i++)
        {
            tr = new TableRow(this);
            tr.setLayoutParams(trParams);
            for (int j = 0; j < 5; j++)
            {
                btn_Stage[itemCount] = (CustomButtonStage) li.inflate(R.layout.menu_button_stage, tr, false);
                btn_Stage[itemCount].btnId = itemCount;
                btn_Stage[itemCount].setState(state_stage[mAssetsState.current_stateChapter][mAssetsState.current_statePage][itemCount]);
                if (itemCount < 10)
                {
                    btn_Stage[itemCount].setSrcBmp(bitmap_Stage_num10.bitmap);
                    btn_Stage[itemCount].setSrcBmpRect(bitmap_Stage_num10.bounds[itemCount]);
                }
                else if (itemCount < 20)
                {
                    btn_Stage[itemCount].setSrcBmp(bitmap_Stage_num20.bitmap);
                    btn_Stage[itemCount].setSrcBmpRect(bitmap_Stage_num20.bounds[itemCount - 10]);
                }
                else
                {
                    btn_Stage[itemCount].setSrcBmp(bitmap_Stage_num30.bitmap);
                    btn_Stage[itemCount].setSrcBmpRect(bitmap_Stage_num30.bounds[itemCount - 20]);
                }
                btn_Stage[itemCount].setOnTouchListener(new OnTouchListener() {
                    
                    @Override
                    public boolean onTouch(View v, MotionEvent event)
                    {
                        int action = event.getAction();
                        switch (action)
                        {
                            case MotionEvent.ACTION_DOWN:
                                
                                break;
                            case MotionEvent.ACTION_UP:
                                CustomButtonStage cbs = (CustomButtonStage) v;
                                if (cbs.isUnlock())
                                {
                                    mAssetsState.current_stateStage = cbs.btnId;
                                    Intent intent = new Intent(ViewMenu.this, ViewGame.class);
                                    AssetsGetWord[] agw = wsp.getSelectWord(mAssetsState.current_stateChapter + 1, mAssetsState.current_statePage + 1, mAssetsState.current_stateStage + 1);
                                    
                                    intent.putExtra(KEY_DATA, agw);
                                    startActivity(intent);
                                    overridePendingTransition(R.anim.fade, R.anim.hold);
                                    AssetsAudio.touch.play(1);
                                }
                                break;
                            default:
                                break;
                        }
                        return false;
                    }
                });
                tr.addView(btn_Stage[itemCount], trParams);
                itemCount++;
            }
            tl.addView(tr);
        }
    }
    
    @Override
    protected void onResume()
    {
        super.onResume();
        // Log.d(TAG, "OnResume");
        updateView();
    }
    
    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        saveObject(mFileIo, FILE_EXTENSION_STATE, mAssetsState);
        finishToast.cancel();
    }
    
    public void saveObject(FileIO fileIo, String fileExtension, Serializable s)
    {
        
        ObjectOutputStream out = null;
        try
        {
            out = new ObjectOutputStream(fileIo.writeFile(fileExtension));
            out.writeObject(s);
        }
        catch (IOException e)
        {
        }
        finally
        {
            try
            {
                if (out != null) out.close();
            }
            catch (IOException e)
            {
            }
        }
    }
    
    public Serializable loadObject(FileIO files, String fileExtension)
    {
//        AssetsState as = new AssetsState();
//        
//        saveObject(files, fileExtension, as);
//        return as;
        
         ObjectInputStream in = null;
         Serializable s = null;
         try
         {
         in = new ObjectInputStream(files.readFile(fileExtension));
         s = (Serializable) in.readObject();
         }
         catch (IOException e)
         {
         // :( It's ok we havedefaults
         // Log.d(TAG, e.getMessage());
         AssetsState as = new AssetsState();
         saveObject(files, fileExtension, as);
         return as;
         }
         catch (NumberFormatException e)
         {
         Log.d(TAG, e.getMessage());
         // :/It's ok, defaults save our day
         }
         catch (ClassNotFoundException e)
         {
         Log.d(TAG, e.getMessage());
        
         }
         finally
         {
         try
         {
         if (in != null) in.close();
         }
         catch (IOException e)
         {
         }
         }
         return s;
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == CALLER_REQUEST)
        {
            if (resultCode == RESULT_OK)
            {
                int chapter = data.getExtras().getInt("chapter");
                mAssetsState.current_stateChapter = chapter;
                updateView();
            }
        }
    }
    
    private void updateView()
    {
        int state_page[][] = mAssetsState.statePage;
        int state_stage[][][] = mAssetsState.stateStage;
        
        int current_stateChapter = mAssetsState.current_stateChapter;
        for (int i = 0; i < btn_Page.length; i++)
            btn_Page[i].setState(state_page[current_stateChapter][i]);
        
        for (int i = 0; i < state_page.length; i++)
        {
            if (state_page[current_stateChapter][i] == 2)
            {
                mAssetsState.current_statePage = i;
                break;
            }
        }
        
        int current_statePage = mAssetsState.current_statePage;
        for (int i = 0; i < btn_Stage.length; i++)
            btn_Stage[i].setState(state_stage[current_stateChapter][current_statePage][i]);
        
    }
    
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg)
        {
            if (msg.what == 0)
            {
                isFinish = false;
            }
        }
    };
    
    @Override
    public void onBackPressed()
    {
        if (!isFinish)
        {
            finishToast.show();
            isFinish = true;
            handler.sendEmptyMessageDelayed(0, 1000 * 2);
        }
        else
        {
            super.onBackPressed();
        }
        AssetsAudio.touch.play(1F);
    }
    
}// class

