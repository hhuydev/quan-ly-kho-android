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

public class PhieuNhapKhoActivity extends AppCompatActivity {
    EditText edtNhapMaPhieuNhapKho, edtNhapSoLuong, edtNgayNhapKho;
    Spinner spnHangHoa, spnKho, spnNhanVien;
    Button btnChonNgayNhap, btnThemPhieuNhap;
    DatabaseHandler db;
    ArrayList<String> loadDSHangHoa;
    ArrayList<String> loadDSKho;
    ArrayList<String> loadDSNhanVien;
    ListView lvPhieuNhap;
    int vitri = -1;
    String maKho = "", maNhanVien = "", maHangHoa = "";
    PhieuNhapKhoAdapter phieuNhapKhoAdapter;
    ArrayList<PhieuNhapKho> dsPhieuNhapKho;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phieu_nhap_kho);

        edtNhapMaPhieuNhapKho = findViewById(R.id.edtNhapMaPhieuNhapKho);
        edtNhapSoLuong = findViewById(R.id.edtNhapSoLuongNhap);
        edtNgayNhapKho = findViewById(R.id.edtNgayNhapKho);
        spnHangHoa = findViewById(R.id.spnSanPham);
        spnKho = findViewById(R.id.spnKho);
        spnNhanVien = findViewById(R.id.spnNhanVien);

        lvPhieuNhap = findViewById(R.id.lvPhieuNhapKho);

        btnChonNgayNhap = findViewById(R.id.btnChonNgayNhapKho);
        btnThemPhieuNhap = findViewById(R.id.btnThemMoiPhieuNhapKho);

        loadDSKho = new ArrayList<String>();
        loadDSNhanVien = new ArrayList<String>();
        loadDSHangHoa = new ArrayList<String>();

        dsPhieuNhapKho = new ArrayList<PhieuNhapKho>();

        phieuNhapKhoAdapter = new PhieuNhapKhoAdapter(PhieuNhapKhoActivity.this, R.layout.item_phieunhapkho, dsPhieuNhapKho);

        //load nv
        db = new DatabaseHandler(PhieuNhapKhoActivity.this);
        Cursor c = db.layDSNhanVien();
        if (c.getCount() == 0) {
            Toast.makeText(PhieuNhapKhoActivity.this, "Chưa có dữ liệu nhân viên", Toast.LENGTH_SHORT).show();
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

        ArrayAdapter nhanVienAdapter = new ArrayAdapter(PhieuNhapKhoActivity.this, android.R.layout.simple_spinner_item, loadDSNhanVien);
        nhanVienAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spnNhanVien.setAdapter(nhanVienAdapter);

        //load kho
        Cursor c2 = db.layDSKho();
        if (c2.getCount() == 0) {
            Toast.makeText(PhieuNhapKhoActivity.this, "Chưa có dữ liệu kho", Toast.LENGTH_SHORT).show();
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

        ArrayAdapter khoAdapter = new ArrayAdapter(PhieuNhapKhoActivity.this, android.R.layout.simple_spinner_item, loadDSKho);
        khoAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spnKho.setAdapter(khoAdapter);

        //load hang hoa
        Cursor c3 = db.layAllDSHangHoa();
        if (c3.getCount() == 0) {
            Toast.makeText(PhieuNhapKhoActivity.this, "Chưa có dữ liệu hàng hóa", Toast.LENGTH_SHORT).show();
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

        ArrayAdapter hangHoaAdapter = new ArrayAdapter(PhieuNhapKhoActivity.this, android.R.layout.simple_spinner_item, loadDSHangHoa);
        hangHoaAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spnHangHoa.setAdapter(hangHoaAdapter);

        spnKho.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                vitri = i;
                maKho = loadDSKho.get(vitri);

                db = new DatabaseHandler(PhieuNhapKhoActivity.this);
                Cursor c = db.layDSKhoTheoMa(maKho.split(" ")[0]);

                if (c.getCount() == 0) {
                    Toast.makeText(PhieuNhapKhoActivity.this, "Không thể lấy được thông tin kho", Toast.LENGTH_SHORT).show();
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

                db = new DatabaseHandler(PhieuNhapKhoActivity.this);
                Cursor c = db.layDSNhanVienTheoMa(maNhanVien.split(" ")[0]);
                if (c.getCount() == 0) {
                    Toast.makeText(PhieuNhapKhoActivity.this, "Không thể lấy được thông tin nhân viên", Toast.LENGTH_SHORT).show();

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

                db = new DatabaseHandler(PhieuNhapKhoActivity.this);
                Cursor c = db.layDSHangHoaTheoMa(maHangHoa.split(" ")[0]);

                if (c.getCount() == 0) {
                    Toast.makeText(PhieuNhapKhoActivity.this, "Không thể lấy được thông tin hàng hóa", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btnChonNgayNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chonNgay();
            }
        });

        btnThemPhieuNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db = new DatabaseHandler(PhieuNhapKhoActivity.this);
                String maPhieu = edtNhapMaPhieuNhapKho.getText().toString().trim();
                int soluong = Integer.parseInt(edtNhapSoLuong.getText().toString());
                String ngayNhap = edtNgayNhapKho.getText().toString();
                String makhosplit = maKho.split(" ")[0];
                String manvsplit = maNhanVien.split(" ")[0];
                String mahanghoasplit = maHangHoa.split(" ")[0];


                if (maPhieu.length() == 0 || soluong == 0) {
                    Toast.makeText(PhieuNhapKhoActivity.this, "Thông tin phiếu nhập kho không hợp lệ!", Toast.LENGTH_SHORT).show();
                }
                try {
                    boolean check = db.themPhieuNhapKho(maPhieu, mahanghoasplit, manvsplit, makhosplit, ngayNhap, soluong);


                    if (check == true) {
                        Toast.makeText(PhieuNhapKhoActivity.this, "Thêm phiếu nhập thành công)", Toast.LENGTH_SHORT).show();

                        PhieuNhapKho phieuNhapKho = new PhieuNhapKho(maPhieu, mahanghoasplit, manvsplit, makhosplit,
                                ngayNhap, soluong);
                        dsPhieuNhapKho.add(phieuNhapKho);
                        lvPhieuNhap.setAdapter(phieuNhapKhoAdapter);
                        phieuNhapKhoAdapter.notifyDataSetChanged();

                        edtNgayNhapKho.setText("");
                        edtNgayNhapKho.setText("");
                        edtNhapSoLuong.setText("");
                    } else
                        Toast.makeText(PhieuNhapKhoActivity.this, "Lỗi thêm phiếu nhập kho (trùng mã)", Toast.LENGTH_SHORT).show();

                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(PhieuNhapKhoActivity.this, "Lỗi thêm phiếu nhập kho", Toast.LENGTH_SHORT).show();
                }

//
            }
        });

        lvPhieuNhap.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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

        DatePickerDialog datePickerDialog = new DatePickerDialog(PhieuNhapKhoActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                //gan lai tg neu khong mac dinh lay tg hien tai
                //i: nam - i1: thang - i2: ngay
                c.set(i, i1, i2);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                edtNgayNhapKho.setText(simpleDateFormat.format(c.getTime()));
            }
        }, nam, thang, ngay);
        datePickerDialog.show();
    }
}