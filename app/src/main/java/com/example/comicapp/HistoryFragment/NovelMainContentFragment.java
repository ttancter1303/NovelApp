package com.example.comicapp.HistoryFragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import java.util.Calendar;
import java.util.Date;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
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
import android.widget.Toast;

import com.example.comicapp.R;
import com.example.comicapp.RecycleAdapter.HistoryRecycleAdapter;
import com.example.comicapp.RecycleAdapter.VolumeAdapter;
import com.example.comicapp.Repository.ViewModel.ChapterViewModel;
import com.example.comicapp.Repository.ViewModel.NovelViewModel;
import com.example.comicapp.ViewPager2Adapter.ComicDetailAdapter;
import com.example.comicapp.data.Chapter;
import com.example.comicapp.data.Novel;
import com.example.comicapp.databinding.FragmentComicDetailBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NovelMainContentFragment extends Fragment {
    FragmentComicDetailBinding binding;
    FirebaseStorage storage;
    FirebaseFirestore db;
    Novel mNovel;
    StorageReference mStorageReference;
    FirebaseUser mFirebaseUser;
    FirebaseAuth mFirebaseAuth;
    ImageView mImage;
    TextView mHeader;
    TextView mAuthor;
    TextView mIntro;
    TextView mNovelType;
    Button mBtnReadComic;
    Button mButtonAddLibrary;
    TextView mStatus;
    ChapterViewModel mViewModel;

    public static Novel novel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        storage = FirebaseStorage.getInstance();
        db = FirebaseFirestore.getInstance();

        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        binding = FragmentComicDetailBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        mImage = binding.imageView2;
        mHeader = binding.textComicName;
        mAuthor = binding.textAuthor;
        mButtonAddLibrary = binding.btnAddToLibrary;
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
//        mBtnReadComic = binding.btnReadComic;

        Bundle bundle = requireArguments();
        String id = bundle.getString("id");
        mButtonAddLibrary.setOnClickListener( v->{
//            addNovelToCategory(id);
//            addNovelToCategory(id);
            db.collection("user").document(mFirebaseUser.getUid()).collection("novel_mark")
                    .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isComplete()){
                                boolean isExist = false;
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    if (id != null && id.equals(document.getString("novel_id"))) {
                                        isExist = true;
                                        Toast.makeText(requireContext(), "Truyện đã tồn tại trong tủ truyện", Toast.LENGTH_SHORT).show();
                                        break;
                                    }
                                }
                                if(!isExist){
                                    addNovelToCategory(id);
                                }
                            }
                        }
                    });
        });
        DocumentReference docRef = db.collection("novel").document(id);
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
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
        adapter.setOnItemClickListener(new VolumeAdapter.OnItemClickListener() {
            @Override
            public void onClick(View view, Chapter chapter) {
                Bundle bundle = new Bundle();
                bundle.putString("ChapterID",chapter.getId());
                bundle.putString("NovelID", novel.getId());
                navController.navigate(R.id.readNovelFragment,bundle);
            }
        });

//        mBtnReadComic.setOnClickListener(v->{
//            Bundle bundle2 = new Bundle();
//            bundle.putString("ChapterID",adapter.getFirstItemValue());
//            bundle.putString("NovelID", id);
//            navController.navigate(R.id.readNovelFragment,bundle2);
//        });

        mViewModel = new ViewModelProvider(this).get(ChapterViewModel.class);
        if(mViewModel.getAllChapter(id)!= null){
            mViewModel.getAllChapter(id).observe(getViewLifecycleOwner(), new Observer<List<Chapter>>() {
                @Override
                public void onChanged(List<Chapter> chapters) {
                    for (Chapter chapter : chapters) {
                        adapter.setData(chapters);
                    }
                }
            });
        }
    }
    public void addNovelToCategory(String id){
        Map<String,Object> data = new HashMap<>();
        Date currentTime = Calendar.getInstance().getTime();
        data.put("novel_id",id);
        data.put("date_saved",currentTime);

        db.collection("user").document(mFirebaseUser.getUid()).collection("novel_mark")
                .add(data).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(requireContext(), "Thêm truyện vào tủ truyện thành công", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(requireContext(), "Lỗi không thêm được truyện vào tủ truyện", Toast.LENGTH_SHORT).show();

                    }
                });

    }
}