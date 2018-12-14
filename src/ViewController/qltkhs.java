/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ViewController;

/**
 *
 * @author NhatThanh
 */
public class qltkhs {
    private String masv, name, username, password;
    
    public qltkhs(String masv, String name, String username, String password) {
        this.masv = masv;
        this.name = name;
        this.username = username;
        this.password = password;
    }
     
    public String getMasv() {
        return masv;
    }

    public void setMasv(String masv) {
        this.masv = masv;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
