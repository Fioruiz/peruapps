package com.fruizc.sites.login.view;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.test.mock.MockPackageManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.login.widget.LoginButton;
import com.fruizc.sites.utilitarios.GPSTracker;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.fruizc.sites.R;
import com.fruizc.sites.login.presenter.LoginPresenter;
import com.fruizc.sites.login.presenter.LoginPresenterImpl;
import com.fruizc.sites.view.ContainerActivity;

import java.util.Arrays;

public class LoginActivity extends AppCompatActivity implements LoginView, View.OnClickListener {

    private TextInputLayout username, password;
    private Button login;
    private LoginButton loginButtonFacebook;
    private ProgressBar progressBarLogin;
    private LoginPresenter presenter;

    private static final String TAG = "LoginActivitys";
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private CallbackManager callbackManager;
    private String useremail;


    private static final int REQUEST_CODE_PERMISSION = 2;
    String mPermission = Manifest.permission.ACCESS_FINE_LOCATION;

    // GPSTracker class
    GPSTracker gps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        presenter = new LoginPresenterImpl(this);

        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();



        firebaseAuth = FirebaseAuth.getInstance();
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                if (firebaseUser != null){
                   // Log.w(TAG, "Usuario logeado " + firebaseUser.getEmail());
                    goHome();
                }else {
                   // Log.w(TAG, "Usuario No logeado ");
                }
            }
        };

        username    = (TextInputLayout) findViewById(R.id.username);
        password    = (TextInputLayout) findViewById(R.id.password);

        useremail = username.getEditText().getText().toString();

        login       = (Button) findViewById(R.id.login);
        progressBarLogin = (ProgressBar) findViewById(R.id.progressbarLogin);
        hideProgressBar();



        login.setOnClickListener(this);


    }

    private void signIn(String username, String password) {
        presenter.signIn(username, password, this, firebaseAuth);
    }

    public void goCreateAccount(View view){
        goCreateAccount();
    }


    @Override
    public void enableInputs() {
        username.setEnabled(true);
        password.setEnabled(true);
        login.setEnabled(true);
    }

    @Override
    public void disableInputs() {
        username.setEnabled(false);
        password.setEnabled(false);
        login.setEnabled(false);
    }

    @Override
    public void showProgressBar() {
        progressBarLogin.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        progressBarLogin.setVisibility(View.GONE);
    }

    @Override
    public void loginError(String error) {
        Toast.makeText(this, getString(R.string.login_error) + error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void goCreateAccount() {
        Intent intent = new Intent(this, CreateAccountActivity.class);
        startActivity(intent);
    }

    @Override
    public void goHome() {

        Bundle b = new Bundle();
        b.putString("useremail", useremail);
        Intent intent = new Intent(this, ContainerActivity.class);
        intent.putExtras(b);
        startActivity(intent);
    }

    @Override
    public void showErrorPassword(String error) {
        password.setError(error);

    }

    @Override
    public void showErrorEmail(String error) {
        username.setError(error);
    }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(authStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        firebaseAuth.removeAuthStateListener(authStateListener);
    }


    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.login:

                String user = username.getEditText().getText().toString().trim();
                String pass = password.getEditText().getText().toString().trim();

                if (presenter.isFieldValid(user, pass)) {
                    signIn(user, pass);
                }

                break;
            default:
                break;
        }


    }

    public void getLocation()
    {
        if (ActivityCompat.checkSelfPermission(this, mPermission)
                != MockPackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{mPermission},
                    REQUEST_CODE_PERMISSION);


        }

        gps = new GPSTracker(LoginActivity.this);

        // check if GPS enabled
        if(gps.canGetLocation()){

            double latitude = gps.getLatitude();
            double longitude = gps.getLongitude();

           /* Toast.makeText(getApplicationContext(), "Your Location is - \nLat: "
                    + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();*/
        }else{

            gps.showSettingsAlert();
        }

    }

}
