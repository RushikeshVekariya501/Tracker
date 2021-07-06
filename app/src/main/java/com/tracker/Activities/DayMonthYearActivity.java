package com.tracker.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.tracker.CommonClasse.Constants;
import com.tracker.Fragments.TodayListFragment;
import com.tracker.ModelClasses.TransactionModel;
import com.tracker.R;
import com.tracker.SqliteDatabase.DatabaseHelper;
import com.tracker.Utils.SummaryExporter;

import java.util.ArrayList;

public class DayMonthYearActivity extends AppCompatActivity implements View.OnClickListener {

    private Activity mActivity;
    private Context mContext;
    private static final String TAG = "DayMonthYearActivity";
    private ImageView imgSummary,imgHome,imgBack, imgReport;
    private TextView txtTitle;
    private DatabaseHelper mDatabaseHelper;

    private ViewPager viewPager;
    private int tabId;
    private String tabName;
    private ArrayList<TransactionModel.DataBean> listData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day_month_year);

        setIds();
        setOncliks();
    }

    private void setIds() {

        mActivity = DayMonthYearActivity.this;
        mContext = DayMonthYearActivity.this;

        mDatabaseHelper = new DatabaseHelper(this);
        mDatabaseHelper.open();


        imgBack = (ImageView) findViewById(R.id.imgBack);
        imgHome = (ImageView) findViewById(R.id.imgHome);
        imgSummary = (ImageView) findViewById(R.id.imgSummary);
        imgReport = (ImageView) findViewById(R.id.imgReport);

        txtTitle = (TextView) findViewById(R.id.txtTitle);

        //HIDE REPORT ICON
        imgSummary.setVisibility(View.GONE);

        Intent intent = getIntent();
        tabId = intent.getIntExtra("TAB_ID", 10);
        tabName = intent.getStringExtra("TAB_NAME");
        listData = (ArrayList<TransactionModel.DataBean>) intent.getSerializableExtra("DATA");

        //SET TILE
        txtTitle.setText(tabName);

        FrameLayout frame = new FrameLayout(this);
        frame.setId(R.id.dmyFrame);

        Fragment newFragment = new TodayListFragment(tabId, listData);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.dmyFrame, newFragment).commit();

        if(tabId == Constants.SUMMARY_DAY){
            SummaryExporter.transactionList = TodayListFragment.printDay;
        }else if(tabId == Constants.SUMMARY_MONTH){
            SummaryExporter.transactionList = TodayListFragment.printMonth;
        }else if(tabId == Constants.SUMMARY_YEAR){
            SummaryExporter.transactionList = TodayListFragment.printYear;
        }

    }
    private void setOncliks() {

        imgBack.setOnClickListener(this);
        imgHome.setOnClickListener(this);
        imgSummary.setOnClickListener(this);
        imgReport.setOnClickListener(this);

    }

    @Override
    public void onClick(View mView) {
        Intent mIntent;
        switch (mView.getId()){

            case R.id.imgBack:
                finish();
                break;
            case R.id.imgHome:
                finish();
                break;
            case R.id.imgReport:

                SummaryExporter report= new SummaryExporter(DayMonthYearActivity.this);
                report.createCsvFiles();
                break;
        }

    }

}