package com.example.comicapp.Repository.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SharedViewModel extends ViewModel {
    private MutableLiveData<String> text = new MutableLiveData<>();
    public void setData(String item){
        text.setValue(item);
    }
    public LiveData<String> getSelectedItem(){
        return text;
    }
}
