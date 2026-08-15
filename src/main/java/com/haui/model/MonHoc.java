package com.haui.model;

import java.io.Serializable;

public class MonHoc implements Serializable {
    private static final long serialVersionUID = 1L;

    private String maMH;
    private String tenMH;
    private int soTinChi;
    private String chuyenNganh;

    public MonHoc() {
    }

    public MonHoc(String maMH, String tenMH, int soTinChi, String chuyenNganh) {
        this.maMH = maMH;
        this.tenMH = tenMH;
        this.soTinChi = soTinChi;
        this.chuyenNganh = chuyenNganh;
    }

    public String getMaMH() {
        return maMH;
    }

    public void setMaMH(String maMH) {
        this.maMH = maMH;
    }

    public String getTenMH() {
        return tenMH;
    }

    public void setTenMH(String tenMH) {
        this.tenMH = tenMH;
    }

    public int getSoTinChi() {
        return soTinChi;
    }

    public void setSoTinChi(int soTinChi) {
        this.soTinChi = soTinChi;
    }

    public String getChuyenNganh() {
        return chuyenNganh;
    }

    public void setChuyenNganh(String chuyenNganh) {
        this.chuyenNganh = chuyenNganh;
    }

    @Override
    public String toString() {
        return tenMH + " (" + maMH + ")";
    }
}
