package com.tracker.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.navigation.NavController;
import androidx.recyclerview.widget.RecyclerView;

import com.tracker.Activities.PartyListActivity;
import com.tracker.ModelClasses.PartyNamesModel;
import com.tracker.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PartyNamesAdapter extends RecyclerView.Adapter<PartyNamesAdapter.ViewHolder> {



    private View view;
    private Context mContext;
    private PartyListActivity mActivity;
    private List<PartyNamesModel.DataBean> mPartyNames = new ArrayList<>();
    private PartyNamesAdapter.ViewHolder viewHolder;
    private NavController navController;

    public PartyNamesAdapter(Context mContext, PartyListActivity mActivity, ArrayList<PartyNamesModel.DataBean> groupArrayList) {

        this.mContext = mContext;
        this.mActivity = mActivity;
        this.mPartyNames=groupArrayList;

        //Simple reverse collection list
        Collections.reverse(this.mPartyNames);


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
    public PartyNamesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        view = LayoutInflater.from(mActivity).inflate(R.layout.raw_party_names, parent, false);

        view.setTag(viewHolder);
        viewHolder = new PartyNamesAdapter.ViewHolder(view);


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final PartyNamesAdapter.ViewHolder holder, final int position) {

        //SET GROUP NAME
        holder.txtPartyName.setText(""+mPartyNames.get(position).getName());

        //SET ONCLICK
        holder.relativeItemClick.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                    //PARTY NAME INTERFACE
                    mActivity.commonStringInterface(mPartyNames.get(position).getName());

            }
        });


    }


    @Override
    public int getItemCount() {

        return mPartyNames.size();
    }

}