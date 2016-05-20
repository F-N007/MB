package com.mb.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class User implements Serializable {





    private static final long serialVersionUID = 10L;

    private String user_name, password, name, last_name, email, comments, profile_pic;//En path/sti til profilbilledet
    private int tlf;
    private String create_date;
    SimpleDateFormat formatter = new SimpleDateFormat("MMM dd, yyyy HH:mm");

    public User() {
        this.create_date = formatter.format(new Date());
    }

    public User(String user_name, String password, String name, String last_name, String email, int tlf, String comments, String profile_pic) {
        this.user_name = user_name;
        this.password = password;
        this.name = name;
        this.last_name = last_name;
        this.email = email;
        this.tlf = tlf;
        this.comments = comments;
        this.profile_pic = profile_pic;
        this.create_date = formatter.format(new Date());
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getTlf() {
        return tlf;
    }

    public void setTlf(int tlf) {
        this.tlf = tlf;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getProfile_pic() {
        return profile_pic;
    }

    public void setProfile_pic(String profile_pic) {
        this.profile_pic = profile_pic;
    }

    public String getCreate_date() {
        return create_date;
    }

    public void setCreate_date(String create_date) {
        this.create_date = create_date;
    }

    @Override
    public String toString() {
        return "User: " + "Username: " + user_name + ", Pasword: " + password + ", Name: " + name + ", Last name: " + last_name + ", Email: " + email + ", Phone.: " + tlf + ", Comments: " + comments + ", Profilepicture: " + profile_pic + ", Date: " + create_date + '}';
    }


}
