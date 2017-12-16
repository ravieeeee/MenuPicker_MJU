package db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public final class DBHelper extends SQLiteOpenHelper {
    // DBHelper로 관리할 DB 이름과 버전 정보를 받음
    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    // DB를 새로 생성할 때 호출되는 함수
    @Override
    public void onCreate(SQLiteDatabase db) {
        // 새로운 테이블 생성
        db.execSQL("CREATE TABLE MENUBOOK (_id INTEGER PRIMARY KEY AUTOINCREMENT, campus INTEGER, storeName TEXT, menu TEXT);");
    }

    // DB 업그레이드를 위해 버전이 변경될 때 호출되는 함수
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void insert(int campus, String storeName, String menu) {
        // 읽고 쓰기가 가능하게 DB 열기
        SQLiteDatabase db = getWritableDatabase();
        // 넘어온 parameter를 값으로, DB에 tuple 추가
        db.execSQL("INSERT INTO MENUBOOK VALUES ( null, " + campus + ", '" + storeName + "', '" + menu  + "');");
        db.close();
    }

    public Cursor getCursor() {
        // 읽기가 가능하게 DB 열기
        SQLiteDatabase db = getReadableDatabase();

        // DB에 있는 데이터를 쉽게 처리하기 위해 Cursor를 사용하여 테이블에 있는 모든 데이터 출력
        Cursor cursor = db.rawQuery("SELECT * FROM MENUBOOK ORDER BY storeName;", null);
        return cursor;
    }

    public void deleteAll() {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM MENUBOOK;");
        db.close();
    }

    public void deleteSelectedItem(long id) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM MENUBOOK WHERE _id = " + id + ";");
        db.close();
    }

    public String getRandom() {
        SQLiteDatabase db = getReadableDatabase();
        String result = "";

        Cursor cursor = db.rawQuery("SELECT storeName, menu FROM MENUBOOK ORDER BY RANDOM() LIMIT 1", null);
        while (cursor.moveToNext()) {
            result += cursor.getString(0) + "의 " + cursor.getString(1);
        }
        return result;
    }

    public String getSelectedItem(long id) {
        SQLiteDatabase db = getReadableDatabase();
        String result = "";

        Cursor cursor = db.rawQuery("SELECT storeName, menu FROM MENUBOOK WHERE _id = " + id + ";" , null);
        while (cursor.moveToNext()) {
            result += cursor.getString(0) + "의 " + cursor.getString(1);
        }

        return result;
    }
}
