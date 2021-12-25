package com.example.a17031741_hagiahuy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button btnDangNhap, btnDangKy;
    EditText edtTaiKhoanDN, edtMatKhauDN;
    DatabaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnDangNhap = findViewById(R.id.btnDangNhap);
        btnDangKy = findViewById(R.id.btnDangKy);
        edtMatKhauDN = findViewById(R.id.edtNhapMKDangNhap);
        edtTaiKhoanDN = findViewById(R.id.edtNhapTKDangNhap);

        btnDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, DangKy.class);
                startActivity(i);
            }
        });

        btnDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db = new DatabaseHandler(MainActivity.this);
                String tk = edtTaiKhoanDN.getText().toString().trim();
                String mk = edtMatKhauDN.getText().toString().trim();
                boolean checkThongTin = kiemTraThongTin(tk, mk);
                if (checkThongTin) {
                    try {
                        Intent i = new Intent(MainActivity.this, ChucNangApp.class);
                        Cursor c = db.dangNhap(tk, mk);
                        if (c.getCount() == 0) {
                            Toast.makeText(MainActivity.this, "Tài khoản không tồn tại!", Toast.LENGTH_SHORT).show();
                        } else {
                            String ma = "", ten = "", gioitinh = "", diachi = "", matkhau = "";
                            while (c.moveToNext()) {
                                ma = c.getString(0);
                                ten = c.getString(1);
                                diachi = c.getString(2);
                                gioitinh = c.getString(3);
                            }
                            i.putExtra("manv", ma);
                            i.putExtra("tennv", ten);
                            i.putExtra("diachinv", diachi);
                            i.putExtra("gioitinhnv", gioitinh);
                            i.putExtra("matkhaunv", matkhau);
                            Toast.makeText(MainActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                            startActivity(i);
                        }
                    } catch (Exception e) {
                        Toast.makeText(MainActivity.this, "Đăng nhập không thành công", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Thông tin còn trống. Hãy điền đủ thông tin", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private boolean kiemTraThongTin(String tk, String mk) {
        if (tk.length() == 0) {
            Toast.makeText(MainActivity.this, "Tài khoản nhân viên không được trống", Toast.LENGTH_SHORT).show();
            return false;
        } else if (mk.length() == 0) {
            Toast.makeText(MainActivity.this, "Mật khẩu nhân viên không được trống", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;

    }
}