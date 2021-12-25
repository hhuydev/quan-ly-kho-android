package com.example.a17031741_hagiahuy;

public class PhieuXuatKho {
    String maPhieuXuat, maHangHoa, maNhanVien, maKho, ngayXuat;
    int soLuong;

    public PhieuXuatKho(String maPhieuXuat, String maHangHoa, String maNhanVien, String maKho, String ngayXuat, int soLuong) {
        this.maPhieuXuat = maPhieuXuat;
        this.maHangHoa = maHangHoa;
        this.maNhanVien = maNhanVien;
        this.maKho = maKho;
        this.ngayXuat = ngayXuat;
        this.soLuong = soLuong;
    }

    public PhieuXuatKho(String maHangHoa, String maNhanVien, String maKho, String ngayXuat, int soLuong) {
        this.maHangHoa = maHangHoa;
        this.maNhanVien = maNhanVien;
        this.maKho = maKho;
        this.ngayXuat = ngayXuat;
        this.soLuong = soLuong;
    }

    public PhieuXuatKho() {
    }

    public String getMaPhieuXuat() {
        return maPhieuXuat;
    }

    public void setMaPhieuXuat(String maPhieuXuat) {
        this.maPhieuXuat = maPhieuXuat;
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

    public String getNgayXuat() {
        return ngayXuat;
    }

    public void setNgayXuat(String ngayXuat) {
        this.ngayXuat = ngayXuat;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }
}
