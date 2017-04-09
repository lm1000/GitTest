package com.example.ming.ch64270320;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by ming on 2017/3/20.
 */

class MyDBHandler extends SQLiteOpenHelper {

    private static final String TAG = "MyDBHandler";

    private static final int DATABASE_VERSION = 1;

    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_PH_NO = "phone_number";

    private static final String MYDATABASE_NAME = "commments.db";
    private static final int DB_VERSION = 1;

    // Database creation sql statement
    private static final String CREATE_CONTACTS_TABLE = "CREATE TABLE " + "contacts" + "(" + "id" + " integer primary key autoincrement, " + "name" + " TEXT," + "phone_number" + " TEXT" + ")";

    public MyDBHandler(Context context) {
        super(context, MYDATABASE_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_CONTACTS_TABLE);

        Log.i(TAG + "onCreate", "->start");
    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        Log.i(TAG + "onUpgrade", "->start");
        db.execSQL("DROP TABLE IF EXISTS " + " contacts ");

        // Create tables again
        onCreate(db);

    }


}
