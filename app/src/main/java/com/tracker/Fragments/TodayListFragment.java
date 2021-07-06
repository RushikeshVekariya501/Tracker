package com.tracker.Fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tracker.Adapter.TransactionForEachGroupAdapter;
import com.tracker.CommonClasse.Constants;
import com.tracker.ModelClasses.TransactionModel;
import com.tracker.R;
import com.tracker.SqliteDatabase.DatabaseHelper;
import com.tracker.Utils.SummaryExporter;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class TodayListFragment extends Fragment {

    private Activity mActivity;
    private Context mContext;
    public static final String TAG = "TodayListFragment";

    private View mView;

    public NavController navController;
    private LinearLayoutManager recylerViewLayoutManager;
    private DatabaseHelper mDatabaseHelper;

    private TransactionForEachGroupAdapter mTransactionForEachGroupAdapter;
    private RecyclerView recycleTodaysData;
    RelativeLayout headerLayout;
    int tabId;

    ArrayList<TransactionModel.DataBean> transactionsList;
    public static ArrayList<TransactionModel.DataBean> printToday;
    public static ArrayList<TransactionModel.DataBean> printAll;
    public static ArrayList<TransactionModel.DataBean> printDay;
    public static ArrayList<TransactionModel.DataBean> printMonth;
    public static ArrayList<TransactionModel.DataBean> printYear;
    public static double total;

    private double totalIncome;
    private double totalExpense;
    private double totalBalance;
    TextView totalIncomeAmount;
    TextView totalExpenseAmount;
    TextView totalBalanceAmount;

    public TodayListFragment(int tabId, ArrayList<TransactionModel.DataBean> transactionsList){
        this.tabId = tabId;
        mDatabaseHelper= new DatabaseHelper(mContext);
        mDatabaseHelper.open();


        if(tabId == Constants.SUMMARY_TODAY){
            transactionsList = mDatabaseHelper.getTransactionDataForToday(true, null, null,null,null,null,null);
            printToday = transactionsList;
        }else if(tabId == Constants.SUMMARY_ALL){
            transactionsList = mDatabaseHelper.getTransactionDataForToday(null, null, null,null,null,null,true);
            printAll = transactionsList;
        }

        if(transactionsList != null){
            this.transactionsList = transactionsList;

            if(tabId == Constants.SUMMARY_DAY){
                printDay = transactionsList;
            }else if(tabId == Constants.SUMMARY_MONTH){
                printMonth = transactionsList;
            }else if(tabId == Constants.SUMMARY_YEAR){
                printYear = transactionsList;
            }
        }
    }

    public void addTotals() {
        totalIncome = 0;
        totalExpense = 0;
        totalBalance = 0;

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
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_today_list, container, false);
        setIds();
        return mView;
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    private void setIds() {

       mActivity = this.getActivity();
       mContext =  this.getActivity();

        headerLayout = (RelativeLayout) mView.findViewById(R.id.headerView);
        headerLayout.setVisibility(View.GONE);


        recycleTodaysData = (RecyclerView) mView.findViewById(R.id.recycleTodaysData);
        recycleTodaysData.setVisibility(View.VISIBLE);


        recylerViewLayoutManager = new LinearLayoutManager(mActivity);
        recycleTodaysData.setLayoutManager(recylerViewLayoutManager);

        if(transactionsList.size() != 0){
            //SET INDIVIDUAL TRANSACTIONS ADAPTER
            mTransactionForEachGroupAdapter = new TransactionForEachGroupAdapter(mContext, mActivity, transactionsList);
            recycleTodaysData.setAdapter(mTransactionForEachGroupAdapter);
        }

        totalIncomeAmount = (TextView) mView.findViewById(R.id.textIncomeAmount);
        totalExpenseAmount = (TextView) mView.findViewById(R.id.textExpenseAmount);
        totalBalanceAmount = (TextView) mView.findViewById(R.id.textBalanceAmount);

        addTotals();

    }
}
