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

public class NhomHangHoaAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private ArrayList<NhomHangHoa> ds;

    public NhomHangHoaAdapter(Context context, int layout, ArrayList<NhomHangHoa> ds) {
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

        TextView maNhomHH = view.findViewById(R.id.txtItemMaNhomHangHoa);
        TextView tenNhomHH = view.findViewById(R.id.txtItemTenNhomHangHoa);


        NhomHangHoa nhomhh = ds.get(i);

        maNhomHH.setText(nhomhh.getMaNhomHang());
        tenNhomHH.setText(nhomhh.getTenNhomHang());


        Button btnSuaNhomHangHoa = view.findViewById(R.id.btnSuaTTNhomHangHoaActivity);
        Button btnXoaXoaNhomHangHoa = view.findViewById(R.id.btnXoaTTNhomHangHoaActivity);

        int vitri = i;

        btnXoaXoaNhomHangHoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ma = nhomhh.getMaNhomHang();
                if (ma == null || ma.length() == 0)
                    Toast.makeText(view.getContext(), "Không thể lấy mã nhóm hàng hóa " + i, Toast.LENGTH_SHORT).show();
                else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                    builder.setTitle("Xác nhận xóa");
                    builder.setMessage("Bạn thật sự muốn xóa nhóm hàng hóa này?")
                            .setPositiveButton("Có", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    DatabaseHandler db = new DatabaseHandler(view.getContext());
                                    try {
                                        db.xoaNhomHangHoa(ma);
                                        ds.remove(vitri);
                                        notifyDataSetChanged();
                                    } catch (Exception e) {
                                        Toast.makeText(view.getContext(), "Lỗi xóa nhóm hàng hóa", Toast.LENGTH_SHORT).show();
                                        e.printStackTrace();
                                    }
                                    Toast.makeText(view.getContext(), "Xóa nhóm hàng hóa thành công", Toast.LENGTH_SHORT).show();
                                }
                            }).setNegativeButton("Không", null).show();
                }

            }
        });

        btnSuaNhomHangHoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String maNhomHH = nhomhh.getMaNhomHang();
                String tenNhomHH = nhomhh.getTenNhomHang();
                suaThongTinNhomHangHoa(ds, i, view.getContext(), maNhomHH, tenNhomHH);
            }
        });

        return view;
    }

    private void suaThongTinNhomHangHoa(ArrayList<NhomHangHoa> ds, int position, Context context, String ma, String ten) {
        Dialog dialog = new Dialog(context);
        dialog.setTitle("Sửa thông tin nhóm hàng hóa");
        dialog.setContentView(R.layout.dialog_suathongtin_nhomhh);

        Button btnSua = dialog.findViewById(R.id.btnUpdateTTNhomHangHoa);

        EditText edtSuaMa = dialog.findViewById(R.id.edtSuaMaNhomHangHoa);
        EditText edtSuaTen = dialog.findViewById(R.id.edtSuaTenNhomHangHoa);

        edtSuaMa.setText(ma);
        edtSuaMa.setFocusable(false);
        edtSuaTen.setText(ten);



        btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseHandler db = new DatabaseHandler(dialog.getContext());
                String suama = edtSuaMa.getText().toString().trim();
                String suaten = edtSuaTen.getText().toString().trim();

                try {
                    db.suaThongTinNhomHangHoa(suama, suaten);
                    ds.set(position, new NhomHangHoa(suama, suaten));
                    notifyDataSetChanged();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Toast.makeText(context, "Sửa thông tin nhóm hàng hóa thành công", Toast.LENGTH_SHORT).show();
                edtSuaMa.setText("");
                edtSuaTen.setText("");
            }
        });

        dialog.show();
    }
}
