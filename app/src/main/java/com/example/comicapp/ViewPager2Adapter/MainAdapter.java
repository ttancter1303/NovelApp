package com.example.comicapp.ViewPager2Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.comicapp.HistoryFragment.ComicDetailFragment;
import com.example.comicapp.HistoryFragment.HistoryFragment;
import com.example.comicapp.HomeFragment.HomeFragment;
import com.example.comicapp.UserFragment.UserFragment;

public class MainAdapter extends FragmentStateAdapter {
    public MainAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new HistoryFragment();
            case 1:
                return new HomeFragment();
            case 2:
//                return new NotificationFragment();
                return new ComicDetailFragment();
            case 3:
                return new UserFragment();
            default:
                return new HomeFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
