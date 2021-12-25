package com.example.a17031741_hagiahuy;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class NhanVienActivity extends AppCompatActivity {
    ListView lvNhanVien;
    ArrayList<NhanVien> dsNhanVien;
    DatabaseHandler db;
    NhanVienAdapter nvAdapter;
    int vitri=-1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nhan_vien);

        lvNhanVien = findViewById(R.id.lvNhanVien);
        dsNhanVien = new ArrayList<NhanVien>();

        db = new DatabaseHandler(NhanVienActivity.this);
        Cursor c = db.layDSNhanVien();
        if (c.getCount() == 0) {
            Toast.makeText(NhanVienActivity.this, "Chưa có dữ liệu nhân viên!", Toast.LENGTH_SHORT).show();
        } else {
            String ma = "", ten = "", diachi = "", gioitinh = "";
            while (c.moveToNext()) {
                ma = c.getString(0);
                ten = c.getString(1);
                diachi = c.getString(2);
                gioitinh = c.getString(3);
                dsNhanVien.add(new NhanVien(ma, ten, diachi, gioitinh));
            }
        }

        nvAdapter = new NhanVienAdapter(NhanVienActivity.this, R.layout.item_nhanvien, dsNhanVien);
        lvNhanVien.setAdapter(nvAdapter);
        nvAdapter.notifyDataSetChanged();

        lvNhanVien.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                vitri = i;

            }
        });
        

    }
}