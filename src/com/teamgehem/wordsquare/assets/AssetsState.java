package com.teamgehem.wordsquare.assets;

import java.io.Serializable;

public class AssetsState implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    private static final int FINAL_CHAPTER = 3;
    
    private static final int FINAL_PAGE = 10;
    
    private static final int FINAL_STAGE = 30;
    
    private static final int INIT_NUMBER = 0;
    
    // /**
    // * The state chapter. true = 열림, false = 닫힘.
    // */
    // public boolean stateChapter[];
    
    /**
     * The state page. 첫번째 배열 >> chapter 구분. 두번째 배열 >> 총 10개. 0 = 비활성, 1 = 활성, 2
     * = 선택.
     * */
    public int statePage[][];
    
    /**
     * The state stage. 첫번째 배열 >> chapter 구분. 두번째 배열 >> Page 구분. 세번째 배열 >> 총30개.
     * 0 = 잠김, 1 = 오픈, 2 = 별1개, 3 = 별2개, 4 = 별3개, 5 = 왕관.
     * */
    public int stateStage[][][];
    
    public boolean isSound;
    
    public int current_stateChapter;
    
    public int current_statePage;
    
    public int current_stateStage;
    
    public AssetsState(boolean[] stateChapter, int statePage[][], int stateStage[][][], boolean isSound)
    {
        this.statePage = statePage;
        this.stateStage = stateStage;
        this.isSound = isSound;
        
        current_stateChapter = INIT_NUMBER;
        current_statePage = INIT_NUMBER;
        current_stateStage = -1;
    }
    
    public AssetsState()
    {
        this.statePage = new int[3][10];
        for (int i = 0; i < statePage.length; i++)
            for (int j = 0; j < statePage[i].length; j++)
                statePage[i][j] = 0;
        
        this.stateStage = new int[3][10][30];
        for (int i = 0; i < stateStage.length; i++)
            for (int j = 0; j < stateStage[i].length; j++)
                for (int k = 0; k < stateStage[i][j].length; k++)
                    stateStage[i][j][k] = 0;
        this.isSound = true;
        
        current_stateChapter = INIT_NUMBER;
        current_statePage = INIT_NUMBER;
        current_stateStage = -1;
        
        statePage[0][0] = 2;
        statePage[1][0] = 2;
        statePage[2][0] = 2;
        stateStage[0][0][0] = 1;
        stateStage[1][0][0] = 1;
        stateStage[2][0][0] = 1;
        
    }
    
    public void nextStageUp()
    {
        if (current_stateStage < FINAL_STAGE-1)
        {
            current_stateStage++;
            if(stateStage[current_stateChapter][current_statePage][current_stateStage] < 1)
                stateStage[current_stateChapter][current_statePage][current_stateStage] = 1;
        }
        else
        {
            current_stateStage++;
            if (current_statePage < FINAL_PAGE-1)
            {
                current_statePage++;
                statePage[current_stateChapter][current_statePage] = 1;
            }
            else
            {
            }
        }
    }
    
    public void setStageGrade(int grade)
    {
        if (stateStage[current_stateChapter][current_statePage][current_stateStage] < grade)
        {
            stateStage[current_stateChapter][current_statePage][current_stateStage] = grade;
        }
    }
    
    public boolean isOpenNextState(){
        if (stateStage[current_stateChapter][current_statePage][current_stateStage] > 1){
            nextStageUp();
            return true;
        }
        return false;
//        int tmpCurrentStateStage = current_stateStage;
//        int tmpCurrentStateChapter = current_stateChapter;
//        int tmpCurrentStatePage = current_statePage;
//        if (tmpCurrentStateStage < FINAL_STAGE - 1)
//        {
//            tmpCurrentStateStage++;
//        }
//        else
//        {
//            tmpCurrentStateStage = INIT_NUMBER;
//            if (tmpCurrentStatePage < FINAL_PAGE - 1)
//            {
//                statePage[tmpCurrentStateChapter][tmpCurrentStatePage] = 1;
//                tmpCurrentStatePage++;
//                statePage[tmpCurrentStateChapter][tmpCurrentStatePage] = 2;
//            }
//            else
//            {
//                statePage[tmpCurrentStateChapter][tmpCurrentStatePage] = 1;
//                tmpCurrentStatePage = INIT_NUMBER;
//                statePage[tmpCurrentStateChapter][tmpCurrentStatePage] = 2;
//                if (tmpCurrentStateChapter < FINAL_CHAPTER - 1)
//                {
//                    tmpCurrentStateChapter++;
//                }
//            }
//        }
        
       
    }
    
    public static long getSerialversionuid()
    {
        return serialVersionUID;
    }
    
}// class
