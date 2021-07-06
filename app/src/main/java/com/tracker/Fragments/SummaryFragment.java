package com.tracker.Fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;

import com.google.android.material.navigation.NavigationView;
import com.tracker.R;
import com.tracker.SqliteDatabase.DatabaseHelper;

public class SummaryFragment extends Fragment implements  NavigationView.OnNavigationItemSelectedListener{

    private Activity mActivity;
    private Context mContext;
    private static final String TAG = "SummaryFragment";

    private View mView;

    public NavController navController;

    private DatabaseHelper mDatabaseHelper;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ((AppCompatActivity) getActivity()).setTitle(getString(R.string.strSummary));

        mView = inflater.inflate(R.layout.summury_fragment, container, false);

        setIds();
        setOnClicks();
        return mView;


    }

    private void setIds() {

        mActivity = getActivity();
        mContext = getActivity();

        mDatabaseHelper = new DatabaseHelper(mContext);
        mDatabaseHelper.open();

    }

    private void setOnClicks() {

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        return false;
    }
}
