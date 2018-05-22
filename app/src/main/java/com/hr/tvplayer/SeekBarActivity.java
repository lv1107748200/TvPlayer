package com.hr.tvplayer;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.WindowManager;

/**
 * Created by 吕 on 2018/5/21.
 */

public class SeekBarActivity extends Activity{
    WindowManager windowManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_seek);

        windowManager = (WindowManager)this.getSystemService(Context.WINDOW_SERVICE);


    }

    public void onClick(View view){


    }
}
