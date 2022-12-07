package com.example.comicapp.Repository.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.comicapp.Repository.NovelRepository;
import com.example.comicapp.data.Novel;

import java.util.ArrayList;
import java.util.List;

public class NovelViewModel extends ViewModel {
    NovelRepository mNovelRepository;
    LiveData<ArrayList<Novel>> mAllNovel;
    public NovelViewModel(){
        mNovelRepository = new NovelRepository();
        mAllNovel = mNovelRepository.getAllNovelv2();
    }
    public LiveData<ArrayList<Novel>> getAllNovel(){
        return mAllNovel;
    }
}
