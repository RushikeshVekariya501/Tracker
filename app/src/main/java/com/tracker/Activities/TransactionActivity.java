package com.tracker.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.tracker.CommonClasse.CommonMethods;
import com.tracker.CommonClasse.Constants;
import com.tracker.ModelClasses.TransactionModel;
import com.tracker.R;
import com.tracker.SqliteDatabase.DatabaseHelper;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class TransactionActivity extends AppCompatActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener {

    private Activity mActivity;
    private Context mContext;
    private static final String TAG = "TransactionActivity";
    private ImageView imgSelected, imgSummary, imgHome, imgBack, imgReport;
    private TextView txtGroupName, txtSave, txtTitle;
    private RadioGroup rgSelectGroup;
    private RadioButton rbtBank, rbtCash;
    private EditText edtCategory, edtBank, edtAmount, edtTitle, edtPartyName, edtDate;
    private RelativeLayout relativeGallary, relativeCamara, relativePic;
    private DatePickerDialog mDatePickerDialog;
    private TextInputLayout inputBank;

    private Long intTransactionGroupID;
    private String strTrasactionType = "";
    private String strBankOrCash = "Cash";
    private Uri imageUri;
    private String strImageURL = "";
    private DatabaseHelper mDatabaseHelper;
    private int intMonth = 0;
    private int intDay = 0;
    private int intYear = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);

        setIds();
        setOncliks();
    }

    private void setIds() {

        mActivity = TransactionActivity.this;
        mContext = TransactionActivity.this;

        mDatabaseHelper = new DatabaseHelper(mContext);
        mDatabaseHelper.open();


        imgBack = (ImageView) findViewById(R.id.imgBack);
        imgHome = (ImageView) findViewById(R.id.imgHome);
        imgSummary = (ImageView) findViewById(R.id.imgSummary);
        imgReport = (ImageView) findViewById(R.id.imgReport);
        imgSelected = (ImageView) findViewById(R.id.imgSelected);

        txtTitle = (TextView) findViewById(R.id.txtTitle);
        txtSave = (TextView) findViewById(R.id.txtSave);
        txtGroupName = (TextView) findViewById(R.id.txtGroupName);

        rgSelectGroup = (RadioGroup) findViewById(R.id.rgSelectGroup);
        rbtCash = (RadioButton) findViewById(R.id.rbtCash);
        rbtBank = (RadioButton) findViewById(R.id.rbtBank);


        edtDate = (EditText) findViewById(R.id.edtDate);
        edtPartyName = (EditText) findViewById(R.id.edtPartyName);
        edtTitle = (EditText) findViewById(R.id.edtTitle);
        edtAmount = (EditText) findViewById(R.id.edtAmount);
        edtBank = (EditText) findViewById(R.id.edtBank);
        edtCategory = (EditText) findViewById(R.id.edtCategory);

        inputBank = (TextInputLayout) findViewById(R.id.inputBank);

        relativePic = (RelativeLayout) findViewById(R.id.relativePic);
        relativeCamara = (RelativeLayout) findViewById(R.id.relativeCamara);
        relativeGallary = (RelativeLayout) findViewById(R.id.relativeGallary);

        //SET TILE
        txtTitle.setText(getIntent().getStringExtra(Constants.INCOME_EXPENSE_TYPE));
        txtGroupName.setText(getIntent().getStringExtra(Constants.GROUP_NAME));

        //SET INCOME EXPENCE TYPE
        strTrasactionType = getIntent().getStringExtra(Constants.INCOME_EXPENSE_TYPE);

        //DISPLAY CURRENT TIME
        edtDate.setText(CommonMethods.getDateFromLong(System.currentTimeMillis()));

        //HIDE ICONE
        imgSummary.setVisibility(View.GONE);
        imgHome.setVisibility(View.GONE);
        imgReport.setVisibility(View.GONE);

        //SET GROUP ID
        intTransactionGroupID = getIntent().getLongExtra(Constants.GROUP_ID, 1L);





        Date today = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(today);
        intDay = cal.get(Calendar.DAY_OF_MONTH);
        intYear = cal.get(Calendar.YEAR);
        intMonth = (cal.get(Calendar.MONTH)+1);



        //CASH OR BANK CLICK
        rgSelectGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int childCount = group.getChildCount();
                for (int x = 0; x < childCount; x++) {
                    RadioButton rbtn = (RadioButton) group.getChildAt(x);

                    if (rbtn.getId() == checkedId) {
                        if (rbtn.getText().toString().equalsIgnoreCase("Cash")) {

                            //HIDE VIEW AS PAR SELECTION
                            edtBank.setVisibility(View.GONE);
                            inputBank.setVisibility(View.GONE);

                            //SELECTED TYPE
                            strBankOrCash = "Cash";

                            //CLEAR BANK VIEW
                            edtBank.setText("");

                        } else {

                            //HIDE VIEW AS PAR SELECTION
                            edtBank.setVisibility(View.VISIBLE);
                            inputBank.setVisibility(View.VISIBLE);

                            //SELECTED TYPE
                            strBankOrCash = "Bank";
                        }


                    }

                }

            }
        });


        //VIEW DATA
        if(getIntent().getBooleanExtra("IS_FROM_VIEW", true)){

            ArrayList<TransactionModel.DataBean> mTransactionList  = mDatabaseHelper.getTransactionDataFromID(getIntent().getLongExtra("TRANSACTION_ID",0));

            //SET TITLE
            txtTitle.setText(mTransactionList.get(0).getStrTransactionTypeName());

            edtDate.setText(CommonMethods.getDateFromLong(mTransactionList.get(0).getLngDateAndTime()));
            edtPartyName.setText(mTransactionList.get(0).getStrPartyName());
            edtTitle.setText(mTransactionList.get(0).getStrTitle());
            edtAmount.setText(""+mTransactionList.get(0).getIntAmount());
            edtBank.setText(""+mTransactionList.get(0).getStrBankName());
            edtCategory.setText(""+mTransactionList.get(0).getStrCategoryName());

            //SET RADIO BUTTON
            if(mTransactionList.get(0).getStrTransactionName().equalsIgnoreCase("Cash")){
                rbtCash.setChecked(true);
                rbtBank.setChecked(false);
                inputBank.setVisibility(View.GONE);
                edtBank.setVisibility(View.GONE);
            }else {
                rbtCash.setChecked(false);
                rbtBank.setChecked(true);
                inputBank.setVisibility(View.VISIBLE);
                edtBank.setVisibility(View.VISIBLE);
            }



            //SET IMAGE
            imgSelected.setImageURI(Uri.parse(mTransactionList.get(0).getStrImageURL()));


            //HIDE SAVE BUTTON
            txtSave.setVisibility(View.GONE);
            edtDate.setClickable(false);
            edtPartyName.setClickable(false);
            edtTitle.setClickable(false);
            edtAmount.setClickable(false);
            edtCategory.setClickable(false);
            edtBank.setClickable(false);

            edtDate.setFocusable(false);
            edtPartyName.setFocusable(false);
            edtTitle.setFocusable(false);
            edtAmount.setFocusable(false);
            edtCategory.setFocusable(false);
            edtBank.setFocusable(false);

            edtDate.setFocusableInTouchMode(false);
            edtPartyName.setFocusableInTouchMode(false);
            edtTitle.setFocusableInTouchMode(false);
            edtAmount.setFocusableInTouchMode(false);
            edtCategory.setFocusableInTouchMode(false);
            edtBank.setFocusableInTouchMode(false);

            edtDate.setEnabled(false);
            edtPartyName.setEnabled(false);
            edtTitle.setEnabled(false);
            edtAmount.setEnabled(false);
            edtCategory.setEnabled(false);
            edtBank.setEnabled(false);

            rgSelectGroup.setEnabled(false);


        }




    }



    private void setOncliks() {

        imgBack.setOnClickListener(this);
        imgHome.setOnClickListener(this);
        imgSummary.setOnClickListener(this);
        relativePic.setOnClickListener(this);
        relativeCamara.setOnClickListener(this);
        relativeGallary.setOnClickListener(this);
        edtDate.setOnClickListener(this);
        edtPartyName.setOnClickListener(this);
        edtCategory.setOnClickListener(this);
        edtBank.setOnClickListener(this);
        txtSave.setOnClickListener(this);
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
            case R.id.edtDate:
                openDatePickerDialog();
                break;
            case R.id.edtPartyName:

                Intent mIntentParty = new Intent(TransactionActivity.this, PartyListActivity.class);
                startActivityForResult(mIntentParty, 100);


                break;
            case R.id.edtCategory:

                Intent mIntentCategory = new Intent(TransactionActivity.this, CategoryListActivity.class);
                startActivityForResult(mIntentCategory, 200);

                break;
            case R.id.edtBank:

                Intent mIntentBank = new Intent(TransactionActivity.this, BanksListActivity.class);
                startActivityForResult(mIntentBank, 300);

                break;

            case R.id.relativeGallary:

                Intent galleryIntent = new Intent(Intent.ACTION_OPEN_DOCUMENT, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                final Intent chooserIntent = Intent.createChooser(galleryIntent, "Select Source");
                startActivityForResult(chooserIntent, 400);

                break;
            case R.id.relativeCamara:

                Intent camara = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(camara, 500);

                break;

            case R.id.txtSave:

                if (checkValidation()) {

                    int id = 0;
                    if (strTrasactionType.equalsIgnoreCase("Add Income")) {
                        id = 1;
                    } else {
                        id = 2;
                    }

                    //INSERT DATA IN TO DATABASE
                    mDatabaseHelper.insertTransactionData(System.currentTimeMillis(),
                            intTransactionGroupID,
                            strBankOrCash,
                            id,
                            strTrasactionType,
                            CommonMethods.getLongDateFromStringDate(edtDate.getText().toString()),
                            edtPartyName.getText().toString(),
                            edtTitle.getText().toString(),
                            Float.valueOf(edtAmount.getText().toString()),
                            edtBank.getText().toString(),
                            ""+CommonMethods.getStringMonths(intMonth)+" "+intYear,
                            strImageURL,
                            txtGroupName.getText().toString(),
                            edtCategory.getText().toString(),
                            intDay,
                            intMonth,
                            intYear);

                    finish();

                }

                break;

        }

    }

    //VALIDATE FORM DATA BEFORE SUBMIT
    private boolean checkValidation() {

        if (edtDate.getText().toString().trim().isEmpty()) {
            CommonMethods.buildDialog(mContext, R.style.DialogTheme, "Please select date");
            return false;
        } else if (edtPartyName.getText().toString().trim().isEmpty()) {
            CommonMethods.buildDialog(mContext, R.style.DialogTheme, "Please select party name");
            return false;
        } else if (edtTitle.getText().toString().trim().isEmpty()) {
            CommonMethods.buildDialog(mContext, R.style.DialogTheme, "Please enter title");
            return false;
        } else if (edtAmount.getText().toString().trim().isEmpty()) {
            CommonMethods.buildDialog(mContext, R.style.DialogTheme, "Please enter amount");
            return false;
        } else if (strBankOrCash.equalsIgnoreCase("Bank") && edtBank.getText().toString().trim().isEmpty()) {
            CommonMethods.buildDialog(mContext, R.style.DialogTheme, "Please select bank");
            return false;
        } else if (edtCategory.getText().toString().trim().isEmpty()) {
            CommonMethods.buildDialog(mContext, R.style.DialogTheme, "Please select category");
            return false;
        } else if (strImageURL.equalsIgnoreCase("")) {
            CommonMethods.buildDialog(mContext, R.style.DialogTheme, "Please select image");
            return false;
        } else {
            return true;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {


        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && data != null) {
            edtPartyName.setText(data.getStringExtra("VALUE"));
        } else if (requestCode == 200 && data != null) {
            edtCategory.setText(data.getStringExtra("VALUE"));
        } else if (requestCode == 300 && data != null) {
            edtBank.setText(data.getStringExtra("VALUE"));
        } else if (requestCode == 400 && data != null) {
            imageUri = data.getData();
            imgSelected.setImageURI(imageUri);
            strImageURL = "" + imageUri;
        } else if (requestCode == 500 && data != null) {
            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
            imgSelected.setImageBitmap(thumbnail);
        }
    }


    private void openDatePickerDialog() {

        Calendar calenderToDate = Calendar.getInstance();

        mDatePickerDialog = com.wdullaer.materialdatetimepicker.date.DatePickerDialog.newInstance(this,
                calenderToDate.get(Calendar.YEAR),
                calenderToDate.get(Calendar.MONTH),
                calenderToDate.get(Calendar.DAY_OF_MONTH)
        );


        mDatePickerDialog.setMaxDate(calenderToDate);
        mDatePickerDialog.setCancelColor(Color.BLACK);
        mDatePickerDialog.setOkColor(Color.BLACK);
        mDatePickerDialog.show(mActivity.getFragmentManager(), "Datepickerdialog");
    }

    @Override
    public void onDateSet(DatePickerDialog view, int selectedYear, int selectedMonth, int selectedDay) {

        intYear = selectedYear;
        intMonth = (selectedMonth + 1);
        intDay = selectedDay;

        edtDate.setText("" + intDay + "/" + intMonth + "/" + intYear);
    }



}
