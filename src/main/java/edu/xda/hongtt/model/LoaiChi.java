package edu.xda.hongtt.model;

public class LoaiChi {
    public int id;
    public String tenLoaiChi;
    public int deleteFlag;

    public LoaiChi(int id, String tenLoaiChi, int deleteFlag) {
        this.id = id;
        this.tenLoaiChi = tenLoaiChi;
        this.deleteFlag = deleteFlag;
    }

    public LoaiChi(String tenLoaiChi, int deleteFlag) {
        this.tenLoaiChi = tenLoaiChi;
        this.deleteFlag = deleteFlag;
    }

    public LoaiChi(int id, String tenLoaiChi) {
        this.id = id;
        this.tenLoaiChi = tenLoaiChi;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTenLoaiChi() {
        return tenLoaiChi;
    }

    public void setTenLoaiChi(String tenLoaiChi) {
        this.tenLoaiChi = tenLoaiChi;
    }

    public int getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(int deleteFlag) {
        this.deleteFlag = deleteFlag;
    }
}
