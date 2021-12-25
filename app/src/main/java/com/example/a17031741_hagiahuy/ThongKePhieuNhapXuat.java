package com.example.a17031741_hagiahuy;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ThongKePhieuNhapXuat extends AppCompatActivity {
    ListView lvThongKePhieuNhap, lvThongKePhieuXuat;
    PhieuNhapKhoAdapter phieuNhapKhoAdapter;
    PhieuXuatKhoAdapter phieuXuatKhoAdapter;
    ArrayList<PhieuNhapKho> dsPhieuNhap;
    ArrayList<PhieuXuatKho> dsPhieuXuat;
    DatabaseHandler db;
    int vitri=-1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_ke_phieu_nhap_xuat);

        lvThongKePhieuNhap  = findViewById(R.id.lvThongKePhieuNhap);
        lvThongKePhieuXuat  = findViewById(R.id.lvThongKePhieuXuat);

        dsPhieuNhap = new ArrayList<PhieuNhapKho>();
        dsPhieuXuat = new ArrayList<PhieuXuatKho>();

        phieuNhapKhoAdapter = new PhieuNhapKhoAdapter(ThongKePhieuNhapXuat.this, R.layout.item_phieunhapkho, dsPhieuNhap);
        phieuXuatKhoAdapter = new PhieuXuatKhoAdapter(ThongKePhieuNhapXuat.this, R.layout.item_phieuxuatkho, dsPhieuXuat);

        db = new DatabaseHandler(ThongKePhieuNhapXuat.this);
        Cursor c = db.layDSPhieuNhapKho();
        if (c.getCount() == 0) {
            Toast.makeText(ThongKePhieuNhapXuat.this, "Chưa có dữ liệu phiếu nhâp kho", Toast.LENGTH_SHORT).show();
        } else {
            String maPhieuNhap = "", maHangHoa = "",maNV = "", maKho="", ngayNhap="";
            int soLuong=0;
            PhieuNhapKho phieunhap = new PhieuNhapKho();
            while (c.moveToNext()) {
                maPhieuNhap = c.getString(0);
                maHangHoa = c.getString(1);
                maNV = c.getString(2);
                maKho = c.getString(3);
                ngayNhap = c.getString(4);
                soLuong = c.getInt(5);
                phieunhap = new PhieuNhapKho(maPhieuNhap, maHangHoa, maNV, maKho, ngayNhap, soLuong);

                dsPhieuNhap.add(phieunhap);
                lvThongKePhieuNhap.setAdapter(phieuNhapKhoAdapter);
                phieuNhapKhoAdapter.notifyDataSetChanged();
            }
        }

        Cursor c2 = db.layDSPhieuXuatKho();
        if (c.getCount() == 0) {
            Toast.makeText(ThongKePhieuNhapXuat.this, "Chưa có dữ liệu phiếu xuất kho", Toast.LENGTH_SHORT).show();
        } else {
            String maPhieuXuat = "", maHangHoa = "",maNV = "", maKho="", ngayXuat="";
            int soLuong=0;
            PhieuXuatKho phieuxuat = new PhieuXuatKho();
            while (c2.moveToNext()) {
                maPhieuXuat = c2.getString(0);
                maHangHoa = c2.getString(1);
                maNV = c2.getString(2);
                maKho = c2.getString(3);
                ngayXuat = c2.getString(4);
                soLuong = c2.getInt(5);
                phieuxuat = new PhieuXuatKho(maPhieuXuat, maHangHoa, maNV, maKho, ngayXuat, soLuong);

                dsPhieuXuat.add(phieuxuat);
                lvThongKePhieuXuat.setAdapter(phieuXuatKhoAdapter);
                phieuXuatKhoAdapter.notifyDataSetChanged();
            }
        }

        lvThongKePhieuXuat.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                vitri=i;
            }
        });

        lvThongKePhieuNhap.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                vitri=i;
            }
        });
    }
}