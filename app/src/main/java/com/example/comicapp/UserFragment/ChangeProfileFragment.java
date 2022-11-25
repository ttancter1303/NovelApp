package com.example.comicapp.UserFragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.comicapp.R;
import com.example.comicapp.databinding.FragmentChangeProfileBinding;


public class ChangeProfileFragment extends Fragment {
    FragmentChangeProfileBinding binding;
    Button mBtnAccept;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

    }
}