package com.tracker.Adapter;

import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tracker.Activities.DayMonthYearActivity;
import com.tracker.ModelClasses.TransactionModel;
import com.tracker.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
public class DayMonthYearAdapter extends RecyclerView.Adapter<DayMonthYearAdapter.ViewHolder> {

    private Activity mActivity;
    private Context mContext;
    private final List<String> mItemString;
    private final HashMap<String, ArrayList<TransactionModel.DataBean>> mItemData;
    private final int tabId;

    public DayMonthYearAdapter(Context mContext, Activity mActivity, List<String> items, HashMap<String, ArrayList<TransactionModel.DataBean>> listViewData, int tabId) {
         this.mActivity = mActivity;
         this.mContext = mContext;
        mItemString = items;
        mItemData = listViewData;
        this.tabId = tabId;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_day_month_year, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mItemString.get(position);
        holder.mContentView.setText(holder.mItem);

        holder.dayMonthYearItem.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                String item = ((TextView) view.findViewById(R.id.content)).getText().toString();
                Intent mIntent = new Intent(mActivity, DayMonthYearActivity.class);
                mIntent.putExtra("TAB_ID", tabId);
                mIntent.putExtra("TAB_NAME", item);
                mIntent.putExtra("DATA", mItemData.get(item));
                mActivity.startActivity(mIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mItemString.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mContentView;
        public String mItem;
        public LinearLayout dayMonthYearItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mContentView = (TextView) view.findViewById(R.id.content);

            dayMonthYearItem = (LinearLayout) view.findViewById(R.id.dayMonthYearItem);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}