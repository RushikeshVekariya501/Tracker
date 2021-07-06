package com.tracker.Activities;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.tracker.CommonClasse.CommonMethods;
import com.tracker.R;
import com.tracker.SqliteDatabase.DatabaseHelper;
import com.tracker.Utils.DownloadAsync;
import com.tracker.Utils.UploadFileAsync;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private Activity mActivity;
    private Context mContext;
    private static final String TAG = "MainActivity";

    public Toolbar toolbar;
    public DrawerLayout drawerLayout;
    public NavController navController;
    public NavigationView navigationView;
    boolean doubleBackToExitPressedOnce = false;
    private DatabaseHelper mDatabaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupNavigation();
    }


    //Setting Up One Time Navigation
    private void setupNavigation() {

        mActivity = MainActivity.this;
        mContext = MainActivity.this;

        mDatabaseHelper = new DatabaseHelper(mContext);
        mDatabaseHelper.open();


        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigationView);
        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        navController = Navigation.findNavController(MainActivity.this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(MainActivity.this, navController, drawerLayout);
        NavigationUI.setupWithNavController(navigationView, navController);


        navigationView.setNavigationItemSelectedListener(MainActivity.this);

        //TAKE PERMISSION FIRST
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] { Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE }, 0);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 0) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
            }
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        return NavigationUI.navigateUp(drawerLayout, Navigation.findNavController(MainActivity.this, R.id.nav_host_fragment));
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        menuItem.setChecked(true);

        drawerLayout.closeDrawers();

        int id = menuItem.getItemId();

        switch (id) {

            case R.id.groups:
                navController.navigate(R.id.groupList);
                break;
            case R.id.driveBackup:
                //navController.navigate(R.id.driveBackup);
                new UploadFileAsync(mContext, navController).execute("");
                break;
            case R.id.driveRestore:
                //navController.navigate(R.id.driveRestore);

                takeRestoreConfirmation();
                break;


        }
        return true;

    }

    public void addGroups(MenuItem item) {

        // Create an alert builder
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setTitle("Add Group Name");

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

                                if(!editText.getText().toString().isEmpty()) {
                                    mDatabaseHelper.insertGroupName(System.currentTimeMillis(), editText.getText().toString());
                                    navController.navigate(R.id.groupList);
                                }else {
                                    CommonMethods.displayToast(mContext,"Please enter group name");
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

    public void openSummaryScreen(MenuItem item) {
        Intent nIntent= new Intent(MainActivity.this, SummaryActivity.class);
        startActivity(nIntent);
    }

    @Override
    protected void onResume() {
        super.onResume();
}

    @Override
    protected void onPause() {
        super.onPause();

    }
    //EXIT ON SECOND BACK PRESS
    @Override
    public void onBackPressed() {

        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }


        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;

        CommonMethods.displayToast(mContext, "Click back again");

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 20000);
    }

    //RESTORATION CONFIRMATION DIALOG BOX
    public void takeRestoreConfirmation(){
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setCancelable(false);
        builder.setTitle("Confirm Restoration ?");

        // set the custom layout
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        final View customLayout = inflater.inflate(R.layout.confirm_restore, null);
        TextView confirmRestoreText = (TextView) customLayout.findViewById(R.id.confirmRestoreText);
        confirmRestoreText.setText(mContext.getText(R.string.strConfirmRestore));
        builder.setView(customLayout);

        builder.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {new DownloadAsync(mContext, navController).execute();}
                });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                navController.navigate(R.id.groupList);
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
