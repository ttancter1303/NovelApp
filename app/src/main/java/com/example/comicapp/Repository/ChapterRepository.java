package com.example.comicapp.Repository;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.comicapp.data.Chapter;
import com.example.comicapp.data.Novel;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class ChapterRepository {
    FirebaseFirestore mFirestore;
    FirebaseStorage storage;
    StorageReference mStorageReference;

    public ChapterRepository() {
        storage = FirebaseStorage.getInstance();
        mFirestore = FirebaseFirestore.getInstance();
    }
    public LiveData<List<Chapter>> getAllChapter(String novelID){
        MutableLiveData<List<Chapter>> chapters = new MutableLiveData<>(new ArrayList<>());
        mFirestore.collection("novel")
                .document(novelID)
                .collection("chapter")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        List<Chapter> chapterList = new ArrayList<>();
                        for (DocumentSnapshot document : value.getDocuments()){
                            String id = document.getId();
                            String name = document.get("name",String.class);
                            String content = document.get("content",String.class);
                            Chapter chapter = new Chapter(id,name,content);
                            chapterList.add(chapter);
                            chapters.setValue(chapterList);

                        }
                    }
                });

        return chapters;
    }
}
