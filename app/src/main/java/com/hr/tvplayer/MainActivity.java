package com.hr.tvplayer;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Handler;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Transition;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.utils.GSYVideoType;
import com.shuyu.gsyvideoplayer.utils.OrientationUtils;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Button btn;
    private Button btnJian;

    SampleVideo videoPlayer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        videoPlayer = findViewById(R.id.video_player);
        btn = findViewById(R.id.btnJin);
        btnJian = findViewById(R.id.btnJian);


        btn .setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()){

                    case MotionEvent.ACTION_DOWN:
                        System.out.println("ACTION_DOWN--->");
                        videoPlayer.keySpeed(true);

                        break;

                    case MotionEvent.ACTION_UP:
                        System.out.println("ACTION_UP--->");
                        videoPlayer.stopKeySpeed();
                        break;
                }

                return false;
            }
        });

//        btnJian .setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//
//                switch (event.getAction()){
//
//                    case MotionEvent.ACTION_DOWN:
//                        System.out.println("ACTION_DOWN--->");
//                        videoPlayer.keySpeed(false);
//
//
//                        break;
//
//                    case MotionEvent.ACTION_UP:
//                        System.out.println("ACTION_UP--->");
//                        videoPlayer.stopKeySpeed();
//                        break;
//                }
//
//                return false;
//            }
//        });


        btnJian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,SeekBarActivity.class);

                startActivity(intent);
            }
        });

        init();
    }

    private void init() {

        GSYVideoType.setShowType(GSYVideoType.SCREEN_TYPE_FULL);//全屏显示



        String url = "https://res.exexm.com/cw_145225549855002";

        //String url = "http://7xse1z.com1.z0.glb.clouddn.com/1491813192";
        //需要路径的
        //videoPlayer.setUp(url, true, new File(FileUtils.getPath()), "");

        //借用了jjdxm_ijkplayer的URL
        String source1 = "rtmp://42.159.206.249:1935/1001/31117091";
        String name = "普通";
        SwitchVideoModel switchVideoModel = new SwitchVideoModel(name, source1);

        String source2 = "http://9890.vod.myqcloud.com/9890_4e292f9a3dd011e6b4078980237cc3d3.f30.mp4";
       // String source2 = "rtmp://live.hkstv.hk.lxdns.com/live/hks";
        String name2 = "清晰";
        SwitchVideoModel switchVideoModel2 = new SwitchVideoModel(name2, source2);

        List<SwitchVideoModel> list = new ArrayList<>();
        list.add(switchVideoModel);
        list.add(switchVideoModel2);

        videoPlayer.setUp(list, false, "测试视频");

        //增加封面
        ImageView imageView = new ImageView(this);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setImageResource(R.drawable.xxx2);
        //videoPlayer.setThumbImageView(imageView);

        //增加title
        videoPlayer.getTitleTextView().setVisibility(View.VISIBLE);
        //videoPlayer.setShowPauseCover(false);

        //videoPlayer.setSpeed(2f);

        //设置返回键
        videoPlayer.getBackButton().setVisibility(View.VISIBLE);

        //设置旋转


        //设置全屏按键功能,这是使用的是选择屏幕，而不是全屏
        videoPlayer.getFullscreenButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        //videoPlayer.setBottomProgressBarDrawable(getResources().getDrawable(R.drawable.video_new_progress));
        //videoPlayer.setDialogVolumeProgressBar(getResources().getDrawable(R.drawable.video_new_volume_progress_bg));
        //videoPlayer.setDialogProgressBar(getResources().getDrawable(R.drawable.video_new_progress));
        //videoPlayer.setBottomShowProgressBarDrawable(getResources().getDrawable(R.drawable.video_new_seekbar_progress),
        //getResources().getDrawable(R.drawable.video_new_seekbar_thumb));
        //videoPlayer.setDialogProgressColor(getResources().getColor(R.color.colorAccent), -11);

        //是否可以滑动调整
        videoPlayer.setIsTouchWiget(true);

        //设置返回按键功能
        videoPlayer.getBackButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }


    @Override
    protected void onPause() {
        super.onPause();
        videoPlayer.onVideoPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        videoPlayer.onVideoResume();
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        //先返回正常状态

        //释放所有
        videoPlayer.setVideoAllCallBack(null);
        GSYVideoManager.releaseAllVideos();

        finish();
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        System.out.println("--->"+keyCode);

        switch (keyCode){
            case KeyEvent.KEYCODE_0:
                videoPlayer.onClickUiToggle();
                break;
            case KeyEvent.KEYCODE_1:

                break;
        }

        return super.onKeyDown(keyCode, event);
    }
}
