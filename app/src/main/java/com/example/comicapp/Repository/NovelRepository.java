package com.example.comicapp.Repository;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;

import com.example.comicapp.data.Chapter;
import com.example.comicapp.data.Novel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.lang.ref.Reference;
import java.util.ArrayList;
import java.util.List;

public class NovelRepository {
    FirebaseFirestore mFirestore;
    FirebaseStorage storage;
    StorageReference mStorageReference;
    FirebaseUser mFirebaseUser;
    FirebaseAuth mFirebaseAuth;


    public NovelRepository() {
        storage = FirebaseStorage.getInstance();
        mFirestore = FirebaseFirestore.getInstance();

    }
    public MutableLiveData<List<Novel>> getAllNovelv2() {
        MutableLiveData<List<Novel>> novels = new MutableLiveData<>(new ArrayList<>());
        mFirestore.collection("novel")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        List<Novel> novelList = new ArrayList<>();
                        for (DocumentSnapshot document : value.getDocuments()) {
//                           Novel novel = new Novel(document.getId(),document.get("name",String.class),document.get("image",String.class),document.get("status",Boolean.class));
//                           Log.d("ttan", "onEvent: "+ novel.toString());
                            String id = document.getId();
                            String gioiThieu = document.get("intro", String.class);
                            String image = document.get("image", String.class);
                            String name = document.get("name", String.class);
                            Boolean status = document.get("status", Boolean.class);
                            String type = document.get("type",String.class);
                            DocumentReference author = document.get("author", DocumentReference.class);
                            Novel novel = new Novel(id, name, gioiThieu, image,type, author, status);
                            document.getReference().collection("chapter")
                                    .addSnapshotListener(new EventListener<QuerySnapshot>() {
                                        @Override
                                        public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                                            List<Chapter> chapters = null;
                                            for (DocumentSnapshot valueDocument : value.getDocuments()) {
                                                chapters = new ArrayList<>();
                                                String id = valueDocument.getId(); // xem lai id
                                                String name = valueDocument.get("name", String.class);
                                                String content = valueDocument.get("content", String.class);
                                                Chapter chapter = new Chapter(id, name, content);
                                                chapters.add(chapter);
                                            }
                                            novel.setChapters(chapters);
                                            novelList.add(novel);
                                            novels.setValue(novelList);
                                        }
                                    });

                        }

                    }
                });
        return novels;
    }
    public MutableLiveData<List<Novel>> getAllNovelCategory(String userID){
        MutableLiveData<List<Novel>> Listnovels = new MutableLiveData<>(new ArrayList<>());
        mFirestore.collection("user").document(userID)
                .collection("novel_mark").addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        List<Novel> novels = new ArrayList<>();
                        for (DocumentSnapshot document : value.getDocuments()) {
                            mFirestore.collection("novel").document(document.get("novel_id",String.class)).addSnapshotListener(new EventListener<DocumentSnapshot>() {
                                @Override
                                public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                                    String id = value.getId();
                                    String gioiThieu = value.get("intro", String.class);
                                    String image = value.get("image", String.class);
                                    String name = value.get("name", String.class);
                                    Boolean status = value.get("status", Boolean.class);
                                    String type = value.get("type",String.class);
                                    DocumentReference author = value.get("author", DocumentReference.class);
                                    Novel novel = new Novel(id, name, gioiThieu, image,type, author, status);
                                    value.getReference().collection("chapter")
                                            .addSnapshotListener(new EventListener<QuerySnapshot>() {
                                                @Override
                                                public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                                                    List<Chapter> chapters = null;
                                                    for (DocumentSnapshot valueDocument : value.getDocuments()) {
                                                        chapters = new ArrayList<>();
                                                        String id = valueDocument.getId(); // xem lai id
                                                        String name = valueDocument.get("name", String.class);
                                                        String content = valueDocument.get("content", String.class);
                                                        Chapter chapter = new Chapter(id, name, content);
                                                        chapters.add(chapter);
                                                    }
                                                    novel.setChapters(chapters);

                                                }
                                            });
                                    novels.add(novel);
                                }
                            });
                            Listnovels.setValue(novels);
                        }


                    }
                });
        return Listnovels;
    }
    public List<Novel> getNovelByID(String novelID){
        List<Novel> novels = new ArrayList<>();
        mFirestore.collection("novel").document(novelID).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                String id = value.getId();
                String gioiThieu = value.get("intro", String.class);
                String image = value.get("image", String.class);
                String name = value.get("name", String.class);
                Boolean status = value.get("status", Boolean.class);
                String type = value.get("type",String.class);
                DocumentReference author = value.get("author", DocumentReference.class);
                Novel novel = new Novel(id, name, gioiThieu, image,type, author, status);
                value.getReference().collection("chapter")
                        .addSnapshotListener(new EventListener<QuerySnapshot>() {
                            @Override
                            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                                List<Chapter> chapters = null;
                                for (DocumentSnapshot valueDocument : value.getDocuments()) {
                                    chapters = new ArrayList<>();
                                    String id = valueDocument.getId(); // xem lai id
                                    String name = valueDocument.get("name", String.class);
                                    String content = valueDocument.get("content", String.class);
                                    Chapter chapter = new Chapter(id, name, content);
                                    chapters.add(chapter);
                                    novel.setChapters(chapters);
                                    novels.add(novel);
                                }
                            }
                        });
            }
        });
        return novels;
    }
}

