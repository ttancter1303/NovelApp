package com.example.comicapp.Repository.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.comicapp.Repository.NovelRepository;
import com.example.comicapp.data.Novel;

import java.util.ArrayList;
import java.util.List;

public class NovelViewModel extends ViewModel {
    NovelRepository mNovelRepository;
    LiveData<List<Novel>> mAllNovel;
    LiveData<List<Novel>> mNovelCategory;
    LiveData<List<Novel>> mNewNovel;

    public NovelViewModel(){
        mNovelRepository = new NovelRepository();
        mAllNovel = mNovelRepository.getAllNovelv2();
        mNewNovel = mNovelRepository.getNewNovel();
    }
    public LiveData<List<Novel>> getAllNovel(){
        return mAllNovel;
    }
    public LiveData<List<Novel>> getNewNovel(){return mNewNovel;}
    public LiveData<List<Novel>> getAllNovelCategory(String userID){
        mNovelCategory = mNovelRepository.getAllNovelCategory(userID);
        return  mNovelCategory;
    }
}
