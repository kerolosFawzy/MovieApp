package com.massive.movieapp;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class myApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder(this)
                .name(Realm.DEFAULT_REALM_NAME)
                .schemaVersion(0)
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(realmConfiguration);

//        OkHttpClient picassoClient = new OkHttpClient();
        //picassoClient.interceptors().add(new OAuth2Interceptor());
//        File cache = CacheUtils.createDefaultCacheDir(this);
//        picassoClient.setCache(new com.squareup.okhttp.Cache(cache, CacheUtils.calculateDiskCacheSize(cache)));
//
//        Picasso picasso = new Picasso.Builder(this)
//                .defaultBitmapConfig(Bitmap.Config.RGB_565)
//                .indicatorsEnabled(BuildConfig.DEBUG)
//                .downloader(new OkHttpDownloader(picassoClient))
//                .build();
//
//        Picasso.setSingletonInstance(picasso);

    }
}
