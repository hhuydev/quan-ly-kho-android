package com.example.a17031741_hagiahuy;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class XemDanhSachHangHoa extends AppCompatActivity {
    Spinner spnLoadDSNhomHangHoa;
    ArrayList<String> dsNhomHangHoa;
    DatabaseHandler db;
    HangHoaAdapter hangHoaAdapter;
    ArrayList<HangHoa> dsHangHoa;
    ListView lvHangHoa;
    int vitri = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xem_danh_sach_hang_hoa);

        lvHangHoa = findViewById(R.id.lvHangHoa);
        spnLoadDSNhomHangHoa = findViewById(R.id.spnLoadDSNhomHangHoa);

        dsNhomHangHoa = new ArrayList<String>();

        db = new DatabaseHandler(XemDanhSachHangHoa.this);
        Cursor c = db.layDSNhomHangHoa();
        if (c.getCount() == 0) {
            Toast.makeText(XemDanhSachHangHoa.this, "Chưa có dữ liệu nhóm hàng hóa", Toast.LENGTH_SHORT).show();
        } else {
            String ma = "", ten = "";
            NhomHangHoa nhh = new NhomHangHoa();
            while (c.moveToNext()) {
                ma = c.getString(0);
                ten = c.getString(1);

                nhh = new NhomHangHoa(ma, ten);
                dsNhomHangHoa.add(nhh.getMaNhomHang() + " " + nhh.getTenNhomHang());
            }
        }

        ArrayAdapter arrayAdapter = new ArrayAdapter(XemDanhSachHangHoa.this, android.R.layout.simple_spinner_item, dsNhomHangHoa);
        arrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spnLoadDSNhomHangHoa.setAdapter(arrayAdapter);

        spnLoadDSNhomHangHoa.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                vitri = i;
                String nhomhh = dsNhomHangHoa.get(vitri);

                dsHangHoa = new ArrayList<HangHoa>();
                hangHoaAdapter = new HangHoaAdapter(XemDanhSachHangHoa.this, R.layout.item_hanghoa, dsHangHoa);

                db = new DatabaseHandler(XemDanhSachHangHoa.this);
                Cursor c = db.layDSHangHoa(nhomhh.split(" ")[0]);
                if (c.getCount() == 0) {
                    Toast.makeText(XemDanhSachHangHoa.this, "Nhóm hàng hóa chưa có hàng hóa", Toast.LENGTH_SHORT).show();
                } else {
                    String ten = "", ma = "";
                    float dongia = 0;
                    while (c.moveToNext()) {
                        ma = c.getString(0);
                        ten = c.getString(1);
                        dongia = c.getFloat(2);

                        dsHangHoa.add(new HangHoa(ma, ten, dongia));
                        lvHangHoa.setAdapter(hangHoaAdapter);
                        hangHoaAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
}