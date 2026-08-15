package com.haui.model;

import java.io.Serializable;

public class GiangVien implements Serializable {
    private static final long serialVersionUID = 1L;

    private String maGV;
    private String hoTen;
    private String gioiTinh;
    private String email;
    private String soDienThoai;
    private String hocVi;
    private String khoa;

    public GiangVien() {
    }

    public GiangVien(String maGV, String hoTen, String gioiTinh, String email, String soDienThoai, String hocVi, String khoa) {
        this.maGV = maGV;
        this.hoTen = hoTen;
        this.gioiTinh = gioiTinh;
        this.email = email;
        this.soDienThoai = soDienThoai;
        this.hocVi = hocVi;
        this.khoa = khoa;
    }

    public String getMaGV() {
        return maGV;
    }

    public void setMaGV(String maGV) {
        this.maGV = maGV;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(String gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSoDienThoai() {
        return soDienThoai;
    }

    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }

    public String getHocVi() {
        return hocVi;
    }

    public void setHocVi(String hocVi) {
        this.hocVi = hocVi;
    }

    public String getKhoa() {
        return khoa;
    }

    public void setKhoa(String khoa) {
        this.khoa = khoa;
    }

    @Override
    public String toString() {
        return hoTen + " (" + maGV + ")";
    }
}
