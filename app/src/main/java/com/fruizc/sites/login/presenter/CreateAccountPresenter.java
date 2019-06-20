package com.fruizc.sites.login.presenter;

import android.app.Activity;
import android.content.Context;

import com.fruizc.sites.modeldb.User;
import com.google.firebase.auth.FirebaseAuth;



public interface CreateAccountPresenter {

    void createAccount(User user, Context context, Activity activity, FirebaseAuth firebaseAuth); // Interactor
    void createAccountSuccess();
    void createAccountError(String error);
}
