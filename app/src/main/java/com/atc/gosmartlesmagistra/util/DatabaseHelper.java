package com.atc.gosmartlesmagistra.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.atc.gosmartlesmagistra.model.User;
import com.google.gson.Gson;

/**
 * Created by hendrigunawan on 07/06/17.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 2;

    // database name
    private static final String DATABASE_NANE = "gosmartdb";

    // table name
    private static final String TABLE_USER = "user";

    // column name
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_CODE = "code";
    private static final String COLUMN_RAW = "raw";
    private static final String COLUMN_CREATED_AT = "created_at";

    private static final String COLUMN_FIRST_NAME = "first_name";
    private static final String COLUMN_LAST_NAME = "last_name";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_LATITUDE = "latitude";
    private static final String COLUMN_LONGITUDE = "longitude";
    private static final String COLUMN_PHOTO = "photo";
    private static final String COLUMN_FIREBASE_TOKEN = "firebase_token";
    private static final String COLUMN_PHONE = "phone_number";
    private static final String COLUMN_STATUS = "status";
    private static final String COLUMN_ROLE = "role";
    private static final String COLUMN_LAST_LOGIN_AT = "last_login_at";

    private static final String CREATE_TABLE_USER = "CREATE TABLE "
            + TABLE_USER + "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_CODE + " TEXT, "
            + COLUMN_FIRST_NAME + " TEXT, "
            + COLUMN_LAST_NAME + " TEXT, "
            + COLUMN_EMAIL + " TEXT, "
            + COLUMN_LATITUDE + " TEXT, "
            + COLUMN_LONGITUDE + " TEXT, "
            + COLUMN_PHONE + " TEXT, "
            + COLUMN_PHOTO + " TEXT, "
            + COLUMN_FIREBASE_TOKEN + " TEXT, "
            + COLUMN_STATUS + " TEXT, "
            + COLUMN_ROLE + " TEXT, "
            + COLUMN_LAST_LOGIN_AT + " TEXT)";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NANE, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);

        onCreate(db);
    }

    public void createUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_USER, null, null);

        ContentValues values = new ContentValues();
        values.put(COLUMN_CODE, user.getUniqueNumber());
        values.put(COLUMN_FIRST_NAME, user.getFirstName());
        values.put(COLUMN_LAST_NAME, user.getLastName());
        values.put(COLUMN_EMAIL, user.getEmail());
        values.put(COLUMN_LATITUDE, user.getLatitude());
        values.put(COLUMN_LONGITUDE, user.getLongitude());
        values.put(COLUMN_PHONE, user.getPhoneNumber());
        if (user.getPhoto() != null) {
            values.put(COLUMN_PHOTO, user.getPhoto().toString());
        } else {
            values.put(COLUMN_PHOTO, "");
        }
        values.put(COLUMN_FIREBASE_TOKEN, user.getFirebaseToken());

        values.put(COLUMN_PHONE, user.getPhoneNumber());
        values.put(COLUMN_STATUS, user.getStatus().toString());
        values.put(COLUMN_ROLE, user.getRole().toString());
        values.put(COLUMN_LAST_LOGIN_AT, user.getLastLoginAt());
        db.insert(TABLE_USER, null, values);
        db.close();
    }

    public User getUserByUniqueNumber(String memberCode) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        User user = new User();
        try {
            cursor = db.rawQuery("SELECT * FROM " + TABLE_USER + " WHERE " + COLUMN_CODE + "=?", new String[] {memberCode});
            if(cursor.getCount() > 0) {
                cursor.moveToFirst();
                user.setUniqueNumber(cursor.getString(cursor.getColumnIndex(COLUMN_CODE)));
                user.setFirstName(cursor.getString(cursor.getColumnIndex(COLUMN_FIRST_NAME)));
                user.setLastName(cursor.getString(cursor.getColumnIndex(COLUMN_LAST_NAME)));
                user.setEmail(cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL)));
                user.setPhoto(cursor.getString(cursor.getColumnIndex(COLUMN_PHOTO)));
                user.setFirebaseToken(cursor.getString(cursor.getColumnIndex(COLUMN_FIREBASE_TOKEN)));
                user.setLatitude(cursor.getDouble(cursor.getColumnIndex(COLUMN_LATITUDE)));
                user.setLongitude(cursor.getDouble(cursor.getColumnIndex(COLUMN_LATITUDE)));
                user.setPhoneNumber(cursor.getString(cursor.getColumnIndex(COLUMN_PHONE)));
                user.setStatus(cursor.getInt(cursor.getColumnIndex(COLUMN_STATUS)));
                user.setRole(cursor.getInt(cursor.getColumnIndex(COLUMN_ROLE)));
                user.setLastLoginAt(cursor.getString(cursor.getColumnIndex(COLUMN_LAST_LOGIN_AT)));
            }
            return user;
        }finally {
            cursor.close();
        }
    }

    public void deleteUser() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_USER, null, null);
        db.close();
    }
}
