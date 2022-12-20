package com.example.comicapp.ViewPager2Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.comicapp.HistoryFragment.NovelMainContentFragment;
import com.example.comicapp.HistoryFragment.ComicVolFragment;
import com.example.comicapp.HistoryFragment.NovelContentFragment1;

public class ComicDetailAdapter extends FragmentStateAdapter {
    public ComicDetailAdapter(@NonNull NovelMainContentFragment fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new NovelContentFragment1();
            case 1:
                return new ComicVolFragment();
            default:
                return new NovelContentFragment1();
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
