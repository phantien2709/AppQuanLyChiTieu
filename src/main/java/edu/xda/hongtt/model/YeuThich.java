package edu.xda.hongtt.model;

public class YeuThich {
    String tenTaiKhoan;
    int idSuKien;

    public YeuThich(String tenTaiKhoan, int trangThai) {
        this.tenTaiKhoan = tenTaiKhoan;
        this.idSuKien = trangThai;
    }

    public String getTenTaiKhoan() {
        return tenTaiKhoan;
    }

    public void setTenTaiKhoan(String tenTaiKhoan) {
        this.tenTaiKhoan = tenTaiKhoan;
    }

    public int getIdSuKien() {
        return idSuKien;
    }

    public void setIdSuKien(int trangThai) {
        this.idSuKien = trangThai;
    }
}
