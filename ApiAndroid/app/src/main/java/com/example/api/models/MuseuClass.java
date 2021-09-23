package com.example.api.models;

import com.example.api.Museu;

public class MuseuClass {
    public int idM;
    public String nameM;
    public String addressM;
    public int userM;

    public MuseuClass(){}

    public MuseuClass(String nameM, String addressM, int userM) {
        this.nameM = nameM;
        this.addressM = addressM;
        this.userM = userM;
    }

    public MuseuClass(int idM, String nameM, String addressM, int userM) {
        this.idM = idM;
        this.nameM = nameM;
        this.addressM = addressM;
        this.userM = userM;
    }

    public int getIdM() {
        return idM;
    }

    public void setIdM(int idM) {
        this.idM = idM;
    }

    public String getNameM() {
        return nameM;
    }

    public void setNameM(String nameM) {
        this.nameM = nameM;
    }

    public String getAddressM() {
        return addressM;
    }

    public void setAddressM(String addressM) {
        this.addressM = addressM;
    }

    public int getUserM() {
        return userM;
    }

    public void setUserM(int userM) {
        this.userM = userM;
    }
}
