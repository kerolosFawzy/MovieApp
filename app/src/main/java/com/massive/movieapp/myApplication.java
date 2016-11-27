package com.massive.movieapp;

import android.app.Application;
import android.graphics.Bitmap;

import com.massive.movieapp.utils.CacheUtils;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;

import java.io.File;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class myApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        RealmConfiguration configuration = new RealmConfiguration
                .Builder(this).name("Movie_database.realm").build();
        Realm.setDefaultConfiguration(configuration);

        OkHttpClient picassoClient = new OkHttpClient();
        //picassoClient.interceptors().add(new OAuth2Interceptor());
        File cache = CacheUtils.createDefaultCacheDir(this);
        picassoClient.setCache(new com.squareup.okhttp.Cache(cache, CacheUtils.calculateDiskCacheSize(cache)));

        Picasso picasso = new Picasso.Builder(this)
                .defaultBitmapConfig(Bitmap.Config.RGB_565)
                .indicatorsEnabled(BuildConfig.DEBUG)
                .downloader(new OkHttpDownloader(picassoClient))
                .build();

        Picasso.setSingletonInstance(picasso);

    }
}
