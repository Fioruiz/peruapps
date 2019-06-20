package com.fruizc.sites.login.interactor;

import android.app.Activity;
import android.content.Context;

import com.fruizc.sites.modeldb.User;
import com.google.firebase.auth.FirebaseAuth;



public interface CreateAccountInteractor {
    void createAccount(User user, Context context, Activity activity,  FirebaseAuth firebaseAuth); // Interactor
}
