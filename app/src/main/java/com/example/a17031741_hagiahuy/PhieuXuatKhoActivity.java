package com.example.a17031741_hagiahuy;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class PhieuXuatKhoActivity extends AppCompatActivity {
    EditText edtXuatMaPhieuXuatKho, edtXuatSoLuong, edtNgayXuatKho;
    Spinner spnHangHoa, spnKho, spnNhanVien;
    Button btnChonNgayXuat, btnThemPhieuXuat;
    DatabaseHandler db;
    ArrayList<String> loadDSHangHoa;
    ArrayList<String> loadDSKho;
    ArrayList<String> loadDSNhanVien;
    ListView lvPhieuXuat;
    int vitri = -1;
    String maKho = "", maNhanVien = "", maHangHoa = "";
    PhieuXuatKhoAdapter phieuXuatKhoAdapter;
    ArrayList<PhieuXuatKho> dsPhieuXuatKho;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phieu_xuat_kho);

        edtXuatMaPhieuXuatKho = findViewById(R.id.edtNhapMaPhieuXuatKho);
        edtXuatSoLuong = findViewById(R.id.edtNhapSoLuongXuat);
        edtNgayXuatKho = findViewById(R.id.edtNgayXuatKho);
        spnHangHoa = findViewById(R.id.spnSanPhamXuat);
        spnKho = findViewById(R.id.spnKhoXuat);
        spnNhanVien = findViewById(R.id.spnNhanVienXuat);
        lvPhieuXuat = findViewById(R.id.lvPhieuXuatKho);

        btnChonNgayXuat = findViewById(R.id.btnChonNgayXuatKho);
        btnThemPhieuXuat = findViewById(R.id.btnThemMoiPhieuXuatKho);


        loadDSKho = new ArrayList<String>();
        loadDSNhanVien = new ArrayList<String>();
        loadDSHangHoa = new ArrayList<String>();


        dsPhieuXuatKho = new ArrayList<PhieuXuatKho>();

        phieuXuatKhoAdapter = new PhieuXuatKhoAdapter(PhieuXuatKhoActivity.this, R.layout.item_phieuxuatkho, dsPhieuXuatKho);

        //load nv
        db = new DatabaseHandler(PhieuXuatKhoActivity.this);
        Cursor c = db.layDSNhanVien();
        if (c.getCount() == 0) {
            Toast.makeText(PhieuXuatKhoActivity.this, "Chưa có dữ liệu nhân viên", Toast.LENGTH_SHORT).show();
        } else {
            String ma = "", ten = "";
            NhanVien nhanVien = new NhanVien();
            while (c.moveToNext()) {
                ma = c.getString(0);
                ten = c.getString(1);

                nhanVien = new NhanVien(ma, ten);
                loadDSNhanVien.add(nhanVien.getMaNV() + " " + nhanVien.getTenNV());
            }
        }

        ArrayAdapter nhanVienAdapter = new ArrayAdapter(PhieuXuatKhoActivity.this, android.R.layout.simple_spinner_item, loadDSNhanVien);
        nhanVienAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spnNhanVien.setAdapter(nhanVienAdapter);

        //load kho
        Cursor c2 = db.layDSKho();
        if (c2.getCount() == 0) {
            Toast.makeText(PhieuXuatKhoActivity.this, "Chưa có dữ liệu kho", Toast.LENGTH_SHORT).show();
        } else {
            String ma = "", ten = "", diadiem = "";
            Kho k = new Kho();
            while (c2.moveToNext()) {
                ma = c2.getString(0);
                ten = c2.getString(1);
                diadiem = c2.getString(2);

                k = new Kho(ma, ten, diadiem);
                loadDSKho.add((k.getMaKho() + " " + k.getTenKho() + " " + k.getDiaDiem()));
            }
        }

        ArrayAdapter khoAdapter = new ArrayAdapter(PhieuXuatKhoActivity.this, android.R.layout.simple_spinner_item, loadDSKho);
        khoAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spnKho.setAdapter(khoAdapter);

        //load hang hoa
        Cursor c3 = db.layAllDSHangHoa();
        if (c3.getCount() == 0) {
            Toast.makeText(PhieuXuatKhoActivity.this, "Chưa có dữ liệu hàng hóa", Toast.LENGTH_SHORT).show();
        } else {
            String ma = "", ten = "";
            HangHoa hh = new HangHoa();
            while (c3.moveToNext()) {
                ma = c3.getString(0);
                ten = c3.getString(1);

                hh = new HangHoa(ma, ten);
                loadDSHangHoa.add(hh.getMaHangHoa() + " " + hh.getTenHangHoa());
            }
        }

        ArrayAdapter hangHoaAdapter = new ArrayAdapter(PhieuXuatKhoActivity.this, android.R.layout.simple_spinner_item, loadDSHangHoa);
        hangHoaAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spnHangHoa.setAdapter(hangHoaAdapter);

        spnKho.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                vitri = i;
                maKho = loadDSKho.get(vitri);

                db = new DatabaseHandler(PhieuXuatKhoActivity.this);
                Cursor c = db.layDSKhoTheoMa(maKho.split(" ")[0]);

                if (c.getCount() == 0) {
                    Toast.makeText(PhieuXuatKhoActivity.this, "Không thể lấy được thông tin kho", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spnNhanVien.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                vitri = i;
                maNhanVien = loadDSNhanVien.get(vitri);

                db = new DatabaseHandler(PhieuXuatKhoActivity.this);
                Cursor c = db.layDSNhanVienTheoMa(maNhanVien.split(" ")[0]);
                if (c.getCount() == 0) {
                    Toast.makeText(PhieuXuatKhoActivity.this, "Không thể lấy được thông tin nhân viên", Toast.LENGTH_SHORT).show();
                }
            }


            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spnHangHoa.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                vitri = i;
                maHangHoa = loadDSHangHoa.get(vitri);

                db = new DatabaseHandler(PhieuXuatKhoActivity.this);
                Cursor c = db.layDSHangHoaTheoMa(maHangHoa.split(" ")[0]);

                if (c.getCount() == 0) {
                    Toast.makeText(PhieuXuatKhoActivity.this, "Không thể lấy được thông tin hàng hóa", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btnChonNgayXuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chonNgay();
            }
        });

        btnThemPhieuXuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db = new DatabaseHandler(PhieuXuatKhoActivity.this);
                String maPhieu = edtXuatMaPhieuXuatKho.getText().toString().trim();
                int soluong = Integer.parseInt(edtXuatSoLuong.getText().toString());
                String ngayNhap = edtNgayXuatKho.getText().toString();
                String makhosplit = maKho.split(" ")[0];
                String manvsplit = maNhanVien.split(" ")[0];
                String mahanghoasplit = maHangHoa.split(" ")[0];


                if (maPhieu.length() == 0 || soluong == 0) {
                    Toast.makeText(PhieuXuatKhoActivity.this, "Thông tin phiếu xuất kho không hợp lệ!", Toast.LENGTH_SHORT).show();
                }
                try {
                    boolean check = db.themPhieuXuatKho(maPhieu, mahanghoasplit, manvsplit, makhosplit, ngayNhap, soluong);

                    if (check == true) {
                        Toast.makeText(PhieuXuatKhoActivity.this, "Thêm phiếu xuất thành công)", Toast.LENGTH_SHORT).show();

                        PhieuXuatKho phieuXuatKho = new PhieuXuatKho(maPhieu, mahanghoasplit, manvsplit, makhosplit,
                                ngayNhap, soluong);
                        dsPhieuXuatKho.add(phieuXuatKho);
                        lvPhieuXuat.setAdapter(phieuXuatKhoAdapter);
                        phieuXuatKhoAdapter.notifyDataSetChanged();

                        edtNgayXuatKho.setText("");
                        edtXuatMaPhieuXuatKho.setText("");
                        edtXuatSoLuong.setText("");
                    } else
                        Toast.makeText(PhieuXuatKhoActivity.this, "Lỗi thêm phiếu xuất kho (trùng mã)", Toast.LENGTH_SHORT).show();

                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(PhieuXuatKhoActivity.this, "Lỗi thêm phiếu xuất kho", Toast.LENGTH_SHORT).show();
                }
            }
        });

        lvPhieuXuat.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                vitri = i;
            }
        });
    }

    private void chonNgay() {
        Calendar c = Calendar.getInstance();
        int nam = c.get(Calendar.YEAR);
        int thang = c.get(Calendar.MONTH);
        int ngay = c.get(Calendar.DATE);

        DatePickerDialog datePickerDialog = new DatePickerDialog(PhieuXuatKhoActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                //gan lai tg neu khong mac dinh lay tg hien tai
                //i: nam - i1: thang - i2: ngay
                c.set(i, i1, i2);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                edtNgayXuatKho.setText(simpleDateFormat.format(c.getTime()));
            }
        }, nam, thang, ngay);
        datePickerDialog.show();
    }
}