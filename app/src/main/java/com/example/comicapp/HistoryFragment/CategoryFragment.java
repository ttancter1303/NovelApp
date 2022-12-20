package com.example.comicapp.HistoryFragment;

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

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.comicapp.R;
import com.example.comicapp.RecycleAdapter.HistoryRecycleAdapter;
import com.example.comicapp.Repository.NovelRepository;
import com.example.comicapp.Repository.ViewModel.NovelViewModel;
import com.example.comicapp.data.Novel;

import java.util.ArrayList;
import java.util.List;

public class CategoryFragment extends Fragment {
    HistoryRecycleAdapter adapter;
    NavController mController;
    NovelViewModel mNovelViewModel;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_category, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        RecyclerView recyclerView = view.findViewById(R.id.recycleViewCategory);
        adapter = new HistoryRecycleAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mController = Navigation.findNavController(requireActivity(), R.id.fragment_host_container);
        adapter.setOnItemClickListener(new HistoryRecycleAdapter.OnItemClickListener() {
            @Override
            public void onClick(View view, Novel novel) {
                Bundle bundle = new Bundle();
                bundle.putString("id",novel.getId());
                mController.navigate(R.id.comicDetailFragment);
            }
        });

        mNovelViewModel = new ViewModelProvider(this).get(NovelViewModel.class);
        if(mNovelViewModel.getAllNovel() != null){
            mNovelViewModel.getAllNovel().observe(getViewLifecycleOwner(), new Observer<List<Novel>>() {
                @Override
                public void onChanged(List<Novel> novels) {
                    for (Novel novel : novels) {
                        adapter.setData(novels);
                    }
                }
            });
        }
    }
}