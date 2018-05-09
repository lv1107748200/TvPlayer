package com.hr.myapplication;

import android.content.Context;
import android.util.AttributeSet;
import android.view.SurfaceView;

/**
 * Created by 吕 on 2018/3/27.
 */

public class VideoSurfaceView extends SurfaceView implements MeasureHelper.MeasureFormVideoParamsListener {

    private MeasureHelper measureHelper;

    private  MeasureHelper.MeasureFormVideoParamsListener paramsListener;


    public VideoSurfaceView(Context context) {
        this(context,null);
    }

    public VideoSurfaceView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public VideoSurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
    private void init(){
        measureHelper = new MeasureHelper(this, this);
    }

    public void setParamsListener(MeasureHelper.MeasureFormVideoParamsListener paramsListener) {
        this.paramsListener = paramsListener;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        measureHelper.prepareMeasure(widthMeasureSpec, heightMeasureSpec, (int) getRotation());
        setMeasuredDimension(measureHelper.getMeasuredWidth(), measureHelper.getMeasuredHeight());
    }

    @Override
    public int getCurrentVideoWidth() {
        if(null != paramsListener){
            return paramsListener.getCurrentVideoWidth();
        }
        return 0;
    }

    @Override
    public int getCurrentVideoHeight() {
        if(null != paramsListener){
            return paramsListener.getCurrentVideoHeight();
        }
        return 0;
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
