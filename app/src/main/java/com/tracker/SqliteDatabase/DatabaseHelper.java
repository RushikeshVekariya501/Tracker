package com.tracker.SqliteDatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import com.tracker.ModelClasses.BanksNamesModel;
import com.tracker.ModelClasses.CategoryNamesModel;
import com.tracker.ModelClasses.GroupNames;
import com.tracker.ModelClasses.PartyNamesModel;
import com.tracker.ModelClasses.TransactionModel;

import java.util.ArrayList;
import java.util.Calendar;

public class DatabaseHelper {

    //DATABASE NAME
    //public static final String DATABASE_NAME = "CSUtracker.db";
    public static final String DATABASE_NAME = "Rtracker.db";
    //public static final String DATABASE_NAME = "Dtracker.db";
    public static final int DATABASE_VERSION = 1;

    // Table Names
    public static String TABLE_GROUP_NAME = "tableGroupName";
    public static String TABLE_PARTY_NAME = "tablePartyName";
    public static String TABLE_CATEGORY_NAME = "tableCategoryName";
    public static String TABLE_BANK_NAME = "tableBankName";
    public static String TABLE_TRANSACTION = "tableTransaction";

    //TRACKER FIELD
    public static String KEY_ID = "keyID";
    public static String KEY_GROUP_NAME = "keyGroupName";
    public static String KEY_GROUP_ID = "keyGroupId";

    //PARTY NAMES FIELD
    public static String KEY_PARTY_NAME = "keypPartyName";
    public static String KEY_PARTY_ID = "keyPartyId";

    //CATEGOTY NAMES FIELD
    public static String KEY_CATEGOTY_NAME = "keyCategoryName";
    public static String KEY_CATEGOTY_ID = "keyCategoryId";

    //BANKS NAMES FIELD
    public static String KEY_BANK_NAME = "keyBankName";
    public static String KEY_BANK_ID = "keyBankId";




    //TRANSACTION FIELD
    public static String KEY_TRANSACTION_ID = "keyTransactionID";
    public static String KEY_TRANSACTION_GROUP_ID = "keyTransactionGroupID";
    public static String KEY_TRANSACTION_NAME = "keyTransactionName";
    public static String KEY_TRANSACTION_TYPE_ID = "keyTransactionTypeId";
    public static String KEY_TRANSACTION_TYPE_NAME = "keyTransactionTypeName";
    public static String KEY_TRANSACTION_DATE_TIME = "keyTransactionDate";
    public static String KEY_TRANSACTION_PARTY_NAME = "keyTransactionPartyName";
    public static String KEY_TRANSACTION_TITLE = "keyBankTransactionTitle";
    public static String KEY_TRANSACTION_AMOUNT = "keyBankTransactionAmount";
    public static String KEY_TRANSACTION_BANK_NAME = "keyTransactionBankName";
    public static String KEY_TRANSACTION_MONTH_YEAR_FOR_FILTER = "keyTransactionYearAndMonth";
    public static String KEY_TRANSACTION_IMAGE_URL = "keyTransactionImageURL";
    public static String KEY_TRANSACTION_GROUP_NAME = "keyTransactionGroupName";
    public static String KEY_TRANSACTION_CATEGORY_NAME = "keyTransactionCategoryName";
    public static String KEY_TRANSACTION_CATEGORY_DAY = "keyTransactionCategoryDay";
    public static String KEY_TRANSACTION_CATEGORY_MONTH = "keyTransactionCategoryMonth";
    public static String KEY_TRANSACTION_CATEGORY_YEAR = "keyTransactionCategoryYear";


    DatabaseManager dbManager;
    Context mContext;
    Cursor result = null;
    private static SQLiteDatabase mSqLiteDatabase;


    public DatabaseHelper(Context mContext) {
        this.mContext = mContext;
        dbManager = DatabaseManager.getInstance(mContext, DATABASE_NAME, null, DATABASE_VERSION);

    }

    //OPEN WRITABLE DATABASE
    public void open() {
        if (mSqLiteDatabase == null || mSqLiteDatabase.isOpen())
            mSqLiteDatabase = dbManager.getWritableDatabase();
    }

    public void close() {

        mSqLiteDatabase.close();
    }



    //INSERT TRANSACTION DATA
    public Long insertTransactionData(Long intTransactionID ,
                                      Long intTransactionGroupID,
                                      String strTransactionName,
                                      int intTrascationTypeID,
                                      String strTransactionTypeName,
                                      Long lngDateAndTime,
                                      String strPartyName,
                                      String strTitle,
                                      float intAmount,
                                      String strBankName,
                                      String strMonthYear,
                                      String strImageURL,
                                      String strGroupName,
                                      String strCatgory,
                                      int intDay,
                                      int intMonth,
                                      int intYear
                                        ) {

        ContentValues values = new ContentValues();
        values.put(KEY_TRANSACTION_ID, intTransactionID);
        values.put(KEY_TRANSACTION_GROUP_ID, intTransactionGroupID);
        values.put(KEY_TRANSACTION_NAME, strTransactionName);
        values.put(KEY_TRANSACTION_TYPE_ID, intTrascationTypeID);
        values.put(KEY_TRANSACTION_TYPE_NAME, strTransactionTypeName);
        values.put(KEY_TRANSACTION_DATE_TIME, lngDateAndTime);
        values.put(KEY_TRANSACTION_PARTY_NAME, strPartyName);
        values.put(KEY_TRANSACTION_TITLE, strTitle);
        values.put(KEY_TRANSACTION_AMOUNT, intAmount);
        values.put(KEY_TRANSACTION_BANK_NAME, strBankName);
        values.put(KEY_TRANSACTION_MONTH_YEAR_FOR_FILTER, strMonthYear);
        values.put(KEY_TRANSACTION_IMAGE_URL, strImageURL);
        values.put(KEY_TRANSACTION_GROUP_NAME, strGroupName);
        values.put(KEY_TRANSACTION_CATEGORY_NAME, strCatgory);
        values.put(KEY_TRANSACTION_CATEGORY_DAY, intDay);
        values.put(KEY_TRANSACTION_CATEGORY_MONTH, intMonth);
        values.put(KEY_TRANSACTION_CATEGORY_YEAR, intYear);

        return mSqLiteDatabase.insert(TABLE_TRANSACTION, null, values);

    }
    //GET TRANSACTION DATA USING ID
    public ArrayList<TransactionModel.DataBean> getTransactionData(Long lngTransactionGroupID) {
        ArrayList<TransactionModel.DataBean> mTransactionList = new ArrayList<>();

        try {
            if(lngTransactionGroupID != null){
                result = mSqLiteDatabase.query(TABLE_TRANSACTION, new String[]{}, KEY_TRANSACTION_GROUP_ID + "=" + lngTransactionGroupID, null,
                        null, null, null);
            }else{
                result = mSqLiteDatabase.query(TABLE_TRANSACTION, new String[]{}, null, null,
                        null, null, null);
            }



            if (result.moveToFirst()) {
                do {
                    TransactionModel.DataBean mTransactionModel = new TransactionModel.DataBean();
                    mTransactionModel.setLngTransactionID(result.getLong(1));
                    mTransactionModel.setLngTransactionGroupID(result.getLong(2));
                    mTransactionModel.setStrTransactionName(result.getString(3));
                    mTransactionModel.setIntTrascationTypeID(result.getInt(4));
                    mTransactionModel.setStrTransactionTypeName(result.getString(5));
                    mTransactionModel.setLngDateAndTime(result.getLong(6));
                    mTransactionModel.setStrPartyName(result.getString(7));
                    mTransactionModel.setStrTitle(result.getString(8));
                    mTransactionModel.setIntAmount(result.getFloat(9));
                    mTransactionModel.setStrBankName(result.getString(10));
                    mTransactionModel.setStrMonthYear(result.getString(11));
                    mTransactionModel.setStrImageURL(result.getString(12));
                    mTransactionModel.setStrGroupName(result.getString(13));
                    mTransactionModel.setStrCategoryName(result.getString(14));
                    mTransactionModel.setStrDay(result.getString(15));
                    mTransactionModel.setStrMonth(result.getString(16));
                    mTransactionModel.setStrYear(result.getString(17));
                    mTransactionList.add(mTransactionModel);

                }while (result.moveToNext());
            }

        } catch (Exception e) {

        } finally {
            if (result != null) {
                result.close();
                result = null;
            }
        }


        return mTransactionList;
    }

    //GET TRANSACTION DATA FOR CUSTOM PARAMETERS
    public ArrayList<TransactionModel.DataBean> getTransactionDataForCustom(String strTransactionType, boolean allDates, Long lngFromDate, Long lngToDate, String strGroupName, String strParty, String strBank) {
        ArrayList<TransactionModel.DataBean> mTransactionList = new ArrayList<>();

        try {
            String whereClause = "";
            String[] whereArgs = new String[6];
            int argNumber = 0;

            if(!strTransactionType.equals("Custom")){
                whereClause += KEY_TRANSACTION_NAME+" =? ";
                whereArgs[argNumber] = ""+strTransactionType;
            }
            if(!allDates){
                if(!whereClause.equals("")){whereClause += "AND "; argNumber++;}
                whereClause += KEY_TRANSACTION_DATE_TIME+" >=? AND "+ KEY_TRANSACTION_DATE_TIME+" <=? ";
                whereArgs[argNumber] = ""+lngFromDate;
                argNumber++;
                whereArgs[argNumber] = ""+lngToDate;
            }
            if(!strGroupName.isEmpty()){
                if(!whereClause.equals("")){whereClause += "AND "; argNumber++;}
                whereClause += KEY_TRANSACTION_GROUP_NAME+" =? ";
                whereArgs[argNumber] = ""+strGroupName;
            }
            if(!strParty.isEmpty()){
                if(!whereClause.equals("")){whereClause += "AND "; argNumber++;}
                whereClause += KEY_TRANSACTION_PARTY_NAME+" =? ";
                whereArgs[argNumber] = ""+strParty;
            }
            if(!strBank.isEmpty()){
                if(!whereClause.equals("")){whereClause += "AND "; argNumber++;}
                whereClause += KEY_TRANSACTION_BANK_NAME+" =? ";
                whereArgs[argNumber] = ""+strBank;
            }

            if(whereClause.isEmpty()){
                whereClause = null;
                whereArgs = null;
            }else{
                String[] cleanWhereArgs = new String[argNumber+1];
                int i=0;
                for(String arg: whereArgs){
                    if(arg != null){
                        cleanWhereArgs[i] = arg;
                        i++;
                    }
                }
                whereArgs = cleanWhereArgs;
            }

            result = mSqLiteDatabase.query(TABLE_TRANSACTION, new String[]{}, whereClause, whereArgs,
                    null, null, null);

            if (result.moveToFirst()) {
                do {
                    TransactionModel.DataBean mTransactionModel = new TransactionModel.DataBean();
                    mTransactionModel.setLngTransactionID(result.getLong(1));
                    mTransactionModel.setLngTransactionGroupID(result.getLong(2));
                    mTransactionModel.setStrTransactionName(result.getString(3));
                    mTransactionModel.setIntTrascationTypeID(result.getInt(4));
                    mTransactionModel.setStrTransactionTypeName(result.getString(5));
                    mTransactionModel.setLngDateAndTime(result.getLong(6));
                    mTransactionModel.setStrPartyName(result.getString(7));
                    mTransactionModel.setStrTitle(result.getString(8));
                    mTransactionModel.setIntAmount(result.getFloat(9));
                    mTransactionModel.setStrBankName(result.getString(10));
                    mTransactionModel.setStrMonthYear(result.getString(11));
                    mTransactionModel.setStrImageURL(result.getString(12));
                    mTransactionModel.setStrGroupName(result.getString(13));
                    mTransactionModel.setStrCategoryName(result.getString(14));
                    mTransactionModel.setStrDay(result.getString(15));
                    mTransactionModel.setStrMonth(result.getString(16));
                    mTransactionModel.setStrYear(result.getString(17));
                    mTransactionList.add(mTransactionModel);

                }while (result.moveToNext());
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (result != null) {
                result.close();
                result = null;
            }
        }


        return mTransactionList;
    }

    // GET TRANSACTION DATA FOR CURRENT DAY
    public ArrayList<TransactionModel.DataBean> getTransactionDataForToday(Boolean isToday, Integer day, String month, Integer year, Long fromDate, Long toDate, Boolean isAll) {
        ArrayList<TransactionModel.DataBean> mTransactionList = new ArrayList<>();

        try {
            String whereClause = null;
            String[] whereArgs = null;

            if(isToday != null && isToday){
                Calendar today = Calendar.getInstance();
                whereClause = KEY_TRANSACTION_CATEGORY_DAY+" =? AND "+KEY_TRANSACTION_CATEGORY_MONTH+" =? AND "+KEY_TRANSACTION_CATEGORY_YEAR+" =?";
                whereArgs = new String[]{""+today.get(Calendar.DAY_OF_MONTH), ""+(today.get(Calendar.MONTH)+1), ""+today.get(Calendar.YEAR)};
            }else if(isAll != null && isAll){
                //do nothing
            }

            result = mSqLiteDatabase.query(TABLE_TRANSACTION, new String[]{}, whereClause, whereArgs,
                    null, null, null);


            if (result.moveToFirst()) {
                do {
                    TransactionModel.DataBean mTransactionModel = new TransactionModel.DataBean();
                    mTransactionModel.setLngTransactionID(result.getLong(1));
                    mTransactionModel.setLngTransactionGroupID(result.getLong(2));
                    mTransactionModel.setStrTransactionName(result.getString(3));
                    mTransactionModel.setIntTrascationTypeID(result.getInt(4));
                    mTransactionModel.setStrTransactionTypeName(result.getString(5));
                    mTransactionModel.setLngDateAndTime(result.getLong(6));
                    mTransactionModel.setStrPartyName(result.getString(7));
                    mTransactionModel.setStrTitle(result.getString(8));
                    mTransactionModel.setIntAmount(result.getFloat(9));
                    mTransactionModel.setStrBankName(result.getString(10));
                    mTransactionModel.setStrMonthYear(result.getString(11));
                    mTransactionModel.setStrImageURL(result.getString(12));
                    mTransactionModel.setStrGroupName(result.getString(13));
                    mTransactionModel.setStrCategoryName(result.getString(14));
                    mTransactionModel.setStrDay(result.getString(15));
                    mTransactionModel.setStrMonth(result.getString(16));
                    mTransactionModel.setStrYear(result.getString(17));
                    mTransactionList.add(mTransactionModel);

                }while (result.moveToNext());
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (result != null) {
                result.close();
                result = null;
            }
        }


        return mTransactionList;
    }


    //GET TRANSACTIONS DATA USING TRANSACTION ID
    public ArrayList<TransactionModel.DataBean> getTransactionDataFromID(Long lngTransactionID) {
        ArrayList<TransactionModel.DataBean> mTransactionList = new ArrayList<>();

        result = null;
        try {

            result = mSqLiteDatabase.query
                    (TABLE_TRANSACTION,
                            new String[]{
                                    KEY_ID,
                                    KEY_TRANSACTION_ID,
                                    KEY_TRANSACTION_GROUP_ID,
                                    KEY_TRANSACTION_NAME,
                                    KEY_TRANSACTION_TYPE_ID,
                                    KEY_TRANSACTION_TYPE_NAME,
                                    KEY_TRANSACTION_DATE_TIME,
                                    KEY_TRANSACTION_PARTY_NAME,
                                    KEY_TRANSACTION_TITLE,
                                    KEY_TRANSACTION_AMOUNT,
                                    KEY_TRANSACTION_BANK_NAME,
                                    KEY_TRANSACTION_MONTH_YEAR_FOR_FILTER,
                                    KEY_TRANSACTION_IMAGE_URL,
                                    KEY_TRANSACTION_GROUP_NAME,
                                    KEY_TRANSACTION_CATEGORY_NAME,
                                    KEY_TRANSACTION_CATEGORY_DAY,
                                    KEY_TRANSACTION_CATEGORY_MONTH,
                                    KEY_TRANSACTION_CATEGORY_YEAR},
                            KEY_TRANSACTION_ID + "=" + lngTransactionID,
                            null, null, null, null, null
                    );

            if (result.moveToFirst()) {
                do {
                    TransactionModel.DataBean mTransactionModel = new TransactionModel.DataBean();
                    mTransactionModel.setLngTransactionID(result.getLong(1));
                    mTransactionModel.setLngTransactionGroupID(result.getLong(2));
                    mTransactionModel.setStrTransactionName(result.getString(3));
                    mTransactionModel.setIntTrascationTypeID(result.getInt(4));
                    mTransactionModel.setStrTransactionTypeName(result.getString(5));
                    mTransactionModel.setLngDateAndTime(result.getLong(6));
                    mTransactionModel.setStrPartyName(result.getString(7));
                    mTransactionModel.setStrTitle(result.getString(8));
                    mTransactionModel.setIntAmount(result.getFloat(9));
                    mTransactionModel.setStrBankName(result.getString(10));
                    mTransactionModel.setStrMonthYear(result.getString(11));
                    mTransactionModel.setStrImageURL(result.getString(12));
                    mTransactionModel.setStrGroupName(result.getString(13));
                    mTransactionModel.setStrCategoryName(result.getString(14));
                    mTransactionModel.setStrDay(result.getString(15));
                    mTransactionModel.setStrMonth(result.getString(16));
                    mTransactionModel.setStrYear(result.getString(17));
                    mTransactionList.add(mTransactionModel);

                }while (result.moveToNext());
            }

        } catch (Exception e) {

        } finally {
            if (result != null) {
                result.close();
                result = null;
            }
        }


        return mTransactionList;
    }
    //GET TRANSACTIONS LIST IN DESCENDING ORDER
    public ArrayList<TransactionModel.DataBean> getTransactionData() {
        ArrayList<TransactionModel.DataBean> mTransactionList = new ArrayList<>();

        try {
            result = mSqLiteDatabase.query(TABLE_TRANSACTION, new String[]{}, null, null,
                    null, null, KEY_TRANSACTION_DATE_TIME+" DESC");


            if (result.moveToFirst()) {
                do {
                    TransactionModel.DataBean mTransactionModel = new TransactionModel.DataBean();
                    mTransactionModel.setLngTransactionID(result.getLong(1));
                    mTransactionModel.setLngTransactionGroupID(result.getLong(2));
                    mTransactionModel.setStrTransactionName(result.getString(3));
                    mTransactionModel.setIntTrascationTypeID(result.getInt(4));
                    mTransactionModel.setStrTransactionTypeName(result.getString(5));
                    mTransactionModel.setLngDateAndTime(result.getLong(6));
                    mTransactionModel.setStrPartyName(result.getString(7));
                    mTransactionModel.setStrTitle(result.getString(8));
                    mTransactionModel.setIntAmount(result.getFloat(9));
                    mTransactionModel.setStrBankName(result.getString(10));
                    mTransactionModel.setStrMonthYear(result.getString(11));
                    mTransactionModel.setStrImageURL(result.getString(12));
                    mTransactionModel.setStrGroupName(result.getString(13));
                    mTransactionModel.setStrCategoryName(result.getString(14));
                    mTransactionModel.setStrDay(result.getString(15));
                    mTransactionModel.setStrMonth(result.getString(16));
                    mTransactionModel.setStrYear(result.getString(17));
                    mTransactionList.add(mTransactionModel);

                }while (result.moveToNext());
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (result != null) {
                result.close();
                result = null;
            }
        }


        return mTransactionList;
    }



    //INSERT BANK NAMES
    public Long insertBankName(Long id, String strBankName) {

        ContentValues values = new ContentValues();
        values.put(KEY_BANK_ID, id);
        values.put(KEY_BANK_NAME, strBankName);
        return mSqLiteDatabase.insert(TABLE_BANK_NAME, null, values);

    }

    //GET BANK NAMES
    public ArrayList<BanksNamesModel.DataBean> getBankList() {
        ArrayList<BanksNamesModel.DataBean> mBankList = new ArrayList<>();

        try {
            result = mSqLiteDatabase.query(TABLE_BANK_NAME, new String[]{}, null, null,
                    null, null, null);

            if (result.moveToFirst()) {
                do {
                    BanksNamesModel.DataBean mBankModel = new BanksNamesModel.DataBean();
                    mBankModel.setId(result.getLong(1));
                    mBankModel.setName(result.getString(2));
                    mBankList.add(mBankModel);

                }while (result.moveToNext());
            }

        } catch (Exception e) {

        } finally {
            if (result != null) {
                result.close();
                result = null;
            }
        }


        return mBankList;
    }



    //INSERT CATEGOTY NAMES
    public Long insertCategoryName(Long id, String strCategoryName) {

        ContentValues values = new ContentValues();
        values.put(KEY_CATEGOTY_ID, id);
        values.put(KEY_CATEGOTY_NAME, strCategoryName);
        return mSqLiteDatabase.insert(TABLE_CATEGORY_NAME, null, values);

    }

    //GET CATRGORY NAMES
    public ArrayList<CategoryNamesModel.DataBean> getCategoryList() {
        ArrayList<CategoryNamesModel.DataBean> mCategoryList = new ArrayList<>();

        try {
            result = mSqLiteDatabase.query(TABLE_CATEGORY_NAME, new String[]{}, null, null,
                    null, null, null);

            if (result.moveToFirst()) {
                do {
                    CategoryNamesModel.DataBean mCategoryModel = new CategoryNamesModel.DataBean();
                    mCategoryModel.setId(result.getLong(1));
                    mCategoryModel.setName(result.getString(2));
                    mCategoryList.add(mCategoryModel);

                }while (result.moveToNext());
            }

        } catch (Exception e) {

        } finally {
            if (result != null) {
                result.close();
                result = null;
            }
        }


        return mCategoryList;
    }



    //INSERT PARTY NAMES
    public Long insertPartyName(Long id, String strPartyName) {

        ContentValues values = new ContentValues();
        values.put(KEY_PARTY_ID, id);
        values.put(KEY_PARTY_NAME, strPartyName);
        return mSqLiteDatabase.insert(TABLE_PARTY_NAME, null, values);

    }
    //GET PARTY NAMES
    public ArrayList<PartyNamesModel.DataBean> getPartyNameList() {
        ArrayList<PartyNamesModel.DataBean> mPartlyList = new ArrayList<>();

        try {
            result = mSqLiteDatabase.query(TABLE_PARTY_NAME, new String[]{}, null, null,
                    null, null, null);

            if (result.moveToFirst()) {
                do {
                    PartyNamesModel.DataBean mPartyListModel = new PartyNamesModel.DataBean();
                    mPartyListModel.setId(result.getLong(1));
                    mPartyListModel.setName(result.getString(2));
                    mPartlyList.add(mPartyListModel);

                }while (result.moveToNext());
            }

        } catch (Exception e) {

        } finally {
            if (result != null) {
                result.close();
                result = null;
            }
        }


        return mPartlyList;
    }



    //INSERT GROUPNAME
    public Long insertGroupName(Long id, String strGroupName) {

        ContentValues values = new ContentValues();
        values.put(KEY_GROUP_ID, id);
        values.put(KEY_GROUP_NAME, strGroupName);
        return mSqLiteDatabase.insert(TABLE_GROUP_NAME, null, values);

    }

    //GET GROUPNAMES
    public ArrayList<GroupNames.DataBean> getAllGroups() {
        ArrayList<GroupNames.DataBean> mGroupList = new ArrayList<>();

        try {
            result = mSqLiteDatabase.query(TABLE_GROUP_NAME, new String[]{}, null, null,
                    null, null, null);

            if (result.moveToFirst()) {
                do {
                    GroupNames.DataBean mReasonMaster = new GroupNames.DataBean();
                    mReasonMaster.setId(result.getLong(1));
                    mReasonMaster.setName(result.getString(2));
                    mGroupList.add(mReasonMaster);

                }while (result.moveToNext());
            }

        } catch (Exception e) {

        } finally {
            if (result != null) {
                result.close();
                result = null;
            }
        }


        return mGroupList;
    }




}