package com.example.a17031741_hagiahuy;

public class PhieuNhapKho {
    String maPhieuNhap, maHangHoa, maNhanVien, maKho, ngayNhap;
    int soLuong;

    public PhieuNhapKho(String maPhieuNhap, String maHangHoa, String maNhanVien, String maKho, String ngayNhap, int soLuong) {
        this.maPhieuNhap = maPhieuNhap;
        this.maHangHoa = maHangHoa;
        this.maNhanVien = maNhanVien;
        this.maKho = maKho;
        this.ngayNhap = ngayNhap;
        this.soLuong = soLuong;
    }

    public PhieuNhapKho(String maHangHoa, String maNhanVien, String maKho, String ngayNhap, int soLuong) {
        this.maHangHoa = maHangHoa;
        this.maNhanVien = maNhanVien;
        this.maKho = maKho;
        this.ngayNhap = ngayNhap;
        this.soLuong = soLuong;
    }

    public PhieuNhapKho() {
    }

    public String getMaPhieuNhap() {
        return maPhieuNhap;
    }

    public void setMaPhieuNhap(String maPhieuNhap) {
        this.maPhieuNhap = maPhieuNhap;
    }

    public String getMaHangHoa() {
        return maHangHoa;
    }

    public void setMaHangHoa(String maHangHoa) {
        this.maHangHoa = maHangHoa;
    }

    public String getMaNhanVien() {
        return maNhanVien;
    }

    public void setMaNhanVien(String maNhanVien) {
        this.maNhanVien = maNhanVien;
    }

    public String getMaKho() {
        return maKho;
    }

    public void setMaKho(String maKho) {
        this.maKho = maKho;
    }

    public String getNgayNhap() {
        return ngayNhap;
    }

    public void setNgayNhap(String ngayNhap) {
        this.ngayNhap = ngayNhap;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }
}
