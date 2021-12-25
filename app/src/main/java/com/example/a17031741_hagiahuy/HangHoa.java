package com.example.a17031741_hagiahuy;

public class HangHoa {
    String maHangHoa, tenHangHoa;
    float donGia;

    public HangHoa(String maHangHoa, String tenHangHoa, float donGia) {
        this.maHangHoa = maHangHoa;
        this.tenHangHoa = tenHangHoa;
        this.donGia = donGia;
    }

    public HangHoa(String tenHangHoa, float donGia) {
        this.tenHangHoa = tenHangHoa;
        this.donGia = donGia;
    }

    public HangHoa() {
    }

    public HangHoa(String maHangHoa, String tenHangHoa) {
        this.maHangHoa = maHangHoa;
        this.tenHangHoa = tenHangHoa;
    }

    public String getMaHangHoa() {
        return maHangHoa;
    }

    public void setMaHangHoa(String maHangHoa) {
        this.maHangHoa = maHangHoa;
    }

    public String getTenHangHoa() {
        return tenHangHoa;
    }

    public void setTenHangHoa(String tenHangHoa) {
        this.tenHangHoa = tenHangHoa;
    }

    public float getDonGia() {
        return donGia;
    }

    public void setDonGia(float donGia) {
        this.donGia = donGia;
    }
}
