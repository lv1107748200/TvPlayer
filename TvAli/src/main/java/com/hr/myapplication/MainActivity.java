package com.hr.myapplication;

import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.alivc.player.AliVcMediaPlayer;
import com.alivc.player.MediaPlayer;
import com.alivc.player.VcPlayerLog;

import java.util.Timer;
import java.util.TimerTask;

/**
 *
 */
public class MainActivity extends FragmentActivity
    implements
        MeasureHelper.MeasureFormVideoParamsListener,
        MediaPlayer.MediaPlayerPreparedListener,//准备完成时触发
        MediaPlayer.MediaPlayerPcmDataListener, //音频数据回调接口，在需要处理音频时使用，如拿到视频音频，然后绘制音柱
        MediaPlayer.MediaPlayerFrameInfoListener,//首帧显示时触发
        MediaPlayer.MediaPlayerErrorListener,//错误发生时触发，错误码见接口文档
        MediaPlayer.MediaPlayerCompletedListener, //视频正常播放完成时触发
        MediaPlayer.MediaPlayerSeekCompleteListener,   //视频seek完成时触发
        MediaPlayer.MediaPlayerStoppedListener,//使用stop接口时触发
        MediaPlayer.MediaPlayerCircleStartListener //循环播放开始
{

    private String url = "http://qt1.alivecdn.com/timeline/cctv5td.m3u8?auth_key=1523725203-0-0-11caa0a4ead86d9f1a8eb700be7aafe2";
   // private String url = "http://9890.vod.myqcloud.com/9890_4e292f9a3dd011e6b4078980237cc3d3.f20.mp4";

    private AliVcMediaPlayer mPlayer;//阿里控制中心

    private VideoControlLayout control_layout ;

    private Button btnChange,btnJ,btnT;

    private FrameLayout frameLayout;

    private VideoSurfaceView videoSurfaceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_main);
        control_layout = findViewById(R.id.control_layout);
        btnChange = findViewById(R.id.btn_change);
        btnJ = findViewById(R.id.btn_j);
        btnT = findViewById(R.id.btn_t);

        frameLayout = findViewById(R.id.framelayout);


        control_layout.setLoad_relayout(View.VISIBLE);

        //initMPlayer();

        setAddView();


        btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                frameLayout.requestLayout();

            }
        });
        btnJ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



            }
        });
        btnT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



            }
        });
    }

    private void initMPlayer(SurfaceView surfaceView){


        mPlayer = new AliVcMediaPlayer(this, surfaceView);

        control_layout.setmPlayer(mPlayer);

        mPlayer.setPreparedListener(this);
        mPlayer.setPcmDataListener(this);
        mPlayer.setCircleStartListener(this);
        mPlayer.setFrameInfoListener(this);
        mPlayer.setErrorListener(this);
        mPlayer.setCompletedListener(this);
        mPlayer.setSeekCompleteListener(this);
        mPlayer.setStoppedListener(this);

        //打开、关闭播放器日志
        mPlayer.enableNativeLog();
        mPlayer.setVideoScalingMode(MediaPlayer.VideoScalingMode.VIDEO_SCALING_MODE_SCALE_TO_FIT);
//        mPlayer.disableNativeLog();

        mPlayer.prepareToPlay(url);

        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            public void surfaceCreated(SurfaceHolder holder) {
//                holder.setType(SurfaceHolder.SURFACE_TYPE_GPU);
                holder.setKeepScreenOn(true);
                // Important: surfaceView changed from background to front, we need reset surface to mediaplayer.
                // 对于从后台切换到前台,需要重设surface;部分手机锁屏也会做前后台切换的处理
                if (mPlayer != null) {
                    mPlayer.setVideoSurface(holder.getSurface());
                }

            }

            public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {

                if (mPlayer != null) {
                    mPlayer.setSurfaceChanged();
                }

            }

            public void surfaceDestroyed(SurfaceHolder holder) {
//                if (mPlayer != null) {
//                    mPlayer.releaseVideoSurface();
//                }
            }
        });


    }

    //首帧显示时触发
    @Override
    public void onFrameInfoListener() {
        System.out.println("首帧显示时触发--->");
        control_layout.startUpSeekBar();
    }
    //音频数据回调接口，在需要处理音频时使用，如拿到视频音频，然后绘制音柱。
    @Override
    public void onPcmData(byte[] bytes, int i) {
      //  System.out.println("音频数据回调--->");
    }
    //准备完成时触发
    @Override
    public void onPrepared() {
        System.out.println("准备完成--->");
        start();
        control_layout.setLoad_relayout(View.INVISIBLE);
    }
    //错误发生时触发，错误码见接口文档
    @Override
    public void onError(int i, String s) {
        System.out.println("错误发生--->"+s);

    }
    //视频正常播放完成时触发
    @Override
    public void onCompleted() {
        System.out.println("视频正常播放完--->");
        control_layout.stopUpSeekBar();

    }
    //视频seek完成时触发
    @Override
    public void onSeekCompleted() {
        System.out.println("视频seek完成--->");

        control_layout.setInSeek(false);

    }
    //使用stop接口时触发
    @Override
    public void onStopped() {
        System.out.println("使用stop接口--->");

    }
    //循环播放开始
    @Override
    public void onCircleStart() {
        System.out.println("循环播放开始--->");

    }

    private void start() {
        if (mPlayer != null) {
            mPlayer.play();
        }
    }
    private void pause() {
        if (mPlayer != null) {
            mPlayer.pause();
        }
    }

    private void resume() {
        if (mPlayer != null) {
            mPlayer.play();
        }
    }

    private void stop() {
        if (mPlayer != null) {
            mPlayer.stop();
        }
    }

    private void replay() {
        stop();
        start();
    }

    //销毁
    private void destroy() {
        if (mPlayer != null) {
            mPlayer.stop();
            mPlayer.destroy();
        }
    }
    @Override
    protected void onDestroy() {

        if(null == mPlayer)
            return;

        mPlayer.setPreparedListener(null);//准备完成时触发
        mPlayer.setPcmDataListener(null);//音频数据回调接口，在需要处理音频时使用，如拿到视频音频，然后绘制音柱。
        mPlayer.setCircleStartListener(null);
        mPlayer.setFrameInfoListener(null);//首帧显示时触发
        mPlayer.setErrorListener(null); //错误发生时触发，错误码见接口文档
        mPlayer.setCompletedListener(null);//视频正常播放完成时触发
        mPlayer.setSeekCompleteListener(null);//视频seek完成时触发
        mPlayer.setStoppedListener(null);//使用stop接口时触发

        destroy();
        control_layout.stopUpSeekBar();
        super.onDestroy();

    }



    private void setAddView(){

        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        layoutParams.gravity = Gravity.CENTER;

        videoSurfaceView = new VideoSurfaceView(this);

        videoSurfaceView.setParamsListener(this);

        frameLayout.addView(videoSurfaceView,layoutParams);

        initMPlayer(videoSurfaceView);

    }



    @Override
    public int getCurrentVideoWidth() {
        return mPlayer.getVideoWidth();
    }

    @Override
    public int getCurrentVideoHeight() {
        return mPlayer.getVideoHeight();
    }

    @Override
    public int getVideoSarNum() {
        return 0;
    }

    @Override
    public int getVideoSarDen() {
        return 0;
    }
}
