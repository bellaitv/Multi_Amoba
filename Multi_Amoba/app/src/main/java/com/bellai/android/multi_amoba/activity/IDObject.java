package com.bellai.android.multi_amoba.activity;

/**
 * Created by adam.bellai on 2017. 01. 01..
 */
public class IDObject {

    private int id;
    private int sign;

    public IDObject(int id, int sign) {
        this.id = id;
        this.sign = sign;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSign() {
        return sign;
    }

    public void setSign(int sign) {
        this.sign = sign;
    }
}
