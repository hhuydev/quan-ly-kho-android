package com.example.a17031741_hagiahuy;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class NhomHangHoaAcitivty extends AppCompatActivity {

    Button btnTaoNhomHangHoa, btnXemDSNhomHangHoa;
    DatabaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nhom_hang_hoa_acitivty);

        btnTaoNhomHangHoa = findViewById(R.id.btnThemNhomHangHoa);
        btnXemDSNhomHangHoa = findViewById(R.id.btnXemDSNhomHangHoa);

        btnTaoNhomHangHoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                themMoiNhomHangHoa();
            }
        });

        btnXemDSNhomHangHoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(NhomHangHoaAcitivty.this, XemDanhSachNhomHangHoa.class);
                startActivity(i);
            }
        });

    }

    private void themMoiNhomHangHoa() {
        Dialog dialog = new Dialog(NhomHangHoaAcitivty.this);
        dialog.setTitle("Thêm nhóm hàng hóa");
        dialog.setContentView(R.layout.dialog_tao_nhomhanghoa);

        Button btnLuu = dialog.findViewById(R.id.btnThemMoiNhomHangHoa);
        EditText edtThemMa = dialog.findViewById(R.id.edtThemMaNhomHangHoa);
        EditText edtThemTen = dialog.findViewById(R.id.edtThemTenNhomHangHoa);

        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db = new DatabaseHandler(dialog.getContext());
                String ma = edtThemMa.getText().toString();
                String ten = edtThemTen.getText().toString();
                if (ma.length() == 0 || ten.length() == 0) {
                    Toast.makeText(NhomHangHoaAcitivty.this, "Thông tin nhóm hàng hóa không bỏ trống!", Toast.LENGTH_SHORT).show();
                }
                try {
                    boolean check = db.themNhomHangHoa(ma, ten);
                    if (check == true) {
                        Toast.makeText(NhomHangHoaAcitivty.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                        edtThemTen.setText("");
                        edtThemMa.setText("");
                    } else {
                        Toast.makeText(NhomHangHoaAcitivty.this, "Lỗi thêm nhóm hàng hóa (mã bị trùng)", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(NhomHangHoaAcitivty.this, "Lỗi thêm nhóm hàng hóa", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }

            }
        });
        dialog.show();
    }
}