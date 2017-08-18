package com.atc.gosmartlesmagistra.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.atc.gosmartlesmagistra.model.CourseLevel;
import com.atc.gosmartlesmagistra.model.Order;
import com.atc.gosmartlesmagistra.model.Payment;
import com.atc.gosmartlesmagistra.model.TeacherTermCondition;
import com.atc.gosmartlesmagistra.model.User;
import com.atc.gosmartlesmagistra.model.response.CourseLevelSpinnerSuccess;
import com.atc.gosmartlesmagistra.model.response.LoginSuccess;
import com.atc.gosmartlesmagistra.model.response.OrderSuccess;
import com.atc.gosmartlesmagistra.model.response.PaymentBankSuccess;
import com.atc.gosmartlesmagistra.model.response.TeacherTermConditionSuccess;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hendrigunawan on 07/06/17.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    // database name
    private static final String DATABASE_NANE = "gosmartdb";

    // table name
    private static final String TABLE_USER = "user";
    private static final String TABLE_USER_RAW = "user_raw";
    private static final String TABLE_TERM_CONDITION = "termcondition";
    private static final String TABLE_COURSE_LEVEL = "course_level";
    private static final String TABLE_ORDER = "order_raw";
    private static final String TABLE_PAYMENT = "payment";

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

    private static final String CREATE_TABLE_PAYMENT = "CREATE TABLE "
            + TABLE_PAYMENT + "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_RAW + " BLOB, "
            + COLUMN_CREATED_AT + " datetime default current_timestamp)";

    private static final String CREATE_TABLE_TERM_CONDITION = "CREATE TABLE "
            + TABLE_TERM_CONDITION + "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_RAW + " BLOB, "
            + COLUMN_CREATED_AT + " datetime default current_timestamp)";

    private static final String CREATE_TABLE_ORDER = "CREATE TABLE "
            + TABLE_ORDER + "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_CODE + " TEXT, "
            + COLUMN_RAW + " BLOB, "
            + COLUMN_CREATED_AT + " datetime default current_timestamp)";

    private static final String CREATE_TABLE_COURSE_LEVEL = "CREATE TABLE "
            + TABLE_COURSE_LEVEL + "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_RAW + " BLOB, "
            + COLUMN_CREATED_AT + " datetime default current_timestamp)";

    private static final String CREATE_TABLE_USER_RAW = "CREATE TABLE "
            + TABLE_USER_RAW + "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_CODE + " TEXT, "
            + COLUMN_RAW + " BLOB, "
            + COLUMN_CREATED_AT + " datetime default current_timestamp)";

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
        db.execSQL(CREATE_TABLE_USER_RAW);
        db.execSQL(CREATE_TABLE_TERM_CONDITION);
        db.execSQL(CREATE_TABLE_COURSE_LEVEL);
        db.execSQL(CREATE_TABLE_ORDER);
        db.execSQL(CREATE_TABLE_PAYMENT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER_RAW);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TERM_CONDITION);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COURSE_LEVEL);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ORDER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PAYMENT);

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

    public void createTeacherTermCondition(TeacherTermConditionSuccess terms) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_TERM_CONDITION, null, null);

        ContentValues values = new ContentValues();
        values.put(COLUMN_RAW, new Gson().toJson(terms).getBytes());
        db.insert(TABLE_TERM_CONDITION, null, values);

        db.close();
    }

    public TeacherTermCondition getTeacherTermCondition() {
        TeacherTermCondition terms = new TeacherTermCondition();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        try {
            cursor = db.rawQuery("SELECT * FROM " + TABLE_TERM_CONDITION, null);
            if(cursor.getCount() > 0) {
                cursor.moveToFirst();
                Gson gson = new Gson();
                TeacherTermConditionSuccess term = gson.fromJson(new String(cursor.getBlob(1)), TeacherTermConditionSuccess.class);
                terms = new TeacherTermCondition(term.getTeacherTermCondition().getDescription());
            }
            return terms;
        }finally {
            cursor.close();
        }
    }

    public void createUser(LoginSuccess loginSuccess) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_USER_RAW, null, null);

        ContentValues values = new ContentValues();
        values.put(COLUMN_CODE, loginSuccess.getUser().getUniqueNumber());
        values.put(COLUMN_RAW, new Gson().toJson(loginSuccess).getBytes());
        db.insert(TABLE_USER_RAW, null, values);

        db.close();
    }

    public User getUser(String memberCode) {
        User user = new User();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        try {
            cursor = db.rawQuery("SELECT * FROM " + TABLE_USER_RAW + " WHERE " + COLUMN_CODE + "=?", new String[] {memberCode});
            if(cursor.getCount() > 0) {
                cursor.moveToFirst();
                Gson gson = new Gson();
                LoginSuccess loginSuccess = gson.fromJson(new String(cursor.getBlob(2)), LoginSuccess.class);
                return loginSuccess.getUser();
            }
            return user;
        }finally {
            cursor.close();
        }
    }

    public void createCourseLevel(CourseLevelSpinnerSuccess terms) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_COURSE_LEVEL, null, null);

        ContentValues values = new ContentValues();
        values.put(COLUMN_RAW, new Gson().toJson(terms).getBytes());
        db.insert(TABLE_COURSE_LEVEL, null, values);

        db.close();
    }

    public List<CourseLevel> getCourseLevels() {
        List<CourseLevel> courseLevels = new ArrayList();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        try {
            cursor = db.rawQuery("SELECT * FROM " + TABLE_COURSE_LEVEL, null);
            if(cursor.getCount() > 0) {
                cursor.moveToFirst();
                Gson gson = new Gson();
                CourseLevelSpinnerSuccess term = gson.fromJson(new String(cursor.getBlob(1)), CourseLevelSpinnerSuccess.class);
                courseLevels.addAll(term.getCourseLevels());
            }
            return courseLevels;
        }finally {
            cursor.close();
        }
    }

    public void createOrder(String uniqueNumber, OrderSuccess orderSuccess) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_ORDER, null, null);

        ContentValues values = new ContentValues();
        values.put(COLUMN_CODE, uniqueNumber);
        values.put(COLUMN_RAW, new Gson().toJson(orderSuccess).getBytes());
        db.insert(TABLE_ORDER, null, values);

        db.close();
    }

    public Order getOrder(String uniqueNumber) {
        Order order = new Order();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        try {
            cursor = db.rawQuery("SELECT * FROM " + TABLE_ORDER + " WHERE " + COLUMN_CODE + "=?", new String[] {uniqueNumber});
            if(cursor.getCount() > 0) {
                cursor.moveToFirst();
                Gson gson = new Gson();
                OrderSuccess orderSuccess = gson.fromJson(new String(cursor.getBlob(2)), OrderSuccess.class);
                return orderSuccess.getOrder();
            }
            return order;
        }finally {
            cursor.close();
        }
    }

    public void createPayment(PaymentBankSuccess paymentBankSuccess) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PAYMENT, null, null);

        ContentValues values = new ContentValues();
        values.put(COLUMN_RAW, new Gson().toJson(paymentBankSuccess).getBytes());
        db.insert(TABLE_PAYMENT, null, values);

        db.close();
    }

    public List<Payment> getPayments() {
        List<Payment> payments = new ArrayList();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        try {
            cursor = db.rawQuery("SELECT * FROM " + TABLE_PAYMENT, null);
            if(cursor.getCount() > 0) {
                cursor.moveToFirst();
                Gson gson = new Gson();
                PaymentBankSuccess term = gson.fromJson(new String(cursor.getBlob(1)), PaymentBankSuccess.class);
                payments.addAll(term.getPayments());
            }
            return payments;
        }finally {
            cursor.close();
        }
    }
}
