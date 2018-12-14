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
public class Student {
    private String ID,name,tenlop,email,phone,address,avartar;
    private String sex;
    

    public Student(String ID, String name, String tenlop, String email, String phone, String sex, String address, String avartar) {
        this.ID = ID;
        this.name = name;
        this.tenlop = tenlop;
        this.email = email;
        this.phone = phone;
        this.sex = sex;
        this.address = address;
        this.avartar = avartar;
    }
    
    public Student(String ID, String name, String tenlop, String email, String phone, String sex, String address) {
        this.ID = ID;
        this.name = name;
        this.tenlop = tenlop;
        this.email = email;
        this.phone = phone;
        this.sex = sex;
        this.address = address;
    }
    
    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public String getLop() {
        return tenlop;
    }

    public void setLop(String tenlop) {
        this.tenlop = tenlop;
    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAvartar() {
        return avartar;
    }

    public void setAvartar(String avartar) {
        this.avartar = avartar;
    }
    
}
