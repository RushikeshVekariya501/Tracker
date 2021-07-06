package com.tracker.Fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputLayout;
import com.tracker.Activities.BanksListActivity;
import com.tracker.Activities.GroupListActivity;
import com.tracker.Activities.PartyListActivity;
import com.tracker.Adapter.TransactionForEachGroupAdapter;
import com.tracker.CommonClasse.CommonMethods;
import com.tracker.CommonClasse.Constants;
import com.tracker.ModelClasses.TransactionModel;
import com.tracker.R;
import com.tracker.SqliteDatabase.DatabaseHelper;
import com.tracker.Utils.SummaryExporter;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class CustomFragment extends Fragment implements View.OnClickListener, DatePickerDialog.OnDateSetListener {

    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 1;
    public static final String TAG = "CustomFragment";


    private Activity mActivity;
    private Context mContext;
    private DatabaseHelper mDatabaseHelper;
    int tabId;

    ArrayList<TransactionModel.DataBean> transactionsList;
    public static ArrayList<TransactionModel.DataBean> printDay;
    public static ArrayList<TransactionModel.DataBean> printMonth;
    public static double total;

    HashMap<String, ArrayList<TransactionModel.DataBean>> listViewData = new HashMap<>();
    ArrayList<String> listViewValues = null;
    private View mView;
    private EditText edtFromDate, edtToDate, edtGroupName, edtPartyName, edtBank;
    private TextView totalBalanceAmount,totalExpenseAmount,totalIncomeAmount,txtFilter;
    private RadioGroup rgSelectGroup;
    private RadioButton rbtCustom, rbtBank, rbtCash;
    private TextInputLayout inputBank;
    private CheckBox checkALl;
    private boolean isFromStartDate = false;
    private DatePickerDialog mDatePickerDialog;
    private String strTransactionType ="Custom";
    private LinearLayout linearList,linearHeader;
    private LinearLayoutManager recylerViewLayoutManager;
    private RecyclerView recycleTodaysData;
    private TransactionForEachGroupAdapter mTransactionForEachGroupAdapter;

    private ImageView imgHome, imgRefresh;

    public CustomFragment(int tabId) {
        this.tabId = tabId;
        mDatabaseHelper = new DatabaseHelper(mContext);
        mDatabaseHelper.open();


    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = this.getActivity();
        mContext = this.getActivity();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_custom, container, false);
        setIds();
        setOnClicks();
        return mView;
    }

    //SET INITIALIZE FORM FIELDS
    void initializeForm(){
        strTransactionType ="Custom";
        checkALl.setChecked(true);
        rgSelectGroup.getChildAt(2).setSelected(true);
        edtFromDate.setText(""+CommonMethods.getDateFromLong(System.currentTimeMillis()));
        edtFromDate.setFocusable(false);
        edtFromDate.setFocusableInTouchMode(false);
        edtFromDate.setEnabled(false);

        edtToDate.setText(""+CommonMethods.getDateFromLong(System.currentTimeMillis()));
        edtToDate.setFocusable(false);
        edtToDate.setFocusableInTouchMode(false);
        edtToDate.setEnabled(false);

        edtPartyName.setText("");
        edtGroupName.setText("");
        edtBank.setText("");
    }


    private void setIds() {

        imgHome = (ImageView) mActivity.findViewById(R.id.imgHome);
        imgRefresh = (ImageView) mActivity.findViewById(R.id.imgRefresh);

        edtFromDate = (EditText) mView.findViewById(R.id.edtFromDate);
        edtToDate = (EditText) mView.findViewById(R.id.edtToDate);
        edtGroupName = (EditText) mView.findViewById(R.id.edtGroupName);
        edtPartyName = (EditText) mView.findViewById(R.id.edtPartyName);
        edtBank = (EditText) mView.findViewById(R.id.edtBank);


        txtFilter = (TextView) mView.findViewById(R.id.txtFilter);

        inputBank = (TextInputLayout) mView.findViewById(R.id.inputBank);

        rgSelectGroup = (RadioGroup) mView.findViewById(R.id.rgSelectGroup);
        rbtCash = (RadioButton) mView.findViewById(R.id.rbtCash);
        rbtBank = (RadioButton) mView.findViewById(R.id.rbtBank);
        rbtCustom = (RadioButton) mView.findViewById(R.id.rbtCustom);

        checkALl =(CheckBox)mView.findViewById(R.id.checkALl);

        linearHeader=(LinearLayout)mView.findViewById(R.id.linearHeader);
        linearList=(LinearLayout)mView.findViewById(R.id.linearList);

        initializeForm();

        //CASH OR BANK CLICK
        rgSelectGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int childCount = group.getChildCount();
                for (int x = 0; x < childCount; x++) {
                    RadioButton rbtn = (RadioButton) group.getChildAt(x);

                    if (rbtn.getId() == checkedId) {

                        //TRANSACTION TYPE
                        strTransactionType = rbtn.getText().toString();

                        if (rbtn.getText().toString().equalsIgnoreCase("Bank")) {

                            inputBank.setVisibility(View.VISIBLE);
                            edtBank.setVisibility(View.VISIBLE);

                        } else {
                            inputBank.setVisibility(View.GONE);
                            edtBank.setVisibility(View.GONE);
                        }

                    }

                }

            }
        });

        checkALl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(((CompoundButton) view).isChecked()){

                    edtFromDate.setFocusable(false);
                    edtFromDate.setFocusableInTouchMode(false);
                    edtFromDate.setEnabled(false);

                    edtToDate.setFocusable(false);
                    edtToDate.setFocusableInTouchMode(false);
                    edtToDate.setEnabled(false);

                    //SET INITIAL DATA
                    edtFromDate.setText(""+CommonMethods.getDateFromLong(System.currentTimeMillis()));
                    edtToDate.setText(""+CommonMethods.getDateFromLong(System.currentTimeMillis()));


                } else {


                    //edtFromDate.setFocusable(true);
                    //edtFromDate.setFocusableInTouchMode(true);
                    edtFromDate.setEnabled(true);

                    //edtToDate.setFocusable(true);
                    //edtToDate.setFocusableInTouchMode(true);
                    edtToDate.setEnabled(true);
                }
            }
        });

        edtFromDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                isFromStartDate = true;

                openStartDatePickerDialog();

            }
        });

        edtToDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (edtFromDate.getText().toString().trim().isEmpty()) {
                    CommonMethods.displayToast(mContext, "Please select start date!");
                } else {
                    isFromStartDate = false;

                    openEndDatePickerDialog();
                }

            }
        });
    }
    private void setOnClicks() {
        imgRefresh.setOnClickListener(this);

        edtGroupName.setOnClickListener(this);
        edtPartyName.setOnClickListener(this);
        edtBank.setOnClickListener(this);
        txtFilter.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.imgRefresh:
                initializeForm();
                linearHeader.setVisibility(View.VISIBLE);
                linearList.setVisibility(View.GONE);

                imgRefresh.setVisibility(View.GONE);
                break;


            case R.id.edtGroupName:

                Intent mIntentGroup = new Intent(mContext, GroupListActivity.class);
                startActivityForResult(mIntentGroup, 200);

                break;
            case R.id.edtPartyName:

                Intent mIntentParty = new Intent(mContext, PartyListActivity.class);
                startActivityForResult(mIntentParty, 100);

                break;
            case R.id.edtBank:

                Intent mIntentBank = new Intent(mContext, BanksListActivity.class);
                startActivityForResult(mIntentBank, 300);

                break;

            case R.id.txtFilter:

                if(checkValidation()){


                    //GET PARAMETERS FOR CUSTOM FILTER
                    String strFromDate= edtFromDate.getText().toString();
                    String strToDate= edtToDate.getText().toString();

                    String strTransactionType = this.strTransactionType;
                    String strGroupName= edtGroupName.getText().toString();
                    String strBank= edtBank.getText().toString();
                    String strParty= edtPartyName.getText().toString();
                    Long lngFromDate = CommonMethods.getLongDateFromStringDate(strFromDate);
                    Long lngToDate = CommonMethods.getLongDateFromStringDate(strToDate);
                    boolean allDates = checkALl.isChecked();


                    //HIDE VIEW LAYOUT
                    linearHeader.setVisibility(View.GONE);
                    linearList.setVisibility(View.VISIBLE);

                    imgRefresh.setVisibility(View.VISIBLE);

                    //SET RECYCLE VIEW
                    recycleTodaysData = (RecyclerView) mView.findViewById(R.id.recycleTodaysData);
                    recycleTodaysData.setVisibility(View.VISIBLE);

                    recylerViewLayoutManager = new LinearLayoutManager(mActivity);
                    recycleTodaysData.setLayoutManager(recylerViewLayoutManager);

                    //GET DATA ACCORDING TO THE PARAMETERS
                    transactionsList = mDatabaseHelper.getTransactionDataForCustom(strTransactionType, allDates, lngFromDate, lngToDate, strGroupName, strParty, strBank);


                    if(transactionsList.size() != 0){
                        //SET INDIVIDUAL TRANSACTION ADAPTER
                        mTransactionForEachGroupAdapter = new TransactionForEachGroupAdapter(mContext, mActivity, transactionsList);
                        recycleTodaysData.setAdapter(mTransactionForEachGroupAdapter);
                    }

                    totalIncomeAmount = (TextView) mView.findViewById(R.id.textIncomeAmount);
                    totalExpenseAmount = (TextView) mView.findViewById(R.id.textExpenseAmount);
                    totalBalanceAmount = (TextView) mView.findViewById(R.id.textBalanceAmount);

                    addTotals();

                    if(tabId == Constants.SUMMARY_CUSTOM){
                        SummaryExporter.transactionList = transactionsList;
                    }


                }

                break;
        }

    }

    public void addTotals() {
        double totalIncome = 0;
        double totalExpense = 0;
        double totalBalance = 0;

        for (TransactionModel.DataBean transaction: transactionsList){
            if(transaction.getIntTrascationTypeID() == Constants.INCOME_TYPE_ID){
                totalIncome +=  transaction.getIntAmount();
            }else if(transaction.getIntTrascationTypeID() == Constants.EXPENSE_TYPE_ID){
                totalExpense +=  transaction.getIntAmount();
            }
        }
        totalBalance = totalIncome-totalExpense;

        total = totalBalance;

        totalIncomeAmount.setText(""+roundOff(totalIncome, 2));
        totalExpenseAmount.setText(""+roundOff(totalExpense, 2));
        totalBalanceAmount.setText(""+roundOff(totalBalance, 2));
        SummaryExporter.transactionTotal = roundOff(totalBalance, 2);
    }

    static double roundOff(double x, int position)
    {
        double a = x;
        double temp = Math.pow(10.0, position);
        a *= temp;
        a = Math.round(a);
        return (a / (double)temp);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {


        if (requestCode == 300 && data != null) {
            edtBank.setText(data.getStringExtra("VALUE"));
        }else if(requestCode == 200 && data != null){
            edtGroupName.setText(data.getStringExtra("VALUE"));
        }else if(requestCode == 100 && data != null){
            edtPartyName.setText(data.getStringExtra("VALUE"));
        }
    }

    //VALIDATE CUSTOM FILTER PARAMETERS
    private boolean checkValidation() {
        if(strTransactionType.trim().isEmpty()){
            CommonMethods.displayToast(mContext ,"Please select transaction type!");
            return false;
        }else if(edtFromDate.getText().toString().trim().isEmpty() && !checkALl.isChecked()){
            CommonMethods.displayToast(mContext ,"Please select start Date!");
            return false;
        }else if(edtToDate.getText().toString().trim().isEmpty() && !checkALl.isChecked()){
            CommonMethods.displayToast(mContext ,"Please select To Date!");
            return false;
        }
        return true;
    }



    private void openEndDatePickerDialog() {

        Date fromDate = null;
        DateFormat format = new SimpleDateFormat(Constants.COMMON_DATE_FORMAT);

        Calendar calenderToDate = Calendar.getInstance();
        Calendar calenderFromDate = Calendar.getInstance();

        try {
            fromDate = format.parse(edtFromDate.getText().toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        calenderFromDate.setTime(fromDate);

        calenderToDate.setTime(fromDate);

        calenderToDate.set(Calendar.DAY_OF_MONTH, calenderToDate.getActualMaximum(Calendar.DAY_OF_MONTH));

        mDatePickerDialog = com.wdullaer.materialdatetimepicker.date.DatePickerDialog.newInstance(this,
                calenderToDate.get(Calendar.YEAR),
                calenderToDate.get(Calendar.MONTH),
                calenderToDate.get(Calendar.DAY_OF_MONTH)
        );

        mDatePickerDialog.setMinDate(calenderFromDate);
        mDatePickerDialog.setMaxDate(calenderToDate);
        mDatePickerDialog.setCancelColor(Color.BLACK);
        mDatePickerDialog.setOkColor(Color.BLACK);
        mDatePickerDialog.show(getActivity().getFragmentManager(), "Datepickerdialog");
    }

    private void openStartDatePickerDialog() {
        Date fromDate = null;
        DateFormat format = new SimpleDateFormat(Constants.COMMON_DATE_FORMAT);

        Calendar calenderToDate = Calendar.getInstance();
        Calendar calenderFromDate = Calendar.getInstance();

        mDatePickerDialog = com.wdullaer.materialdatetimepicker.date.DatePickerDialog.newInstance(this,
                calenderToDate.get(Calendar.YEAR),
                calenderToDate.get(Calendar.MONTH),
                calenderToDate.get(Calendar.DAY_OF_MONTH)
        );


        mDatePickerDialog.setMaxDate(calenderFromDate);
        mDatePickerDialog.setCancelColor(Color.BLACK);
        mDatePickerDialog.setOkColor(Color.BLACK);
        mDatePickerDialog.show(getActivity().getFragmentManager(), "Datepickerdialog");
    }

    @Override
    public void onDateSet(DatePickerDialog view, int intYear, int monthOfYear, int dayOfMonth) {


        int year = intYear;
        int month = (monthOfYear + 1);
        int day = dayOfMonth;

        if (isFromStartDate) {
            edtFromDate.setText("" + day + "/" + month + "/" + year);
        } else {
            edtToDate.setText("" + day + "/" + month + "/" + year);
        }
    }
}