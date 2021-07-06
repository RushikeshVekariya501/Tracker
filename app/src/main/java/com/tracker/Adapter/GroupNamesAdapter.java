package com.tracker.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.navigation.NavController;
import androidx.recyclerview.widget.RecyclerView;

import com.tracker.Activities.GroupListActivity;
import com.tracker.ModelClasses.GroupNames;
import com.tracker.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GroupNamesAdapter extends RecyclerView.Adapter<GroupNamesAdapter.ViewHolder> {



    private View view;
    private Context mContext;
    private GroupListActivity mActivity;
    private List<GroupNames.DataBean> mGroupList = new ArrayList<>();
    private GroupNamesAdapter.ViewHolder viewHolder;
    private NavController navController;

    public GroupNamesAdapter(Context mContext, GroupListActivity mActivity, ArrayList<GroupNames.DataBean> mGroupList) {

        this.mContext = mContext;
        this.mActivity = mActivity;
        this.mGroupList=mGroupList;

        //Simple reverse collection list
        Collections.reverse(this.mGroupList);


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
    public GroupNamesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        view = LayoutInflater.from(mActivity).inflate(R.layout.raw_party_names, parent, false);

        view.setTag(viewHolder);
        viewHolder = new GroupNamesAdapter.ViewHolder(view);


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final GroupNamesAdapter.ViewHolder holder, final int position) {

        //SET GROUP NAME
        holder.txtPartyName.setText(""+mGroupList.get(position).getName());

        //SET ONCLICK
        holder.relativeItemClick.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                    //PARTY NAME INTERFACE
                    mActivity.commonStringInterface(mGroupList.get(position).getName());

            }
        });


    }


    @Override
    public int getItemCount() {

        return mGroupList.size();
    }

}