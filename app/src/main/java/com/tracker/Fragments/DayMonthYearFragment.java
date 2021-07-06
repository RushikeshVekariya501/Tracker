package com.tracker.Fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tracker.Adapter.DayMonthYearAdapter;
import com.tracker.CommonClasse.CommonMethods;
import com.tracker.CommonClasse.Constants;
import com.tracker.ModelClasses.TransactionModel;
import com.tracker.R;
import com.tracker.SqliteDatabase.DatabaseHelper;

import java.util.ArrayList;
import java.util.HashMap;

public class DayMonthYearFragment extends Fragment {

    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 1;
    public static final String TAG = "DayMonthYearFragment";


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

    public DayMonthYearFragment(int tabId) {
        this.tabId = tabId;
        mDatabaseHelper= new DatabaseHelper(mContext);
        mDatabaseHelper.open();
        transactionsList = mDatabaseHelper.getTransactionData();

        getData();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = this.getActivity();
        mContext =  this.getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_day_month_year_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            recyclerView.setAdapter(new DayMonthYearAdapter(mContext, mActivity, listViewValues, listViewData, tabId));
        }
        return view;
    }

    // GET TRANSACTIONS LIST FROM DATABASE ACCORDING TO THE TAB CHOSEN
    void getData(){
        String listTitle = "";
        ArrayList<TransactionModel.DataBean> sortedList = null;

        listViewValues = new ArrayList<>();

        if(tabId == Constants.SUMMARY_DAY){
            for (int position = 0; position<transactionsList.size(); position++){
                if(position==0){
                    listTitle = transactionsList.get(position).getStrDay()+" "+transactionsList.get(position).getStrMonthYear();
                    sortedList = new ArrayList<>();
                    listViewValues = new ArrayList<>();
                }

                if(listTitle.equals(transactionsList.get(position).getStrDay()+" "+transactionsList.get(position).getStrMonthYear())){
                    sortedList.add(transactionsList.get(position));
                }else{
                    listViewData.put(listTitle, sortedList);
                    listViewValues.add(listTitle);
                    sortedList = new ArrayList<>();
                    sortedList.add(transactionsList.get(position));
                    listTitle = transactionsList.get(position).getStrDay()+" "+transactionsList.get(position).getStrMonthYear();
                }

                if(position == transactionsList.size()-1){
                    listViewData.put(listTitle, sortedList);
                    listViewValues.add(listTitle);
                }

            }
        }else if(tabId == Constants.SUMMARY_MONTH){
            for (int position = 0; position<transactionsList.size(); position++){
                if(position==0){
                    listTitle = CommonMethods.getStringFullMonths(transactionsList.get(position).getStrMonth()) +" "+transactionsList.get(position).getStrYear();
                    sortedList = new ArrayList<>();
                    listViewValues = new ArrayList<>();
                }

                if(listTitle.equals(CommonMethods.getStringFullMonths(transactionsList.get(position).getStrMonth()) +" "+transactionsList.get(position).getStrYear())){
                    sortedList.add(transactionsList.get(position));
                }else{
                    listViewData.put(listTitle, sortedList);
                    listViewValues.add(listTitle);
                    sortedList = new ArrayList<>();
                    sortedList.add(transactionsList.get(position));
                    listTitle = CommonMethods.getStringFullMonths(transactionsList.get(position).getStrMonth()) +" "+transactionsList.get(position).getStrYear();
                }

                if(position == transactionsList.size()-1){
                    listViewData.put(listTitle, sortedList);
                    listViewValues.add(listTitle);
                }

            }
        }else if(tabId == Constants.SUMMARY_YEAR){
            for (int position = 0; position<transactionsList.size(); position++){
                if(position==0){
                    listTitle = transactionsList.get(position).getStrYear();
                    sortedList = new ArrayList<>();
                    listViewValues = new ArrayList<>();
                }

                if(listTitle.equals(transactionsList.get(position).getStrYear())){
                    sortedList.add(transactionsList.get(position));
                }else{
                    listViewData.put(listTitle, sortedList);
                    listViewValues.add(listTitle);
                    sortedList = new ArrayList<>();
                    sortedList.add(transactionsList.get(position));
                    listTitle = transactionsList.get(position).getStrYear();
                }

                if(position == transactionsList.size()-1){
                    listViewData.put(listTitle, sortedList);
                    listViewValues.add(listTitle);
                }

            }
        }
    }
}