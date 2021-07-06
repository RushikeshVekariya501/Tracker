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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tracker.Adapter.TransactionForEachGroupAdapter;
import com.tracker.CommonClasse.CommonMethods;
import com.tracker.CommonClasse.Constants;
import com.tracker.ModelClasses.TransactionModel;
import com.tracker.R;
import com.tracker.SqliteDatabase.DatabaseHelper;

import java.util.ArrayList;

public class AddIncomExpenseActivity extends AppCompatActivity implements View.OnClickListener {

    private Activity mActivity;
    private Context mContext;
    private static final String TAG = "AddIncomExpenseActivity";
    private EditText edtLicenceOrMobile;
    private TextView txtSignIn;
    private ImageView imgSummary,imgHome,imgBack, imgReport;
    private TextView txtFilter,txtAddExpense,txtAddIncome,txtTitle;
    private RecyclerView recyclMonthlyData;
    private LinearLayoutManager recylerViewLayoutManager;
    private DatabaseHelper mDatabaseHelper;
    private TransactionForEachGroupAdapter mTransactionForEachGroupAdapter;
    private Long intTransactionGroupID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_income_expense);

        setIds();
        setOncliks();
    }

    private void setIds() {

        mActivity = AddIncomExpenseActivity.this;
        mContext = AddIncomExpenseActivity.this;

        mDatabaseHelper = new DatabaseHelper(mContext);
        mDatabaseHelper.open();

        imgBack = (ImageView) findViewById(R.id.imgBack);
        imgHome = (ImageView) findViewById(R.id.imgHome);
        imgSummary = (ImageView) findViewById(R.id.imgSummary);
        imgReport = (ImageView) findViewById(R.id.imgReport);

        txtTitle = (TextView) findViewById(R.id.txtTitle);
        txtAddIncome = (TextView) findViewById(R.id.txtAddIncome);
        txtAddExpense = (TextView) findViewById(R.id.txtAddExpense);
        txtFilter = (TextView) findViewById(R.id.txtFilter);

        recyclMonthlyData = (RecyclerView)findViewById(R.id.recyclMonthlyData);
        recylerViewLayoutManager = new LinearLayoutManager(mActivity);
        recyclMonthlyData.setLayoutManager(recylerViewLayoutManager);

        //SET TILE
        txtTitle.setText(getIntent().getStringExtra(Constants.GROUP_NAME));

        //HIDE HOME, REPORT ICON
        imgHome.setVisibility(View.GONE);
        imgReport.setVisibility(View.GONE);
        //imgSummary.setVisibility(View.GONE);


        intTransactionGroupID = getIntent().getLongExtra(Constants.GROUP_ID, 1L);


    }

    @Override
    protected void onResume() {
        super.onResume();

        loadRecycleView();
    }

    private void loadRecycleView() {

        ArrayList<TransactionModel.DataBean> transactionsList = mDatabaseHelper.getTransactionData(intTransactionGroupID);

        if(transactionsList.size() != 0){
            //SET GROUP ADAPTER
            mTransactionForEachGroupAdapter = new TransactionForEachGroupAdapter(mContext, AddIncomExpenseActivity.this, transactionsList);
            recyclMonthlyData.setAdapter(mTransactionForEachGroupAdapter);
        }
    }

    private void setOncliks() {

        imgBack.setOnClickListener(this);
        imgHome.setOnClickListener(this);
        imgSummary.setOnClickListener(this);
        txtAddIncome.setOnClickListener(this);
        txtAddExpense.setOnClickListener(this);
        txtFilter.setOnClickListener(this);
    }

    @Override
    public void onClick(View mView) {

        switch (mView.getId()){

            case R.id.imgBack:

                    finish();
                break;

            case R.id.imgHome:
                finish();
                break;
            case R.id.imgSummary:

                Intent mIntent =new Intent(AddIncomExpenseActivity.this, SummaryActivity.class);
                startActivity(mIntent);
                finish();

                break;

            case R.id.txtAddIncome:

                Intent mAddIntent =new Intent(AddIncomExpenseActivity.this, TransactionActivity.class);
                mAddIntent.putExtra(Constants.INCOME_EXPENSE_TYPE, txtAddIncome.getText().toString());
                mAddIntent.putExtra(Constants.GROUP_NAME, txtTitle.getText().toString());
                mAddIntent.putExtra("IS_FROM_VIEW", false);
                mAddIntent.putExtra(Constants.INCOME_EXPENSE_ID,1);
                mAddIntent.putExtra(Constants.GROUP_ID, intTransactionGroupID);
                startActivity(mAddIntent);


                break;
            case R.id.txtAddExpense:

                Intent mAddExIntent =new Intent(AddIncomExpenseActivity.this, TransactionActivity.class);
                mAddExIntent.putExtra(Constants.INCOME_EXPENSE_TYPE, txtAddExpense.getText().toString());
                mAddExIntent.putExtra(Constants.GROUP_NAME, txtTitle.getText().toString());
                mAddExIntent.putExtra("IS_FROM_VIEW", false);
                mAddExIntent.putExtra(Constants.INCOME_EXPENSE_ID,2);
                mAddExIntent.putExtra(Constants.GROUP_ID, intTransactionGroupID);
                startActivity(mAddExIntent);


                break;
            case R.id.txtFilter:

                CommonMethods.displayToast(mContext ,"FILTER PENDING");

                break;
        }

    }
}
