package com.smie.project;

import java.util.List;

/**
 * Created by wgyscsf on 2016/5/7.
 */
public class MenuWs {
    private int bg;
    private List<MenuCw> cw;

    public int getBg() {
        return bg;
    }

    public void setBg(int bg) {
        this.bg = bg;
    }

    public List<MenuCw> getCw() {
        return cw;
    }

    public void setCw(List<MenuCw> cw) {
        this.cw = cw;
    }

    @Override
    public String toString() {
        return "Ws{" +
                "bg=" + bg +
                ", cw=" + cw +
                '}';
    }
}
