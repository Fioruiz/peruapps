package com.fruizc.sites.login.view;

/**
 * Created by anahisalgado on 24/05/17.
 */

public interface LoginView {

    void enableInputs();
    void disableInputs();

    void showProgressBar();
    void hideProgressBar();

    void loginError(String error);

    void goCreateAccount();
    void goHome();

    void showErrorPassword(String error);
    void showErrorEmail(String error);

}
