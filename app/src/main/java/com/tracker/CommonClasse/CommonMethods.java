package com.tracker.CommonClasse;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.text.format.DateFormat;
import android.util.Log;
import android.widget.Toast;

import com.tracker.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CommonMethods {

    private static AlertDialog dialog;
    private static ProgressDialog pDialog;

    public static boolean isInternetAvailable(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }

        }
        return false;
    }

    public static void buildDialog(Context mContext , int animationSource, String strMessege) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle(mContext.getResources().getString(R.string.strWarningDialogTitle));
        builder.setMessage(strMessege);
        builder.setNegativeButton(mContext.getResources().getString(R.string.strOk), null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }


    public static void displayToast(Context mContext, String strMessege){

        Toast.makeText(mContext , strMessege, Toast.LENGTH_SHORT).show();
    }

    public static void displayLog(Context mContext, String strTag , String strMessege){

        Log.e(strTag, strMessege);
    }

    public static void printLog(Context mContext, String strTAG , String strMessage){

        Log.e(strTAG, strMessage);
    }

    public static void showDialog(Context mContext) {
        pDialog = new ProgressDialog(mContext);
        pDialog.setMessage(mContext.getResources().getString(R.string.strPleaseWait));
        pDialog.setCancelable(false);
        pDialog.show();
    }


    public static void closeDialog() {
        pDialog.dismiss();
    }

    public static  String getDeviceToken(Activity mActivity) {
        return Settings.Secure.getString(mActivity.getContentResolver(),
                Settings.Secure.ANDROID_ID);


    }

    public static String getDateFromLong(Long lngDate){

        long millisecond = Long.parseLong(String.valueOf(lngDate));
        // or you already have long value of date, use this instead of milliseconds variable.
        String dateString = DateFormat.format(Constants.COMMON_DATE_FORMAT, new Date(millisecond)).toString();

        return dateString;
    }

    public static Long getLongDateFromStringDate(String date){
        long startDate = 0;
        try {
            String dateString = date;
            SimpleDateFormat sdf = new SimpleDateFormat(Constants.COMMON_DATE_FORMAT);
            Date mDate1 = sdf.parse(dateString);

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(mDate1);
            Calendar timeInstance = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, timeInstance.get(Calendar.HOUR_OF_DAY));
            calendar.set(Calendar.MINUTE, timeInstance.get(Calendar.MINUTE));
            calendar.set(Calendar.SECOND, timeInstance.get(Calendar.SECOND));

            //startDate = mDate1.getTime();

            startDate = calendar.getTime().getTime();

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return startDate;
    }

    public static String getStringDays(int day){
        String strDay ="";
        if(day<10){
            strDay+= "0"+day;
        }else{
            strDay+= day;
        }
        return strDay;
    }

    public static String getStringMonths(int intMonth){

        String strMonth ="Jan";

        switch (intMonth){

            case 1:
                strMonth="Jan";
                break;
            case 2:
                strMonth="Feb";
                break;
            case 3:
                strMonth="Mar";
                break;
            case 4:
                strMonth="Apr";
                break;
            case 5:
                strMonth="May";
                break;
            case 6:
                strMonth="Jun";
                break;
            case 7:
                strMonth="Jul";
                break;
            case 8:
                strMonth="Aug";
                break;
            case 9:
                strMonth="Sep";
                break;
            case 10:
                strMonth="Oct";
                break;
            case 11:
                strMonth="Nov";
                break;
            case 12:
                strMonth="Dec";
                break;

        }


        return strMonth;

    }

    public static String getStringFullMonths(String strMonth){

        String strFullMonth ="January";

        switch (strMonth){

            case "1":
                strFullMonth="January";
                break;
            case "2":
                strFullMonth="February";
                break;
            case "3":
                strFullMonth="March";
                break;
            case "4":
                strFullMonth="April";
                break;
            case "5":
                strFullMonth="May";
                break;
            case "6":
                strFullMonth="June";
                break;
            case "7":
                strFullMonth="July";
                break;
            case "8":
                strFullMonth="August";
                break;
            case "9":
                strFullMonth="September";
                break;
            case "10":
                strFullMonth="October";
                break;
            case "11":
                strFullMonth="November";
                break;
            case "12":
                strFullMonth="December";
                break;

        }


        return strFullMonth;

    }

}
