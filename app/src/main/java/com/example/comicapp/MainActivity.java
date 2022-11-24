package com.example.comicapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.NavGraph;
import androidx.navigation.NavInflater;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.example.comicapp.HistoryFragment.ComicDetailFragment;
import com.example.comicapp.HistoryFragment.HistoryFragment;
import com.example.comicapp.HomeFragment.HomeFragment;
import com.example.comicapp.NotificationFragment.NotificationFragment;
import com.example.comicapp.UserFragment.UserFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {
    private ViewPager2 viewPager2;
    private TabLayout tabLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        if(savedInstanceState == null){
//            replaceFragment(new HistoryFragment());
//        }
//        viewPager2 = findViewById(R.id.viewPager2Home);
//        tabLayout = findViewById(R.id.tabHome);
//        MainAdapter adapter = new MainAdapter(this);
//        viewPager2.setAdapter(adapter);
//
//        TabLayoutMediator mediator =new TabLayoutMediator(tabLayout, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
//            @Override
//            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
//                switch (position){
//                    case 0:
//                        tab.setText("Tủ Truyện");
//
//                        break;
//                    case 1:
//                        tab.setText("Home");
//
//                        break;
//                    case 2:
//                        tab.setText("Notification");
//
//                        break;
//                    case 3:
//                        tab.setText("Setting");
//
//                        break;
//                }
//            }
//        });
//        mediator.attach();
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNav);
//        NavController navController = Navigation.findNavController(this, R.id.fragment_host_container);
        NavHostFragment navHostFragment =
                (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_host_container);
        NavGraph graph = navHostFragment.getNavController().getNavInflater().inflate(R.navigation.nav_graph);
        navHostFragment.getNavController().setGraph(graph);
        NavController navController = navHostFragment.getNavController();

        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.action_history:
                    navController.popBackStack();
                    navController.navigate(R.id.historyFragment);
//                    replaceFragment(new HistoryFragment());
                    break;
                case R.id.action_home:
                    navController.popBackStack();
                    navController.navigate(R.id.homeFragment);
//                    replaceFragment(new HomeFragment());
                    break;
                case R.id.action_notification:
                    navController.popBackStack();
                    navController.navigate(R.id.notificationFragment);
//                    replaceFragment(new NotificationFragment());
//                    replaceFragment(new ComicDetailFragment());
                    break;
                case R.id.action_user:
                    navController.popBackStack();
                    navController.navigate(R.id.userFragment);
//                    replaceFragment(new UserFragment());
                    break;

            }
            return true;
        });
    }

//    private void replaceFragment(Fragment fragment){
//        FragmentManager fragmentManager = getSupportFragmentManager();
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        fragmentTransaction.replace(R.id.fragment_host_container, fragment);
//        fragmentTransaction.commit();
//    }
}