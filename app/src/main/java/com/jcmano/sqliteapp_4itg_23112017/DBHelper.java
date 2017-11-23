package com.jcmano.sqliteapp_4itg_23112017;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by JCMANO on 23/11/2017.
 */

public class DBHelper extends SQLiteOpenHelper{

    Context context;

    public DBHelper(Context context) {

        super(context, "grades.sqlite", null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL("CREATE TABLE student_grade (_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "FNAME TEXT, LNAME TEXT, GRADE TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS student_grade");
        onCreate(sqLiteDatabase);

    }

    public boolean insertData(String fname, String lname, String grade){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("fname", fname);
        cv.put("lname", lname);
        cv.put("grade", grade);
        long result = db.insert("student_grade", null, cv);

        if(result == -1) {
            return false;
        }else {
            return true;
        }
    }

    public boolean updateData(String id, String fname, String lname, String grade){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("_id", id);
        cv.put("fname", fname);
        cv.put("lname", lname);
        cv.put("grade", grade);
        db.update("student_grade", cv, "_id = ?", new String[]{ id });

        return true;
    }

    public Integer deleteData(String id){
        SQLiteDatabase db = this.getWritableDatabase();

        return db.delete("student_grade", "_id = ?", new String[]{ id });
    }

    public Cursor getAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("SELECT * FROM student_grade", null);
    }

}
