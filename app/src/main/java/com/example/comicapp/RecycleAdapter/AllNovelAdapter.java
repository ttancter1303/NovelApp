package com.example.comicapp.RecycleAdapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.comicapp.R;
import com.example.comicapp.data.Novel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AllNovelAdapter extends RecyclerView.Adapter<AllNovelAdapter.HomeViewHolder>{
    List<Novel> mData = new ArrayList<>();
    FirebaseStorage storage;

    public void setData(List<Novel> data){
        mData = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public HomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        storage = FirebaseStorage.getInstance();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_read_highest,parent,false);
        return new HomeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeViewHolder holder, int position) {
        holder.bindView(mData.get(position));

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class HomeViewHolder extends RecyclerView.ViewHolder {
        ImageView mImage;
        TextView mTxtHeader;
        StorageReference mStorageReference;
        public HomeViewHolder(@NonNull View itemView) {
            super(itemView);
            mImage = itemView.findViewById(R.id.imageView3);
            mTxtHeader = itemView.findViewById(R.id.textView);
            itemView.setOnClickListener(v -> {
                if(mOnItemClickListener != null){
                    mOnItemClickListener.onClick(itemView, mData.get(getLayoutPosition()));
                }
            });

        }
        public void bindView(@NonNull Novel novel){
            mStorageReference = storage.getReference(novel.getImage());
            mTxtHeader.setText(novel.getname());
            try {
                final File localFile = File.createTempFile("temp1","jpg");
                mStorageReference.getFile(localFile)
                        .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                                Bitmap bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                                try {
                                    FileOutputStream out = new FileOutputStream(localFile.getAbsolutePath());
                                    bitmap.compress(Bitmap.CompressFormat.JPEG,100,out);

                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }

//                                mImage.setImageBitmap(bitmap);
                                Glide.with(mImage.getContext())
                                        .load(bitmap)
                                        .centerCrop()
                                        .into(mImage);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d("PhucDVb", "onFailure: ", e);
                            }
                        });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private HistoryRecycleAdapter.OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(HistoryRecycleAdapter.OnItemClickListener onItemClickListener){
        mOnItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        public void onClick(View view, Novel novel);
    }
}
