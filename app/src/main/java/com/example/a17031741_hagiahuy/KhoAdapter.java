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

public class KhoAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private ArrayList<Kho> ds;

    public KhoAdapter(Context context, int layout, ArrayList<Kho> ds) {
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

        TextView makho = view.findViewById(R.id.txtItemMaKho);
        TextView tenkho = view.findViewById(R.id.txtItemTenKho);
        TextView diadiemkho = view.findViewById(R.id.txtItemDiaDiemKho);

        Kho kho = ds.get(i);

        makho.setText(kho.getMaKho());
        tenkho.setText(kho.getTenKho());
        diadiemkho.setText(String.valueOf(kho.getDiaDiem()));

        Button btnSuaHangHoa = view.findViewById(R.id.btnSuaTTKhoActivity);
        Button btnXoaHangHoa = view.findViewById(R.id.btnXoaTTKhoActivity);

        int vitri = i;

        btnSuaHangHoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ma = kho.getMaKho();
                String ten = kho.getTenKho();
                String diadiem = kho.getDiaDiem();
                suaThongTinKho(ds, vitri, view.getContext(), ma, ten, diadiem);
            }
        });

        btnXoaHangHoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ma = kho.getMaKho();
                if (ma == null || ma.length() == 0)
                    Toast.makeText(view.getContext(), "Không thể lấy mã kho " + i, Toast.LENGTH_SHORT).show();
                else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                    builder.setTitle("Xác nhận xóa");
                    builder.setMessage("Bạn thật sự muốn xóa kho này?")
                            .setPositiveButton("Có", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    DatabaseHandler db = new DatabaseHandler(view.getContext());
                                    try {
                                        db.xoaKho(ma);
                                        ds.remove(vitri);
                                        notifyDataSetChanged();
                                    } catch (Exception e) {
                                        Toast.makeText(view.getContext(), "Lỗi xóa kho", Toast.LENGTH_SHORT).show();
                                        e.printStackTrace();
                                    }
                                    Toast.makeText(view.getContext(), "Xóa kho thành công", Toast.LENGTH_SHORT).show();
                                }
                            }).setNegativeButton("Không", null).show();
                }
            }
        });

        return view;
    }

    private void suaThongTinKho(ArrayList<Kho> ds, int position, Context context, String ma, String ten, String diadiem) {
        Dialog dialog = new Dialog(context);
        dialog.setTitle("Sửa thông tin kho");
        dialog.setContentView(R.layout.dialog_suathongtin_kho);

        Button btnSua = dialog.findViewById(R.id.btnUpdateTTKho);

        EditText edtSuaMa = dialog.findViewById(R.id.edtSuaMaKho);
        EditText edtSuaTen = dialog.findViewById(R.id.edtSuaTenKho);
        EditText edtSuaDiaDiem = dialog.findViewById(R.id.edtSuaDiaDiemKho);

        edtSuaMa.setText(ma);
        edtSuaMa.setFocusable(false);
        edtSuaTen.setText(ten);
        edtSuaDiaDiem.setText(String.valueOf(diadiem));


        btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseHandler db = new DatabaseHandler(dialog.getContext());
                String suama = edtSuaMa.getText().toString().trim();
                String suaten = edtSuaTen.getText().toString().trim();
                String suadiadiem = edtSuaDiaDiem.getText().toString().trim();
                try {
                    db.suaThongTinKho(suama, suaten, suadiadiem);
                    ds.set(position, new Kho(suama, suaten, suadiadiem));
                    notifyDataSetChanged();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Toast.makeText(context, "Sửa thông tin thành công", Toast.LENGTH_SHORT).show();
                edtSuaMa.setText("");
                edtSuaTen.setText("");
                edtSuaDiaDiem.setText("");
            }
        });
        dialog.show();
    }
}
