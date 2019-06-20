package com.fruizc.sites;

import android.app.Application;
import android.support.annotation.NonNull;
import android.util.Log;

import com.facebook.FacebookSdk;
import com.fruizc.sites.modeldb.DaoMaster;
import com.fruizc.sites.modeldb.DaoSession;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.greenrobot.greendao.database.Database;

/**
 * Created by anahisalgado on 06/06/17.
 */

public class AppController extends Application {

    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseStorage firebaseStorage;

    private String TAG = "Sites";
    public static final boolean ENCRYPTED = true;
    private DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();


        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this,"users-db");
        Database db = helper.getWritableDb();
        daoSession = new DaoMaster(db).newSession();



        FacebookSdk.sdkInitialize(getApplicationContext());

        firebaseAuth = FirebaseAuth.getInstance();

        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                if (firebaseUser != null){

                   // FirebaseCrash.logcat(Log.WARN, TAG, "Usuario logeado " + firebaseUser.getEmail());
                }else {

                    //FirebaseCrash.logcat(Log.WARN, TAG, "Usuario No logeado ");
                }
            }
        };

        firebaseStorage = FirebaseStorage.getInstance();
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }

    public StorageReference getStorageReference(){
        return firebaseStorage.getReference();
    }
}
