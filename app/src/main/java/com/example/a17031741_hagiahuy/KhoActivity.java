package com.example.a17031741_hagiahuy;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class KhoActivity extends AppCompatActivity {

    Button btnThemKho, btnXemKho;
    DatabaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kho);

        btnThemKho = findViewById(R.id.btnThemKho);
        btnXemKho = findViewById(R.id.btnXemDSKho);

        btnThemKho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                themMoiKho();
            }
        });

        btnXemKho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(KhoActivity.this, XemKhoActivity.class);
                startActivity(i);
            }
        });
    }

    private void themMoiKho() {
        Dialog dialog = new Dialog(KhoActivity.this);
        dialog.setTitle("Thêm kho");
        dialog.setContentView(R.layout.dialog_taokho);

        Button btnLuu = dialog.findViewById(R.id.btnThemMoiKho);
        EditText edtThemMa = dialog.findViewById(R.id.edtThemMaKho);
        EditText edtThemTen = dialog.findViewById(R.id.edtThemTenKho);
        EditText edtDiaDiem = dialog.findViewById(R.id.edtThemDiaDiemKho);

        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db = new DatabaseHandler(dialog.getContext());
                String ma = edtThemMa.getText().toString();
                String ten = edtThemTen.getText().toString();
                String diadiem = edtDiaDiem.getText().toString();
                if (ma.length() == 0 || ten.length() == 0) {
                    Toast.makeText(KhoActivity.this, "Thông tin không không bỏ trống!", Toast.LENGTH_SHORT).show();
                }
                try {
                    boolean check = db.themKho(ma, ten, diadiem);
                    if (check == true) {
                        Toast.makeText(KhoActivity.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                        edtThemTen.setText("");
                        edtThemMa.setText("");
                        edtDiaDiem.setText("");
                    } else {
                        Toast.makeText(KhoActivity.this, "Lỗi thêm kho (mã bị trùng)", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(KhoActivity.this, "Lỗi thêm kho", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }

            }
        });
        dialog.show();
    }
}