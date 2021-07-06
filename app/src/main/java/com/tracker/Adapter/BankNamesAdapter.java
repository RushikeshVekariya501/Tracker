package com.tracker.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.navigation.NavController;
import androidx.recyclerview.widget.RecyclerView;

import com.tracker.Activities.BanksListActivity;
import com.tracker.ModelClasses.BanksNamesModel;
import com.tracker.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BankNamesAdapter extends RecyclerView.Adapter<BankNamesAdapter.ViewHolder> {



    private View view;
    private Context mContext;
    private BanksListActivity mActivity;
    private List<BanksNamesModel.DataBean> mBankNames = new ArrayList<>();
    private BankNamesAdapter.ViewHolder viewHolder;
    private NavController navController;

    public BankNamesAdapter(Context mContext, BanksListActivity mActivity, ArrayList<BanksNamesModel.DataBean> mBankArrayList) {

        this.mContext = mContext;
        this.mActivity = mActivity;
        this.mBankNames=mBankArrayList;

        //Simple reverse collection list
        Collections.reverse(this.mBankNames);


    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView txtPartyName ;
        private final RelativeLayout relativeItemClick;

        public ViewHolder(View mView) {

            super(mView);
            txtPartyName = (TextView) mView.findViewById(R.id.txtPartyName);
            relativeItemClick = (RelativeLayout) mView.findViewById(R.id.relativeItemClick);

        }
    }

    @Override
    public BankNamesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        view = LayoutInflater.from(mActivity).inflate(R.layout.raw_party_names, parent, false);

        view.setTag(viewHolder);
        viewHolder = new BankNamesAdapter.ViewHolder(view);


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final BankNamesAdapter.ViewHolder holder, final int position) {

        //SET GROUP NAME
        holder.txtPartyName.setText(""+mBankNames.get(position).getName());

        //SET ONCLICK
        holder.relativeItemClick.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                    //PARTY NAME INTERFACE
                    mActivity.commonStringInterface(mBankNames.get(position).getName());

            }
        });


    }


    @Override
    public int getItemCount() {

        return mBankNames.size();
    }

}