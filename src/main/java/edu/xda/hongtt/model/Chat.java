package edu.xda.hongtt.model;

public class Chat {
    public int idChat;
    public String noiDung;
    public String idTaiKhoan;


    public Chat(int id, String noiDung, String idTaiKhoan) {
        this.idChat = id;
        this.noiDung = noiDung;
        this.idTaiKhoan = idTaiKhoan;
    }
    public Chat( String noiDung, String idTaiKhoan) {
        this.noiDung = noiDung;
        this.idTaiKhoan = idTaiKhoan;
    }
    public int getId() {
        return idChat;
    }

    public void setId(int id) {
        this.idChat = id;
    }

    public String getNoiDung() {
        return noiDung;
    }

    public void setNoiDung(String noiDung) {
        this.noiDung = noiDung;
    }

    public String getIdTaiKhoan() {
        return idTaiKhoan;
    }

    public void setIdTaiKhoan(String idTaiKhoan) {
        this.idTaiKhoan = idTaiKhoan;
    }

}
