package com.teamgehem.wordsquare.db;

import android.content.Context;

import com.teamgehem.gehemengine.provider.GehemDBHandler;
import com.teamgehem.gehemengine.provider.GehemDBPath;

public class WordSquareDBHandler extends GehemDBHandler
{
    
    private WordSquareDBHandler(Context context, GehemDBPath gdbp)
    {
        super(context,gdbp);
    }
    
    public static WordSquareDBHandler getDBHandler(Context context, GehemDBPath gdbp){
        return new WordSquareDBHandler(context, gdbp);
    }
    
}
