package com.fruizc.sites.login.presenter;

import android.app.Activity;
import android.util.Patterns;

import com.google.firebase.auth.FirebaseAuth;
import com.fruizc.sites.login.interactor.LoginInteractor;
import com.fruizc.sites.login.interactor.LoginInteractorImpl;
import com.fruizc.sites.login.view.LoginView;



public class LoginPresenterImpl implements LoginPresenter {

    private LoginView loginView;
    private LoginInteractor interactor;

    public LoginPresenterImpl(LoginView loginView) {
        this.loginView = loginView;
        interactor = new LoginInteractorImpl(this);
    }

    @Override
    public void signIn(String username, String password, Activity activity, FirebaseAuth firebaseAuth) {

        loginView.disableInputs();
        loginView.showProgressBar();

        interactor.signIn(username,password, activity, firebaseAuth);
    }

    @Override
    public void loginSuccess() {
        loginView.goHome();

        loginView.hideProgressBar();
    }

    @Override
    public void loginError(String error) {
        loginView.enableInputs();
        loginView.hideProgressBar();
        loginView.loginError(error);
    }

    @Override
    public boolean isFieldValid(String email, String password) {
        boolean isValid = true;

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches() || email==null || email.equals("")) {
            isValid = false;
            loginView.showErrorEmail("Ingrese un Usuario correcto");
        }
        else if(password == null || password.equals("")) {
            isValid = false;
            loginView.showErrorPassword("Ingrese un password correcto");
        }
        return isValid;
    }
}
