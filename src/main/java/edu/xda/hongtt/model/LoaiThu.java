package edu.xda.hongtt.model;

public class LoaiThu {
    public int id;
    public String tenLoaiThu;
    public int deleteFlag;

    public LoaiThu(int id, String tenLoaiThu, int deleteFlag) {
        this.id = id;
        this.tenLoaiThu = tenLoaiThu;
        this.deleteFlag = deleteFlag;
    }

    public LoaiThu(String tenLoaiThu, int deleteFlag){
        this.tenLoaiThu = tenLoaiThu;
        this.deleteFlag = deleteFlag;
    }
    public LoaiThu(int id, String tenLoaiThu){
        this.id = id;
        this.tenLoaiThu = tenLoaiThu;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTenLoaiThu() {
        return tenLoaiThu;
    }

    public void setTenLoaiThu(String tenLoaiThu) {
        this.tenLoaiThu = tenLoaiThu;
    }

    public int getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(int deleteFlag) {
        this.deleteFlag = deleteFlag;
    }
}
