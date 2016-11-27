package com.massive.movieapp.db;

import android.app.Activity;
import android.app.Application;
import android.app.Fragment;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.exceptions.RealmMigrationNeededException;


public class RealmControllers {
    public static Realm realm;
    private static RealmControllers instance = getRealmInstance();
    RealmResults<MovieDb>  mRealmresults;

    public RealmControllers(Application application) {

        try {
            realm = Realm.getDefaultInstance();
        }catch (RealmMigrationNeededException r){

        }
    }

    public static RealmControllers with(Fragment fragment1) {
        if (instance == null) {
            instance = new RealmControllers(fragment1.getActivity().getApplication());
        }
        return instance;
    }

    public static RealmControllers with(Activity activity) {
        if (instance == null) {
            instance = new RealmControllers(activity.getApplication());
        }
        return instance;
    }

    public static RealmControllers with(Application application) {
        if (instance == null) {
            instance = new RealmControllers(application);
        }
        return instance;
    }
    //and this used to get class instance
    public static RealmControllers getRealmInstance() {
        return instance;
    }
    //it used just to get realm
    public Realm getRealm() {
        return realm;
    }
    //to refresh realm
    public ArrayList<MovieDb> refresh() {
        ArrayList<MovieDb> last=new ArrayList<>();
        for (MovieDb s: mRealmresults){
            last.add(s);
        }
        return last;
    }
    public void BuildRealm(){
    }
    //to delete every thing from realm
    public void deleteAll() {
        realm.beginTransaction();
        realm.clear(MovieDb.class);
        realm.commitTransaction();
    }

    //to get all data from realm
    public static RealmResults<MovieDb> getData() {
        return realm.where(MovieDb.class).findAll();
    }

    //to et movie id if we need to use it
    public MovieDb getMovieid(String name) {
        return realm.where(MovieDb.class).equalTo("id", name).findFirst();
    }

    //check if the realm is empty or not
    public boolean hasData() {
        return !realm.allObjects(MovieDb.class).isEmpty();
    }

    public static void onDestroyRealm() {
        realm.close();
    }

}
