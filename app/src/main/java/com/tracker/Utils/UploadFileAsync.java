package com.tracker.Utils;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;

import androidx.navigation.NavController;

import com.tracker.CommonClasse.CommonMethods;
import com.tracker.R;
import com.tracker.SqliteDatabase.DatabaseHelper;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.channels.FileChannel;

public class UploadFileAsync extends AsyncTask<String, Void, String> {

    public final String PARAM_SUBMIT = "submit";
    String sourceFileUri = "";
    Context mContext;
    public NavController navController;
    String message="Backup successfully completed!!";

    public UploadFileAsync(Context context, NavController navController){
        this.mContext = context;
        this.navController = navController;
    }
    @Override
    protected String doInBackground(String... params) {

        try {
            File file = getDBFile();
            sourceFileUri = file.getAbsolutePath();

            HttpURLConnection conn = null;
            DataOutputStream dos = null;
            String lineEnd = "\r\n";
            String twoHyphens = "--";
            String boundary = "*****";
            int bytesRead, bytesAvailable, bufferSize;
            byte[] buffer;
            int maxBufferSize = 1 * 1024 * 1024;
            File sourceFile = new File(sourceFileUri);
            //boolean b = a.isFile();
            if (sourceFile.isFile()) {

                try {
                    String upLoadServerUri = "http://rushisoftware.com.au/api/abc.php?";

                    // open a URL connection to the service
                    FileInputStream fileInputStream = new FileInputStream(
                            sourceFile);
                    URL url = new URL(upLoadServerUri);

                    // Open a HTTP connection to the URL
                    conn = (HttpURLConnection) url.openConnection();
                    conn.setDoInput(true); // Allow Inputs
                    conn.setDoOutput(true); // Allow Outputs
                    conn.setUseCaches(false); // Don't use a Cached Copy
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Connection", "Keep-Alive");
                    conn.setRequestProperty("ENCTYPE", "multipart/form-data");
                    conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);

                    conn.setRequestProperty("bill", sourceFileUri);
                    conn.setRequestProperty(PARAM_SUBMIT, PARAM_SUBMIT);

                    dos = new DataOutputStream(conn.getOutputStream());

                    dos.writeBytes(twoHyphens + boundary + lineEnd);
                    dos.writeBytes("Content-Disposition: form-data; name=\"bill\";filename=\"" + sourceFileUri + "\"" + lineEnd);

                    dos.writeBytes(lineEnd);

                    // create a buffer of maximum size
                    bytesAvailable = fileInputStream.available();

                    bufferSize = Math.min(bytesAvailable, maxBufferSize);
                    buffer = new byte[bufferSize];

                    // read file and write it into form...
                    bytesRead = fileInputStream.read(buffer, 0, bufferSize);

                    while (bytesRead > 0) {

                        dos.write(buffer, 0, bufferSize);
                        bytesAvailable = fileInputStream.available();
                        bufferSize = Math
                                .min(bytesAvailable, maxBufferSize);
                        bytesRead = fileInputStream.read(buffer, 0,
                                bufferSize);

                    }

                    // send multipart form data necesssary after file
                    // data...
                    dos.writeBytes(lineEnd);
                    dos.writeBytes(twoHyphens + boundary + twoHyphens
                            + lineEnd);

                    // Responses from the server (code and message)
                    int serverResponseCode = conn.getResponseCode();
                    String serverResponseMessage = conn
                            .getResponseMessage();

                    if (serverResponseCode != 200) {
                        message = "Server issue: Backup unsuccessful !!";
                    }

                    // close the streams
                    fileInputStream.close();
                    dos.flush();
                    dos.close();

                } catch (Exception e) {
                    e.printStackTrace();

                }
            } // End else block


        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "Executed";
    }

    @Override
    protected void onPostExecute(String result) {
        CommonMethods.closeDialog();
        CommonMethods.buildDialog(mContext, R.style.DialogTheme, message);
        navController.navigate(R.id.groupList);
    }

    @Override
    protected void onPreExecute() {
        CommonMethods.showDialog(mContext);
    }

    @Override
    protected void onProgressUpdate(Void... values) {
    }

    public File getDBFile(){
        File sd, currentDB, backupDB = null;
        try {
            sd = Environment.getExternalStorageDirectory();

            if (sd.canWrite()) {
                String currentDBPath = "/data/data/" + "com.tracker" + "/databases/"+ DatabaseHelper.DATABASE_NAME;
                String backupDBPath = DatabaseHelper.DATABASE_NAME;
                currentDB = new File(currentDBPath);
                backupDB = new File(sd, backupDBPath);

                if (currentDB.exists()) {
                    FileChannel src = new FileInputStream(currentDB).getChannel();
                    FileChannel dst = new FileOutputStream(backupDB).getChannel();
                    dst.transferFrom(src, 0, src.size());
                    src.close();
                    dst.close();
                }
            }
        } catch (Exception e) {

        }
        return backupDB;
    }
}