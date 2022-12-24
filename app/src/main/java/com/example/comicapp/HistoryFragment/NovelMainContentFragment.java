package com.example.comicapp.HistoryFragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.comicapp.R;
import com.example.comicapp.RecycleAdapter.VolumeAdapter;
import com.example.comicapp.ViewPager2Adapter.ComicDetailAdapter;
import com.example.comicapp.data.Novel;
import com.example.comicapp.databinding.FragmentComicDetailBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class NovelMainContentFragment extends Fragment {

    FragmentComicDetailBinding binding;
    FirebaseStorage storage;
    FirebaseFirestore db;
    Novel mNovel;
    StorageReference mStorageReference;
    ImageView mImage;
    TextView mHeader;
    TextView mAuthor;
    TextView mIntro;
    TextView mNovelType;
    Button mBtnReadComic;
    Button mButtonAddLibrary;
    TextView mStatus;

    public static Novel novel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        storage = FirebaseStorage.getInstance();
        db = FirebaseFirestore.getInstance();
        binding = FragmentComicDetailBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        mImage = binding.imageView2;
        mHeader = binding.textComicName;
        mAuthor = binding.textAuthor;
        mButtonAddLibrary = binding.btnAddToLiberry;
        mBtnReadComic = binding.btnReadComic;
        mNovelType = binding.typeNovel;
        mIntro = binding.txtIntro;
        mStatus = binding.txtStatus;


        NavController navController = Navigation.findNavController(requireActivity(),R.id.fragment_host_container);
//        ViewPager2 viewPager2 = view.findViewById(R.id.bodyComicDetailViewPager2);
//        TabLayout tabLayout = view.findViewById(R.id.tabLayoutComicDetail);
//        ComicDetailAdapter adapter = new ComicDetailAdapter(this);
//        viewPager2.setAdapter(adapter);
//        TabLayoutMediator mediator = new TabLayoutMediator(tabLayout, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
//            @Override
//            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
//                switch (position){
//                    case 0:
//                        tab.setText("Giới thiệu");
//                        break;
//                    case 1:
//                        tab.setText("Danh sách chương");
//                        break;
//                }
//            }
//        });
//        mediator.attach();
        mBtnReadComic = binding.btnReadComic;
        mBtnReadComic.setOnClickListener(v->{
           navController.navigate(R.id.readNovelFragment);
        });

        Bundle bundle = requireArguments();
        String id = bundle.getString("id");

        DocumentReference docRef = db.collection("novel").document(id);
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Log.d("ttan", "onSuccess: "+documentSnapshot.toString());
                novel = documentSnapshot.toObject(Novel.class);
                novel.setId(id);
                mHeader.setText(novel.getname());
                mIntro.setText(novel.getIntro());
                mNovelType.setText(" Thể loại: " + novel.getType());
                if(novel.getStatus() != false){
                    mStatus.setText("Đang tiến hành");
                }else{
                    mStatus.setText("Chưa hoàn thành");
                }


                novel.getAuthor().get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        mAuthor.setText("Tác giả: "+ documentSnapshot.get("name"));
                    }
                });
                mStorageReference = storage.getReference(novel.getImage());
                try {
                    final File localFile = File.createTempFile("temp","jpg");
                    mStorageReference.getFile(localFile)
                            .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                                    Bitmap bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                                    mImage.setImageBitmap(bitmap);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d("ttan", "onFailure: ", e);
                                }
                            });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        RecyclerView recyclerView = view.findViewById(R.id.RecycleViewVolume);
        VolumeAdapter adapter = new VolumeAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//
//        DocumentReference docRef = db.collection("novel").document(id);
//        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                if (task.isSuccessful()) {
//                    DocumentSnapshot document = task.getResult();
//
//                    if (document.exists()) {
//                        Log.d("ttan", "DocumentSnapshot data: " + document.getData());
//                        mHeader.setText(document.get("name",String.class));
//                        mAuthor.setText(document.get("author",String.class));
//                        mStorageReference = storage.getReference(document.get("image",String.class));
//                        try {
//                            final File localFile = File.createTempFile("temp","jpg");
//                            mStorageReference.getFile(localFile)
//                                    .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
//                                        @Override
//                                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
//                                            Bitmap bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
//                                            mImage.setImageBitmap(bitmap);
//                                        }
//                                    }).addOnFailureListener(new OnFailureListener() {
//                                        @Override
//                                        public void onFailure(@NonNull Exception e) {
//                                            Log.d("PhucDVb", "onFailure: ", e);
//                                        }
//                                    });
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                    } else {
//                        Log.d("ttan", "No such document");
//                    }
//                } else {
//                    Log.d("ttan", "get failed with ", task.getException());
//                }
//            }
//        });

    }
}