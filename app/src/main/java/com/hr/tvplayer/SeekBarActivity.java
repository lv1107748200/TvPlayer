package com.hr.tvplayer;

import android.app.Activity;
import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;

/**
 * Created by 吕 on 2018/5/21.
 */

public class SeekBarActivity extends Activity{
    WindowManager windowManager;
    WindowManager.LayoutParams params;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_seek);

        windowManager = (WindowManager)this.getSystemService(Context.WINDOW_SERVICE);
        params = new WindowManager.LayoutParams();
        params.height = FrameLayout.LayoutParams.WRAP_CONTENT;
        params.width = FrameLayout.LayoutParams.WRAP_CONTENT;
        params.format = PixelFormat.TRANSLUCENT;
        params.type = WindowManager.LayoutParams.TYPE_APPLICATION_PANEL;
        params.flags =
                WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL | WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH
                        | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        params.gravity = Gravity.TOP | Gravity.LEFT;

    }

    public void onClick(View view){


    }
}
