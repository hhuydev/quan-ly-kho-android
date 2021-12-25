package com.example.a17031741_hagiahuy;

public class Kho {
    String maKho, tenKho, diaDiem;

    public Kho(String maKho, String tenKho, String diaDiem) {
        this.maKho = maKho;
        this.tenKho = tenKho;
        this.diaDiem = diaDiem;
    }

    public Kho() {
    }

    public Kho(String tenKho, String diaDiem) {
        this.tenKho = tenKho;
        this.diaDiem = diaDiem;
    }

    public String getMaKho() {
        return maKho;
    }

    public void setMaKho(String maKho) {
        this.maKho = maKho;
    }

    public String getTenKho() {
        return tenKho;
    }

    public void setTenKho(String tenKho) {
        this.tenKho = tenKho;
    }

    public String getDiaDiem() {
        return diaDiem;
    }

    public void setDiaDiem(String diaDiem) {
        this.diaDiem = diaDiem;
    }
}
