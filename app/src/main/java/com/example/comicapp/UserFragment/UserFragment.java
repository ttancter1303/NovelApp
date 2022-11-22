package com.example.comicapp.UserFragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.comicapp.R;
import com.example.comicapp.databinding.FragmentUserBinding;

public class UserFragment extends Fragment {
    TextView txtChangeProfile;
    TextView txtSetting;
    TextView txtChangePassword;
    TextView txtLogout;
    FragmentUserBinding binding;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        txtChangeProfile = binding.txtChangeProfile;
        txtSetting = binding.txtSetting;
        txtChangePassword = binding.txtChangePassword;
        txtLogout = binding.txtLogout;

        txtChangeProfile.setOnClickListener(v->{
            Navigation.findNavController(requireActivity(), R.id.fragment_host_container).navigate(R.id.action_userFragment_to_changeProfileFragment);
        });
        txtSetting.setOnClickListener(v->{
            Navigation.findNavController(requireActivity(), R.id.fragment_host_container).navigate(R.id.action_userFragment_to_settingFragment);
        });
        txtChangePassword.setOnClickListener(v->{

        });
        txtLogout.setOnClickListener(v->{

        });


    }
}