package com.example.comicapp.HomeFragment;

import android.os.Bundle;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ShareViewModel extends ViewModel {
    MutableLiveData<Bundle> liveData = new MutableLiveData<>();

    public void setLiveData(MutableLiveData<Bundle> liveData) {
        this.liveData = liveData;
    }
    public MutableLiveData<Bundle> getText(){
        return liveData;
    }
}
