package com.tracker.Utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import androidx.core.content.FileProvider;

import com.tracker.CommonClasse.CommonMethods;
import com.tracker.CommonClasse.Constants;
import com.tracker.ModelClasses.TransactionModel;
import com.tracker.SqliteDatabase.DatabaseHelper;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;

public class SummaryExporter {
    //object to access sqlite database
    DatabaseHelper db;
    Context context;
    public static ArrayList<TransactionModel.DataBean> transactionList;
    public static double transactionTotal;


    public SummaryExporter(Context context)
    {
        this.context=context;
        this.db=new DatabaseHelper(context);
    }

    public void about(){

    }

    //share files
    public void share(){
        String[] filenames={"Transactions.csv"};
        ArrayList<Uri> arrayUri=new ArrayList<Uri>();

        for(String filename:filenames)
        {
            File path=new File(context.getFilesDir(),"csv_files");
            File file=new File(path,filename);

            Uri uri= FileProvider.getUriForFile(context,"com.tracker", file);
            arrayUri.add(uri);
        }
        Intent intentShareFile = new Intent(Intent.ACTION_SEND);
        intentShareFile.setAction(Intent.ACTION_SEND_MULTIPLE);
        intentShareFile.putParcelableArrayListExtra(Intent.EXTRA_STREAM, arrayUri);
        intentShareFile.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intentShareFile.setType("text/csv");
        context.startActivity(intentShareFile);
    }

    public void createCsvFiles(){
        createCsvFileForTransaction();
    }

    private void createCsvFileForTransaction(){
        File path=new File(context.getFilesDir(),"csv_files");
        if(!path.exists())
            path.mkdir();
        File file=new File(path,"Transactions.csv");
        try
        {
            FileWriter writer=new FileWriter(file);
            String line= "Date, Description, Party Name, category, Amount" + "\n";
            writer.write(line);
            for(TransactionModel.DataBean transaction: transactionList)
            {
                line= CommonMethods.getDateFromLong(transaction.getLngDateAndTime())+ "," +
                        "\""+ transaction.getStrTitle() + "\","+
                        "\""+transaction.getStrPartyName() + "\","+
                        "\""+transaction.getStrCategoryName() + "\",";
                        if(transaction.getIntTrascationTypeID() == Constants.INCOME_TYPE_ID){
                            line+= ""+transaction.getIntAmount() + "\n";
                        }else{
                            line+= "-"+transaction.getIntAmount() + "\n";
                        }

                //replace any new line character within the field data
                line=line.replace(System.getProperty("line.separator"),"");
                line=line+"\n";   // insert new line at the end of the line
                writer.write(line);
            }
            line= "*, *, *, TOTAL,"+ SummaryExporter.transactionTotal+ "\n";
            writer.write(line);

            writer.flush();
            writer.close();

            share();
        }
        catch(Exception e){

            Log.e("EXCEPTION : ","  "+e);
        }
    }
}