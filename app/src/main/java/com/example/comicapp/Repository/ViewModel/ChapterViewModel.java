package com.example.comicapp.Repository.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.comicapp.Repository.ChapterRepository;
import com.example.comicapp.Repository.NovelRepository;
import com.example.comicapp.data.Chapter;

import java.util.List;

public class ChapterViewModel extends ViewModel {
    ChapterRepository mChapterRepository;
    LiveData<List<Chapter>> mAllChapter;
    public ChapterViewModel(){
        mChapterRepository = new ChapterRepository();
    }
    public LiveData<List<Chapter>> getAllChapter(String novelId){
        mAllChapter = mChapterRepository.getAllChapter(novelId);
        return mAllChapter;
    }
}
