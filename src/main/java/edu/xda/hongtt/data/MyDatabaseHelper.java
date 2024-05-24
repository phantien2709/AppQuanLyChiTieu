package edu.xda.hongtt.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import edu.xda.hongtt.model.Chat;
import edu.xda.hongtt.model.Chi;
import edu.xda.hongtt.model.LoaiChi;
import edu.xda.hongtt.model.LoaiThu;
import edu.xda.hongtt.model.ThanhVien;
import edu.xda.hongtt.model.Thu;

public class MyDatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 4;
    private static final String DATABASE_NAME = "QLCT";

    private static final String TABLE_THAM_SO = "THAMSO";
    private static final String COLUMN_MA_THAM_SO = "MaThamSo";
    private static final String COLUMN_GIA_TRI = "GiaTri";

    //Nguoi yeu thich

    private static final String TABLE_NGUOI_YEU_THICH = "YEUTHICH";
    private static final String  COLUMN_YT_TEN_TK = "TenTaiKhoan";
    private static final String COLUMN_YT_ID_SU_KIEN = "IdSuKien";
    private static final String COLUMN_YT_ID_YT = "Id_YT";
    private static final String CREATE_TABLE_YEU_THICH =  "CREATE TABLE " + TABLE_NGUOI_YEU_THICH + "(" +COLUMN_YT_ID_YT +" INTEGER PRIMARY KEY AUTOINCREMENT,"+ COLUMN_YT_TEN_TK + " TEXT," + COLUMN_YT_ID_SU_KIEN + " INTEGER" + ")";

    public void themYeuThich (String tenTaiKhoan, int idSuKien){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_YT_TEN_TK,tenTaiKhoan);
        values.put(COLUMN_YT_ID_SU_KIEN,idSuKien);
        db.insert(TABLE_NGUOI_YEU_THICH,null,values);
    }
    //Su kien

    private static final String TABLE_SU_KIEN = "SUKIEN";
    private static final String COLUMN_SK_ID = "IdSuKien";
    private static final String COLUMN_SK_NOI_DUNG = "NoiDungSuKien";
    private static final String COLUMN_SK_HINH_ANH = "HinhAnh";
    private static final String COLUMN_SK_TEN_TK = "TenTaikhoan";

    private static final String CREATE_TABLE_SUKIEN = "CREATE TABLE " + TABLE_SU_KIEN + "(" + COLUMN_SK_ID +" INTEGER PRIMARY KEY AUTOINCREMENT,"+ COLUMN_SK_NOI_DUNG + " TEXT," + COLUMN_SK_HINH_ANH + " TEXT," + COLUMN_SK_TEN_TK + " TEXT" + ")";

    public void SuKienMoi(String noiDung,String linkHinhAnh , String tenTaiKhoan){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_SK_NOI_DUNG,noiDung);
        values.put(COLUMN_SK_HINH_ANH,linkHinhAnh);
        values.put(COLUMN_SK_TEN_TK,tenTaiKhoan);
        db.insert(TABLE_SU_KIEN,null,values);
    }
    //Binh luan
    private static final String TABLE_BINH_LUAN = "BINHLUAN";
    private static final String COLUMN_BL_ID = "IdBinhLuan";
    private static final String COLUMN_BL_ND = "NoiDungBinhLuan";
    private static final String COLUMN_BL_ID_SU_KIEN = "Id_SK";
    private static final String COLUMN_BL_NBL = "TenTaiKhoan";
    private static final String CREATE_TABLER_BINHLUAN = "CREATE TABLE "+ TABLE_BINH_LUAN + "(" + COLUMN_BL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +COLUMN_BL_ND + " TEXT,"+COLUMN_BL_ID_SU_KIEN + " TEXT," + COLUMN_BL_NBL + " TEXT" + ")";

    public void themBinhLuan(int id_SK, String tenTaiKhoan, String noiDungBinhLuan){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_BL_ID_SU_KIEN,id_SK);
        values.put(COLUMN_BL_NBL,tenTaiKhoan);
        values.put(COLUMN_BL_ND, noiDungBinhLuan);
        db.insert(TABLE_BINH_LUAN,null,values);
    }

    // ẩn sự kiện

    private static final String TABLE_SK_HIDDEN_STATUS = "SU_KIEN_HIDDEN_STATUS";
    private static final String COLUMN_SK_HIDDEN_STATUS_ID = "id";
    private static final String COLUMN_SK_HIDDEN_STATUS_SUKIEN_ID = "idSuKien";
    private static final String COLUMN_SK_HIDDEN_STATUS_ACCOUNT_ID = "tenTaiKhoan";
    private static final String CREATE_TABLE_SK_HIDDEN_STATUS = "CREATE TABLE " + TABLE_SK_HIDDEN_STATUS + "(" +
            COLUMN_SK_HIDDEN_STATUS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            COLUMN_SK_HIDDEN_STATUS_SUKIEN_ID + " INTEGER," +
            COLUMN_SK_HIDDEN_STATUS_ACCOUNT_ID + " TEXT" +
            ")";
    public void anSuKien(String tenTaiKhoan, int idSuKien){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_SK_HIDDEN_STATUS_ACCOUNT_ID,tenTaiKhoan);
        values.put(COLUMN_SK_HIDDEN_STATUS_SUKIEN_ID,idSuKien);
        db.insert(TABLE_SK_HIDDEN_STATUS,null,values);
    }
    public boolean isSuKien(int idSuKien, String accountId) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_SK_HIDDEN_STATUS + " WHERE " +
                COLUMN_SK_HIDDEN_STATUS_SUKIEN_ID + " = ? AND " +
                COLUMN_SK_HIDDEN_STATUS_ACCOUNT_ID + " = ?", new String[]{String.valueOf(idSuKien), accountId});

        boolean isHidden = cursor.getCount() > 0;
        cursor.close();
        return isHidden;
    }

    //chat
    private static final String TABLE_CHAT = "CHAT";
    private static final String COLUMN_CHAT_ID = "idChat";
    private static final String COLUMN_CHAT_ND ="NoiDung";
    private static final String COLUMN_CHAT_IDUSER = "idUser";
    private static final String CREATE_TABLE_CHAT = "CREATE TABLE " + TABLE_CHAT + "(" + COLUMN_CHAT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_CHAT_ND + " TEXT," + COLUMN_CHAT_IDUSER + " TEXT"+")";

    public  void chatMoi(Chat chat){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_CHAT_ND,chat.getNoiDung());
        values.put(COLUMN_CHAT_IDUSER,chat.getIdTaiKhoan());
        db.insert(TABLE_CHAT,null,values);
    }
    //ẩn tin nhắc

    private static final String TABLE_CHAT_HIDDEN_STATUS = "CHAT_HIDDEN_STATUS";
    private static final String COLUMN_CHAT_HIDDEN_STATUS_ID = "id";
    private static final String COLUMN_CHAT_HIDDEN_STATUS_TINNHAN_ID = "idTinNhan";
    private static final String COLUMN_CHAT_HIDDEN_STATUS_ACCOUNT_ID = "tenTaiKhoan";
    private static final String CREATE_TABLE_CHAT_HIDDEN_STATUS = "CREATE TABLE " + TABLE_CHAT_HIDDEN_STATUS + "(" +
            COLUMN_CHAT_HIDDEN_STATUS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            COLUMN_CHAT_HIDDEN_STATUS_TINNHAN_ID + " INTEGER," +
            COLUMN_CHAT_HIDDEN_STATUS_ACCOUNT_ID + " TEXT" +
            ")";

    public void anTinNhan(String tenTaiKhoan, int idTinNhan){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_CHAT_HIDDEN_STATUS_ACCOUNT_ID,tenTaiKhoan);
        values.put(COLUMN_CHAT_HIDDEN_STATUS_TINNHAN_ID,idTinNhan);
        db.insert(TABLE_CHAT_HIDDEN_STATUS,null,values);
    }
    public boolean isTinNhanAn(int tinNhanId, String accountId) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_CHAT_HIDDEN_STATUS + " WHERE " +
                COLUMN_CHAT_HIDDEN_STATUS_TINNHAN_ID + " = ? AND " +
                COLUMN_CHAT_HIDDEN_STATUS_ACCOUNT_ID + " = ?", new String[]{String.valueOf(tinNhanId), accountId});

        boolean isHidden = cursor.getCount() > 0;
        cursor.close();
        return isHidden;
    }

    //table user
    private static final String TABLE_USER = "NguoiDung";
    private static final String COLUMN_USER_NAME = "TenDangNhap";
    private static final String COLUMN_USER_PASSWORD = "MatKhau";
    private static final String COLUMN_USER_VAI_VE = "VaiVe";
    private static final String COLUMN_USER_TRANG_THAI = "TrangThai";
    private static final String CREATE_CLASS_TABLE_USER = "CREATE TABLE " + TABLE_USER + "(" + COLUMN_USER_NAME + " TEXT," + COLUMN_USER_PASSWORD + " TEXT," +COLUMN_USER_VAI_VE + " TEXT,"  + COLUMN_USER_TRANG_THAI + " INTEGER" +")";

    public boolean insert(String username, String password,String vaiVe) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("TenDangNhap", username);
        contentValues.put("MatKhau", password);
        contentValues.put("VaiVe",vaiVe);
        contentValues.put("TrangThai",0);
        long ins = db.insert("NguoiDung", null, contentValues);
        if (ins == -1) return false;
        else return true;
    }

    public Boolean checkLogin(String username, String password) {
        boolean check = false;
        Cursor i = GetDate("SELECT * FROM " + TABLE_USER);
        if(i.getCount()==0){
            insert("tuan","1","ADMIN");
        }
        String countQuery = "SELECT * FROM " + TABLE_USER + " WHERE " + COLUMN_USER_NAME + " = ? AND " + COLUMN_USER_PASSWORD + " = ?";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, new String[]{username, password});
        if (cursor.getCount() > 0) {
            check = true;
        }
        return check;
    }
    public Boolean checkForgot(String username){
        boolean check = false;
        String countQuery = "SELECT * FROM " + TABLE_USER + " WHERE " + COLUMN_USER_NAME + " = ?  " ;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, new String[]{username});
        if (cursor.getCount() > 0) {
            check = true;
        }
        return check;
    }
    public Boolean checkUsername(String username) {
        boolean check = true;
        String countQuery = "SELECT * FROM " + TABLE_USER + " WHERE " + COLUMN_USER_NAME + " = ?";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, new String[]{username});
        if (cursor.getCount() > 0) {
            check = false;
        }
        return check;
    }

    // Thành viên
    private static final String KEY_NAME_TABLE_THANHVIEN = "thanhvien";
    private static final String KEY_TABLE_ID_THANHVIEN = "id";
    private static final String KEY_TABLE_NAME_THANHVIEN = "tenThanhVien";
    private static final String KEY_TABLE_DATE_NGAYSINH = "ngaySinh";
    private static final String KEY_TABLE_GENDER_GIOITINH = "gioiTinh";
    private static final String KEY_TABLE_ROLE_VAIVE = "vaiVe";
    private static final String KEY_TABLE_DELETEFLAG_THANHVIEN = "deleteFlag";
    private static final String CREATE_CLASS_TABLE_THANHVIEN = "CREATE TABLE " + KEY_NAME_TABLE_THANHVIEN + "(" + KEY_TABLE_ID_THANHVIEN + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_TABLE_NAME_THANHVIEN + " TEXT," + KEY_TABLE_DATE_NGAYSINH + " TEXT," + KEY_TABLE_GENDER_GIOITINH
            + " TEXT," + KEY_TABLE_ROLE_VAIVE + " TEXT,"+ KEY_TABLE_DELETEFLAG_THANHVIEN+ " INTEGER" + ")";
    public void addThanhVien(ThanhVien thanhVien)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_TABLE_NAME_THANHVIEN, thanhVien.getHoTen());
        values.put(KEY_TABLE_DATE_NGAYSINH, thanhVien.getNgaySinh());
        values.put(KEY_TABLE_GENDER_GIOITINH, thanhVien.getGioiTinh());
        values.put(KEY_TABLE_ROLE_VAIVE, thanhVien.getVaiVe());
        values.put(KEY_TABLE_DELETEFLAG_THANHVIEN, thanhVien.getXoaThanhVien());
        db.insert(KEY_NAME_TABLE_THANHVIEN,null,values);
        db.close();
    }


    // ---Table Khoan Thu---
    private static final String KEY_NAME_TABLE_LOAITHU = "loaithu";
    private static final String KEY_TABLE_ID_LOAITHU = "id";
    private static final String KEY_TABLE_NAME_LOAITHU = "tenLoaiThu";
    private static final String KEY_TABLE_DELETEFLAG_LOAITHU = "deleteGlag";

    // ---Table Thu---

    private static final String KEY_NAME_TABLE_THU = "thu";
    private static final String KEY_TABLE_ID_THU = "id";
    private static final String KEY_TABLE_TENMUCTHU_THU = "tenMucThu";
    private static final String KEY_TABLE_DINHMUCTHU_THU = "dinhMucThu";
    private static final String KEY_TABLE_DONVITHU_THU = "donViThu";
    private static final String KEY_TABLE_THOIDIEMAPDUNGTHU_THU = "thoiDiemApDungThu";
    private static final String KEY_TABLE_DANHGIA_THU = "danhGia";
    private static final String KEY_TABLE_DELETEFLAG_THU = "deleteFlag";
    private static final String KEY_TABLE_IDLOAITHU_THU = "idLoaiThu";

    // ---Table Loai Chi ---

    private static final String KEY_NAME_TABLE_LOAICHI = "loaiChi";
    private static final String KEY_TABLE_ID_LOAICHI = "id";
    private static final String KEY_TABLE_NAME_LOAICHI = "tenLoaiChi";
    private static final String KEY_TABLE_DELETEFLAG_LOAICHI = "deleteFlag";

    // ---Table Chi---

    private static final String KEY_NAME_TABLE_CHI = "chi";
    private static final String KEY_TABLE_ID_CHI = "id";
    private static final String KEY_TABLE_TENMUCCHI_CHI = "tenMucChi";
    private static final String KEY_TABLE_DINHMUCCHI_CHI = "dinhMucChi";
    private static final String KEY_TABLE_DONVICHI_CHI = "donViCHI";
    private static final String KEY_TABLE_THOIDIEMAPDUNGCHI_CHI = "thoiDiemApDungChi";
    private static final String KEY_TABLE_DANHGIA_CHI = "danhGia";
    private static final String KEY_TABLE_DELETEFLAG_CHI = "deleteFlag";
    private static final String KEY_TABLE_IDLOAICHI_CHI = "idLoaiChi";


    private static final String CREATE_CLASS_TABLE_LOAITHU = "CREATE TABLE " + KEY_NAME_TABLE_LOAITHU + "(" + KEY_TABLE_ID_LOAITHU + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_TABLE_NAME_LOAITHU + " TEXT," + KEY_TABLE_DELETEFLAG_LOAITHU + " INTEGER" + ")";

    private static final String CREATE_CLASS_TABLE_THU = "CREATE TABLE " + KEY_NAME_TABLE_THU + "(" + KEY_TABLE_ID_THU + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_TABLE_TENMUCTHU_THU + " TEXT," + KEY_TABLE_DINHMUCTHU_THU + " DECIMAL," + KEY_TABLE_DONVITHU_THU + " TEXT," + KEY_TABLE_THOIDIEMAPDUNGTHU_THU + " DATETIME," + KEY_TABLE_DANHGIA_THU + " INTEGER," + KEY_TABLE_DELETEFLAG_THU + " INTEGER," + KEY_TABLE_IDLOAITHU_THU + " INTEGER" + ")";

    private static final String CREATE_CLASS_TABLE_LOAICHI = "CREATE TABLE " + KEY_NAME_TABLE_LOAICHI + "(" + KEY_TABLE_ID_LOAICHI + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_TABLE_NAME_LOAICHI + " TEXT," + KEY_TABLE_DELETEFLAG_LOAICHI + " INTEGER" + ")";

    private static final String CREATE_CLASS_TABLE_CHI = "CREATE TABLE " + KEY_NAME_TABLE_CHI + "(" + KEY_TABLE_ID_CHI + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_TABLE_TENMUCCHI_CHI + " TEXT," + KEY_TABLE_DINHMUCCHI_CHI + " DECIMAL," + KEY_TABLE_DONVICHI_CHI + " TEXT," + KEY_TABLE_THOIDIEMAPDUNGCHI_CHI + " DATETIME," + KEY_TABLE_DANHGIA_CHI + " INTEGER," + KEY_TABLE_DELETEFLAG_CHI + " INTEGER," + KEY_TABLE_IDLOAICHI_CHI + " INTEGER" + ")";

    public MyDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void QueryData(String sql) {
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL(sql);
    }

    public Cursor GetDate(String sql) {
        SQLiteDatabase database = getReadableDatabase();
        return database.rawQuery(sql, null);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_CLASS_TABLE_USER);
        db.execSQL(CREATE_CLASS_TABLE_THU);
        db.execSQL(CREATE_CLASS_TABLE_LOAITHU);
        db.execSQL(CREATE_CLASS_TABLE_LOAICHI);
        db.execSQL(CREATE_CLASS_TABLE_CHI);
        db.execSQL(CREATE_TABLE_CHAT);
        db.execSQL(CREATE_TABLE_SUKIEN);
        db.execSQL(CREATE_TABLER_BINHLUAN);
        db.execSQL(CREATE_TABLE_YEU_THICH);
        db.execSQL(CREATE_TABLE_CHAT_HIDDEN_STATUS);
        db.execSQL(CREATE_TABLE_SK_HIDDEN_STATUS);
        db.execSQL(CREATE_CLASS_TABLE_THANHVIEN);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + KEY_NAME_TABLE_THU);
        db.execSQL("DROP TABLE IF EXISTS " + KEY_NAME_TABLE_LOAITHU);
        db.execSQL("DROP TABLE IF EXISTS " + KEY_NAME_TABLE_CHI);
        db.execSQL("DROP TABLE IF EXISTS " + KEY_NAME_TABLE_LOAICHI);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SU_KIEN);

        onCreate(db);
    }

    public void addLoaiThu(LoaiThu loaiThu) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_TABLE_NAME_LOAITHU, loaiThu.getTenLoaiThu());
        values.put(KEY_TABLE_DELETEFLAG_LOAITHU, loaiThu.getDeleteFlag());

        db.insert(KEY_NAME_TABLE_LOAITHU, null, values);
        db.close();
    }

    public void addThu(Thu thu) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_TABLE_TENMUCTHU_THU, thu.getTenMucThu());
        values.put(KEY_TABLE_DINHMUCTHU_THU, thu.getDinhMucThu());
        values.put(KEY_TABLE_DONVITHU_THU, thu.getDonViThu());
        values.put(KEY_TABLE_THOIDIEMAPDUNGTHU_THU, thu.getThoiDiemApDungThu());
        values.put(KEY_TABLE_DANHGIA_THU, thu.getDanhGia());
        values.put(KEY_TABLE_DELETEFLAG_THU, thu.getDeleteFlag());
        values.put(KEY_TABLE_IDLOAITHU_THU, thu.getIdLoaiThu());

        db.insert(KEY_NAME_TABLE_THU, null, values);
        db.close();
    }

    public void addLoaiChi(LoaiChi loaiChi) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_TABLE_NAME_LOAICHI, loaiChi.getTenLoaiChi());
        values.put(KEY_TABLE_DELETEFLAG_LOAICHI, loaiChi.getDeleteFlag());

        db.insert(KEY_NAME_TABLE_LOAICHI, null, values);
        db.close();
    }

    public void addChi(Chi chi) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_TABLE_TENMUCCHI_CHI, chi.getTenMucChi());
        values.put(KEY_TABLE_DINHMUCCHI_CHI, chi.getDinhMucChi());
        values.put(KEY_TABLE_DONVICHI_CHI, chi.getDonViChi());
        values.put(KEY_TABLE_THOIDIEMAPDUNGCHI_CHI, chi.getThoiDiemApDungChi());
        values.put(KEY_TABLE_DANHGIA_CHI, chi.getDanhGia());
        values.put(KEY_TABLE_DELETEFLAG_CHI, chi.getDeleteFlag());
        values.put(KEY_TABLE_IDLOAICHI_CHI, chi.getIdLoaiChi());
        db.insert(KEY_NAME_TABLE_CHI, null, values);
        db.close();
    }


}


