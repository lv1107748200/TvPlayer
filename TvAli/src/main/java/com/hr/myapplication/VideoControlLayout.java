package com.hr.myapplication;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.alivc.player.AliVcMediaPlayer;
import com.warkiz.widget.IndicatorSeekBar;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by 吕 on 2018/3/19.
 */

public class VideoControlLayout extends FrameLayout {

    private RelativeLayout load_relayout;
    private TextView current,total;
    private IndicatorSeekBar seekBar;

    protected Timer seekBarTimer;

    protected Timer upKey;

    private AliVcMediaPlayer mPlayer;//阿里控制中心

    private boolean inSeek = false;

    private int press;
    private int mDownPosition;//触发的时间
    private boolean isK = false;
    private boolean isT = false;
    private int savePress;//调节的时间
    private int duration;//视频总时间





    public VideoControlLayout(@NonNull Context context) {
        this(context,null);
    }

    public VideoControlLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public VideoControlLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context){
      View view =  View.inflate(context,R.layout.video_control_layout,this);

      load_relayout = view.findViewById(R.id.load_relayout);
      current = findViewById(R.id.current);
      total = findViewById(R.id.total);
      seekBar = findViewById(R.id.progress);
    }

    /**
     * @param mPlayer 初始化
     */
    public void setmPlayer(AliVcMediaPlayer mPlayer) {
        this.mPlayer = mPlayer;
    }

    /**
     * 显示隐藏加载页面
     */
    public void setLoad_relayout(int show){
        load_relayout.setVisibility(show);
    }

    /**
     * 开始更新seekbar
     */
    public void startUpSeekBar(){

        seekBarTimer = null;

        if(null == seekBarTimer){
            seekBarTimer = new Timer();
        }

        seekBarTimer.schedule(new UpdataSeekBarTimerTask(),0,300);

    }

    /**
     * 停止更新
     */
    public void stopUpSeekBar(){

        if(null != seekBarTimer){
            seekBarTimer.cancel();
            seekBarTimer = null;
        }

    }

    /**
     *
     */
    private void getData(){
        if (mPlayer != null ) {

            int curPosition = (int) mPlayer.getCurrentPosition();
            int duration = (int) mPlayer.getDuration();
            int bufferPosition = mPlayer.getBufferPosition();

            System.out.println("--->"+"curPosition = " + curPosition + " , duration = " + duration + " ， inSeek = " + inSeek);
            current.setText(Formatter.formatTime(curPosition));

            if (!inSeek) {
                total.setText(Formatter.formatTime(duration));
                seekBar.setMax(duration);

                if(curPosition >= savePress)
                seekBar.setProgress(curPosition);
            }

        }
    }

    /**
     * 开播
     */
    private void start() {
        if (mPlayer != null) {
            mPlayer.play();
        }
    }

    /**
     * 暂停
     */
    private void pause() {
        if (mPlayer != null) {
            mPlayer.pause();
        }
    }

    /**
     * 更改播放
     */
    public void  changeVideo(String url){
        if(null != mPlayer){
            setLoad_relayout(View.VISIBLE);

            stopUpSeekBar();

            mPlayer.stop();
            mPlayer.prepareToPlay(url);
            mPlayer.play();

            savePress = 0;
        }
    }

    /**
     * 快进
     */
    public void keySpeed(){

        if(null != mPlayer){
            if(mPlayer.isPlaying()){

                seekBar.getBuilder()
                        .setIndicatorStay(true)
                        .setIndicatorColor(Color.parseColor("#77CEEE"))
                        .setIndicatorCustomTopContentLayout(R.layout.custom_top_content_view_rect_without_progress)
                        .apply();

                setInSeek(true);
                savePress = mPlayer.getCurrentPosition();
                duration = mPlayer.getDuration();

                satrtKeyTask();
            }
        }

    }

    /**
     * 快退
     */
    public void keyBackward(){
        setInSeek(true);

    }

    public void setInSeek(boolean inSeek) {
        this.inSeek = inSeek;

    }

    private void satrtKeyTask(){
        upKey = null;

        if(null == upKey){
            upKey = new Timer();
        }

        upKey.schedule(new UpdataKeySeekBarTimerTask(),0,50);
    }

    /**
     * 停止快进或快退
     */
    public void stopKeyTask(){

        if(null != upKey){
            upKey.cancel();
            upKey = null;

            seekBar.getBuilder()
                    .setIndicatorStay(false)
                    .setIndicatorCustomTopContentLayout(R.layout.custom_top_content_view_rect_without_progress)
                    .apply();

            if(null != mPlayer){
                mPlayer.seekTo(savePress);
            }
        }

    }

    private class UpdataSeekBarTimerTask extends TimerTask {
        @Override
        public void run() {
            post(new Runnable() {
                @Override
                public void run() {

                    getData();

                }
            });
        }
    }

    private class UpdataKeySeekBarTimerTask extends TimerTask {
        @Override
        public void run() {
            post(new Runnable() {
                @Override
                public void run() {

                    if(null != mPlayer){

                        savePress = (savePress + 1000 );

                        if(savePress > duration){
                            savePress = duration;
                            upKey.cancel();
                        }
                        seekBar.setProgress(savePress);
                    }

                }
            });
        }
    }

}
