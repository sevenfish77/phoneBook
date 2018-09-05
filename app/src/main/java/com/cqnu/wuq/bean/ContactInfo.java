package com.cqnu.wuq.bean;


public class ContactInfo {                 //用于通讯录的数据封装
    private String name;
    private String phone;
    private String email;
    private String home;
    private String nickname;

    //构造方法
    public ContactInfo() {
    }

    //构造方法
    public ContactInfo(String name) {
        this.name = name;
    }

    public ContactInfo(String name, String phone, String email, String home, String nickname) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.home = home;
        this.nickname = nickname;
    }

    //设置名字
    public void setName(String name) {
        this.name = name;
    }

    //获取名称
    public String getName() {
        return name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhone() {
        return phone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }


    public void setHome(String home) {
        this.home = home;
    }

    public String getHome() {
        return home;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getNickname() {
        return nickname;
    }


    public String toString() {
        return "ContactInfo{" +
                "name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", home='" + home + '\'' +
                ", nickname='" + nickname + '\'' +
                '}';
    }
}
