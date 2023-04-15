package com.example.comicapp.UserFragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.comicapp.LoginActivity;
import com.example.comicapp.R;
import com.example.comicapp.databinding.FragmentUserBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;

public class UserFragment extends Fragment {
    TextView txtChangeProfile;
    TextView txtSetting;
    TextView txtName;
    TextView txtStatus;
    ImageView imgUser;
    TextView txtChangePassword;
    TextView txtLogout;
    FragmentUserBinding binding;
    StorageReference mStorageReference;
    FirebaseUser mFirebaseUser;
    FirebaseAuth mFirebaseAuth;
    FirebaseFirestore db;
    FirebaseStorage storage;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        storage = FirebaseStorage.getInstance();
        mStorageReference = storage.getReference();
        db = FirebaseFirestore.getInstance();
        // Inflate the layout for this fragment
        binding = FragmentUserBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        txtChangeProfile = binding.txtChangeProfile;
        txtChangePassword = binding.txtChangePassword;
        txtLogout = binding.txtLogout;
        txtName = binding.userName;
        txtStatus = binding.userStatus;
        imgUser = binding.imgUser;


        NavController navController = Navigation.findNavController(requireActivity(),R.id.fragment_host_container);
        txtChangeProfile.setOnClickListener(v->{
            navController.navigate(R.id.changeProfileFragment);
        });
//        txtSetting.setOnClickListener(v->{
//            navController.popBackStack();
//            navController.navigate(R.id.action_userFragment_to_settingFragment);
////            navController.navigate(R.id.settingsFragment);
//        });
        txtChangePassword.setOnClickListener(v->{
            navController.navigate(R.id.action_userFragment_to_forgotPasswordFragment);
        });
        txtLogout.setOnClickListener(v->{
            mFirebaseAuth.signOut();
            startActivity(new Intent(requireContext(), LoginActivity.class));
            requireActivity().finish();
        });


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        String user = mFirebaseAuth.getUid();
        DocumentReference docRef = db.collection("user").document(user);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        txtName.setText(document.get("name").toString());
                        txtStatus.setText(document.get("note").toString());
                        String imagePath = document.get("image").toString();
                        Log.d("Ttan", "onComplete: "+imagePath);
                        mStorageReference = storage.getReference(imagePath);
                        try {
                            final File localFile = File.createTempFile("temp1","jpg");
                            mStorageReference.getFile(localFile)
                                    .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                                        @Override
                                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                                            Bitmap bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                                            Glide.with(imgUser.getContext())
                                                    .load(bitmap)
                                                    .centerCrop()
                                                    .into(imgUser);
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
                        Log.d("ttan", "DocumentSnapshot data: " + document.getData());
                    } else {
                        Log.d("ttan", "No such document");
                    }
                } else {
                    Log.d("ttan", "get failed with ", task.getException());
                }
            }
        });
    }
}