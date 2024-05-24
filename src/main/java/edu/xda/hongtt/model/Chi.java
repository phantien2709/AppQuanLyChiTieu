package edu.xda.hongtt.model;

public class Chi {
    public int idChi;
    public String tenMucChi;
    public String dinhMucChi;
    public String donViChi;
    public String thoiDiemApDungChi;
    public int danhGia;
    public int deleteFlag;
    public int idLoaiChi;

    public Chi(int idChi, String tenMucChi, String dinhMucChi, String donViChi, String thoiDiemApDungChi, int danhGia, int deleteFlag, int idLoaiChi) {
        this.idChi = idChi;
        this.tenMucChi = tenMucChi;
        this.dinhMucChi = dinhMucChi;
        this.donViChi = donViChi;
        this.thoiDiemApDungChi = thoiDiemApDungChi;
        this.danhGia = danhGia;
        this.deleteFlag = deleteFlag;
        this.idLoaiChi = idLoaiChi;
    }

    public Chi(String tenMucChi, String dinhMucChi, String donViChi, String thoiDiemApDungChi, int danhGia, int deleteFlag, int idLoaiChi) {
        this.tenMucChi = tenMucChi;
        this.dinhMucChi = dinhMucChi;
        this.donViChi = donViChi;
        this.thoiDiemApDungChi = thoiDiemApDungChi;
        this.danhGia = danhGia;
        this.deleteFlag = deleteFlag;
        this.idLoaiChi = idLoaiChi;
    }

    public int getIdChi() {
        return idChi;
    }

    public void setIdChi(int idChi) {
        this.idChi = idChi;
    }

    public String getTenMucChi() {
        return tenMucChi;
    }

    public void setTenMucChi(String tenMucChi) {
        this.tenMucChi = tenMucChi;
    }

    public String getDinhMucChi() {
        return dinhMucChi;
    }

    public void setDinhMucChi(String dinhMucChi) {
        this.dinhMucChi = dinhMucChi;
    }

    public String getDonViChi() {
        return donViChi;
    }

    public void setDonViChi(String donViChi) {
        this.donViChi = donViChi;
    }

    public String getThoiDiemApDungChi() {
        return thoiDiemApDungChi;
    }

    public void setThoiDiemApDungChi(String thoiDiemApDungChi) {
        this.thoiDiemApDungChi = thoiDiemApDungChi;
    }

    public int getDanhGia() {
        return danhGia;
    }

    public void setDanhGia(int danhGia) {
        this.danhGia = danhGia;
    }

    public int getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(int deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public int getIdLoaiChi() {
        return idLoaiChi;
    }

    public void setIdLoaiChi(int idLoaiChi) {
        this.idLoaiChi = idLoaiChi;
    }
}

