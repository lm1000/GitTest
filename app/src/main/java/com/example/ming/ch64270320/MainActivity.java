package com.example.ming.ch64270320;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button createDBButton;
    private Button addDBButton;
    private Button updateDB;
    private Button deleteDB;
    private Button queryDB;



    private SQLiteOpenHelper MyDBHandler;


    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.i(TAG + " MainActivity "," onCreate->  ");

        createDBButton = (Button) findViewById(R.id.buttonCreateDB);
		
		Log.i(TAG + " MainActivity "," onCreate-> for Git ");
        createDBButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Log.i(TAG + " setOnClickListener "," onClick-> create DB ");

                createDB();
				
				Log.i(TAG + " MainActivity "," onCreate-> for Git debug 1.1 ");
            }
        });
        addDBButton = (Button) findViewById(R.id.buttonAddData);
        addDBButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Log.i(TAG + " setOnClickListener "," onClick-> add DB ");

                addData();

                Log.i(TAG + " setOnClickListener "," after add DB ");
            }
        });
        updateDB = (Button) findViewById(R.id.buttonUpdate);
        updateDB.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Log.i(TAG + " setOnClickListener "," onClick-> update ");

                updateDB();

                Log.i(TAG + " setOnClickListener "," after update ");
            }
        });

        deleteDB = (Button) findViewById(R.id.buttonDelete);
        deleteDB.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Log.i(TAG + " setOnClickListener "," onClick-> delete ");

                deleteData();

                Log.i(TAG + " setOnClickListener "," after delete ");
            }
        });

        queryDB = (Button) findViewById(R.id.buttonQuery);
        queryDB.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Log.i(TAG + " setOnClickListener "," onClick-> query ");

                queryData();

                Log.i(TAG + " setOnClickListener "," after query ");
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        closeDB();


    }

    void closeDB(){
        if (MyDBHandler != null){
            MyDBHandler.close();
            Log.i(TAG + " -closeDB", "DB is closed ");
        }

    };

    void createDB(){
        Log.i(TAG + "createDB", " Start ");
        MyDBHandler = new MyDBHandler(MainActivity.this);

        MyDBHandler.getWritableDatabase();
        Log.i(TAG + "createDB", "End ");
    };

    void addData(){
        Log.i(TAG + "-addData", " Start ");
        SQLiteDatabase sqLiteDatabase;
        if(MyDBHandler == null)
        {
            MyDBHandler = new MyDBHandler(MainActivity.this);
        }

        sqLiteDatabase  = MyDBHandler.getWritableDatabase();

        for(int i =0;i<10;i++){

            ContentValues values = new ContentValues();

            values.put("name",String.valueOf(i));
            values.put("phone_number",i + "1234567");
            if(sqLiteDatabase.insert("contacts",null,values) == -1) {
                Log.e(TAG + "-addData", " insert error!! at " +  String.valueOf(i) );
                return;
            };

            }

        Log.i(TAG + "-addData", " End ");

        };





    void updateDB(){
        Log.i(TAG + "updateDB", " Start ");

        SQLiteDatabase sqLiteDatabase;
        if(MyDBHandler == null)
        {
            MyDBHandler = new MyDBHandler(MainActivity.this);
        }

        SQLiteDatabase db = MyDBHandler.getReadableDatabase();

// New value for one column
        ContentValues values = new ContentValues();

        values.put("phone_number","9999999");

// Which row to update, based on the title
        String selection = "name" + " LIKE ?";
        String[] selectionArgs = { "4" };

        int count = db.update(
                "contacts",
                values,
                selection,
                selectionArgs);

        Log.i(TAG + "updateDB", count + " items is updated ");

    };



    void deleteData(){
        Log.i(TAG + "deleteData", " Start ");

        SQLiteDatabase sqLiteDatabase;
        if(MyDBHandler == null)
        {
            MyDBHandler = new MyDBHandler(MainActivity.this);
        }

        SQLiteDatabase db = MyDBHandler.getReadableDatabase();

        String selection = "name" + " LIKE ?";
// Specify arguments in placeholder order.
        String[] selectionArgs = { "3" };
// Issue SQL statement.
        db.delete("contacts", selection, selectionArgs);

        Log.i(TAG + "deleteData", " End ");
    };

    void queryData(){

        Log.i(TAG + "queryData", " Start ");
        SQLiteDatabase sqLiteDatabase;
        if(MyDBHandler == null)
        {
            MyDBHandler = new MyDBHandler(MainActivity.this);
        }

        SQLiteDatabase db = MyDBHandler.getReadableDatabase();

        String[] projection = {
                "id",
                "name",
                "phone_number"
        };
        Cursor cursor = db.query(
                "contacts",                     // The table to query
                projection,                               // The columns to return
                null,                                // The columns for the WHERE clause
                null,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null                                 // The sort order
        );
//"CREATE TABLE " + "contacts" + "(" + "id" + " integer primary key autoincrement, " + "name" + " TEXT," + "phone_number" + " TEXT" + ")";
        while(cursor.moveToNext()) {
            int userId = cursor.getInt(cursor.getColumnIndex("id"));
            String userName = cursor.getString(cursor.getColumnIndex("name"));
            String userPhone = cursor.getString(cursor.getColumnIndex("phone_number"));

            Log.i(" id is " +userId, " Name is" + userName + " Phone is " + userPhone);


        }
        cursor.close();


        Log.i(TAG + "queryData", " End ");


    };


}
