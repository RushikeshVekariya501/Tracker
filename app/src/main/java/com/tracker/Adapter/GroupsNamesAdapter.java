package com.tracker.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.navigation.NavController;
import androidx.recyclerview.widget.RecyclerView;

import com.tracker.Activities.AddIncomExpenseActivity;
import com.tracker.CommonClasse.Constants;
import com.tracker.ModelClasses.GroupNames;
import com.tracker.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GroupsNamesAdapter extends RecyclerView.Adapter<GroupsNamesAdapter.ViewHolder> {



    private View view;
    private Context mContext;
    private Activity mActivity;
    private List<GroupNames.DataBean> groupArrayList = new ArrayList<>();
    private GroupsNamesAdapter.ViewHolder viewHolder;
    private NavController navController;

    public GroupsNamesAdapter(Context mContext, Activity mActivity, ArrayList<GroupNames.DataBean> groupArrayList) {

        this.mContext = mContext;
        this.mActivity = mActivity;
        this.groupArrayList=groupArrayList;

        //Simple reverse collection list
        Collections.reverse(this.groupArrayList);


    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView txtGroupName ;
        private final RelativeLayout relativeItemClick;

        public ViewHolder(View mView) {

            super(mView);
            txtGroupName = (TextView) mView.findViewById(R.id.txtGroupName);
            relativeItemClick = (RelativeLayout) mView.findViewById(R.id.relativeItemClick);
        }
    }

    @Override
    public GroupsNamesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        view = LayoutInflater.from(mActivity).inflate(R.layout.raw_groups_names, parent, false);

        view.setTag(viewHolder);
        viewHolder = new GroupsNamesAdapter.ViewHolder(view);


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final GroupsNamesAdapter.ViewHolder holder, final int position) {

        //SET GROUP NAME
        holder.txtGroupName.setText(""+groupArrayList.get(position).getName());

        //SET ONCLICK
        holder.relativeItemClick.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Intent mIntent = new Intent(mContext, AddIncomExpenseActivity.class);
                mIntent.putExtra(Constants.GROUP_NAME, groupArrayList.get(position).getName());
                mIntent.putExtra(Constants.GROUP_ID, groupArrayList.get(position).getId());
                mContext.startActivity(mIntent);

            }
        });


    }


    @Override
    public int getItemCount() {

        return groupArrayList.size();
    }

}