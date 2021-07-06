package com.tracker.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tracker.Adapter.CategoryNamesAdapter;
import com.tracker.CommonClasse.CommonMethods;
import com.tracker.Interfaces.CommonStringInterface;
import com.tracker.R;
import com.tracker.SqliteDatabase.DatabaseHelper;

public class CategoryListActivity extends AppCompatActivity implements View.OnClickListener, CommonStringInterface {

    private Activity mActivity;
    private Context mContext;
    private static final String TAG = "CategoryListActivity";
    private EditText edtLicenceOrMobile;
    private TextView txtSignIn;
    private ImageView imgSummary, imgHome, imgBack;
    private TextView txtTitle;
    private DatabaseHelper mDatabaseHelper;
    private LinearLayoutManager recylerViewLayoutManager;
    private RecyclerView recycleCategoryList;
    private CategoryNamesAdapter mCategoryNamesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_list);

        setIds();
        setOncliks();
    }

    private void setIds() {

        mActivity = CategoryListActivity.this;
        mContext = CategoryListActivity.this;

        mDatabaseHelper = new DatabaseHelper(mContext);
        mDatabaseHelper.open();

        imgBack = (ImageView) findViewById(R.id.imgBack);
        imgHome = (ImageView) findViewById(R.id.imgHome);
        imgSummary = (ImageView) findViewById(R.id.imgSummary);

        recycleCategoryList = (RecyclerView)findViewById(R.id.recycleCategoryList);
        recylerViewLayoutManager = new LinearLayoutManager(mActivity);
        recycleCategoryList.setLayoutManager(recylerViewLayoutManager);

        txtTitle = (TextView) findViewById(R.id.txtTitle);

        //SET TILE
        txtTitle.setText("Select Category");

        //HIDE HOME ICONE
        imgHome.setVisibility(View.GONE);

        //SET ICON
        imgSummary.setImageDrawable(getResources().getDrawable(R.drawable.plus));

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

                //OPEN ADD CATEGORY POPUP
                openCreateCategoryNameDialog();

                break;
        }
    }

    private void openCreateCategoryNameDialog() {

        // Create an alert builder
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setTitle("Add Category");

        // set the custom layout
        final View customLayout = getLayoutInflater().inflate(R.layout.add_group_dialog, null);
        builder.setView(customLayout);

        // add a button
        builder.setPositiveButton("Add",
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        // send data from the
                        // AlertDialog to the Activity
                        EditText editText = customLayout.findViewById(R.id.edtAddGroup);

                        if (!editText.getText().toString().isEmpty()) {
                            mDatabaseHelper.insertCategoryName(System.currentTimeMillis(), editText.getText().toString());

                            //REFRESH ADAPTER
                            setCategoryListAdpter();

                        } else {
                            CommonMethods.displayToast(mContext, "Please enter Category name");
                        }

                    }
                });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.cancel();
            }
        });

        // create and show
        // the alert dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void setCategoryListAdpter() {

        if(mDatabaseHelper.getPartyNameList().size() != 0) {
            //SET GROUP ADAPTER
            mCategoryNamesAdapter = new CategoryNamesAdapter(mContext, CategoryListActivity.this, mDatabaseHelper.getCategoryList());
            recycleCategoryList.setAdapter(mCategoryNamesAdapter);
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
