package com.example.comicapp.UserFragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.comicapp.databinding.FragmentChangeProfileBinding;
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

import java.util.HashMap;
import java.util.Map;


public class ChangeProfileFragment extends Fragment {
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
    FirebaseFirestore db;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
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
                        // @TODO những thuộc tính này chưa thấy có đủ trên firebase
//                        mUserBirth.setText(document.get("birth").toString());
//                        mUserPhone.setText(document.get("phone").toString());
//                        mUserNote.setText(document.get("note").toString());


                    } else {
                        Log.d("ttan", "No such document");
                    }
                } else {
                    Log.d("ttan", "get failed with ", task.getException());
                }
            }
        });

        mUserEmail.setText(mFirebaseUser.getEmail());



//        docRef = db.collection("user").document(user);
//        docRef.update("email",mUserEmail.getText().toString()
//        ,"name",mUserName.getText().toString()
//        ,"birth",mUserBirth.getText().toString()
//        ,"phone",mUserPhone.getText().toString()
//        ,"note",mUserNote.getText().toString());




//        .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//            @Override
//            public void onSuccess(DocumentSnapshot documentSnapshot) {
////                City city = documentSnapshot.toObject(City.class);
//                changeUserFromDB(mUserEmail.getText().toString()
//                        ,mUserName.getText().toString()
//                        ,mUserBirth.getText().toString()
//                        ,mUserPhone.getText().toString()
//                        ,mUserNote.getText().toString()
//                        );
//            }
//        });

//        db.collection("user")
//                .document(user)
//                .addSnapshotListener(new OnSuccessListener<DocumentSnapshot>() {
//                    @Override
//                    public void onSuccess(DocumentSnapshot documentSnapshot) {
//
//                    }
//                })

    }

    public void changeUserFromDB(String Email,String Name,String Birth, String Phone, String Note){
        Map<String,Object> dataMap = new HashMap<>();
        dataMap.put("email",Email);
        dataMap.put("name",Name);
        dataMap.put("birth",Birth);
        dataMap.put("Phone",Phone);
        dataMap.put("Note", Note);
        FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();
        if(firebaseUser != null){
            db.collection("user")
                    .document(firebaseUser.getUid())
                    .set(dataMap);
        }
    }
}