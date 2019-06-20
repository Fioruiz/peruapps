package com.fruizc.sites.login.interactor;

import android.app.Activity;

import com.google.firebase.auth.FirebaseAuth;
import com.fruizc.sites.login.presenter.LoginPresenter;
import com.fruizc.sites.login.repository.LoginRepository;
import com.fruizc.sites.login.repository.LoginRepositoryImpl;



public class LoginInteractorImpl  implements LoginInteractor{

    private LoginPresenter presenter;
    private LoginRepository repository;

    public LoginInteractorImpl(LoginPresenter presenter) {
        this.presenter = presenter;
        repository = new LoginRepositoryImpl(presenter);
    }

    @Override
    public void signIn(String username, String password, Activity activity, FirebaseAuth firebaseAuth) {
        repository.signIn(username, password, activity, firebaseAuth);
    }
}
