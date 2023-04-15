package com.example.comicapp.Repository.ViewModel;

import android.net.Uri;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SharedViewModel extends ViewModel {
    private MutableLiveData<String> stringID = new MutableLiveData<>("7RijD0lXUf6zZUHsTqJW");
    private MutableLiveData<Uri> uriMutableLiveData = new MutableLiveData<>();

    public void setData(Uri item){
        uriMutableLiveData.setValue(item);
    }
    public void setData(String item){
        stringID.setValue(item);
    }
    public LiveData<String> getData(){
        return stringID;
    }
    public LiveData<Uri> getUri(){return uriMutableLiveData;}
}
