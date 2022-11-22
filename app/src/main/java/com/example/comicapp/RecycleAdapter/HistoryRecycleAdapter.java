package com.example.comicapp.RecycleAdapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.comicapp.ComicDetailActivity;
import com.example.comicapp.OnClick.OnClickComicListenr;
import com.example.comicapp.R;

import java.util.List;

public class HistoryRecycleAdapter extends RecyclerView.Adapter<HistoryRecycleAdapter.ViewHolder> {
    private final List<String> mListData;
    String COMIC_DETAIL;
    public String test = "1";
    public HistoryRecycleAdapter(List<String> mListData) {
        this.mListData = mListData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_comic,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        holder.layoutItem.findViewById(R.id.comicMain).setOnClickListener(view -> {
//            Navigation.findNavController(view).navigate(R.id.action_FirstFragment_to_SecondFragment);
//        });
    }


    @Override
    public int getItemCount() {
        return 100;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements OnClickComicListenr {
        private ImageView imgAvatar;
        private TextView txtHeader;
        private TextView txtRead;
        private LinearLayout layoutItem;
        private Context mContext;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
//
//            imgAvatar = itemView.findViewById(R.id.imageView);
//            txtHeader = itemView.findViewById(R.id.header_title);
//            txtRead = itemView.findViewById(R.id.txt_read);
//            layoutItem = itemView.findViewById(R.id.comicMain);
//            layoutItem.setOnClickListener(view -> {
//                Intent intent = new Intent(mContext, ComicDetailActivity.class);
//                Bundle bundle = new Bundle();
//
//                bundle.putSerializable(COMIC_DETAIL,test);
//                intent.putExtras(bundle);
//                mContext.startActivity(intent);
//            });

        }
        public void bindView(String data){
        }

        @Override
        public void OnClickComic(int position) {

        }
    }
}
