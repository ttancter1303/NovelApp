package com.example.comicapp.UserFragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.comicapp.MainActivity;
import com.example.comicapp.R;
import com.example.comicapp.Repository.ViewModel.SharedViewModel;
import com.example.comicapp.SendData;
import com.example.comicapp.data.User;
import com.example.comicapp.databinding.FragmentChangeProfileBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;


public class ChangeProfileFragment extends Fragment {
    private static final int PHOTO_PICKER_REQUEST_CODE = 22;
    FragmentChangeProfileBinding binding;
    ProgressDialog progressDialog;
    Button mBtnAccept;
    ImageView imgUser;
    EditText mUserName;
    EditText mUserEmail;
    EditText mUserPhone;
    EditText mUserBirth;
    EditText mUserNote;
    DocumentReference docRef;
    FirebaseUser mFirebaseUser;
    FirebaseAuth mFirebaseAuth;
    TextView btnSelect;
    String fileName;
    StorageReference storageRef;
    FirebaseStorage storage;
    FirebaseFirestore db;
    NavController navController;
    String image;
    Uri imageUri;
    SharedViewModel viewModel;
    private static SendData sendData;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFirebaseAuth = FirebaseAuth.getInstance();
        storage = FirebaseStorage.getInstance();
        mFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        storageRef = storage.getReference();
        db = FirebaseFirestore.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentChangeProfileBinding.inflate(inflater,container,false);
        return binding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.CANADA);
        Date now = new Date();
        fileName = formatter.format(now)+"jpg";
        mBtnAccept = binding.btnSaveChageProfile;
        mBtnAccept.setOnClickListener(v->{
            Toast.makeText(requireContext(), "Thay đổi thành công", Toast.LENGTH_SHORT).show();
        });
        mUserName = binding.edtUserName;
        mUserEmail = binding.edtUserEmail;
        mUserBirth = binding.edtUserBirth;
        mUserPhone = binding.edtUserPhone;
        mUserNote = binding.edtUserNote;
        btnSelect = binding.link;
        imgUser = binding.imgUser;
//        mGroup = binding.radio;
//        mMale = binding.btnMale;
//        mFamale = binding.btnFemale;

        btnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                SelectImage();
            }
        });


        NavController navController = Navigation.findNavController(requireActivity(), R.id.fragment_host_container);
        mUserEmail.setText(mFirebaseUser.getEmail());
        String user = mFirebaseAuth.getUid();
        DocumentReference docRef = db.collection("user").document(user);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d("ttan", "DocumentSnapshot data: " + document.getData());
                        mUserName.setText(document.get("name").toString());
                        mUserEmail.setText(document.get("email").toString());
                        mUserBirth.setText(document.get("birth").toString());
                        mUserPhone.setText(document.get("phone").toString());
                        mUserNote.setText(document.get("note").toString());
                        image = document.get("image").toString();
                        viewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
                        storageRef = storage.getReference(image);
                        try {
                            final File localFile = File.createTempFile("temp1","jpg");
                            storageRef.getFile(localFile)
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
                    } else {
                        Log.d("ttan", "No such document");
                    }
                } else {
                    Log.d("ttan", "get failed with ", task.getException());
                }
            }
        });
        mBtnAccept.setOnClickListener(v->{
            changeUserFromDBV2(mUserEmail.getText().toString(),mUserName.getText().toString(),mUserBirth.getText().toString(),mUserPhone.getText().toString(),mUserNote.getText().toString(),user,fileName);
            navController.navigate(R.id.userFragment);
        });

    }

    private void SelectImage() {
        Intent intent = new Intent(MediaStore.ACTION_PICK_IMAGES);
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,100);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 100 && data != null && data.getData() != null){

            imageUri = data.getData();
            imgUser.setImageURI(imageUri);


        }
    }
    private void uploadImage() {

        progressDialog = new ProgressDialog(requireActivity());
        progressDialog.setTitle("Uploading File....");
        progressDialog.show();
        storageRef = FirebaseStorage.getInstance().getReference("user_image/"+fileName);
        storageRef.putFile(imageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        imgUser.setImageURI(null);
                        Toast.makeText(requireActivity(),"Successfully Uploaded",Toast.LENGTH_SHORT).show();
                        if (progressDialog.isShowing())
                            progressDialog.dismiss();

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        if (progressDialog.isShowing())
                            progressDialog.dismiss();
                        Toast.makeText(requireActivity(),"Failed to Upload",Toast.LENGTH_SHORT).show();


                    }
                });

    }

    public void changeUserFromDBV2(String Email,String Name,String Birth, String Phone, String Note,String user,String Image){
        uploadImage();
        Map<String,Object> dataMap = new HashMap<>();
        dataMap.put("email",Email);
        dataMap.put("name",Name);
        dataMap.put("birth",Birth);
        dataMap.put("phone",Phone);
        dataMap.put("note", Note);
        if (Image != null){
            dataMap.put("image","user_image/"+Image);
        }else {
            dataMap.put("image","user_image/149071.png");
        }

        db.collection("user")
                .document(user)
                .set(dataMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(requireContext(), "Update thành công", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(requireContext(), "Update thất bại", Toast.LENGTH_SHORT).show();

                    }
                });
    }


}