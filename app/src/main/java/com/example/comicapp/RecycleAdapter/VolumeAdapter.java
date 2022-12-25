package com.example.comicapp.RecycleAdapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.comicapp.R;
import com.example.comicapp.data.Chapter;
import com.example.comicapp.data.Novel;

import java.util.ArrayList;
import java.util.List;

public class VolumeAdapter extends RecyclerView.Adapter<VolumeAdapter.ViewHolder>{
    String novelId;
    List<Chapter> mData = new ArrayList<>();
    public void setData(List<Chapter> data){
        mData = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_comic_vol_detail,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VolumeAdapter.ViewHolder holder, int position) {
        holder.bindView(mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView mName;
        TextView mIndex;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
//            mIndex = itemView.findViewById(R.id.txt_soChuong);
            mName = itemView.findViewById(R.id.txt_tenChuong);
            itemView.setOnClickListener(v -> {
                if(mOnItemClickListener != null){
                    mOnItemClickListener.onClick(itemView,mData.get(getLayoutPosition()));
                }
            });
        }

        public void bindView(Chapter chapter) {
            mName.setText(chapter.getName());
        }
    }
    private VolumeAdapter.OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(VolumeAdapter.OnItemClickListener onItemClickListener){
        mOnItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        public void onClick(View view, Chapter chapter);
    }
}
