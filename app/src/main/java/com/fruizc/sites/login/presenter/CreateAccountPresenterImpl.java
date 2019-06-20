package com.fruizc.sites.login.presenter;

import android.app.Activity;
import android.content.Context;

import com.fruizc.sites.login.interactor.CreateAccountInteractor;
import com.fruizc.sites.login.interactor.CreateAccountInteractorImpl;
import com.fruizc.sites.login.view.CreateAccountView;
import com.fruizc.sites.modeldb.User;
import com.google.firebase.auth.FirebaseAuth;


public class CreateAccountPresenterImpl implements CreateAccountPresenter {

    private CreateAccountView createAccountView;
    private CreateAccountInteractor interactor;

    public CreateAccountPresenterImpl(CreateAccountView createAccountView) {
        this.createAccountView = createAccountView;
        interactor = new CreateAccountInteractorImpl(this);
    }

    @Override
    public void createAccount(User user, Context context,  Activity activity,FirebaseAuth firebaseAuth) {
        createAccountView.showProgressBar();
        interactor.createAccount(user, context, activity,firebaseAuth);
    }



    @Override
    public void createAccountSuccess() {
        createAccountView.goHome();
        createAccountView.hideProgressBar();
    }

    @Override
    public void createAccountError(String error) {
        createAccountView.createAccountError(error);
    }
}
