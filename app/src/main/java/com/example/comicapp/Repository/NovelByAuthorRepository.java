package com.example.comicapp.Repository;

import android.util.Log;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.comicapp.data.Comic;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class NovelByAuthorRepository {
    FirebaseFirestore mFirestore;

    public NovelByAuthorRepository(){
        mFirestore = FirebaseFirestore.getInstance();
    }

    public LiveData<List<Comic>> getAllNovel(){
        MutableLiveData<List<Comic>> comics = new MutableLiveData<>(new ArrayList<>());
       mFirestore.collection( "/author/ZwRd0wM9KxHAzr7NCAmf/novel")
               .addSnapshotListener(new EventListener<QuerySnapshot>() {
                   @Override
                   public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                       for (DocumentSnapshot document : value.getDocuments()) {
                           Comic comic = new Comic(document.getId(),document.get("name",String.class),document.get("image",String.class),document.get("status",Boolean.class));
                           Log.d("ttan", "onEvent: "+comic.toString());
                       }     
                   }
               });

        return comics;
    }
}
