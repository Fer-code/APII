package com.example.api.models;

public class ArtFavClass {
    public int idUser;
    public int idArt;

    public ArtFavClass(){}

    public ArtFavClass(int idUser, int idArt) {
        this.idUser = idUser;
        this.idArt = idArt;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public int getIdArt() {
        return idArt;
    }

    public void setIdArt(int idArt) {
        this.idArt = idArt;
    }
}
