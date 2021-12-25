package com.example.a17031741_hagiahuy;

public class NhomHangHoa {
    String maNhomHang, tenNhomHang;

    public NhomHangHoa(String maNhomHang, String tenNhomHang) {
        this.maNhomHang = maNhomHang;
        this.tenNhomHang = tenNhomHang;
    }

    public NhomHangHoa(String tenNhomHang) {
        this.tenNhomHang = tenNhomHang;
    }

    public NhomHangHoa() {
    }

    public String getMaNhomHang() {
        return maNhomHang;
    }

    public void setMaNhomHang(String maNhomHang) {
        this.maNhomHang = maNhomHang;
    }

    public String getTenNhomHang() {
        return tenNhomHang;
    }

    public void setTenNhomHang(String tenNhomHang) {
        this.tenNhomHang = tenNhomHang;
    }
}
