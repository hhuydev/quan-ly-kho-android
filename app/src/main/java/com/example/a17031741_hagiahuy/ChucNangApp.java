package com.example.a17031741_hagiahuy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ChucNangApp extends AppCompatActivity {

    TextView txtLoiChaoNhanVien;
    DatabaseHandler db;

    //Option Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //goi menu tu thu muc res/menu
        getMenuInflater().inflate(R.menu.menu_nhanvien, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //xu ly su kien click tren option menu
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mnThongTin:
                Intent i = getIntent();
                String ma = i.getStringExtra("manv");
                String ten = i.getStringExtra("tennv");
                String diachi = i.getStringExtra("diachinv");
                String gioitinh = i.getStringExtra("gioitinhnv");
                suaThongTinNhanVien(ma, ten, diachi, gioitinh);
                break;
            case R.id.mnDSNhanVien:
                Intent i2 = new Intent(ChucNangApp.this, NhanVienActivity.class);
                startActivity(i2);
                break;
            case R.id.mnDoiMatKhau:
                i = getIntent();
                ma = i.getStringExtra("manv");
                String matkhaucu = i.getStringExtra("matkhaunv");
                doiMatKhauNhanVien(ma, matkhaucu);
                break;
            case R.id.mnDSNhomHangHoa:
                i2 = new Intent(ChucNangApp.this, NhomHangHoaAcitivty.class);
                startActivity(i2);
                break;
            case R.id.mnDSHangHoa:
                i2 = new Intent(ChucNangApp.this, HangHoaActivity.class);
                startActivity(i2);
                break;
            case R.id.mnQuanLyKho:
                i2 = new Intent(ChucNangApp.this, KhoActivity.class);
                startActivity(i2);
                break;
            case R.id.mnQuanLyNhapKho:
                i2 = new Intent(ChucNangApp.this, PhieuNhapKhoActivity.class);
                startActivity(i2);
                break;
            case R.id.mnQuanLyXuatKho:
                i2 = new Intent(ChucNangApp.this, PhieuXuatKhoActivity.class);
                startActivity(i2);
                break;
            case R.id.mnThongKe:
                i2 = new Intent(ChucNangApp.this, ThongKePhieuNhapXuat.class);
                startActivity(i2);
                break;
            case R.id.mnThoat:
                AlertDialog.Builder builder = new AlertDialog.Builder(ChucNangApp.this);
                builder.setTitle("Xác nhận thoát");
                builder.setMessage("Bạn thực sự muốn đăng xuất?")
                        .setPositiveButton("Có", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                finish();
                            }
                        }).setNegativeButton("Không", null).show();
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chuc_nang_app);

        txtLoiChaoNhanVien = findViewById(R.id.txtLoiChaoNhanVien);
        Intent i = getIntent();
        String tennv = i.getStringExtra("tennv");
        txtLoiChaoNhanVien.setText("Xin chào, " + tennv);

    }

    private void suaThongTinNhanVien(String manv, String tennv, String diachi, String gioitinh) {
        Dialog dialog = new Dialog(ChucNangApp.this);
        dialog.setTitle("Sửa thông tin nhân viên");
        dialog.setContentView(R.layout.dialog_suathongtin_nv);

        Button btnSua = dialog.findViewById(R.id.btnUpdateTTNhanVien);

        EditText edtSuaMa = dialog.findViewById(R.id.edtSuaMaNhanVien);
        EditText edtSuaTenSach = dialog.findViewById(R.id.edtSuaTenNhanVien);
        EditText edtSuaDiaChi = dialog.findViewById(R.id.edtSuaDiaChiNhanVien);
        EditText edtSuaGioiTinh = dialog.findViewById(R.id.edtSuaGioiTinhNhanVien);

        edtSuaMa.setText(manv);
        edtSuaMa.setFocusable(false);
        edtSuaTenSach.setText(tennv);
        edtSuaDiaChi.setText(diachi);
        edtSuaGioiTinh.setText(gioitinh);


        btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db = new DatabaseHandler(dialog.getContext());
                String suama = edtSuaMa.getText().toString().trim();
                String suaten = edtSuaTenSach.getText().toString().trim();
                String suangdiachi = edtSuaDiaChi.getText().toString().trim();
                String suagioitinh = edtSuaGioiTinh.getText().toString().trim();
                try {
                    db.suaThongTinNhanVien(suama, suaten, suangdiachi, suagioitinh);

                } catch (Exception e) {
                    e.printStackTrace();
                }
                Toast.makeText(ChucNangApp.this, "Sửa thông tin thành công", Toast.LENGTH_SHORT).show();
                edtSuaMa.setText("");
                edtSuaTenSach.setText("");
                edtSuaDiaChi.setText("");
                edtSuaGioiTinh.setText("");

            }
        });

        dialog.show();
    }

    private void doiMatKhauNhanVien(String manv, String matkhaucu) {
        Dialog dialog = new Dialog(ChucNangApp.this);
        dialog.setTitle("Đổi mật khẩu nhân viên");
        dialog.setContentView(R.layout.dialog_doimatkhau_nv);

        Button btnDoiMK = dialog.findViewById(R.id.btnDoiMatKhau);

        EditText edtMKCU = dialog.findViewById(R.id.edtNhapMKCu);
        EditText edtMKMoi = dialog.findViewById(R.id.edtNhapMKMoi);
        EditText edtXacNhanMKMoi = dialog.findViewById(R.id.edtXacNhanMKMoi);

        btnDoiMK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db = new DatabaseHandler(dialog.getContext());
                String matKhauCu = edtMKCU.getText().toString().trim();
                if (matKhauCu.equalsIgnoreCase(matKhauCu)) {
                    String matKhauMoi = edtMKMoi.getText().toString().trim();
                    String xacNhanMKMoi = edtXacNhanMKMoi.getText().toString().trim();
                    if (matKhauMoi.equalsIgnoreCase(xacNhanMKMoi)) {
                        try {
                            db.doiMatKhauNhanVien(manv, matKhauMoi);
                        } catch (Exception e) {
                            Toast.makeText(ChucNangApp.this, "Đổi mật khẩu không thành công!", Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
                        Toast.makeText(ChucNangApp.this, "Đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
                        edtMKCU.setText("");
                        edtMKMoi.setText("");
                        edtXacNhanMKMoi.setText("");
                    } else {
                        Toast.makeText(ChucNangApp.this, "Mật khẩu mới không giống nhau!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(ChucNangApp.this, "Mật khẩu cũ không đúng!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        dialog.show();
    }
}