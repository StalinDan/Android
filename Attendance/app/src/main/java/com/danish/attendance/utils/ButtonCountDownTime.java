package com.danish.attendance.utils;

import android.os.CountDownTimer;

/**
 * Created by danish on 2018/7/2.
 */

public class ButtonCountDownTime extends CountDownTimer {

    private ButtonCountDownTimeCallBack timeCallBack;

    public void setTimeCallBack(ButtonCountDownTimeCallBack timeCallBack) {
        this.timeCallBack = timeCallBack;
    }

    public interface ButtonCountDownTimeCallBack{
        public void tick(long time);
        public void finish();
    }

    /**
     * @param millisInFuture    The number of millis in the future from the call
     *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
     *                          is called.
     * @param countDownInterval The interval along the way to receive
     *                          {@link #onTick(long)} callbacks.
     */
    public ButtonCountDownTime(long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
    }

    @Override
    public void onTick(long millisUntilFinished) {
        this.timeCallBack.tick(millisUntilFinished);
    }

    @Override
    public void onFinish() {
        this.timeCallBack.finish();
    }
}
