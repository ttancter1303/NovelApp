package com.example.comicapp.HomeFragment;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.comicapp.ComicFragment.NewComicFragment;
import com.example.comicapp.R;
import com.example.comicapp.RecycleAdapter.HomeReadHighestAdapter;
import com.example.comicapp.RecycleAdapter.AllNovelAdapter;
import com.example.comicapp.Repository.ViewModel.NovelViewModel;
import com.example.comicapp.data.Novel;

import java.util.List;


public class HomeFragment extends Fragment {
    NovelViewModel mViewModel;
    RecyclerView mRecyclerViewAllNovel;
    RecyclerView mRecyclerViewReadHighest;
    AllNovelAdapter mAllNovelAdapter;
    HomeReadHighestAdapter homeReadHighestAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerViewAllNovel = view.findViewById(R.id.rev_newComic);
        mAllNovelAdapter = new AllNovelAdapter();
        mRecyclerViewAllNovel.setAdapter(mAllNovelAdapter);
        mRecyclerViewAllNovel.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false));

        mRecyclerViewReadHighest = view.findViewById(R.id.rev_read_highest);
        homeReadHighestAdapter = new HomeReadHighestAdapter();
        mRecyclerViewReadHighest.setAdapter(homeReadHighestAdapter);
        mRecyclerViewReadHighest.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false));

        mViewModel = new ViewModelProvider(this).get(NovelViewModel.class);
        if(mViewModel.getAllNovel() != null){
            mViewModel.getAllNovel().observe(getViewLifecycleOwner(), new Observer<List<Novel>>() {
                @Override
                public void onChanged(List<Novel> novels) {
                    for (Novel novel : novels) {
                        mAllNovelAdapter.setData(novels);
                        homeReadHighestAdapter.setData(novels);
                    }
                }
            });
        }
        mAllNovelAdapter.setOnItemClickListener(new AllNovelAdapter.OnItemClickListener() {
            @Override
            public void onClick(View view, Novel novel) {
                Intent intent = new Intent(requireContext(), NewComicFragment.class);
                Bundle bundle = new Bundle();
                bundle.putString("id", novel.getId());
                bundle.putString("name",novel.getname());
                bundle.putString("intro",novel.getIntro());
                bundle.putString("image",novel.getImage());

                intent.putExtra("bundle",bundle);
                startActivity(intent);

            }
        });


    }


}