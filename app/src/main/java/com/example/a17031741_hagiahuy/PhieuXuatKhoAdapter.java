package com.example.a17031741_hagiahuy;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import java.util.ArrayList;

public class PhieuXuatKhoAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private ArrayList<PhieuXuatKho> ds;

    public PhieuXuatKhoAdapter(Context context, int layout, ArrayList<PhieuXuatKho> ds) {
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

        TextView makho = view.findViewById(R.id.txtItemMaKhoPhieuXuat);
        TextView soluong = view.findViewById(R.id.txtItemSoLuongXuat);
        TextView ngaynhap = view.findViewById(R.id.txtItemNgayXuat);
        TextView maphieuxuat = view.findViewById(R.id.txtItemMaPhieuXuatKho);
        TextView manhanvien = view.findViewById(R.id.txtItemMaNhanVienPhieuXuat);
        TextView mahanghoa = view.findViewById(R.id.txtItemMaHangHoaPhieuXuat);

        PhieuXuatKho phieuxuat = ds.get(i);

        makho.setText(phieuxuat.getMaKho());
        soluong.setText(String.valueOf(phieuxuat.getSoLuong()));
        ngaynhap.setText(phieuxuat.getNgayXuat());
        maphieuxuat.setText(phieuxuat.getMaPhieuXuat());
        manhanvien.setText(phieuxuat.getMaNhanVien());
        mahanghoa.setText(phieuxuat.getMaHangHoa());

        Button btnXoaPhieuXuat = view.findViewById(R.id.btnXoaTTPhieuXuatKhoActivity);

        int vitri = i;

        btnXoaPhieuXuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ma = phieuxuat.getMaPhieuXuat();
                if (ma == null || ma.length() == 0)
                    Toast.makeText(view.getContext(), "Không thể lấy mã phiếu xuất " + i, Toast.LENGTH_SHORT).show();
                else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                    builder.setTitle("Xác nhận xóa");
                    builder.setMessage("Bạn thật sự muốn xóa phiếu xuất này?")
                            .setPositiveButton("Có", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    DatabaseHandler db = new DatabaseHandler(view.getContext());
                                    try {
                                        db.xoaNhomHangHoa(ma);
                                        ds.remove(vitri);
                                        notifyDataSetChanged();
                                    } catch (Exception e) {
                                        Toast.makeText(view.getContext(), "Lỗi xóa phiếu xuất", Toast.LENGTH_SHORT).show();
                                        e.printStackTrace();
                                    }
                                    Toast.makeText(view.getContext(), "Xóa phiếu xuất thành công", Toast.LENGTH_SHORT).show();
                                }
                            }).setNegativeButton("Không", null).show();
                }
            }
        });

        return view;
    }
}
