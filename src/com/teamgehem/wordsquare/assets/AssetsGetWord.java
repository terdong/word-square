package com.teamgehem.wordsquare.assets;

import android.os.Parcel;
import android.os.Parcelable;

public class AssetsGetWord implements Parcelable
{
    private int id_match;
    private int arrange;
    private int chapter;
    private int page;
    private int stage;
    public String word;
    public String mean;
    
    public AssetsGetWord(int id_match, int arrange, int chapter, int page, int stage, String word, String mean)
    {
        this.id_match = id_match;
        this.arrange = arrange;
        this.chapter = chapter;
        this.page = page;
        this.stage = stage;
        this.word = word;
        this.mean = mean;
    }
    
    public AssetsGetWord(Parcel src){
        this.id_match = src.readInt();
        this.arrange = src.readInt();
        this.chapter = src.readInt();
        this.page =src.readInt();
        this.stage = src.readInt();
        this.word = src.readString();
        this.mean = src.readString();
    }
    

    public int getId_match()
    {
        return id_match;
    }

    public int getArrange()
    {
        return arrange;
    }

    public int getChapter()
    {
        return chapter;
    }

    public int getPage()
    {
        return page;
    }

    public int getStage()
    {
        return stage;
    }

    @Override
    public String toString()
    {
        return "word = "+word + " mean = "+mean;
    }

    @Override
    public int describeContents()
    {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeInt(id_match);
        dest.writeInt(arrange);
        dest.writeInt(chapter);
        dest.writeInt(page);
        dest.writeInt(stage);
        dest.writeString(word);
        dest.writeString(mean);
    }
    
    public static final Parcelable.Creator<AssetsGetWord> CREATOR = new Parcelable.Creator<AssetsGetWord>() {
        @Override
        public AssetsGetWord createFromParcel(Parcel in){
            return new AssetsGetWord(in);
        }

        @Override
        public AssetsGetWord[] newArray(int size)
        {
            
            return new AssetsGetWord[size];
        }
        
    };
    
    
    
}//class
