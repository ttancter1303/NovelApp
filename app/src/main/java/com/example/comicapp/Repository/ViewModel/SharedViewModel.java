package com.example.comicapp.Repository.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SharedViewModel extends ViewModel {
    private MutableLiveData<String> stringID = new MutableLiveData<>();
    public void setData(String item){
        stringID.setValue(item);
    }
    public LiveData<String> getSelectedItem(){
        return stringID;
    }
}
