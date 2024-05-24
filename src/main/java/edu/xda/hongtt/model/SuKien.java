package edu.xda.hongtt.model;

import android.net.Uri;
import android.widget.EditText;

public class SuKien {
    int idSuKien;
    String noiDung;
    int thaTim;
    String tenTaiKhoan;
    String hinhAnh;

    public SuKien(int idSuKien, String noiDung, String tenTaiKhoan, String hinhAnh) {
        this.idSuKien = idSuKien;
        this.noiDung = noiDung;
        this.tenTaiKhoan = tenTaiKhoan;
        this.hinhAnh = hinhAnh;
    }

    public SuKien(String noiDung, String tenTaiKhoan, String hinhAnh) {
        this.noiDung = noiDung;
        this.tenTaiKhoan = tenTaiKhoan;
        this.hinhAnh = hinhAnh;
    }

    public int getIdSuKien() {
        return idSuKien;
    }

    public void setIdSuKien(int idSuKien) {
        this.idSuKien = idSuKien;
    }

    public String getNoiDung() {
        return noiDung;
    }

    public void setNoiDung(String noiDung) {
        this.noiDung = noiDung;
    }

    public int getThaTim() {
        return thaTim;
    }

    public void setThaTim(int thaTim) {
        this.thaTim = thaTim;
    }

    public String getTenTaiKhoan() {
        return tenTaiKhoan;
    }

    public void setTenTaiKhoan(String tenTaiKhoan) {
        this.tenTaiKhoan = tenTaiKhoan;
    }

    public String getHinhAnh() {
        return hinhAnh;
    }

    public void setHinhAnh(String hinhAnh) {
        this.hinhAnh = hinhAnh;
    }
}
