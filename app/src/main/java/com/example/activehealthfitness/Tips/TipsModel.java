package com.example.activehealthfitness.Tips;

public class TipsModel {
    String tipNo;
    String tipName;
    String tipDesc;

    public TipsModel(String tipNo, String tipName, String tipDesc) {
        this.tipNo = tipNo;
        this.tipName = tipName;
        this.tipDesc = tipDesc;
    }

    public String getTipNo() {
        return tipNo;
    }

    public String getTipName() {
        return tipName;
    }

    public String getTipDesc() {
        return tipDesc;
    }

}
