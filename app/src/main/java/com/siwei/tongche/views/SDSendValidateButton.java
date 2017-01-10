package com.siwei.tongche.views;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;


import com.siwei.tongche.R;

import java.util.Timer;
import java.util.TimerTask;

public class SDSendValidateButton extends Button
{

    private static final int DISABLE_TIME = 60;
    private static final int MSG_TICK = 0;

    private Timer mTimer = null;
    private TimerTask mTask = null;

    private int mDisableTime = DISABLE_TIME; // 默认60秒
    private int mEnableColor = ContextCompat.getColor(getContext(), R.color.colorAccent);
    private int mDisableColor = ContextCompat.getColor(getContext(),R.color.text_gray);

    private int mEnableBgColor = ContextCompat.getColor(getContext(),R.color.color_f1f1f1) ;
    private int mDisableBgColor = ContextCompat.getColor(getContext(),R.color.color_f1f1f1) ;

    private String mEnableString = "获取验证码";
	private String mDisableString = "秒后重新获取";

    private boolean mClickBle = true;

    private SDSendValidateButtonListener mListener = null;

    public int getmDisableTime()
    {
        return mDisableTime;
    }

    public int getmEnableColor()
    {
        return mEnableColor;
    }

    public void setmEnableColor(int mEnableColor)
    {
        this.mEnableColor = mEnableColor;
        this.setTextColor(mEnableColor);
    }

    public int getmDisableColor()
    {
        return mDisableColor;
    }

    public void setmDisableColor(int mDisableColor)
    {
        this.mDisableColor = mDisableColor;
    }

    public String getmEnableString()
    {
        return mEnableString;
    }

    public void setmEnableString(String mEnableString)
    {
        this.mEnableString = mEnableString;
        if (this.mEnableString != null)
        {
            this.setText(mEnableString);
        }
    }

    public String getmDisableString()
    {
        return mDisableString;
    }

    public void setmDisableString(String mDisableString)
    {
        this.mDisableString = mDisableString;
    }

    public void setmListener(SDSendValidateButtonListener mListener)
    {
        this.mListener = mListener;
    }

    private Handler mHandler = new Handler(Looper.getMainLooper())
    {

        @Override
        public void handleMessage(Message msg)
        {
            switch (msg.what)
            {
                case MSG_TICK:
                    tickWork();
                    break;

                default:
                    break;
            }
            super.handleMessage(msg);
        }

    };

    public SDSendValidateButton(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        initView();
    }

    private void initView()
    {
        this.setText(mEnableString);
        this.setGravity(Gravity.CENTER);
        this.setTextColor(mEnableColor);
        this.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
        this.setBackgroundColor(mEnableBgColor);
        initTimer();
        this.setOnClickListener(new OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                if (mListener != null && mClickBle)
                {
                    mListener.onClickSendValidateButton();
                }
            }
        });
    }

    private void initTimer()
    {
        mTimer = new Timer();

    }

    private void initTimerTask()
    {
        mTask = new TimerTask()
        {
            @Override
            public void run()
            {
                mHandler.sendEmptyMessage(MSG_TICK);
            }
        };
    }

    public void startTickWork()
    {
        if (mClickBle)
        {
            mClickBle = false;
            SDSendValidateButton.this.setText(mDisableTime + mDisableString);
            SDSendValidateButton.this.setTextColor(mDisableColor);
            SDSendValidateButton.this.setBackgroundColor(mDisableBgColor);
            initTimerTask();
            mTimer.schedule(mTask, 0, 1000);
        }
    }

    /**
     * ÿ���ӵ���һ��
     */
    private void tickWork()
    {
        mDisableTime--;
        this.setText(mDisableTime + mDisableString);
        if (mDisableTime <= 0)
        {
            stopTickWork();
        }
    }

    public void stopTickWork()
    {	
    	if(mTask!=null){
    		 	mTask.cancel();
    	        mTask = null;
    	        mDisableTime = DISABLE_TIME;
    	        this.setText(mEnableString);
    	        this.setTextColor(mEnableColor);
                this.setBackgroundColor(mEnableBgColor);
    	        mClickBle = true;
    	}
    }

    public interface SDSendValidateButtonListener
    {	
    	/**
    	 * 点击事件
    	 */
        public void onClickSendValidateButton();
    }

}