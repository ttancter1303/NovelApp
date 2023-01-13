
package com.example.comicapp.HistoryFragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.comicapp.R;
import com.example.comicapp.ViewPager2Adapter.HistoryViewPager2Adapter;
import com.example.comicapp.databinding.FragmentHistoryBinding;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class HistoryFragment extends Fragment {
    FragmentHistoryBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentHistoryBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        ViewPager2 viewPager2 = binding.viewPagerHistory;
        TabLayout tabLayout = binding.tabHistory;
        HistoryViewPager2Adapter adapter = new HistoryViewPager2Adapter(this);
        viewPager2.setAdapter(adapter);
        TabLayoutMediator mediator = new TabLayoutMediator(tabLayout, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch (position){
//                    case 0:
//                        tab.setText("Lịch sử");
//                        break;
                    case 1:
                        tab.setText("Đánh dấu");
                        break;
                }
            }
        });
        mediator.attach();
//        ImageView mBtnSetting = binding.btnSetting;
//        mBtnSetting.setOnClickListener(v->{
//            Navigation.findNavController(requireActivity(), R.id.fragment_host_container).
//                    navigate(R.id.action_historyFragment_to_settingFragment);
//        });

    }
}