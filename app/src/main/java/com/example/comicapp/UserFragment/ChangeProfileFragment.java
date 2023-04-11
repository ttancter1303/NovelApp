package com.example.comicapp.UserFragment;

import android.content.Intent;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.comicapp.R;
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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
import java.util.Map;


public class ChangeProfileFragment extends Fragment {
    private static final int PICK_IMAGE_REQUEST = 22;
    FragmentChangeProfileBinding binding;
    Button mBtnAccept;

    EditText mUserName;
    EditText mUserEmail;
    EditText mUserPhone;
    EditText mUserBirth;
    EditText mUserNote;
    DocumentReference docRef;
    FirebaseUser mFirebaseUser;
    FirebaseAuth mFirebaseAuth;
    TextView btnSelect;
    StorageReference storageRef;
    FirebaseStorage storage;
    FirebaseFirestore db;
    NavController navController;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFirebaseAuth = FirebaseAuth.getInstance();
        storage = FirebaseStorage.getInstance();
        mFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        StorageReference storageRef = storage.getReference();
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
                    } else {
                        Log.d("ttan", "No such document");
                    }
                } else {
                    Log.d("ttan", "get failed with ", task.getException());
                }
            }
        });
        mBtnAccept.setOnClickListener(v->{
            changeUserFromDBV2(mUserEmail.getText().toString(),mUserName.getText().toString(),mUserBirth.getText().toString(),mUserPhone.getText().toString(),mUserNote.getText().toString(),user);
        });

    }

    private void SelectImage() {

    }

    public void changeUserFromDBV2(String Email,String Name,String Birth, String Phone, String Note,String user){
        uploadImage();
        Map<String,Object> dataMap = new HashMap<>();
        dataMap.put("email",Email);
        dataMap.put("name",Name);
        dataMap.put("birth",Birth);
        dataMap.put("phone",Phone);
        dataMap.put("note", Note);
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
        navController.navigate(R.id.userFragment);
    }

    private void uploadImage() {
    }
}