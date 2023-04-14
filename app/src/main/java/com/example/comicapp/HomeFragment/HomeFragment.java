package com.example.comicapp.HomeFragment;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.comicapp.R;
import com.example.comicapp.RecycleAdapter.HistoryRecycleAdapter;
import com.example.comicapp.RecycleAdapter.AllNovelAdapter;
import com.example.comicapp.RecycleAdapter.NewNovelAdapter;
import com.example.comicapp.Repository.ViewModel.ItemClickAllNovelViewModel;
import com.example.comicapp.Repository.ViewModel.NovelViewModel;
import com.example.comicapp.Repository.ViewModel.SharedViewModel;
import com.example.comicapp.data.Novel;

import java.util.List;


public class HomeFragment extends Fragment {
    private ItemClickAllNovelViewModel mModel;
    NovelViewModel mViewModel;
    RecyclerView mRecyclerViewAllNovel;
    RecyclerView mRecyclerViewReadHighest;
    NewNovelAdapter mNewNovelAdapter;
    AllNovelAdapter mAllNovelAdapter;
    NavController mController;
    SharedViewModel sharedViewModel;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mController = Navigation.findNavController(requireActivity(), R.id.fragment_host_container);
        mRecyclerViewAllNovel = view.findViewById(R.id.rev_newComic);
        mNewNovelAdapter = new NewNovelAdapter();
        mRecyclerViewAllNovel.setAdapter(mNewNovelAdapter);
        mRecyclerViewAllNovel.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false));

        mRecyclerViewReadHighest = view.findViewById(R.id.rev_read_highest);
        mAllNovelAdapter = new AllNovelAdapter();
        mRecyclerViewReadHighest.setAdapter(mAllNovelAdapter);
        mRecyclerViewReadHighest.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false));

        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

        mModel = new ViewModelProvider(this).get(ItemClickAllNovelViewModel.class);
        mAllNovelAdapter.setOnItemClickListener(new HistoryRecycleAdapter.OnItemClickListener() {
            @Override
            public void onClick(View view, Novel novel) {
                // cần sử dụng shared viewmodel để truyền data tới
                mModel.getSelectedItem().observe(getViewLifecycleOwner(), item -> {
                    item.setChapters(novel.getChapters());
                });

                Bundle bundle = new Bundle();
                bundle.putString("id",novel.getId());
                mController.navigate(R.id.comicDetailFragment,bundle);

            }
        });

        mViewModel = new ViewModelProvider(this).get(NovelViewModel.class);
        if(mViewModel.getAllNovel() != null){
            mViewModel.getAllNovel().observe(getViewLifecycleOwner(), new Observer<List<Novel>>() {
                @Override
                public void onChanged(List<Novel> novels) {
                    for (Novel novel : novels) {
                        mAllNovelAdapter.setData(novels);
                    }
                }
            });
        }
        if (mViewModel.getNewNovel() != null){
            mViewModel.getNewNovel().observe(getViewLifecycleOwner(), new Observer<List<Novel>>() {
                @Override
                public void onChanged(List<Novel> novels) {
                    mNewNovelAdapter.setData(novels);
                }
            });
        }


        mNewNovelAdapter.setOnItemClickListener(new NewNovelAdapter.OnItemClickListener() {
            @Override
            public void onClick(View view, Novel novel) {
                sharedViewModel.setData(novel.getId());

//                mController.navigate(R.id.comicDetailFragment,bundle);

            }
        });
    }


}