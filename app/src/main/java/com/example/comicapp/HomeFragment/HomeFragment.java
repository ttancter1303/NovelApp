package com.example.comicapp.HomeFragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.comicapp.R;
import com.example.comicapp.RecycleAdapter.ComicDetailRVAdapter;
import com.example.comicapp.RecycleAdapter.HomeReadHighestAdapter;
import com.example.comicapp.RecycleAdapter.NewComicAdapter;
import com.example.comicapp.Repository.ViewModel.NovelViewModel;
import com.example.comicapp.data.Novel;

import java.util.List;


public class HomeFragment extends Fragment {
    NovelViewModel mViewModel;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(NovelViewModel.class);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerView = view.findViewById(R.id.rev_newComic);
        NewComicAdapter adapterRecycle = new NewComicAdapter();
        recyclerView.setAdapter(adapterRecycle);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false));

        RecyclerView recyclerView2 = view.findViewById(R.id.rev_read_highest);
        HomeReadHighestAdapter homeReadHighestAdapter = new HomeReadHighestAdapter();
        recyclerView2.setAdapter(homeReadHighestAdapter);
        recyclerView2.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false));

        if(mViewModel.getAllNovel() != null){
            mViewModel.getAllNovel().observe(getViewLifecycleOwner(), new Observer<List<Novel>>() {
                @Override
                public void onChanged(List<Novel> novels) {
                    for (Novel novel : novels) {
                        Log.d("ttan", "onChanged: "+ novel);
                    }
                }
            });
        }

    }
}