package com.example.comicapp.HomeFragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.comicapp.R;
import com.example.comicapp.RecycleAdapter.ComicDetailRVAdapter;
import com.example.comicapp.RecycleAdapter.HomeReadHighestAdapter;
import com.example.comicapp.RecycleAdapter.NewComicAdapter;


public class HomeFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
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
    }
}