package com.example.a17031741_hagiahuy;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import java.util.ArrayList;

public class NhanVienAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private ArrayList<NhanVien> ds;

    public NhanVienAdapter(Context context, int layout, ArrayList<NhanVien> ds) {
        this.context = context;
        this.layout = layout;
        this.ds = ds;
    }

    @Override
    public int getCount() {
        return ds.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        view = layoutInflater.inflate(layout, null);

        TextView manv = view.findViewById(R.id.txtItemMaNhanVien);
        TextView tennv = view.findViewById(R.id.txtItemTenNhanVien);
        TextView diachinv = view.findViewById(R.id.txtItemDiaChi);
        TextView gioitinhnv = view.findViewById(R.id.txtItemGioiTinh);

        NhanVien nv = ds.get(i);

        manv.setText(nv.getMaNV());
        tennv.setText(nv.getTenNV());
        diachinv.setText(nv.getDiaChi());
        gioitinhnv.setText(nv.getGioiTinh());

        Button btnSuaNhanVien = view.findViewById(R.id.btnSuaTTNhanVienActivity);
        Button btnXoaNhanVien = view.findViewById(R.id.btnXoaTTNhanVienActivity);

        int vitri = i;

        btnXoaNhanVien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ma = nv.getMaNV();
                if (ma == null || ma.length() == 0)
                    Toast.makeText(view.getContext(), "Không thể lấy mã nhân viên " + i, Toast.LENGTH_SHORT).show();
                else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                    builder.setTitle("Xác nhận xóa");
                    builder.setMessage("Bạn thật sự muốn xóa nhóm nhân viên này?")
                            .setPositiveButton("Có", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    DatabaseHandler db = new DatabaseHandler(view.getContext());
                                    try {
                                        db.xoaNhanVien(ma);
                                        ds.remove(vitri);
                                        notifyDataSetChanged();
                                    } catch (Exception e) {
                                        Toast.makeText(view.getContext(), "Lỗi xóa nhân viên", Toast.LENGTH_SHORT).show();
                                        e.printStackTrace();
                                    }
                                    Toast.makeText(view.getContext(), "Xóa nhân viên thành công", Toast.LENGTH_SHORT).show();
                                }
                            }).setNegativeButton("Không", null).show();
                }
            }
        });

        btnSuaNhanVien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String manv = nv.getMaNV();
                String tennv = nv.getTenNV();
                String diachi = nv.getDiaChi();
                String gioitinh = nv.getGioiTinh();
                suaThongTinNhanVien(ds, i, view.getContext(), manv, tennv, diachi, gioitinh);
            }
        });

        return view;
    }

    private void suaThongTinNhanVien(ArrayList<NhanVien> ds, int position, Context context, String manv, String tennv, String diachi, String gioitinh) {
        Dialog dialog = new Dialog(context);
        dialog.setTitle("Sửa thông tin nhân viên");
        dialog.setContentView(R.layout.dialog_suathongtin_nv);

        Button btnSua = dialog.findViewById(R.id.btnUpdateTTNhanVien);

        EditText edtSuaMa = dialog.findViewById(R.id.edtSuaMaNhanVien);
        EditText edtSuaTenSach = dialog.findViewById(R.id.edtSuaTenNhanVien);
        EditText edtSuaDiaChi = dialog.findViewById(R.id.edtSuaDiaChiNhanVien);
        EditText edtSuaGioiTinh = dialog.findViewById(R.id.edtSuaGioiTinhNhanVien);


        edtSuaMa.setText(manv);
        edtSuaMa.setFocusable(false);
        edtSuaTenSach.setText(tennv);
        edtSuaDiaChi.setText(diachi);
        edtSuaGioiTinh.setText(gioitinh);


        btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseHandler db = new DatabaseHandler(dialog.getContext());
                String suama = edtSuaMa.getText().toString().trim();
                String suaten = edtSuaTenSach.getText().toString().trim();
                String suangdiachi = edtSuaDiaChi.getText().toString().trim();
                String suagioitinh = edtSuaGioiTinh.getText().toString().trim();
                try {
                    db.suaThongTinNhanVien(suama, suaten, suangdiachi, suagioitinh);
                    ds.set(position, new NhanVien(suama, suaten, suangdiachi, suagioitinh));
                    notifyDataSetChanged();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Toast.makeText(context, "Sửa thông tin thành công", Toast.LENGTH_SHORT).show();
                edtSuaMa.setText("");
                edtSuaTenSach.setText("");
                edtSuaDiaChi.setText("");
                edtSuaGioiTinh.setText("");

            }
        });

        dialog.show();
    }
}
