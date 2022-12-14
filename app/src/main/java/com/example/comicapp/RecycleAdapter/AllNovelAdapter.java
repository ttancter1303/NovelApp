package com.example.comicapp.RecycleAdapter;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.comicapp.HistoryFragment.NovelMainContentFragment;
import com.example.comicapp.R;
import com.example.comicapp.data.Novel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AllNovelAdapter extends RecyclerView.Adapter<AllNovelAdapter.ViewHolder>{
    List<Novel> mData = new ArrayList<>();
    FirebaseStorage storage;

//    private ISendDataLister mISendDataLister;



    public void setData(List<Novel> data){
        mData = data;
        notifyDataSetChanged();
    }



    @NonNull
    @Override
    public AllNovelAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        storage = FirebaseStorage.getInstance();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.new_comic,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AllNovelAdapter.ViewHolder holder, int position) {
        holder.bindView(mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView mImage;
        StorageReference mStorageReference;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mImage = itemView.findViewById(R.id.img_newComic);
            itemView.setOnClickListener(v->{
                if(mOnItemClickListener != null){
                    mOnItemClickListener.onClick(itemView,mData.get(getLayoutPosition()));
//                    sendDataToRecommendFragment();
                }

            });
        }
        public void bindView(Novel novel){
            mStorageReference = storage.getReference(novel.getImage());
            try {
                final File localFile = File.createTempFile("temp","jpg");
                mStorageReference.getFile(localFile)
                        .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                                Bitmap bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                                mImage.setImageBitmap(bitmap);
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



    private OnItemClickListener mOnItemClickListener;


    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        mOnItemClickListener = onItemClickListener;
    }
//    private void sendDataToRecommendFragment() {
//        String id = mData.get(getItemCount()).getId();
//        String name = mData.get(getItemCount()).getname();
//        String intro = mData.get(getItemCount()).getIntro();
//        String image = mData.get(getItemCount()).getImage();
//        Log.d("ttan", "sendDataToRecommendFragment: "+ name);
//
//        mOnItemClickListener.SendData(id,name,intro,image);
//    }
//    public interface ISendDataLister{
//        void SendData(String id, String name, String intro,String image);
//    }
    public interface OnItemClickListener {
        void onClick(View view, Novel novel);
//        void SendData(String id, String name, String intro,String image);
    }

}
