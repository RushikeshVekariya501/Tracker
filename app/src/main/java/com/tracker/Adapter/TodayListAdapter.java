package com.tracker.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.navigation.NavController;
import androidx.recyclerview.widget.RecyclerView;

import com.tracker.Activities.AddIncomExpenseActivity;
import com.tracker.Activities.TransactionActivity;
import com.tracker.CommonClasse.Constants;
import com.tracker.ModelClasses.TransactionModel;
import com.tracker.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class TodayListAdapter extends RecyclerView.Adapter<TodayListAdapter.ViewHolder> {


    private View view;
    private Context mContext;
    private AddIncomExpenseActivity mActivity;
    private List<TransactionModel.DataBean> mTransactionData = new ArrayList<>();
    private TodayListAdapter.ViewHolder viewHolder;
    private NavController navController;

    public TodayListAdapter(Context mContext, AddIncomExpenseActivity mActivity, ArrayList<TransactionModel.DataBean> groupArrayList) {

        this.mContext = mContext;
        this.mActivity = mActivity;
        this.mTransactionData = groupArrayList;

        //Simple reverse collection list
        Collections.reverse(this.mTransactionData);

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {


        private final RelativeLayout relativeItemClick;
        private final TextView txtAmountTotal, txtTime, txtCateory, txtPartyName, txtGroupName, txtDate, txtMonth, txtDescription, txtTransactionType, txtCurrency;

        public ViewHolder(View mView) {

            super(mView);

            txtMonth = (TextView) mView.findViewById(R.id.txtMonth);
            txtDate = (TextView) mView.findViewById(R.id.txtDate);
            txtGroupName = (TextView) mView.findViewById(R.id.txtGroupName);
            txtPartyName = (TextView) mView.findViewById(R.id.txtPartyName);
            txtDescription = (TextView) mView.findViewById(R.id.txtDescription);
            txtTransactionType = (TextView) mView.findViewById(R.id.txtTransactionType);
            txtCateory = (TextView) mView.findViewById(R.id.txtCateory);
            txtTime = (TextView) mView.findViewById(R.id.txtTime);
            txtAmountTotal = (TextView) mView.findViewById(R.id.txtAmountTotal);
            txtCurrency = (TextView) mView.findViewById(R.id.txtCurrency);

            relativeItemClick = (RelativeLayout) mView.findViewById(R.id.relativeItemClick);

        }
    }

    @Override
    public TodayListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        view = LayoutInflater.from(mActivity).inflate(R.layout.raw_transaction_for_each_group, parent, false);

        view.setTag(viewHolder);
        viewHolder = new TodayListAdapter.ViewHolder(view);


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final TodayListAdapter.ViewHolder holder, final int position) {

        DateFormat format = new SimpleDateFormat(Constants.COMMON_TIME_FORMAT);
        Date date = new Date(mTransactionData.get(position).getLngDateAndTime());

        //SET GROUP NAME
        holder.txtMonth.setText("" + mTransactionData.get(position).getStrMonthYear());
        holder.txtDate.setText("" + mTransactionData.get(position).getStrDay());
        holder.txtGroupName.setText("" + mTransactionData.get(position).getStrGroupName());
        holder.txtPartyName.setText("" + mTransactionData.get(position).getStrPartyName());
        holder.txtDescription.setText("" + mTransactionData.get(position).getStrTitle());
        holder.txtTransactionType.setText("" + mTransactionData.get(position).getStrTransactionName());
        holder.txtCateory.setText("" + mTransactionData.get(position).getStrCategoryName());
        holder.txtTime.setText("" + format.format(date));
        holder.txtAmountTotal.setText("" + mTransactionData.get(position).getIntAmount());
        holder.txtCurrency.setText(""+ mContext.getString(R.string.txtCurrencySymbol));

        if (mTransactionData.get(position).getIntTrascationTypeID() == 1) {
            holder.txtAmountTotal.setTextColor(mContext.getResources().getColor(R.color.colorGreenDark));
        } else {
            holder.txtAmountTotal.setTextColor(mContext.getResources().getColor(R.color.colorRedDark));
            holder.txtAmountTotal.setText("" + mTransactionData.get(position).getIntAmount());
        }

        if(holder.txtAmountTotal.getText().toString().length() > 2){
            holder.txtAmountTotal.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10f);
        }


        //SET ONCLICK
        holder.relativeItemClick.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Intent mIntent = new Intent(mActivity, TransactionActivity.class);
                mIntent.putExtra("IS_FROM_VIEW", true);
                mIntent.putExtra("TRANSACTION_ID", mTransactionData.get(position).getLngTransactionID());
                mActivity.startActivity(mIntent);


            }
        });


    }


    @Override
    public int getItemCount() {

        return mTransactionData.size();
    }

}