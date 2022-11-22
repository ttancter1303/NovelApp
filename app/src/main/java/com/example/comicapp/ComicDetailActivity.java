package com.example.comicapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.example.comicapp.RecycleAdapter.HistoryRecycleAdapter;
import com.example.comicapp.ViewPager2Adapter.ComicDetailAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;

public class ComicDetailActivity extends AppCompatActivity {
private static final String COMIC_DETAIL = "com.example.comicapp.COMIC_DETAIL";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comic_detail);

        Bundle bundle = getIntent().getExtras();
        if(bundle == null){
            return;
        }
        bundle.get(COMIC_DETAIL);

        ViewPager2 viewPager2 = findViewById(R.id.bodyComicDetailViewPager2);
        TabLayout tabLayout = findViewById(R.id.tabLayoutComicDetail);
        ComicDetailAdapter adapter = new ComicDetailAdapter(this);
        viewPager2.setAdapter(adapter);
        TabLayoutMediator mediator = new TabLayoutMediator(tabLayout, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch (position){
                    case 0:
                        tab.setText("Giới thiệu");
                        break;
                    case 1:
                        tab.setText("Danh sách chương");
                        break;
                }
            }
        });
        mediator.attach();
    }
}