package com.example.danish.baseproject.net.base;


public interface RequestListener{

    /**
     * 交互成功
     * @param response
     */
    public void onFinish(String response);

    /**
     * 交互成功，但数据出错
     * @param errMsg
     */
    public void onFail(String errMsg);

    /**
     * 网络、IO出现错误
     * @param e
     */
    public void onNetError(Throwable e);
}
