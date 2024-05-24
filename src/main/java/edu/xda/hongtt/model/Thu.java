package edu.xda.hongtt.model;

public class Thu {
    public int idThu;
    public String tenMucThu;
    public String dinhMucThu;
    public String donViThu;
    public String thoiDiemApDungThu;
    public int danhGia;
    public int deleteFlag;
    public int idLoaiThu;

    public Thu(int idThu, String tenMucThu, String dinhMucThu, String donViThu, String thoiDiemApDungThu, int danhGia, int deleteFlag, int idLoaiThu) {
        this.idThu = idThu;
        this.tenMucThu = tenMucThu;
        this.dinhMucThu = dinhMucThu;
        this.donViThu = donViThu;
        this.thoiDiemApDungThu = thoiDiemApDungThu;
        this.danhGia = danhGia;
        this.deleteFlag = deleteFlag;
        this.idLoaiThu = idLoaiThu;
    }
    public Thu(String tenMucThu, String dinhMucThu, String donViThu, String thoiDiemApDungThu, int danhGia, int deleteFlag, int idLoaiThu) {
        this.tenMucThu = tenMucThu;
        this.dinhMucThu = dinhMucThu;
        this.donViThu = donViThu;
        this.thoiDiemApDungThu = thoiDiemApDungThu;
        this.danhGia = danhGia;
        this.deleteFlag = deleteFlag;
        this.idLoaiThu = idLoaiThu;
    }


    public int getIdThu() {
        return idThu;
    }

    public void setIdThu(int idThu) {
        this.idThu = idThu;
    }

    public String getTenMucThu() {
        return tenMucThu;
    }

    public void setTenMucThu(String tenMucThu) {
        this.tenMucThu = tenMucThu;
    }

    public String getDinhMucThu() {
        return dinhMucThu;
    }

    public void setDinhMucThu(String dinhMucThu) {
        this.dinhMucThu = dinhMucThu;
    }

    public String getDonViThu() {
        return donViThu;
    }

    public void setDonViThu(String donViThu) {
        this.donViThu = donViThu;
    }

    public String getThoiDiemApDungThu() {
        return thoiDiemApDungThu;
    }

    public void setThoiDiemApDungThu(String thoiDiemApDungThu) {
        this.thoiDiemApDungThu = thoiDiemApDungThu;
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

    public int getIdLoaiThu() {
        return idLoaiThu;
    }

    public void setIdLoaiThu(int idLoaiThu) {
        this.idLoaiThu = idLoaiThu;
    }
}

