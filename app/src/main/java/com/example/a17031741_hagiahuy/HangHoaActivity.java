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
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class HangHoaActivity extends AppCompatActivity {

    Button btnThemHangHoa, btnXemHangHoa;
    DatabaseHandler db;
    Spinner spnDSNhomHangHoa;
    ArrayList<String> dsNhomHangHoa;
    int vitri = -1;
    final String maNhomHH = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hang_hoa);

        btnThemHangHoa = findViewById(R.id.btnThemHangHoa);
        btnXemHangHoa = findViewById(R.id.btnXemDSHangHoa);

        btnThemHangHoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                themMoiHangHoa(maNhomHH);
            }
        });

        btnXemHangHoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(HangHoaActivity.this, XemDanhSachHangHoa.class);
                startActivity(i);
            }
        });

    }

    private void themMoiHangHoa(String maNhomHH) {
        Dialog dialog = new Dialog(HangHoaActivity.this);
        dialog.setTitle("Thêm hàng hóa");
        dialog.setContentView(R.layout.dialog_tao_hanghoa);

        Button btnLuu = dialog.findViewById(R.id.btnThemMoiHangHoa);
        EditText edtThemMa = dialog.findViewById(R.id.edtThemMaHangHoa);
        EditText edtThemTen = dialog.findViewById(R.id.edtThemTenHangHoa);
        EditText edtThemGia = dialog.findViewById(R.id.edtThemGiaHangHoa);

        dsNhomHangHoa = new ArrayList<String>();

        spnDSNhomHangHoa = dialog.findViewById(R.id.spnDSNhomHangHoa);

        final String[] maNhomHangHoa = {""};

        db = new DatabaseHandler(HangHoaActivity.this);
        Cursor c = db.layDSNhomHangHoa();
        if (c.getCount() == 0) {
            Toast.makeText(HangHoaActivity.this, "Chưa có dữ liệu nhóm hàng hóa", Toast.LENGTH_SHORT).show();
        } else {
            String ma = "", ten = "";
            NhomHangHoa nhomhh = new NhomHangHoa();
            while (c.moveToNext()) {
                ma = c.getString(0);
                ten = c.getString(1);

                nhomhh = new NhomHangHoa(ma, ten);
                dsNhomHangHoa.add(nhomhh.getMaNhomHang() + " " + nhomhh.getTenNhomHang());
            }
        }

        spnDSNhomHangHoa.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                vitri = -1;
                maNhomHangHoa[0] = dsNhomHangHoa.get(i).split(" ")[0];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        ArrayAdapter arrayAdapter = new ArrayAdapter(HangHoaActivity.this, android.R.layout.simple_spinner_item, dsNhomHangHoa);
        arrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spnDSNhomHangHoa.setAdapter(arrayAdapter);

        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db = new DatabaseHandler(dialog.getContext());
                if (maNhomHangHoa[0].length() == 0) {
                    Toast.makeText(HangHoaActivity.this, "Không lấy được mã nhóm hàng hóa", Toast.LENGTH_SHORT).show();
                } else {
                    String ma = edtThemMa.getText().toString();
                    String ten = edtThemTen.getText().toString();
                    double gia = Double.parseDouble(edtThemGia.getText().toString());
                    if (ma.length() == 0 || ten.length() == 0) {
                        Toast.makeText(HangHoaActivity.this, "Thông tin hàng hóa không bỏ trống!", Toast.LENGTH_SHORT).show();
                    }
                    try {
                        boolean check = db.themHangHoa(ma, ten, gia, maNhomHangHoa[0]);
                        if (check == true) {
                            Toast.makeText(HangHoaActivity.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                            edtThemTen.setText("");
                            edtThemMa.setText("");
                            edtThemGia.setText("");
                        } else {
                            Toast.makeText(HangHoaActivity.this, "Lỗi thêm hàng hóa (mã bị trùng)", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        Toast.makeText(HangHoaActivity.this, "Lỗi thêm hàng hóa", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                }
            }
        });
        dialog.show();
    }
}