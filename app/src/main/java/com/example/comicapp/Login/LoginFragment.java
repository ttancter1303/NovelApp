package com.example.comicapp.OnClick.Login;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.comicapp.R;
import com.example.comicapp.databinding.FragmentLoginBinding;

public class LoginFragment extends Fragment {
    FragmentLoginBinding binding;
    Button mBtnLogin;
    Button mBtnRegister;
    EditText mEdtAccount;
    EditText mEdtPassword;
    TextView mTxtForgotPassword;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        binding = FragmentLoginBinding.inflate(inflater,container,false);
        return inflater.inflate(R.layout.fragment_login, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mBtnLogin = binding.btnLogin;
        mBtnRegister = binding.btnRegister;
        mEdtAccount = binding.edtAccount;
        mEdtPassword = binding.edtPassword;
        mTxtForgotPassword = binding.txtForgotPassword;

        mBtnLogin.setOnClickListener(v->{

        });
        mBtnRegister.setOnClickListener(v->{

        });
        mEdtAccount.setOnClickListener(v->{


        });
        mEdtPassword.setOnClickListener(v->{

        });
        mTxtForgotPassword.setOnClickListener(v->{

        });
    }
}