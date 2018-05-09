package com.hr.tvplayer;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.KeyEvent;

/**
 * Created by 吕 on 2018/3/26.
 */

public class OnKeyActivity extends FragmentActivity {

    private static double DOUBLE_CLICK_TIME = 0L;

    private boolean isLongPressKey ;

    private boolean lockLongPressKey;//判断长按还是

    private boolean isDoublePressKey;//判断是否快速点击



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onkey);

    }

    @Override

    public boolean onKeyDown(int keyCode, KeyEvent event) {

        switch (keyCode){

            case KeyEvent.KEYCODE_DPAD_LEFT:

                Log.d("xiaowu","getRepeatCount"+event.getRepeatCount());

                if (event.getRepeatCount() == 0) {

                    event.startTracking();

                    isLongPressKey=false;

                    if((System.currentTimeMillis() - DOUBLE_CLICK_TIME)>600){

                        isDoublePressKey=false;

                    }else{

                        isDoublePressKey=true;

                    }

                    DOUBLE_CLICK_TIME = System.currentTimeMillis();

                    return true;

                }else{

                    isLongPressKey=true;

                    return true;

                }

        }

        return super.onKeyDown(keyCode, event);

    }

    @Override

    public boolean onKeyLongPress(int keyCode, KeyEvent event) {

        switch (keyCode){

            case KeyEvent.KEYCODE_DPAD_LEFT:

                lockLongPressKey = true;

                return true;

        }

        return super.onKeyDown(keyCode, event);

    }

    int mKeyTimes=0;//用来按下的上下键长按执行的次数

    int mKeyRunTimes=0;

    @Override

    public boolean onKeyUp(int keyCode, KeyEvent event) {

        if(event.getAction()==KeyEvent.ACTION_UP && isDoublePressKey){

            mKeyTimes++;

        }

        switch (keyCode){

            case KeyEvent.KEYCODE_DPAD_LEFT:

                if(lockLongPressKey){

                    Log.d("xiaowu","isLongPressKey,444 "+event.getRepeatCount()+ "=="+isLongPressKey);


                    isLongPressKey=false;

                    lockLongPressKey=false;

                }else{

                    if(!isLongPressKey ){

                        if(isDoublePressKey){

                        }else{


                        }

                    }

                    Log.d("xiaowu","isLongPressKey,333 "+event.getRepeatCount()+ "=="+isLongPressKey);

                }

                return true;

        }

        return super.onKeyUp(keyCode, event);

    }

}
