package com.example.comicapp.Repository;

import android.util.Log;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.comicapp.data.User;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserListRepository {
    FirebaseFirestore mFirestore;

    public UserListRepository(){
        mFirestore = FirebaseFirestore.getInstance();
    }

    public LiveData<List<User>> getAllUser(){
        MutableLiveData<List<User>> users = new MutableLiveData<>(new ArrayList<>());
        mFirestore.collection( "user")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        for (DocumentSnapshot document : value.getDocuments()) {
                            User user = new User(document.getId(),document.get("name",String.class));
                            Log.d("ttan", "onEvent: "+user.toString());
                        }
                    }
                });

        return users;
    }
}
