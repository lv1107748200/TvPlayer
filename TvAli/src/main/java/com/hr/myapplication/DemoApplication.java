package com.hr.myapplication;

import android.app.Application;

import com.alivc.player.AliVcMediaPlayer;
import com.alivc.player.VcPlayerLog;


/**
 * @Author: lifujun@alibaba-inc.com
 * @Date: 2016/12/29.
 * @Description:
 */

public class DemoApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        VcPlayerLog.enableLog();

        //初始化播放器。不初始化，错误字符串将获取不到。
        AliVcMediaPlayer.init(getApplicationContext());

    }


}
