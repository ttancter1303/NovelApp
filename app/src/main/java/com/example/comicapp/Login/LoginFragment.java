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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.comicapp.MainActivity;
import com.example.comicapp.R;
import com.example.comicapp.Repository.NovelByAuthorRepository;
import com.example.comicapp.Repository.UserListRepository;
import com.example.comicapp.databinding.FragmentLoginBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginFragment extends Fragment {
    FragmentLoginBinding binding;
    Button mBtnLogin;
    Button mBtnRegister;
    EditText mEdtAccount;
    EditText mEdtPassword;
    TextView mTxtForgotPassword;
    ProgressBar mLoading;
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
        mLoading = binding.loading;

        mBtnLogin.setOnClickListener(v->{
            logIn(mEdtAccount.getText().toString(), mEdtPassword.getText().toString());
        });
        mBtnRegister.setOnClickListener(v->{
            navController.navigate(R.id.action_loginFragment_to_registerFragment);
        });

        mTxtForgotPassword.setOnClickListener(v->{
            navController.navigate(R.id.action_loginFragment_to_forgotPasswordFragment2);
        });
    }
    public void logIn(String Email,String Password){
        mLoading.setVisibility(View.VISIBLE);
        mLoading.setProgress(0,true);
        mFirebaseAuth.signInWithEmailAndPassword(Email, Password)
                .addOnCompleteListener(requireActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        mLoading.setVisibility(View.GONE);
                        mLoading.setProgress(0,false);
                        if(task.isSuccessful()){
//                            NovelByAuthorRepository novelByAuthorRepository = new NovelByAuthorRepository();
//                            novelByAuthorRepository.getAllNovel();
                            UserListRepository userListRepository = new UserListRepository();
                            userListRepository.getAllUser();
                            Toast.makeText(requireContext(), "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(requireContext(), MainActivity.class);
                            startActivity(intent);
                            mBtnLogin.setText("");
                            mEdtPassword.setText("");
                        }else{
                            Toast.makeText(requireContext(), task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}