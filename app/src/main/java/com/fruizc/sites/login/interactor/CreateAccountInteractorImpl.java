package com.fruizc.sites.login.interactor;

import android.app.Activity;
import android.content.Context;

import com.fruizc.sites.login.presenter.CreateAccountPresenter;
import com.fruizc.sites.login.repository.CreateAccountRepository;
import com.fruizc.sites.login.repository.CreateAccountRepositoryImpl;
import com.fruizc.sites.modeldb.User;
import com.google.firebase.auth.FirebaseAuth;



public class CreateAccountInteractorImpl implements CreateAccountInteractor{

    private CreateAccountPresenter presenter;
    private CreateAccountRepository repository;

    public CreateAccountInteractorImpl(CreateAccountPresenter presenter) {
        this.presenter = presenter;
        repository = new CreateAccountRepositoryImpl(presenter);
    }


    @Override
    public void createAccount(User user, Context context,  Activity activity, FirebaseAuth firebaseAuth) {
        repository.createAccount(user, context,activity,firebaseAuth);
    }
}
