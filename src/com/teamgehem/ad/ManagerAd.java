package com.teamgehem.ad;

import net.daum.mobilead.AdConfig;
import net.daum.mobilead.AdHttpListener;
import net.daum.mobilead.MobileAdView;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;

import com.cauly.android.ad.AdListener;
import com.google.ads.Ad;
import com.google.ads.AdRequest;
import com.google.ads.AdRequest.ErrorCode;
import com.google.ads.AdSize;
import com.teamgehem.wordsquarelite.R;

public class ManagerAd implements AdHttpListener, AdListener
{
    private static final String TAG = ManagerAd.class.getSimpleName();
    
    private static final String AD_ADAM_KEY = "2378Z1KT135f0bf0236";
    
    private static final String AD_ADMOB_KEY = "a14f58473960788";
    
    private com.google.ads.AdView ad_admob = null;
    
    private com.cauly.android.ad.AdView ad_cauly = null;
    
    private net.daum.mobilead.MobileAdView ad_adam = null;
    
    private FrameLayout.LayoutParams ad_layout_params;
    
    private FrameLayout ad_layout;
    
    private Context context;
    
    public ManagerAd(Context context, FrameLayout ad_layout)
    {
        this.context = context;
        this.ad_layout = ad_layout;
        
        ad_layout_params = new FrameLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
    }
    
    public void ShowAd(int num)
    {
        switch (num)
        {
            case 0:
                if (ad_admob != null) ad_admob.setVisibility(View.INVISIBLE);
                if (ad_cauly == null)
                {
                    ad_cauly = (com.cauly.android.ad.AdView) ((Activity) context).findViewById(R.id.cauly);
                    ad_cauly.setAdListener(this);
                        ad_cauly.setVisibility(View.VISIBLE);
                }
                else
                {
                    ad_cauly.setVisibility(View.VISIBLE);
                }
                break;
            case 1:
                if (ad_cauly != null) ad_cauly.setVisibility(View.INVISIBLE);
                if (ad_adam == null)
                {
                    AdConfig.setClientId(AD_ADAM_KEY);
                    ad_adam = new MobileAdView(context);
                    ad_adam.setLayoutParams(ad_layout_params);
                    ad_adam.setAdListener(this);
                    ad_adam.setVisibility(View.VISIBLE);
                    ad_layout.addView(ad_adam);
                }
                else
                {
                    ad_adam.setVisibility(View.VISIBLE);
                }
                break;
            case 2:
                if (ad_adam != null) ad_adam.setVisibility(View.INVISIBLE);
                if (ad_admob == null)
                {
                    ad_admob = new com.google.ads.AdView((Activity) context, AdSize.BANNER, AD_ADMOB_KEY);
                    AdRequest ar = new AdRequest();
                    ad_admob.setLayoutParams(ad_layout_params);
                    ad_admob.loadAd(ar);
                    ad_admob.setVisibility(View.VISIBLE);
                    ad_admob.setAdListener(new com.google.ads.AdListener() {
                        
                        @Override
                        public void onReceiveAd(Ad arg0)
                        {
                            Log.e(TAG,"admob_onReceiveAd");
                        }
                        
                        @Override
                        public void onPresentScreen(Ad arg0)
                        {
                        }
                        
                        @Override
                        public void onLeaveApplication(Ad arg0)
                        {
                        }
                        
                        @Override
                        public void onFailedToReceiveAd(Ad arg0, ErrorCode arg1)
                        {
                            Log.d(TAG, "AdmobFailed = " + arg1);
                            ShowAd(0);
                        }
                        
                        @Override
                        public void onDismissScreen(Ad arg0)
                        {
                        }
                    });
                    ad_layout.addView(ad_admob);
                }
                else
                {
                    ad_admob.setVisibility(View.VISIBLE);
                }
                break;
            default:
                break;
        }
    }
    
    @Override
    public void didDownloadAd_AdListener()
    {
        Log.e(TAG,"Adam_didDownloadAd");
    }
    
    @Override
    public void failedDownloadAd_AdListener(int arg0, String arg1)
    {
        Log.d(TAG, "AdamFailed = " + arg1);
        ShowAd(2);
    }
    
    @Override
    public void onFailedToReceiveAd(boolean arg0)
    {
        Log.d(TAG, "CaulyFailed = " + ad_cauly.getErrorMessage());
        ShowAd(1);
    }
    
    @Override
    public void onReceiveAd()
    {
        Log.e(TAG,"Cauly_onReceiveAd");
    }
    
    @Override
    public void onCloseInterstitialAd()
    {
    }
    
    public void dispose()
    {
        if (ad_admob != null)
        {
            ad_admob.destroy();
            ad_admob = null;
        }
        if (ad_adam != null)
        {
            ad_adam.destroy();
            ad_adam = null;
        }
        if (ad_cauly != null)
        {
        }
    }
    
}// class
