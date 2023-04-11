package com.example.comicapp.HomeFragment;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ShareViewModel extends ViewModel {
    MutableLiveData<String> liveData = new MutableLiveData<>();

    public void setLiveData(MutableLiveData<String> liveData) {
        this.liveData = liveData;
    }
    public LiveData<String> getText(){
        return liveData;
    }
}
