package com.example.a17031741_hagiahuy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

public class DangKy extends AppCompatActivity {

    Button btnDangKyMoi;
    EditText edtDangKyMaNV, edtDangKyHoTen, edtDangKyTaiKhoan, edtDangKyMatKhau, edtDangKyDiaChi;
    RadioButton radNam, radNu;
    DatabaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ky);

        btnDangKyMoi = findViewById(R.id.btnDangKyMoi);
        edtDangKyMaNV = findViewById(R.id.edtNhapMaNVDangKy);
        edtDangKyHoTen = findViewById(R.id.edtNhapTenNVDangKy);
        edtDangKyTaiKhoan = findViewById(R.id.edtNhapTKDangKy);
        edtDangKyMatKhau = findViewById(R.id.edtNhapMKDangKy);
        edtDangKyDiaChi = findViewById(R.id.edtNhapDiaChiNVDangKy);
        radNam = findViewById(R.id.radNam);
        radNu = findViewById(R.id.radNu);

        btnDangKyMoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db = new DatabaseHandler(DangKy.this);
                String manv = edtDangKyMaNV.getText().toString().trim();
                String tennv = edtDangKyHoTen.getText().toString().trim();
                String gioiTinh = radNam.isChecked() ? "Nam" : "Nữ";
                String taikhoan = edtDangKyTaiKhoan.getText().toString().trim();
                String matkhau = edtDangKyMatKhau.getText().toString().trim();
                String diachi = edtDangKyDiaChi.getText().toString().trim();

                Boolean checkThongTin = kiemTraThongTin(manv, tennv, gioiTinh, taikhoan, matkhau, diachi);
                if (checkThongTin == true) {
                    try {
                        boolean check = db.dangKyNhanVien(manv, tennv, diachi, gioiTinh, taikhoan, matkhau);
                        if (check == true) {
                            Toast.makeText(DangKy.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(DangKy.this, ChucNangApp.class);
                            i.putExtra("manv", manv);
                            i.putExtra("tennv", tennv);
                            i.putExtra("diachinv", diachi);
                            i.putExtra("gioitinhnv", gioiTinh);
                            i.putExtra("matkhaunv", matkhau);
                            startActivity(i);
                        } else {
                            Toast.makeText(DangKy.this, "Đăng ký không thành công (mã nhân viên trùng)!", Toast.LENGTH_SHORT).show();
                        }

                    } catch (Exception e) {
                        Toast.makeText(DangKy.this, "Đăng ký không thành công!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(DangKy.this, "Thông tin còn trống. Hãy điền đủ thông tin", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private boolean kiemTraThongTin(String ma, String ten, String gioitinh, String tk, String mk, String diachi) {
        if (ma.length() == 0) {
            Toast.makeText(DangKy.this, "Mã nhân viên không được trống", Toast.LENGTH_SHORT).show();
            return false;
        } else if (ten.length() == 0) {
            Toast.makeText(DangKy.this, "Tên nhân viên không được trống", Toast.LENGTH_SHORT).show();
            return false;
        } else if (gioitinh.length() == 0) {
            Toast.makeText(DangKy.this, "Giới tính nhân viên không được trống", Toast.LENGTH_SHORT).show();
            return false;
        } else if (tk.length() == 0) {
            Toast.makeText(DangKy.this, "Tài khoản nhân viên không được trống", Toast.LENGTH_SHORT).show();
            return false;
        } else if (mk.length() == 0) {
            Toast.makeText(DangKy.this, "Mật khẩu nhân viên không được trống", Toast.LENGTH_SHORT).show();
            return false;
        } else if (gioitinh.length() == 0) {
            Toast.makeText(DangKy.this, "Giới tính nhân viên không được trống", Toast.LENGTH_SHORT).show();
            return false;

        } else if (diachi.length() == 0) {
            Toast.makeText(DangKy.this, "Địa chỉ nhân viên không được trống", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}