package com.example.comicapp.ComicFragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.comicapp.R;
import com.example.comicapp.data.Chapter;
import com.example.comicapp.databinding.FragmentReadNovelBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;


public class ReadNovelFragment extends Fragment {
    FragmentReadNovelBinding binding;
    TextView mContent;
    TextView mPre;
    TextView mNext;
    TextView mHeader;
    FirebaseStorage storage;
    FirebaseFirestore db;
    public static Chapter chapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentReadNovelBinding.inflate(inflater,container,false);
        storage = FirebaseStorage.getInstance();
        db = FirebaseFirestore.getInstance();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mContent = binding.txtContent;
        mHeader = binding.txtHeader;
//        mPre = binding.txtChuongTruoc;
//        mNext = binding.txtChuongSau;
//        mMain = binding.txtMain;
        Bundle bundle = requireArguments();
        String chapterID = bundle.getString("ChapterID");
        String novelID = bundle.getString("NovelID");
        DocumentReference docRef = db.collection("novel").document(novelID)
                .collection("chapter").document(chapterID);
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Log.d("ttan", "onSuccess: "+documentSnapshot.toString());
                chapter = documentSnapshot.toObject(Chapter.class);
                mContent.setText(chapter.getContent());
                mHeader.setText("Chương "+chapter.getName());
            }
        });

    }
}