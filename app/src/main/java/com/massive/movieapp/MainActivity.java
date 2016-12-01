package com.massive.movieapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.massive.movieapp.fragments.FragmentForActivity;

public class MainActivity extends AppCompatActivity {

    boolean mTwoPane = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
        if (findViewById( R.id.details_frag ) != null) {
            mTwoPane = true;
        } else {
            getFragmentManager().beginTransaction().replace( R.id.Container, new FragmentForActivity() ).commit();
        }
    }

    public boolean CheckTwoPane() {
        return mTwoPane;
    }
}