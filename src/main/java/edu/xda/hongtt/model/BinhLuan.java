package edu.xda.hongtt.model;

import androidx.room.util.StringUtil;

public class BinhLuan {
    int idBinhLuan;
    int idSuKien;
    String tenTaiKhoan;
    String noiDungBinhLuan;

    public BinhLuan(int idBinhLuan, int idSuKien, String noiDungBinhLuan, String tenTaiKhoan) {
        this.idBinhLuan = idBinhLuan;
        this.idSuKien = idSuKien;
        this.noiDungBinhLuan = noiDungBinhLuan;
        this.tenTaiKhoan = tenTaiKhoan;
    }

    public String getTenTaiKhoan() {
        return tenTaiKhoan;
    }

    public void setTenTaiKhoan(String tenTaiKhoan) {
        this.tenTaiKhoan = tenTaiKhoan;
    }

    public int getIdBinhLuan() {
        return idBinhLuan;
    }

    public void setIdBinhLuan(int idBinhLuan) {
        this.idBinhLuan = idBinhLuan;
    }

    public int getIdSuKien() {
        return idSuKien;
    }

    public void setIdSuKien(int idSuKien) {
        this.idSuKien = idSuKien;
    }

    public String getNoiDungBinhLuan() {
        return noiDungBinhLuan;
    }

    public void setNoiDungBinhLuan(String noiDungBinhLuan) {
        this.noiDungBinhLuan = noiDungBinhLuan;
    }
}
