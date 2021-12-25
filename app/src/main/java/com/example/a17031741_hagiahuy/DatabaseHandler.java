package com.example.a17031741_hagiahuy;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler extends SQLiteOpenHelper {
    public DatabaseHandler(Context context) {
        super(context, "hagiahuy_qlkho.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create Table nhanvien(ma_nv TEXT primary key, ten_nv TEXT, dia_chi TEXT, " +
                "gioi_tinh TEXT, tai_khoan TEXT, mat_khau TEXT)");

        db.execSQL("create Table nhomhanghoa(ma_nhom_hang TEXT primary key, ten_nhom_hang TEXT)");

        db.execSQL("create Table hanghoa(ma_hang_hoa TEXT primary key, ten_hang TEXT, don_gia REAL, " +
                "ma_nhom_hang TEXT, FOREIGN KEY (ma_nhom_hang) REFERENCES nhomhanghoa(ma_nhom_hang))");

        db.execSQL("create Table kho(ma_kho TEXT primary key, ten_kho TEXT, dia_diem TEXT)");

        db.execSQL("create Table phieunhapkho(ma_phieu_nhap TEXT primary key, ma_hang_hoa TEXT, ma_nv TEXT, ma_kho TEXT, ngay_nhap TEXT, so_luong INTEGER, " +
                "FOREIGN KEY (ma_hang_hoa) REFERENCES hanghoa(ma_hang_hoa), " +
                "FOREIGN KEY (ma_nv) REFERENCES nhanvien(ma_nv), " +
                "FOREIGN KEY (ma_kho) REFERENCES kho(ma_kho))");

        db.execSQL("create Table phieuxuatkho(ma_phieu_xuat TEXT primary key, ma_hang_hoa TEXT, ma_nv TEXT, ma_kho TEXT, ngay_xuat TEXT, so_luong INTEGER, " +
                "FOREIGN KEY (ma_hang_hoa) REFERENCES hanghoa(ma_hang_hoa), " +
                "FOREIGN KEY (ma_nv) REFERENCES nhanvien(ma_nv), " +
                "FOREIGN KEY (ma_kho) REFERENCES kho(ma_kho))");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("drop Table if exists hanghoa");
        db.execSQL("drop Table if exists phieunhapkho");
        db.execSQL("drop Table if exists phieuxuatkho");
        db.execSQL("drop Table if exists nhanvien");
        db.execSQL("drop Table if exists nhomhanghoa");
        db.execSQL("drop Table if exists kho");

    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onDowngrade(db, oldVersion, newVersion);
    }

    public Boolean dangKyNhanVien(String ma_nv, String ten, String diachi, String gioitinh, String taikhoan, String matkhau) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("ma_nv", ma_nv);
        contentValues.put("ten_nv", ten);
        contentValues.put("dia_chi", diachi);
        contentValues.put("gioi_tinh", gioitinh);
        contentValues.put("tai_khoan", taikhoan);
        contentValues.put("mat_khau", matkhau);

        long result = DB.insert("nhanvien", null, contentValues);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public Cursor dangNhap(String taikhoan, String matkhau) {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("SELECT * from nhanvien where tai_khoan=? and mat_khau=?", new String[]{taikhoan, matkhau});
        return cursor;
    }


    public Boolean suaThongTinNhanVien(String ma, String ten, String diachi, String gioiTinh) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("ten_nv", ten);
        contentValues.put("dia_chi", diachi);
        contentValues.put("gioi_tinh", gioiTinh);
        Cursor cursor = DB.rawQuery("Select * from nhanvien where ma_nv = ?", new String[]{ma});
        if (cursor.getCount() > 0) {
            long result = DB.update("nhanvien", contentValues, "ma_nv=?", new String[]{ma});
            if (result == -1) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    public Boolean doiMatKhauNhanVien(String manv, String matKhauMoi) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("mat_khau", matKhauMoi);
        Cursor cursor = DB.rawQuery("Select * from nhanvien where ma_nv = ?", new String[]{manv});
        if (cursor.getCount() > 0) {
            long result = DB.update("nhanvien", contentValues, "ma_nv=?", new String[]{manv});
            if (result == -1) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    public Cursor layDSNhanVien() {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from nhanvien", null);
        return cursor;
    }

    public Cursor layDSNhanVienTheoMa(String ma) {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from nhanvien where ma_nv = ?", new String[]{ma});
        return cursor;
    }

    public Boolean xoaNhanVien(String ma) {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from nhanvien where ma_nv = ?", new String[]{ma});
        if (cursor.getCount() > 0) {
            long result = DB.delete("nhanvien", "ma_nv=?", new String[]{ma});
            if (result == -1) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    //Nhom hang hoa
    public Boolean themNhomHangHoa(String ma, String ten) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("ma_nhom_hang", ma);
        contentValues.put("ten_nhom_hang", ten);
        long result = DB.insert("nhomhanghoa", null, contentValues);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public Cursor layDSNhomHangHoa() {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from nhomhanghoa", null);
        return cursor;
    }

    public Boolean xoaNhomHangHoa(String ma) {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from nhomhanghoa where ma_nhom_hang = ?", new String[]{ma});
        if (cursor.getCount() > 0) {
            long result = DB.delete("nhomhanghoa", "ma_nhom_hang=?", new String[]{ma});
            if (result == -1) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    public Boolean suaThongTinNhomHangHoa(String ma, String ten) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("ten_nhom_hang", ten);
        Cursor cursor = DB.rawQuery("Select * from nhomhanghoa where ma_nhom_hang = ?", new String[]{ma});
        if (cursor.getCount() > 0) {
            long result = DB.update("nhomhanghoa", contentValues, "ma_nhom_hang=?", new String[]{ma});
            if (result == -1) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    //hang hoa
    public Boolean themHangHoa(String ma, String ten, double gia, String maNhomHangHoa) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("ma_hang_hoa", ma);
        contentValues.put("ten_hang", ten);
        contentValues.put("don_gia", gia);
        contentValues.put("ma_nhom_hang", maNhomHangHoa);
        long result = DB.insert("hanghoa", null, contentValues);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public Cursor layDSHangHoaTheoMa(String ma) {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from hanghoa where ma_hang_hoa = ?", new String[]{ma});
        return cursor;
    }

    public Cursor layDSHangHoa(String maNhomHangHoa) {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from hanghoa where ma_nhom_hang = ?", new String[]{maNhomHangHoa});
        return cursor;
    }

    public Cursor layAllDSHangHoa() {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from hanghoa", null);
        return cursor;
    }

    public Boolean xoaHangHoa(String ma) {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from hanghoa where ma_hang_hoa = ?", new String[]{ma});
        if (cursor.getCount() > 0) {
            long result = DB.delete("hanghoa", "ma_hang_hoa=?", new String[]{ma});
            if (result == -1) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    public Boolean suaThongTinHangHoa(String ma, String ten, double dongia) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("ten_hang", ten);
        contentValues.put("don_gia", dongia);
        Cursor cursor = DB.rawQuery("Select * from hanghoa where ma_hang_hoa = ?", new String[]{ma});
        if (cursor.getCount() > 0) {
            long result = DB.update("hanghoa", contentValues, "ma_hang_hoa=?", new String[]{ma});
            if (result == -1) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    //kho
    public Boolean themKho(String ma, String ten, String diadiem) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("ma_kho", ma);
        contentValues.put("ten_kho", ten);
        contentValues.put("dia_diem", diadiem);
        long result = DB.insert("kho", null, contentValues);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public Cursor layDSKho() {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from kho", null);
        return cursor;
    }

    public Cursor layDSKhoTheoMa(String ma) {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from kho where ma_kho = ?", new String[]{ma});
        return cursor;
    }

    public Boolean xoaKho(String ma) {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from kho where ma_kho = ?", new String[]{ma});
        if (cursor.getCount() > 0) {
            long result = DB.delete("kho", "ma_kho=?", new String[]{ma});
            if (result == -1) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    public Boolean suaThongTinKho(String ma, String ten, String diadiem) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("ten_kho", ten);
        contentValues.put("dia_diem", diadiem);
        Cursor cursor = DB.rawQuery("Select * from kho where ma_kho = ?", new String[]{ma});
        if (cursor.getCount() > 0) {
            long result = DB.update("kho", contentValues, "ma_kho=?", new String[]{ma});
            if (result == -1) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    //hoa don nhap kho
    public Boolean themPhieuNhapKho(String maphieunhap, String mahanghoa, String manv, String makho, String ngaynhap, int soluong) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("ma_phieu_nhap", maphieunhap);
        contentValues.put("ma_hang_hoa", mahanghoa);
        contentValues.put("ma_nv", manv);
        contentValues.put("ma_kho", makho);
        contentValues.put("ngay_nhap", ngaynhap);
        contentValues.put("so_luong", soluong);
        long result = DB.insert("phieunhapkho", null, contentValues);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public Cursor layDSPhieuNhapKho() {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from phieunhapkho", null);
        return cursor;
    }

    public Boolean xoaPhieuNhapKho(String ma) {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from phieunhapkho where ma_phieu_nhap = ?", new String[]{ma});
        if (cursor.getCount() > 0) {
            long result = DB.delete("phieunhapkho", "ma_phieu_nhap=?", new String[]{ma});
            if (result == -1) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    public Boolean suaThongTinPhieuNhapKho(String maphieunhap, String mahanghoa, String manv, String makho, String ngaynhap, int soluong) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("ma_nv", manv);
        contentValues.put("ma_hang_hoa", mahanghoa);
        contentValues.put("ma_kho", makho);
        contentValues.put("ngay_nhap", ngaynhap);
        contentValues.put("so_luong", soluong);
        Cursor cursor = DB.rawQuery("Select * from phieunhapkho where ma_phieu_nhap = ?", new String[]{maphieunhap});
        if (cursor.getCount() > 0) {
            long result = DB.update("kho", contentValues, "ma_kho=?", new String[]{maphieunhap});
            if (result == -1) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    //hoa don nhap kho
    public Boolean themPhieuXuatKho(String maphieuxuat, String mahanghoa, String manv, String makho, String ngaynhap, int soluong) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("ma_phieu_xuat", maphieuxuat);
        contentValues.put("ma_hang_hoa", mahanghoa);
        contentValues.put("ma_nv", manv);
        contentValues.put("ma_kho", makho);
        contentValues.put("ngay_xuat", ngaynhap);
        contentValues.put("so_luong", soluong);
        long result = DB.insert("phieuxuatkho", null, contentValues);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public Cursor layDSPhieuXuatKho() {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from phieuxuatkho", null);
        return cursor;
    }
}
