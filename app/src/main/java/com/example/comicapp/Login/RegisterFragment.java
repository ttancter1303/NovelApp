package com.example.comicapp.Login;

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
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.comicapp.R;
import com.example.comicapp.databinding.FragmentRegisterBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class RegisterFragment extends Fragment {
    FirebaseAuth firebaseAuth;
    private String mEmail;
    private String mPassword;
    FragmentRegisterBinding binding;
    EditText mEdtRegisterName;
    EditText mEdtRegisterEmail;
    EditText mEdtRegisterPassword;
    Button mBtnRegisterAccept;
    ProgressBar mLoading;
    FirebaseAuth mFirebaseAuth;
    FirebaseFirestore mFirestore;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirestore = FirebaseFirestore.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentRegisterBinding.inflate(inflater,container,false);
        return binding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        firebaseAuth = FirebaseAuth.getInstance();
        mEdtRegisterEmail = binding.edtRegisterEmail;
        mEdtRegisterName = binding.edtRegisterName;
        mEdtRegisterPassword = binding.edtRegisterPassword;

        mBtnRegisterAccept = binding.btnRegisterAccept;
        mLoading = binding.loading;
        NavController navController = Navigation.findNavController(requireActivity(),R.id.fragmentContainerView3);

        mBtnRegisterAccept.setOnClickListener(v->{
            signUp(mEdtRegisterEmail.getText().toString(),mEdtRegisterPassword.getText().toString(),mEdtRegisterName.getText().toString());
        });

    }
    public void signUp(String Email, String Password,String Name){
        mLoading.setVisibility(View.VISIBLE);
        mLoading.setProgress(0,true);
        mFirebaseAuth.createUserWithEmailAndPassword(Email, Password)
                .addOnCompleteListener(requireActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        mLoading.setProgress(0,false);
                        mLoading.setVisibility(View.GONE);
                        if(task.isSuccessful()){
                            addUserToDB(Email,Name);
                            Toast.makeText(requireContext(), "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                            requireActivity().onBackPressed();
                        }else{
                            Toast.makeText(requireContext(), task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }
    public void addUserToDB(String Email,String Name){
        Map<String,Object> dataMap = new HashMap<>();
        dataMap.put("email",Email);
        dataMap.put("name",Name);
        FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();
        if(firebaseUser != null){
            mFirestore.collection("user")
                    .document(firebaseUser.getUid())
                    .set(dataMap);
        }
    }


}