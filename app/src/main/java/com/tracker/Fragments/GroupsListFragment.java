package com.tracker.Fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tracker.Adapter.GroupsNamesAdapter;
import com.tracker.R;
import com.tracker.SqliteDatabase.DatabaseHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class GroupsListFragment extends Fragment {

    private Activity mActivity;
    private Context mContext;
    private static final String TAG = "GroupsListFragment";

    private View mView;
    public NavController navController;

    private RecyclerView recycleGroupList;
    private LinearLayoutManager recylerViewLayoutManager;
    private DatabaseHelper mDatabaseHelper;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ((AppCompatActivity)getActivity()).setTitle(getString(R.string.strGroups));
        mView = inflater.inflate(R.layout.group_list_fragment, container, false);

        setIds();
        return mView;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void setIds() {

       mActivity = getActivity();
       mContext = getActivity();

       mDatabaseHelper= new DatabaseHelper(mContext);
       mDatabaseHelper.open();

        recycleGroupList = (RecyclerView) mView.findViewById(R.id.recycleGroupList);
        recylerViewLayoutManager = new LinearLayoutManager(mActivity);
        recycleGroupList.setLayoutManager(recylerViewLayoutManager);

        navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);

        if(mDatabaseHelper.getAllGroups().size() != 0) {
            //SET GROUP ADAPTER
            GroupsNamesAdapter mGroupsNamesAdapter = new GroupsNamesAdapter(mContext, mActivity, mDatabaseHelper.getAllGroups());
            recycleGroupList.setAdapter(mGroupsNamesAdapter);
        }

    }
}
