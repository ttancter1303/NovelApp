package com.example.comicapp.ComicFragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.bumptech.glide.Glide;
import com.example.comicapp.R;
import com.example.comicapp.Repository.ViewModel.SharedViewModel;
import com.example.comicapp.data.Novel;
import com.example.comicapp.databinding.NewComicBinding;
import com.example.comicapp.databinding.NewComicDetailBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.w3c.dom.Text;

import java.io.File;
import java.io.IOException;

public class NewComicFragment extends Fragment {
    TextView mHeader;
    TextView mSubTitle;
    Button mRead;
    Button mAddToLibrary;
    ImageView mImage;
    NewComicDetailBinding binding;
    StorageReference mStorageReference;
    SharedViewModel sharedViewModel;
    FirebaseStorage storage;
    FirebaseFirestore db;
    String data;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = NewComicDetailBinding.inflate(inflater,container,false);
        db = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mHeader = binding.txtHeader;
        mSubTitle = binding.txtSubtitle;
        mRead = binding.btnRead;
//        mAddToLibrary = binding.btnAddtoLibrary;
        mImage = binding.imgComic;

        NavController navController =  Navigation.findNavController(requireActivity(),R.id.fragment_host_container);

        // @TODO Thay thanh id muon truyen qua comicDetailFragment
        Novel novel = new Novel();
//        novel.setId("7RijD0lXUf6zZUHsTqJW");
        Bundle bundle = new Bundle();

        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        sharedViewModel.getData().observe(requireActivity(),v->{
            novel.setId(sharedViewModel.getData().getValue());
            if(novel.getId() == null) {
                novel.setId("7RijD0lXUf6zZUHsTqJW");
            }
            bundle.putString("id", novel.getId());
            DocumentReference docRef = db.collection("novel").document(novel.getId());
            docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    String id = documentSnapshot.getId();
                    String gioiThieu = documentSnapshot.get("intro", String.class);
                    String image = documentSnapshot.get("image", String.class);
                    String name = documentSnapshot.get("name", String.class);
                    Boolean status = documentSnapshot.get("status", Boolean.class);
                    mHeader.setText(name);
                    mSubTitle.setText(gioiThieu);
                    mStorageReference = storage.getReference(image);
                    try {
                        final File localFile = File.createTempFile("temp1","jpg");
                        mStorageReference.getFile(localFile)
                                .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                                    @Override
                                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                                        Bitmap bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                                        Glide.with(mImage.getContext())
                                                .load(bitmap)
                                                .centerCrop()
                                                .into(mImage);
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.d("Ttan", "onFailure: ", e);
                                    }
                                });
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });





        });

//        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
//        sharedViewModel.getSelectedItem().observe(getViewLifecycleOwner(),item->{
//            novel.setId(sharedViewModel.getSelectedItem().getValue());
//            bundle.putString("id", novel.getId());
//            mHeader.setText(novel.getname());
//            mSubTitle.setText(novel.getIntro());
//            mStorageReference = storage.getReference(novel.getImage());
//            try {
//                final File localFile = File.createTempFile("temp1","jpg");
//                mStorageReference.getFile(localFile)
//                        .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
//                            @Override
//                            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
//                                Bitmap bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
//                                Glide.with(mImage.getContext())
//                                        .load(bitmap)
//                                        .centerCrop()
//                                        .into(mImage);
//                            }
//                        }).addOnFailureListener(new OnFailureListener() {
//                            @Override
//                            public void onFailure(@NonNull Exception e) {
//                                Log.d("Ttan", "onFailure: ", e);
//                            }
//                        });
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        });

        mHeader.setOnClickListener(v->{
            if(mOnItemClickListener != null){
            }
            navController.navigate(R.id.comicDetailFragment, bundle);
        });

        mRead.setOnClickListener(v->{
            navController.navigate(R.id.comicDetailFragment, bundle);
        });

        mImage.setOnClickListener(v->{
            navController.navigate(R.id.comicDetailFragment, bundle);
        });

    }
    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        mOnItemClickListener = onItemClickListener;
    }
    public interface OnItemClickListener {
        void onClick(View view, Novel novel);
    }
}
