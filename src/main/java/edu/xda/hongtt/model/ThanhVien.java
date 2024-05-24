package edu.xda.hongtt.model;

public class ThanhVien {
    public int idThanhVien;
    public String hoTen;
    public String ngaySinh;
    public String gioiTinh;
    public String vaiVe;
    public int deleteFlag;

    public ThanhVien(int idThanhVien, String hoTen, String ngaySinh, String gioiTinh, String vaiVe, int deleteFlag) {
        this.idThanhVien = idThanhVien;
        this.hoTen = hoTen;
        this.ngaySinh = ngaySinh;
        this.vaiVe = vaiVe;
        this.deleteFlag = deleteFlag;
        this.gioiTinh = gioiTinh;
    }

    public ThanhVien(String hoTen, String ngaySinh, String gioiTinh, String vaiVe, int deleteFlag) {
        this.hoTen = hoTen;
        this.ngaySinh = ngaySinh;
        this.gioiTinh = gioiTinh;
        this.vaiVe = vaiVe;
        this.deleteFlag = deleteFlag;
    }

    public String getGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(String gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public int getIdThanhVien() {
        return idThanhVien;
    }

    public void setIdThanhVien(int idThanhVien) {
        this.idThanhVien = idThanhVien;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(String ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public String getVaiVe() {
        return vaiVe;
    }

    public void setVaiVe(String vaiVe) {
        this.vaiVe = vaiVe;
    }

    public int getXoaThanhVien() {
        return deleteFlag;
    }

    public void setXoaThanhVien(int xoaThanhVien) {
        this.deleteFlag = xoaThanhVien;
    }
}

