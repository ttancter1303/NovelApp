package com.example.comicapp.Login;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.comicapp.R;
import com.example.comicapp.databinding.FragmentForgotPasswordBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class ForgotPasswordFragment extends Fragment {
    EditText mEmail;
    Button mReset;
    ProgressBar mLoading;
    FragmentForgotPasswordBinding binding;
    FirebaseAuth mFirebaseAuth;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFirebaseAuth = FirebaseAuth.getInstance();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentForgotPasswordBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mReset = binding.btnRegisterAccept;
        mEmail = binding.edtRegisterEmail;
        mLoading = binding.loading;
        mReset.setOnClickListener(v->{
            forgotPassword(mEmail.getText().toString());
        });
    }
    public void forgotPassword(String Email){
        mLoading.setVisibility(View.VISIBLE);
        mLoading.setProgress(0,true);
        mFirebaseAuth.sendPasswordResetEmail(Email)
                .addOnCompleteListener(requireActivity(), new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        mLoading.setVisibility(View.GONE);
                        mLoading.setProgress(0,false);
                        if(task.isSuccessful()){
                            Toast.makeText(requireContext(), "Vui lòng kiểm tra hộp thư của email "+ mEmail.getText(), Toast.LENGTH_SHORT).show();
                            requireActivity().onBackPressed();
                        }else{
                            Toast.makeText(requireContext(),  task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }
}