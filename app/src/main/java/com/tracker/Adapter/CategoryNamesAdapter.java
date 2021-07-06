package com.tracker.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.navigation.NavController;
import androidx.recyclerview.widget.RecyclerView;

import com.tracker.Activities.CategoryListActivity;
import com.tracker.ModelClasses.CategoryNamesModel;
import com.tracker.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CategoryNamesAdapter extends RecyclerView.Adapter<CategoryNamesAdapter.ViewHolder> {



    private View view;
    private Context mContext;
    private CategoryListActivity mActivity;
    private List<CategoryNamesModel.DataBean> mCatoogoryNames = new ArrayList<>();
    private CategoryNamesAdapter.ViewHolder viewHolder;
    private NavController navController;

    public CategoryNamesAdapter(Context mContext, CategoryListActivity mActivity, ArrayList<CategoryNamesModel.DataBean> categoryArrayList) {

        this.mContext = mContext;
        this.mActivity = mActivity;
        this.mCatoogoryNames=categoryArrayList;

        //Simple reverse collection list
        Collections.reverse(this.mCatoogoryNames);


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
    public CategoryNamesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        view = LayoutInflater.from(mActivity).inflate(R.layout.raw_party_names, parent, false);

        view.setTag(viewHolder);
        viewHolder = new CategoryNamesAdapter.ViewHolder(view);


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final CategoryNamesAdapter.ViewHolder holder, final int position) {

        //SET GROUP NAME
        holder.txtPartyName.setText(""+mCatoogoryNames.get(position).getName());

        //SET ONCLICK
        holder.relativeItemClick.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                    //PARTY NAME INTERFACE
                    mActivity.commonStringInterface(mCatoogoryNames.get(position).getName());

            }
        });


    }


    @Override
    public int getItemCount() {

        return mCatoogoryNames.size();
    }

}