package com.example.echoplex_x.rxjava2bestpractice.upload;

/**
 * Created by wangting31 on 2017/10/16.
 */

public interface FeedbackView {
    /**
     * 用于工作线程返回数据后判断当前关联的activity或者fragment是否已经被销毁，防止销毁后调用ui相关方法
     * @return
     */
    boolean isAlive();

    void commitComplete(boolean isSuccess);
    void setUIStateToLoading();
}
