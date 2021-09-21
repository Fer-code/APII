package com.example.api.models;

public class ExpoClass {

    public int CodExpo;
    public String titleExpo;
    public String DIExpo;
    public String DFExpo;
    public String Desc;
    public String Temp;

    public ExpoClass(){}

    public ExpoClass( int _CodExpo, String _titleExpo, String _DIExpo, String _DFExpo, String _Desc, String _Temp){
        this.CodExpo = _CodExpo;
        this.titleExpo = _titleExpo;
        this.DIExpo = _DIExpo;
        this.DFExpo = _DFExpo;
        this.Desc = _Desc;
        this.Temp = _Temp;
    }

    public ExpoClass( String _titleExpo, String _DIExpo, String _DFExpo, String _Desc, String _Temp){
        this.titleExpo = _titleExpo;
        this.DIExpo = _DIExpo;
        this.DFExpo = _DFExpo;
        this.Desc = _Desc;
        this.Temp = _Temp;
    }

    public int getCodExpo() {
        return CodExpo;
    }

    public void setCodExpo(int codExpo) {
        CodExpo = codExpo;
    }

    public String getDesc() {
        return Desc;
    }

    public void setDesc(String desc) {
        Desc = desc;
    }

    public String getDFExpo() {
        return DFExpo;
    }

    public void setDFExpo(String DFExpo) {
        this.DFExpo = DFExpo;
    }

    public String getTitleExpo() {
        return titleExpo;
    }

    public void setTitleExpo(String titleExpo) {
        this.titleExpo = titleExpo;
    }

    public String getDIExpo() {
        return DIExpo;
    }

    public void setDIExpo(String DIExpo) {
        this.DIExpo = DIExpo;
    }

    public String getTemp() {
        return Temp;
    }

    public void setTemp(String temp) {
        Temp = temp;
    }
}
