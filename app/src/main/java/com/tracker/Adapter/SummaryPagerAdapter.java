package com.tracker.Adapter;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.tracker.CommonClasse.Constants;
import com.tracker.Fragments.ComingSoonFragment;
import com.tracker.Fragments.CustomFragment;
import com.tracker.Fragments.DayMonthYearFragment;
import com.tracker.Fragments.TodayListFragment;
import com.tracker.R;

public class SummaryPagerAdapter extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_today, R.string.tab_day, R.string.tab_month, R.string.tab_year, R.string.tab_custom, R.string.tab_all};
    private final Context mContext;

    public SummaryPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        switch(position){

            case 0: return new TodayListFragment(Constants.SUMMARY_TODAY, null);
            case 1: return new DayMonthYearFragment(Constants.SUMMARY_DAY);
            case 2: return new DayMonthYearFragment(Constants.SUMMARY_MONTH);
            case 3: return new DayMonthYearFragment(Constants.SUMMARY_YEAR);
            case 4: return new CustomFragment(Constants.SUMMARY_CUSTOM);
            case 5: return new TodayListFragment(Constants.SUMMARY_ALL, null);
            default: return ComingSoonFragment.newInstance(position + 1);
        }
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        // Show 6 total pages.
        return 6;
    }
}
