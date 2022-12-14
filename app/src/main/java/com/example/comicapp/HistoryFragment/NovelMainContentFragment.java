package com.example.comicapp.HistoryFragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.comicapp.R;
import com.example.comicapp.ViewPager2Adapter.ComicDetailAdapter;
import com.example.comicapp.databinding.FragmentComicDetailBinding;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class NovelMainContentFragment extends Fragment {
    Button mBtnReadComic;
    FragmentComicDetailBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentComicDetailBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        NavController navController = Navigation.findNavController(requireActivity(),R.id.fragment_host_container);
        ViewPager2 viewPager2 = view.findViewById(R.id.bodyComicDetailViewPager2);
        TabLayout tabLayout = view.findViewById(R.id.tabLayoutComicDetail);
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
        mBtnReadComic = binding.btnReadComic;
        mBtnReadComic.setOnClickListener(v->{
           navController.navigate(R.id.readNovelFragment);
        });
    }
}