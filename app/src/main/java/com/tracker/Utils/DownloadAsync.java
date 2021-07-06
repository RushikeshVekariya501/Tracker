package com.tracker.Utils;

import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;

import androidx.navigation.NavController;

import com.tracker.CommonClasse.CommonMethods;
import com.tracker.R;
import com.tracker.SqliteDatabase.DatabaseHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

public class DownloadAsync extends AsyncTask<String, Void, String> {
    boolean download = true;

    Dialog progressDialog;
    Context mContext;
    public NavController navController;
    String message="Data successfully restored!!";

    public DownloadAsync(Context context, NavController navController){
        this.mContext = context;
        this.navController = navController;
    }

    @Override
    protected String doInBackground(String... params)
    {
        File path=new File(mContext.getDatabasePath(DatabaseHelper.DATABASE_NAME),"");

        File directory = new File(path.toString().replace(DatabaseHelper.DATABASE_NAME, ""));

        if (!directory.exists())
        {
            directory.mkdir();
        }
        String fileName = DatabaseHelper.DATABASE_NAME; //db name that will be stored in your device
        try
        {
            InputStream input = null;
            try{
                // link of the db which you want to download
                URL url = new URL("http://rushisoftware.com.au/api/uploads/"+DatabaseHelper.DATABASE_NAME);
                input = url.openStream();
                OutputStream output = new FileOutputStream(new File(directory, fileName));
                download = true;
                try {
                    byte[] buffer = new byte[1024];
                    int bytesRead = 0;
                    while ((bytesRead = input.read(buffer, 0, buffer.length)) >= 0)
                    {
                        output.write(buffer, 0, bytesRead);
                        download = true;
                    }
                    output.close();
                }
                catch (Exception exception)
                {
                    exception.printStackTrace();
                    download = false;
                    output.close();
                }
            }
            catch (Exception ex)
            {

                message = "You don't have any backup on server!";
                ex.printStackTrace();
                download = false;
            }
            finally
            {
                input.close();
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            download = false;
        }
        return ""+download;
    }
    @Override
    protected void onPreExecute() {
        CommonMethods.showDialog(mContext);
    }

    @Override
    protected void onPostExecute(String status)
    {

        CommonMethods.closeDialog();
        CommonMethods.buildDialog(mContext, R.style.DialogTheme, message);

        navController.navigate(R.id.groupList);

    }
}