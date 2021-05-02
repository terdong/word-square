package com.teamgehem.wordsquare.db;

import android.content.Context;
import android.database.Cursor;

import com.teamgehem.gehemengine.provider.GehemDBPath;
import com.teamgehem.wordsquare.assets.AssetsGetWord;

public class WordSquareProvider
{
    private static final String TAG = WordSquareProvider.class.getSimpleName();
    
    private static final String DB_PATH = "/data/data/com.teamgehem.wordsquarelite/";
    
    private static final String DB_DIR = "databases/";
    
    private static final String DB_NAME = "wordsquare.db";
    
    private static final String SQL_GET_SELECT_WORDS = "SELECT * FROM standard_db where chapter=? AND page = ? AND stage = ?";
    
    private static final GehemDBPath gdbp = new GehemDBPath(DB_PATH, DB_DIR, DB_NAME);
    
    private static WordSquareProvider instance;
    
    private WordSquareDBHandler wsdbh;
    
    private Cursor cursor;
    
    private Context context;
    
    private WordSquareProvider(Context context)
    {
        this.context = context;
    }
    
    public static WordSquareProvider getInstance(Context context)
    {
        synchronized (WordSquareProvider.class)
        {
            if (instance == null) instance = new WordSquareProvider(context);
        }
        return instance;
    }
    
    public void setWordsquareHandler()
    {
        wsdbh = WordSquareDBHandler.getDBHandler(context, gdbp);
    }
    
    public void closeWordSquareDBHandler()
    {
        cursor = null;
        wsdbh.close();
        wsdbh = null;
    }
    
    public AssetsGetWord[] getSelectWord(int chapter, int page, int stage)
    {
        AssetsGetWord assetsGetWord[] = null;
        
        setWordsquareHandler();
        cursor = wsdbh.getCursorRawQuery(SQL_GET_SELECT_WORDS,new String[]{String.valueOf(chapter),String.valueOf(page),String.valueOf(stage)});
        int getCount = cursor.getCount();
//        Log.d(TAG, String.valueOf(getCount));
        assetsGetWord = new AssetsGetWord[getCount];
        int count = 0;
        if (cursor != null && getCount > 0)
        {
            do
            {
                assetsGetWord[count] = new AssetsGetWord(cursor.getInt(0), cursor.getInt(1), cursor.getInt(2), cursor.getInt(3), cursor.getInt(4), cursor.getString(5), cursor.getString(6));
//                Log.d(TAG, assetsGetWord[count].toString());
                count++;
            } while (cursor.moveToNext());
        }
        closeWordSquareDBHandler();
        
        return assetsGetWord;
    }
    
}// class
