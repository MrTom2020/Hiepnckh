package com.example.nckh.model;

public class thongtin
{
    private String id;
    private String nhietdo;
    private String doam;
    private String mq135;
    private String density;
    private String time;
    private String date;

    public thongtin(String id, String nhietdo, String doam, String mq135, String density, String time, String date) {
        this.id = id;
        this.nhietdo = nhietdo;
        this.doam = doam;
        this.mq135 = mq135;
        this.density = density;
        this.time = time;
        this.date = date;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNhietdo() {
        return nhietdo;
    }

    public void setNhietdo(String nhietdo) {
        this.nhietdo = nhietdo;
    }

    public String getDoam() {
        return doam;
    }

    public void setDoam(String doam) {
        this.doam = doam;
    }

    public String getMq135() {
        return mq135;
    }

    public void setMq135(String mq135) {
        this.mq135 = mq135;
    }

    public String getDensity() {
        return density;
    }

    public void setDensity(String density) {
        this.density = density;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
