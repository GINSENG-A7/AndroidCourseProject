package com.example.androidcourseproject.kal;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "CourseProjectDB";

    public static final String TABLE_CLIENT = "client";
    public static final String CLIENT_KEY_ID = "client_id";
    public static final String CLIENT_KEY_PASSPORT_SERIES = "passport_series";
    public static final String CLIENT_KEY_PASSPORT_NUMBER = "passport_number";
    public static final String CLIENT_KEY_NAME = "name";
    public static final String CLIENT_KEY_SURNAME = "surname";
    public static final String CLIENT_KEY_PATRONYMIC = "patronymic";
    public static final String CLIENT_KEY_BIRTHDAY = "birthday";
    public static final String CLIENT_KEY_TELEPHONE = "tel_number";

    public static final String TABLE_LIVING = "living";
    public static final String LIVING_KEY_ID = "living_id";
    public static final String LIVING_KEY_SETTLING = "settling";
    public static final String LIVING_KEY_EVICTION = "eviction";
    public static final String LIVING_KEY_VOG = "value_of_guests";
    public static final String LIVING_KEY_VOK = "value_of_kids";
    public static final String LIVING_KEY_APARTMENT_ID = "apartment_id";
    public static final String LIVING_KEY_CLIENT_ID = "client_id";
    public static final String LIVING_KEY_AS = "as_id";

    public DbHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Log.d("zxc", "Вызов конструктора DbHelper");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("zxc", "Вызов метода onCreate класса DbHelper");
        db.execSQL("create table "
                + TABLE_CLIENT
                + "("
                + CLIENT_KEY_ID + " integer primary key,"
                + CLIENT_KEY_PASSPORT_SERIES + " text,"
                + CLIENT_KEY_PASSPORT_NUMBER + " text,"
                + CLIENT_KEY_NAME + " text,"
                + CLIENT_KEY_SURNAME + " text,"
                + CLIENT_KEY_PATRONYMIC + " text,"
                + CLIENT_KEY_TELEPHONE + " text,"
                + CLIENT_KEY_BIRTHDAY + " text"
                + ")"
                + "");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d("zxc", "Вызов метода onUpgrade");
        db.execSQL("drop table if exists " + TABLE_CLIENT);
        onCreate(db);
    }

    public static Cursor readAll(SQLiteDatabase db){
        if (db != null)
            return db.query(DbHelper.TABLE_CLIENT, null, null, null, null, null, null);
        return null;
    }

//    public static void initForTest(SQLiteDatabase db) {
//        ContentValues contentValues = new ContentValues();
//
//        contentValues.put(DbHelper.KEY_NAME, "Михаил");
//        contentValues.put(DbHelper.KEY_MAIL, "misha@mail.ru");
//        db.insert(DbHelper.TABLE_CONTACTS, null, contentValues);
//
//        contentValues.put(DbHelper.KEY_NAME, "Региниа");
//        contentValues.put(DbHelper.KEY_MAIL, "regina@mail.ru");
//        db.insert(DbHelper.TABLE_CONTACTS, null, contentValues);
//
//        contentValues.put(DbHelper.KEY_NAME, "Сергей");
//        contentValues.put(DbHelper.KEY_MAIL, "sergey@mail.ru");
//        db.insert(DbHelper.TABLE_CONTACTS, null, contentValues);
//    }
}
