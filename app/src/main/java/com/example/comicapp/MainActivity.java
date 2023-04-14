package com.example.comicapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavGraph;
import androidx.navigation.fragment.NavHostFragment;
import androidx.viewpager2.widget.ViewPager2;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;

import com.example.comicapp.Repository.ViewModel.SharedViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.FileNotFoundException;
import java.io.IOException;


public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_PHOTO_PICKER_SINGLE_SELECT = 22;
    private ViewPager2 viewPager2;
    private TabLayout tabLayout;
    FirebaseUser mFirebaseUser;
    Uri currentUri;
    FirebaseAuth mFirebaseAuth;
    private static final int PICK_IMAGE_REQUEST = 22;
    private String StringPath;
    SharedViewModel viewModel;

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
        viewModel = new ViewModelProvider(this).get(SharedViewModel.class);
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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            // Handle error
            return;
        }
        switch(requestCode) {
            case REQUEST_PHOTO_PICKER_SINGLE_SELECT:
                // Get photo picker response for single select.
                currentUri = data.getData();
                //you got image path, now you may use this
                viewModel.setData(currentUri);
                Log.d("Ttan", "onActivityResult: "+StringPath);
                return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onResume() {
        super.onResume();

    }
}
