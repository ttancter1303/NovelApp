package com.example.comicapp.ViewPager2Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.comicapp.HistoryFragment.CategoryFragment;
import com.example.comicapp.HistoryFragment.HistoryFragment;
import com.example.comicapp.HistoryFragment.HistoryRecyclerFragment;

public class HistoryViewPager2Adapter extends FragmentStateAdapter {
    public HistoryViewPager2Adapter(@NonNull HistoryFragment fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new HistoryRecyclerFragment();
            case 1:
//                return new ComicDetailFragment();
                return new CategoryFragment();
            default:
                return new HistoryRecyclerFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
