package com.example.ming.ch64270320;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

public class MyContentProvider extends ContentProvider {



    static final String PROVIDER_NAME = "com.example.ming.test.cp";
    static final String URL = "content://" + PROVIDER_NAME + "/contacts";
    static final Uri CONTENT_URI = Uri.parse(URL);


    static final String userID = "id";
    static final String userNAME = "name";
    static final String userNumber = "phone_number";


    static final String TABLE_NAME = "contacts";

    static final int CONTACTS = 1;
    static final int CONTACTS_ID = 2;

    static final UriMatcher uriMatcher;
    static{
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(PROVIDER_NAME, "contacts", CONTACTS);
        uriMatcher.addURI(PROVIDER_NAME, "contacts/#", CONTACTS_ID);
    }

    private static final String TAG = "MyContentProvider";

    private MyDBHandler dbHandler;

    public MyContentProvider() {
    }

    @Override
    public boolean onCreate() {
        Log.i(TAG + "onCreate", " Start ");
        dbHandler = new MyDBHandler(getContext());

        dbHandler.getWritableDatabase();
        Log.i(TAG + "onCreate", "End ");

        return true;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        Log.i(TAG + "delete", " Start ");
        int count = 0;

        SQLiteDatabase sqLiteDatabase;
        if(dbHandler == null)
        {
            dbHandler = new MyDBHandler(getContext());
        }

        sqLiteDatabase  = dbHandler.getWritableDatabase();

        switch (uriMatcher.match(uri)){
            case CONTACTS:
                count = sqLiteDatabase.delete(TABLE_NAME, selection, selectionArgs);
                break;

            case CONTACTS_ID:
                String id = uri.getPathSegments().get(1);
                count = sqLiteDatabase.delete( TABLE_NAME, userID +  " = " + id + (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : ""), selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        Log.i(TAG + "delete",String.valueOf(count) +  " is deleted ");
        return count;

    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // TODO: Implement this to handle requests to insert a new row.
        throw new UnsupportedOperationException("Not yet implemented");
    }



    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        // TODO: Implement this to handle query requests from clients.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
