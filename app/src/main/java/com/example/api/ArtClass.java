package com.example.api;

public class ArtClass {

    int codArt;
    String nomeArt;
    String nomeArtArt;
    String genero;
    String localNasc;
    String AnoNasc;
    String localMorte;
    String culArt;

    public ArtClass(){}


    public ArtClass(int _codArt, String _nomeArt,  String _nomeArtArt, String _gen,
                    String _localNasc, String _AnoNasc, String _localMorte, String culArt){
        this.codArt = _codArt;
        this.nomeArt = _nomeArt;
        this.nomeArtArt = _nomeArtArt;
        this.genero = _gen;
        this.localNasc = _localNasc;
        this.AnoNasc = _AnoNasc;
        this.localMorte = _localMorte;
        this.culArt = culArt;
    }

    public ArtClass( String _nomeArt,  String _nomeArtArt, String _gen,
                    String _localNasc, String _AnoNasc, String _localMorte, String culArt){
        this.nomeArt = _nomeArt;
        this.nomeArtArt = _nomeArtArt;
        this.genero = _gen;
        this.localNasc = _localNasc;
        this.AnoNasc = _AnoNasc;
        this.localMorte = _localMorte;
        this.culArt = culArt;
    }

    public int getCodArt() {
        return codArt;
    }

    public void setCodArt(int codArt) {
        this.codArt = codArt;
    }

    public String getNomeArt() {
        return nomeArt;
    }

    public void setNomeArt(String nomeArt) {
        this.nomeArt = nomeArt;
    }

    public String getNomeArtArt() {
        return nomeArtArt;
    }

    public void setNomeArtArt(String nomeArtArt) {
        this.nomeArtArt = nomeArtArt;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getLocalNasc() {
        return localNasc;
    }

    public void setLocalNasc(String localNasc) {
        this.localNasc = localNasc;
    }

    public String getAnoNasc() {
        return AnoNasc;
    }

    public void setAnoNasc(String anoNasc) {
        AnoNasc = anoNasc;
    }

    public String getLocalMorte() {
        return localMorte;
    }

    public void setLocalMorte(String anoMorte) {
        localMorte = anoMorte;
    }

    public String getCulArt() {
        return culArt;
    }

    public void setCulArt(String culArt) {
        this.culArt = culArt;
    }
}
