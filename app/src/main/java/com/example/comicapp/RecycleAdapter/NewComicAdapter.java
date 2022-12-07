package com.example.comicapp.RecycleAdapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.comicapp.R;
import com.example.comicapp.data.Novel;

import java.util.ArrayList;
import java.util.List;

public class NewComicAdapter extends RecyclerView.Adapter<NewComicAdapter.ViewHolder>{
    ImageView mImage;
    ArrayList<Novel> mData = new ArrayList<>();
    @NonNull
    @Override
    public NewComicAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.new_comic,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewComicAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 100;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mImage = itemView.findViewById(R.id.img_newComic);
        }
        public void bindView(Novel novel){
            mImage.setImageDrawable(novel.getImage());
        }
    }
}
