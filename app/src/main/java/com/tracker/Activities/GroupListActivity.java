package com.tracker.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tracker.Adapter.GroupNamesAdapter;
import com.tracker.Interfaces.CommonStringInterface;
import com.tracker.R;
import com.tracker.SqliteDatabase.DatabaseHelper;

public class GroupListActivity extends AppCompatActivity implements View.OnClickListener, CommonStringInterface {

    private Activity mActivity;
    private Context mContext;
    private static final String TAG = "GroupListActivity";
    private ImageView imgReport,imgSummary, imgHome, imgBack;
    private TextView txtTitle;
    private DatabaseHelper mDatabaseHelper;
    private LinearLayoutManager recylerViewLayoutManager;
    private RecyclerView recyBankList;
    private GroupNamesAdapter mGroupNamesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_list);

        setIds();
        setOncliks();
    }

    private void setIds() {

        mActivity = GroupListActivity.this;
        mContext = GroupListActivity.this;

        mDatabaseHelper = new DatabaseHelper(mContext);
        mDatabaseHelper.open();


        imgBack = (ImageView) findViewById(R.id.imgBack);
        imgHome = (ImageView) findViewById(R.id.imgHome);
        imgSummary = (ImageView) findViewById(R.id.imgSummary);
        imgReport = (ImageView) findViewById(R.id.imgReport);

        recyBankList = (RecyclerView)findViewById(R.id.recyBankList);
        recylerViewLayoutManager = new LinearLayoutManager(mActivity);
        recyBankList.setLayoutManager(recylerViewLayoutManager);

        txtTitle = (TextView) findViewById(R.id.txtTitle);

        //SET TILE
        txtTitle.setText("Select Group");

        //HIDE REPOSR ICONE
        imgHome.setVisibility(View.GONE);
        imgSummary.setVisibility(View.GONE);
        imgReport.setVisibility(View.GONE);

        //SET ADAPTER
        setCategoryListAdpter();

    }



    private void setOncliks() {

        imgBack.setOnClickListener(this);
        imgHome.setOnClickListener(this);
        imgSummary.setOnClickListener(this);
    }

    @Override
    public void onClick(View mView) {

        switch (mView.getId()) {

            case R.id.imgBack:
                finish();
                break;
            case R.id.imgHome:
                finish();
                break;
            case R.id.imgSummary:


                break;
        }
    }

    private void setCategoryListAdpter() {

        if(mDatabaseHelper.getPartyNameList().size() != 0) {
            //SET GROUP ADAPTER
            mGroupNamesAdapter = new GroupNamesAdapter(mContext, GroupListActivity.this, mDatabaseHelper.getAllGroups());
            recyBankList.setAdapter(mGroupNamesAdapter);
        }


    }


    @Override
    public void commonStringInterface(String strPartyName) {

        Intent resultIntent = new Intent();
        resultIntent.putExtra("VALUE", strPartyName);
        setResult(Activity.RESULT_OK, resultIntent);
        finish();


    }
}
