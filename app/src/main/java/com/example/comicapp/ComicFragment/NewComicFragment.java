package com.example.comicapp.ComicFragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
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
import com.example.comicapp.data.Novel;
import com.example.comicapp.databinding.NewComicBinding;
import com.example.comicapp.databinding.NewComicDetailBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.w3c.dom.Text;

import java.io.File;
import java.io.IOException;

public class NewComicFragment extends Fragment {
    TextView mHeader;
    TextView mSubTitle;
    Button mRead;
    Button mAddToLibrary;
    ImageView mImage;
    NewComicDetailBinding binding;
    StorageReference storageRef;

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
        Intent intent = requireActivity().getIntent();



        mHeader = binding.txtHeader;
        mSubTitle = binding.txtSubtitle;
        mRead = binding.btnRead;
        mAddToLibrary = binding.btnAddtoLibrary;
        mImage = binding.imgComic;

        NavController navController =  Navigation.findNavController(requireActivity(),R.id.fragment_host_container);
        mHeader.setOnClickListener(v->{
            if(mOnItemClickListener != null){
            }
            navController.navigate(R.id.comicDetailFragment);
        });

        mRead.setOnClickListener(v->{
            navController.navigate(R.id.comicDetailFragment);
        });

        mImage.setOnClickListener(v->{
            navController.navigate(R.id.comicDetailFragment);
        });

    }
    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        mOnItemClickListener = onItemClickListener;
    }
    public interface OnItemClickListener {
        void onClick(View view, Novel novel);
    }
}
