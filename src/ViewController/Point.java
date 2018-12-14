package ViewController;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author NhatThanh
 */
public class Point {
    private double toan, vatly, hoahoc, sinhhoc, vanhoc, tienganh, lichsu, dialy;

    private String masv, name, tenlop;

    
    
    public Point(String masv, String name, String tenlop, double toan, double vatly, double hoahoc, double sinhhoc, double vanhoc, double tienganh, double lichsu, double dialy) {
        this.toan = toan;
        this.vatly = vatly;
        this.hoahoc = hoahoc;
        this.sinhhoc = sinhhoc;
        this.vanhoc = vanhoc;
        this.tienganh = tienganh;
        this.lichsu = lichsu;
        this.dialy = dialy;
        this.masv = masv;
        this.name = name;
        this.tenlop = tenlop;
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
    
    public String getTenlop() {
        return tenlop;
    }

    public void setTenlop(String tenlop) {
        this.tenlop = tenlop;
    }
    public double getToan() {
        return toan;
    }

    public void setToan(double toan) {
        this.toan = toan;
    }

    public double getVatly() {
        return vatly;
    }

    public void setVatly(double vatly) {
        this.vatly = vatly;
    }

    public double getHoahoc() {
        return hoahoc;
    }

    public void setHoahoc(double hoahoc) {
        this.hoahoc = hoahoc;
    }

    public double getSinhhoc() {
        return sinhhoc;
    }

    public void setSinhhoc(double sinhhoc) {
        this.sinhhoc = sinhhoc;
    }

    public double getVanhoc() {
        return vanhoc;
    }

    public void setVanhoc(double vanhoc) {
        this.vanhoc = vanhoc;
    }

    public double getTienganh() {
        return tienganh;
    }

    public void setTienganh(double tienganh) {
        this.tienganh = tienganh;
    }

    public double getLichsu() {
        return lichsu;
    }

    public void setLichsu(double lichsu) {
        this.lichsu = lichsu;
    }

    public double getDialy() {
        return dialy;
    }

    public void setDialy(double dialy) {
        this.dialy = dialy;
    }
}
