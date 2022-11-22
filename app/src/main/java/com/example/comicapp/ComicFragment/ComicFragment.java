package com.example.comicapp.ComicFragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.comicapp.ComicDetailActivity;
import com.example.comicapp.OnClick.OnClickComicListenr;
import com.example.comicapp.R;
import com.example.comicapp.databinding.FragmentComicBinding;
import com.example.comicapp.databinding.FragmentComicDetailBinding;


public class ComicFragment extends Fragment {
    FragmentComicBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentComicBinding.inflate(getLayoutInflater());
        return inflater.inflate(R.layout.fragment_comic, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView txt_title = binding.headerTitle;
        txt_title.setOnClickListener(v ->{
            Navigation.findNavController(view).navigate(R.id.action_FirstFragment_to_SecondFragment);
        });
    }
}