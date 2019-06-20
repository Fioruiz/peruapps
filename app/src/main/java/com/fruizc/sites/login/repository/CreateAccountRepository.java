package com.fruizc.sites.login.repository;

import android.app.Activity;
import android.content.Context;
import com.fruizc.sites.modeldb.User;
import com.google.firebase.auth.FirebaseAuth;


public interface CreateAccountRepository {
    void createAccount(User user, Context context, Activity activity,FirebaseAuth firebaseAuth); // Interactor
}
