package com.example.comicapp.ComicFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.comicapp.R;
import com.example.comicapp.databinding.NewComicBinding;
import com.example.comicapp.databinding.NewComicDetailBinding;

public class NewComicFragment extends Fragment {
    TextView mHeader;
    Button mRead;
    Button mAddToLibrary;
    ImageView mImage;
    NewComicDetailBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = NewComicDetailBinding.inflate(inflater,container,false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mHeader = binding.txtHeader;
        mRead = binding.btnRead;
        mAddToLibrary = binding.btnAddtoLibrary;
        mImage = binding.imgComic;
        NavController navController =  Navigation.findNavController(requireActivity(),R.id.fragment_host_container);
        mHeader.setOnClickListener(v->{
            navController.navigate(R.id.comicDetailFragment);
        });

        mRead.setOnClickListener(v->{
            navController.navigate(R.id.comicDetailFragment);
        });

        mImage.setOnClickListener(v->{
            navController.navigate(R.id.comicDetailFragment);
        });

    }
}
