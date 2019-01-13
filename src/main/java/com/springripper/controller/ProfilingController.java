package com.springripper.controller;

/**
 * Created by raindream on 04.01.19.
 */
public class ProfilingController implements  ProfilingControllerMBean {
    private boolean enable = true;

    public boolean isEnable() {
        return enable;
    }

    @Override
    public void setEnable(boolean enable) {
        this.enable = enable;
    }

}
