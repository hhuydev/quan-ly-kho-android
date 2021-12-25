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

public class PhieuNhapKhoAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private ArrayList<PhieuNhapKho> ds;

    public PhieuNhapKhoAdapter(Context context, int layout, ArrayList<PhieuNhapKho> ds) {
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

        TextView makho = view.findViewById(R.id.txtItemMaKhoPhieuNhap);
        TextView soluong = view.findViewById(R.id.txtItemSoLuongNhap);
        TextView ngaynhap = view.findViewById(R.id.txtItemNgayNhap);
        TextView maphieunhap = view.findViewById(R.id.txtItemMaPhieuNhapKho);
        TextView manhanvien = view.findViewById(R.id.txtItemMaNhanVienPhieuNhap);
        TextView mahanghoa = view.findViewById(R.id.txtItemMaHangHoaPhieuNhap);


        PhieuNhapKho phieunhap = ds.get(i);

        makho.setText(phieunhap.getMaKho());
        soluong.setText(String.valueOf(phieunhap.getSoLuong()));
        ngaynhap.setText(phieunhap.getNgayNhap());
        maphieunhap.setText(phieunhap.getMaPhieuNhap());
        manhanvien.setText(phieunhap.getMaNhanVien());
        mahanghoa.setText(phieunhap.getMaHangHoa());

        Button btnXoaPhieuNhap = view.findViewById(R.id.btnXoaTTPhieuNhapKhoActivity);

        int vitri = i;

        btnXoaPhieuNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ma = phieunhap.getMaPhieuNhap();
                if (ma == null || ma.length() == 0)
                    Toast.makeText(view.getContext(), "Không thể lấy mã phiếu nhập " + i, Toast.LENGTH_SHORT).show();
                else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                    builder.setTitle("Xác nhận xóa");
                    builder.setMessage("Bạn thật sự muốn xóa phiếu nhập này?")
                            .setPositiveButton("Có", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    DatabaseHandler db = new DatabaseHandler(view.getContext());
                                    try {
                                        db.xoaPhieuNhapKho(ma);
                                        ds.remove(vitri);
                                        notifyDataSetChanged();
                                    } catch (Exception e) {
                                        Toast.makeText(view.getContext(), "Lỗi xóa phiếu nhập", Toast.LENGTH_SHORT).show();
                                        e.printStackTrace();
                                    }
                                    Toast.makeText(view.getContext(), "Xóa phiếu nhập thành công", Toast.LENGTH_SHORT).show();
                                }
                            }).setNegativeButton("Không", null).show();
                }
            }
        });

        return view;
    }

}
