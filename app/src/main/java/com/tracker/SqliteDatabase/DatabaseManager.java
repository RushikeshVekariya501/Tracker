package com.tracker.SqliteDatabase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseManager extends SQLiteOpenHelper {

    private static com.tracker.SqliteDatabase.DatabaseManager sInstance = null;

    public static synchronized com.tracker.SqliteDatabase.DatabaseManager getInstance(Context context,
                                                                                      String name, SQLiteDatabase.CursorFactory factory, int version) {

        if (sInstance == null) {
            sInstance = new com.tracker.SqliteDatabase.DatabaseManager(context.getApplicationContext(),
                    name, factory, version);
        }
        return sInstance;
    }

    public DatabaseManager(Context context, String name, SQLiteDatabase.CursorFactory factory,
                           int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //CREATE DATABASE TABLES
        db.execSQL(tableGroupNames());
        db.execSQL(tablePartyNames());
        db.execSQL(tableCategoryNames());
        db.execSQL(tableBankNames());
        db.execSQL(tableTransaction());

    }



    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        throw new SQLiteException("Can't downgrade database from version " +
                oldVersion + " to " + newVersion);
        //super.onDowngrade(db, oldVersion, newVersion);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {


    }

    private String tableGroupNames() {

        return "CREATE TABLE IF NOT EXISTS " + DatabaseHelper.TABLE_GROUP_NAME
                + " ( " + DatabaseHelper.KEY_ID + " INTEGER PRIMARY KEY,"
                + DatabaseHelper.KEY_GROUP_ID + " Long, "
                + DatabaseHelper.KEY_GROUP_NAME + " text);";
    }


    private String tablePartyNames() {

        return "CREATE TABLE IF NOT EXISTS " + DatabaseHelper.TABLE_PARTY_NAME
                + " ( " + DatabaseHelper.KEY_ID + " INTEGER PRIMARY KEY,"
                + DatabaseHelper.KEY_PARTY_ID + " Long, "
                + DatabaseHelper.KEY_PARTY_NAME + " text);";
    }
    private String tableCategoryNames() {

        return "CREATE TABLE IF NOT EXISTS " + DatabaseHelper.TABLE_CATEGORY_NAME
                + " ( " + DatabaseHelper.KEY_ID + " INTEGER PRIMARY KEY,"
                + DatabaseHelper.KEY_CATEGOTY_ID + " Long, "
                + DatabaseHelper.KEY_CATEGOTY_NAME + " text);";
    }

    private String tableTransaction() {

        return "CREATE TABLE IF NOT EXISTS " + DatabaseHelper.TABLE_TRANSACTION
                + " ( " + DatabaseHelper.KEY_ID + " INTEGER PRIMARY KEY,"
                + DatabaseHelper.KEY_TRANSACTION_ID + " Long, "
                + DatabaseHelper.KEY_TRANSACTION_GROUP_ID + " Long, "
                + DatabaseHelper.KEY_TRANSACTION_NAME + " text, "
                + DatabaseHelper.KEY_TRANSACTION_TYPE_ID + " INTEGER, "
                + DatabaseHelper.KEY_TRANSACTION_TYPE_NAME + " text, "
                + DatabaseHelper.KEY_TRANSACTION_DATE_TIME + " Long, "
                + DatabaseHelper.KEY_TRANSACTION_PARTY_NAME + " text, "
                + DatabaseHelper.KEY_TRANSACTION_TITLE + " text, "
                + DatabaseHelper.KEY_TRANSACTION_AMOUNT + " REAL, "
                + DatabaseHelper.KEY_TRANSACTION_BANK_NAME + " text, "
                + DatabaseHelper.KEY_TRANSACTION_MONTH_YEAR_FOR_FILTER + " text, "
                + DatabaseHelper.KEY_TRANSACTION_IMAGE_URL + " text, "
                + DatabaseHelper.KEY_TRANSACTION_GROUP_NAME + " text, "
                + DatabaseHelper.KEY_TRANSACTION_CATEGORY_NAME + " text, "
                + DatabaseHelper.KEY_TRANSACTION_CATEGORY_DAY + " INTEGER, "
                + DatabaseHelper.KEY_TRANSACTION_CATEGORY_MONTH + " INTEGER, "
                + DatabaseHelper.KEY_TRANSACTION_CATEGORY_YEAR + " INTEGER);";
    }


    private String tableBankNames() {

        return "CREATE TABLE IF NOT EXISTS " + DatabaseHelper.TABLE_BANK_NAME
                + " ( " + DatabaseHelper.KEY_ID + " INTEGER PRIMARY KEY,"
                + DatabaseHelper.KEY_BANK_ID + " Long, "
                + DatabaseHelper.KEY_BANK_NAME + " text);";
    }

}

