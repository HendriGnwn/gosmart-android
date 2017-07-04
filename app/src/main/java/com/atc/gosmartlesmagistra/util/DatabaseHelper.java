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

    private static final int DATABASE_VERSION = 1;

    // database name
    private static final String DATABASE_NANE = "gosmartdb";

    // table name
    private static final String TABLE_USER = "user";

    // column name
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_CODE = "code";
    private static final String COLUMN_RAW = "raw";
    private static final String COLUMN_CREATED_AT = "created_at";

    private static final String CREATE_TABLE_USER = "CREATE TABLE "
            + TABLE_USER + "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_CODE + " TEXT, "
            + COLUMN_RAW + " BLOB, "
            + COLUMN_CREATED_AT + " datetime default current_timestamp)";

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

        db.delete(TABLE_USER, COLUMN_CODE + " = ?",
                new String[] { String.valueOf(user.getCode()) });

        ContentValues values = new ContentValues();
        values.put(COLUMN_CODE, user.getCode());
        values.put(COLUMN_RAW, new Gson().toJson(user).getBytes());
        db.insert(TABLE_USER, null, values);
        db.close();
    }

    public User getUserByCode(String memberCode) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        User user = new User();
        try {
            cursor = db.rawQuery("SELECT * FROM " + TABLE_USER + " WHERE " + COLUMN_CODE + "=?", new String[] {memberCode});
            if(cursor.getCount() > 0) {
                cursor.moveToFirst();
                Gson gson = new Gson();
                user = gson.fromJson(new String(cursor.getBlob(2)), User.class);
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
