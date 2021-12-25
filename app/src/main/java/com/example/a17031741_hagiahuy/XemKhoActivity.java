package com.example.a17031741_hagiahuy;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class XemKhoActivity extends AppCompatActivity {

    ListView lvKho;
    DatabaseHandler db;
    ArrayList<Kho> dsKho;
    KhoAdapter khoAdapter;
    int vitri=-1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xem_kho);

        lvKho = findViewById(R.id.lvKho);
        dsKho = new ArrayList<Kho>();

        db = new DatabaseHandler(XemKhoActivity.this);
        Cursor c = db.layDSKho();
        if (c.getCount() == 0) {
            Toast.makeText(XemKhoActivity.this, "Chưa có dữ liệu kho!", Toast.LENGTH_SHORT).show();
        } else {
            String ma = "", ten = "", diadiem = "";
            while (c.moveToNext()) {
                ma = c.getString(0);
                ten = c.getString(1);
                diadiem = c.getString(2);
                dsKho.add(new Kho(ma, ten, diadiem));
            }
        }

        khoAdapter = new KhoAdapter(XemKhoActivity.this, R.layout.item_kho, dsKho);
        lvKho.setAdapter(khoAdapter);
        khoAdapter.notifyDataSetChanged();

        lvKho.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                vitri = i;
            }
        });
    }
}