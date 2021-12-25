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

public class HangHoaAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private ArrayList<HangHoa> ds;

    public HangHoaAdapter(Context context, int layout, ArrayList<HangHoa> ds) {
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

        TextView mahh = view.findViewById(R.id.txtItemMaHangHoa);
        TextView tenhh = view.findViewById(R.id.txtItemTenHangHoa);
        TextView dongia = view.findViewById(R.id.txtItemGiaHangHoa);

        HangHoa hh = ds.get(i);

        mahh.setText(hh.getMaHangHoa());
        tenhh.setText(hh.getTenHangHoa());
        dongia.setText(String.valueOf(hh.getDonGia()));

        Button btnSuaHangHoa = view.findViewById(R.id.btnSuaTTHangHoaActivity);
        Button btnXoaHangHoa = view.findViewById(R.id.btnXoaTTHangHoaActivity);

        int vitri = i;

        btnSuaHangHoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mahh = hh.getMaHangHoa();
                String tenhh = hh.getTenHangHoa();
                float gia = hh.getDonGia();
                suaThongTinHangHoa(ds, vitri, view.getContext(), mahh, tenhh, gia);
            }
        });

        btnXoaHangHoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ma = hh.getMaHangHoa();
                if (ma == null || ma.length() == 0)
                    Toast.makeText(view.getContext(), "Không thể lấy mã hàng hóa " + i, Toast.LENGTH_SHORT).show();
                else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                    builder.setTitle("Xác nhận xóa");
                    builder.setMessage("Bạn thật sự muốn xóa hàng hóa này?")
                            .setPositiveButton("Có", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    DatabaseHandler db = new DatabaseHandler(view.getContext());
                                    try {
                                        db.xoaHangHoa(ma);
                                        ds.remove(vitri);
                                        notifyDataSetChanged();
                                    } catch (Exception e) {
                                        Toast.makeText(view.getContext(), "Lỗi xóa hàng hóa", Toast.LENGTH_SHORT).show();
                                        e.printStackTrace();
                                    }
                                    Toast.makeText(view.getContext(), "Xóa hàng hóa thành công", Toast.LENGTH_SHORT).show();
                                }
                            }).setNegativeButton("Không", null).show();
                }
            }
        });

        return view;
    }

    private void suaThongTinHangHoa(ArrayList<HangHoa> ds, int position, Context context, String ma, String ten, float dongia) {
        Dialog dialog = new Dialog(context);
        dialog.setTitle("Sửa thông tin hàng hóa");
        dialog.setContentView(R.layout.dialog_suathongtin_hh);

        Button btnSua = dialog.findViewById(R.id.btnUpdateTTHangHoa);

        EditText edtSuaMa = dialog.findViewById(R.id.edtSuaMaHangHoa);
        EditText edtSuaTenSach = dialog.findViewById(R.id.edtSuaTenHangHoa);
        EditText edtSuaDonGia = dialog.findViewById(R.id.edtSuaGiaHangHoa);

        edtSuaMa.setText(ma);
        edtSuaMa.setFocusable(false);
        edtSuaTenSach.setText(ten);
        edtSuaDonGia.setText(String.valueOf(dongia));

        btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseHandler db = new DatabaseHandler(dialog.getContext());
                String suama = edtSuaMa.getText().toString().trim();
                String suaten = edtSuaTenSach.getText().toString().trim();
                float gia = Float.valueOf(edtSuaDonGia.getText().toString().trim());
                try {
                    db.suaThongTinHangHoa(suama, suaten, gia);
                    ds.set(position, new HangHoa(suama, suaten, gia));
                    notifyDataSetChanged();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Toast.makeText(context, "Sửa thông tin thành công", Toast.LENGTH_SHORT).show();
                edtSuaMa.setText("");
                edtSuaTenSach.setText("");
                edtSuaDonGia.setText("");
            }
        });
        dialog.show();
    }
}
