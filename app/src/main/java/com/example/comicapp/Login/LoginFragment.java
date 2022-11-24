package com.example.comicapp.Login;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.comicapp.MainActivity;
import com.example.comicapp.R;
import com.example.comicapp.databinding.FragmentLoginBinding;
import com.google.firebase.auth.FirebaseAuth;

public class LoginFragment extends Fragment {
    FragmentLoginBinding binding;
    Button mBtnLogin;
    Button mBtnRegister;
    EditText mEdtAccount;
    EditText mEdtPassword;
    TextView mTxtForgotPassword;
    FirebaseAuth firebaseAuth;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        binding = FragmentLoginBinding.inflate(inflater,container,false);
        return binding.getRoot();


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        NavController navController = Navigation.findNavController(requireActivity(),R.id.fragmentContainerView3);
        mBtnLogin = binding.btnLogin;
        mBtnRegister = binding.btnRegister;
        mEdtAccount = binding.edtAccount;
        mEdtPassword = binding.edtPassword;
        mTxtForgotPassword = binding.txtForgotPassword;
        firebaseAuth = FirebaseAuth.getInstance();


        mBtnLogin.setOnClickListener(v->{
            Intent intent = new Intent(requireContext(), MainActivity.class);
            startActivity(intent);
        });
        mBtnRegister.setOnClickListener(v->{
            navController.navigate(R.id.action_loginFragment_to_registerFragment);
        });
        mEdtAccount.setOnClickListener(v->{


        });
        mEdtPassword.setOnClickListener(v->{

        });
        mTxtForgotPassword.setOnClickListener(v->{

        });
    }
}