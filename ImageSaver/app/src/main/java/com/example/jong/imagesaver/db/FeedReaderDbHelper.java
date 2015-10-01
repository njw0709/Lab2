package com.example.jong.imagesaver.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by jong on 9/30/15.
 */
public class FeedReaderDbHelper extends SQLiteOpenHelper {
    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + Dbsetup.FeedEntry.TABLE_NAME + " (" +
                    Dbsetup.FeedEntry._ID + " INTEGER PRIMARY KEY," +
                    Dbsetup.FeedEntry.COLUMN_NAME_ENTRY_ID + TEXT_TYPE + COMMA_SEP +
                    Dbsetup.FeedEntry.COLUMN_NAME_TITLE + TEXT_TYPE+
            " )";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + Dbsetup.FeedEntry.TABLE_NAME;


    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "FeedReader.db";

    public FeedReaderDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("FeedReaderDbHelper","Query to form table: " + SQL_CREATE_ENTRIES);
        db.execSQL(SQL_CREATE_ENTRIES);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldv, int newv) {
        Log.d("FeedReaderDbHelper", "Query to delete table" + SQL_DELETE_ENTRIES);
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public boolean writeToDatabase(String imgurl, int position,SQLiteDatabase writabledb){
        ContentValues values = new ContentValues();
        values.put(Dbsetup.FeedEntry.COLUMN_NAME_ENTRY_ID, position);
        values.put(Dbsetup.FeedEntry.COLUMN_NAME_TITLE, imgurl);
        try{
            writabledb.insertOrThrow(Dbsetup.FeedEntry.TABLE_NAME, null, values);
            return true;
        }
        catch (Exception ex){
            Log.e("Database", "Write to Database Failed", ex);
            return false;
        }
    }
    public String Readfromdb(int position, SQLiteDatabase readabledb){
        String[] ColToQuery = {
                Dbsetup.FeedEntry.COLUMN_NAME_TITLE
        };
        String selection = Dbsetup.FeedEntry.COLUMN_NAME_ENTRY_ID+"= ? ";
        String [] pos = {
                Integer.toString(position)
        };
        Cursor c= readabledb.query(Dbsetup.FeedEntry.TABLE_NAME,ColToQuery,selection,pos,null,null,null);
        c.moveToFirst();
        return c.getString(0);
    }
    public void DeleteItemdb(int position, SQLiteDatabase readabledb){
        String selection = Dbsetup.FeedEntry.COLUMN_NAME_ENTRY_ID + "= ? ";
        String[] selectArgs = {
                String.valueOf(position)
        };
        readabledb.delete(Dbsetup.FeedEntry.TABLE_NAME, selection, selectArgs);
    }
    public int CountItemsdb(SQLiteDatabase readabledb){
        String countQuery = "SELECT  * FROM " +Dbsetup.FeedEntry.TABLE_NAME;
        Cursor c = readabledb.rawQuery(countQuery,null);
        int cnt = c.getCount();
        c.close();
        return cnt;
    }

}
