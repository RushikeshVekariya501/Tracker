package com.tracker.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.tracker.Adapter.SummaryPagerAdapter;
import com.tracker.CommonClasse.Constants;
import com.tracker.Fragments.TodayListFragment;
import com.tracker.R;
import com.tracker.SqliteDatabase.DatabaseHelper;
import com.tracker.Utils.SummaryExporter;

public class SummaryActivity extends AppCompatActivity implements View.OnClickListener {

    private Activity mActivity;
    private Context mContext;
    private static final String TAG = "SummaryActivity";
    private EditText edtLicenceOrMobile;
    private TextView txtSignIn;
    private ImageView imgSummary,imgHome,imgBack, imgReport;
    private TextView txtTitle;
    private DatabaseHelper mDatabaseHelper;

    private TabLayout tabs;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summuy);

        setIds();
        setOncliks();
    }

    private void setIds() {

        mActivity = SummaryActivity.this;
        mContext = SummaryActivity.this;

        mDatabaseHelper = new DatabaseHelper(this);
        mDatabaseHelper.open();


        imgBack = (ImageView) findViewById(R.id.imgBack);
        imgHome = (ImageView) findViewById(R.id.imgHome);
        imgSummary = (ImageView) findViewById(R.id.imgSummary);
        imgReport = (ImageView) findViewById(R.id.imgReport);

        txtTitle = (TextView) findViewById(R.id.txtTitle);

        //SET TILE
        txtTitle.setText("Summary");

        //HIDE REPORT ICON
        imgSummary.setVisibility(View.GONE);

        setTabs();


    }

    //SET ALL TABS HERE
    public void setTabs(){
        SummaryPagerAdapter sectionsPagerAdapter = new SummaryPagerAdapter(this, getSupportFragmentManager());
        viewPager = findViewById(R.id.summary_view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);

        tabs = findViewById(R.id.summary_tabs);
        tabs.setupWithViewPager(viewPager);
    }

    //SET ONCLICK LISTENERS
    private void setOncliks() {

        imgBack.setOnClickListener(this);
        imgHome.setOnClickListener(this);
        imgSummary.setOnClickListener(this);
        imgReport.setOnClickListener(this);
        tabs.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager){
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                super.onTabSelected(tab);
                int position = tab.getPosition();
                if(position == Constants.SUMMARY_TODAY){
                    SummaryExporter.transactionList = TodayListFragment.printToday;
                    imgReport.setVisibility(View.VISIBLE);
                }else if(position == Constants.SUMMARY_ALL){
                    SummaryExporter.transactionList = TodayListFragment.printAll;
                    imgReport.setVisibility(View.VISIBLE);
                }

                else if(position == Constants.SUMMARY_CUSTOM){
                    imgReport.setVisibility(View.VISIBLE);
                }else if(position == Constants.SUMMARY_DAY){
                    imgReport.setVisibility(View.GONE);
                }else if(position == Constants.SUMMARY_MONTH){
                    imgReport.setVisibility(View.GONE);
                }else if(position == Constants.SUMMARY_YEAR){
                    imgReport.setVisibility(View.GONE);
                }
            }
        });
    }

    //DEFINE ONCLICK ACTIONS TO BE PERFORMED
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
            case R.id.imgSummary:

                mIntent =new Intent(SummaryActivity.this, SummaryActivity.class);
                startActivity(mIntent);
                finish();

                break;
            case R.id.imgReport:

                SummaryExporter report= new SummaryExporter(SummaryActivity.this);
                report.createCsvFiles();
                break;
        }

    }

}
