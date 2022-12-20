package com.example.comicapp.Repository.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.comicapp.data.Novel;

public class ItemClickAllNovelViewModel extends ViewModel {
    private final MutableLiveData<Novel> selectedItem = new MutableLiveData<Novel>();
    public void selectItem(Novel novel) {
        selectedItem.setValue(novel);
    }
    public LiveData<Novel> getSelectedItem() {
        return selectedItem;
    }

}
