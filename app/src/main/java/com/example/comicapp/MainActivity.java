package com.example.comicapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.NavGraph;
import androidx.navigation.fragment.NavHostFragment;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private ViewPager2 viewPager2;
    private TabLayout tabLayout;
    FirebaseUser mFirebaseUser;
    FirebaseAuth mFirebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNav);
//        NavController navController = Navigation.findNavController(this, R.id.fragment_host_container);

        NavHostFragment navHostFragment =
                (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_host_container);

        NavGraph navGraph = navHostFragment.getNavController().getNavInflater().inflate(R.navigation.nav_graph);

//        if (mFirebaseUser != null){
//            navGraph.setStartDestination(R.id.mainActivity);
//        }else{
//            navGraph.setStartDestination(R.id.loginActivity);
//        }

        navHostFragment.getNavController().setGraph(navGraph);
        NavController navController = navHostFragment.getNavController();

        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.action_history:
                    navController.popBackStack();
                    navController.navigate(R.id.historyFragment);
                    break;
                case R.id.action_home:
                    navController.popBackStack();
                    navController.navigate(R.id.homeFragment);
                    break;
//                case R.id.action_notification:
//                    navController.popBackStack();
//                    navController.navigate(R.id.notificationFragment);
//                    break;
                case R.id.action_user:
                    navController.popBackStack();
                    navController.navigate(R.id.userFragment);
                    break;

            }
            return true;
        });
    }


    @Override
    protected void onResume() {
        super.onResume();

    }
}
