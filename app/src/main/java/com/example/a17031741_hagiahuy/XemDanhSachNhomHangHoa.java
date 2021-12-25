package com.example.a17031741_hagiahuy;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class XemDanhSachNhomHangHoa extends AppCompatActivity {

    ListView lvNhomHangHoa;
    DatabaseHandler db;
    ArrayList<NhomHangHoa> dsNhomHangHoa;
    NhomHangHoaAdapter nhomHangHoaAdapter;
    int vitri=-1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xem_danh_sach_nhom_hang_hoa);

        lvNhomHangHoa = findViewById(R.id.lvNhomHangHoa);
        dsNhomHangHoa = new ArrayList<NhomHangHoa>();

        db = new DatabaseHandler(XemDanhSachNhomHangHoa.this);
        Cursor c = db.layDSNhomHangHoa();
        if (c.getCount() == 0) {
            Toast.makeText(XemDanhSachNhomHangHoa.this, "Chưa có dữ liệu nhóm hàng hóa!", Toast.LENGTH_SHORT).show();
        } else {
            String ma = "", ten = "";
            while (c.moveToNext()) {
                ma = c.getString(0);
                ten = c.getString(1);
                dsNhomHangHoa.add(new NhomHangHoa(ma, ten));
            }
        }

        nhomHangHoaAdapter = new NhomHangHoaAdapter(XemDanhSachNhomHangHoa.this, R.layout.item_nhomhanghoa, dsNhomHangHoa);
        lvNhomHangHoa.setAdapter(nhomHangHoaAdapter);
        nhomHangHoaAdapter.notifyDataSetChanged();

        lvNhomHangHoa.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                vitri = i;
            }
        });

    }
}