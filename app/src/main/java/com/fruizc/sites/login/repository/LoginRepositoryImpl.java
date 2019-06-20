package com.fruizc.sites.login.repository;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.util.Log;

import com.fruizc.sites.modeldb.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.fruizc.sites.login.presenter.LoginPresenter;


public class LoginRepositoryImpl implements LoginRepository {

    LoginPresenter presenter;
    private String TAG = "LoginRepositoryImpl";


    public LoginRepositoryImpl(LoginPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void signIn(final String username, final String password, final Activity activity, FirebaseAuth firebaseAuth) {

        firebaseAuth.signInWithEmailAndPassword(username,password)
                .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){

                            FirebaseUser user = task.getResult().getUser();

                            SharedPreferences preferences
                                    = activity.getSharedPreferences("USER", Context.MODE_PRIVATE);

                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putString("email", user.getEmail());
                            editor.commit();

                            presenter.loginSuccess();

                        }else {
                            presenter.loginError("Ocurrió un Error");
                        }
                    }
                });


    }


}
