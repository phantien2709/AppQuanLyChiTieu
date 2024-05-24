package edu.xda.hongtt.model;

public class User {
    private String username;
    private String password;
    private String vaiVe;
    public User(String tenDangNhap){
        this.username = tenDangNhap;
    }

    public User(String s, String s1) {
        this.username = s;
        this.password = s1;
    }

    public User(String username, String password, String vaiVe) {

        this.username = username;
        this.password = password;
        this.vaiVe = vaiVe;

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
