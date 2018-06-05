package com.hr.tvplayer;

import android.app.Activity;
import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

/**
 * Created by 吕 on 2018/5/21.
 */

public class SeekBarActivity extends Activity{
    WindowManager windowManager;
    WindowManager.LayoutParams params;
    private LinearLayout   linearLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_seek);

        linearLayout = (LinearLayout) LayoutInflater.from(getApplication()).inflate(R.layout.view_audio_surfaceview, null);


        windowManager = (WindowManager)this.getSystemService(Context.WINDOW_SERVICE);
        params = new WindowManager.LayoutParams();
        params.height = 80;
        params.width = 300;
        params.format = PixelFormat.TRANSLUCENT;
        params.type = WindowManager.LayoutParams.TYPE_APPLICATION_PANEL;
        params.flags =
                WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL | WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH
                        | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        params.gravity = Gravity.TOP|Gravity.LEFT;

    }

    public void onClick(View view){
        switch (view.getId()){
            case R.id.btn_show:

                windowManager.addView(linearLayout, params);

                break;
            case R.id.btn_change:

                params.x = 200;
                params.y = 400;
                windowManager.updateViewLayout(linearLayout,params);

                break;
            case R.id.btn_close:
                windowManager.removeViewImmediate(linearLayout);
                break;
        }


    }


}
