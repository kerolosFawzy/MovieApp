package com.massive.movieapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getFragmentManager().beginTransaction().replace(R.id.Container, new FragmentForActivity()).commit();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
     //   onDestroyRealm();
    }
}
