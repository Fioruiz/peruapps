package com.fruizc.sites.login.view;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.test.mock.MockPackageManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.fruizc.sites.modeldb.User;
import com.fruizc.sites.utilitarios.GPSTracker;
import com.fruizc.sites.view.ContainerActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.fruizc.sites.R;
import com.fruizc.sites.login.presenter.CreateAccountPresenter;
import com.fruizc.sites.login.presenter.CreateAccountPresenterImpl;

public class CreateAccountActivity extends AppCompatActivity implements CreateAccountView{

    private static final String TAG = "CreateAccountActivity";


    private Button btnJoinUs;
    private TextInputEditText edtEmail, edtName, edtUser, edtPassword, edtPasswordConfirm;
    private ProgressBar progressBarCreateAccount;
    private static final int REQUEST_CODE_PERMISSION = 2;
    private String mPermission = Manifest.permission.ACCESS_FINE_LOCATION;
    private double latitude ;
    private double longitude ;

    // GPSTracker class
    GPSTracker gps;

    private boolean userLogged = false;
     User user;
     Activity activity;

    private FirebaseAuth firebaseAuth;
    private CallbackManager callbackManager;


    private CreateAccountPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        activity = this;
        showToolbar(getResources().getString(R.string.toolbar_tittle_createaccount), true);

        getLocation();


        presenter = new CreateAccountPresenterImpl(this);

        btnJoinUs           = (Button) findViewById(R.id.joinUs);
        edtEmail            = (TextInputEditText) findViewById(R.id.email);
        edtName             = (TextInputEditText) findViewById(R.id.name);
        edtUser             = (TextInputEditText) findViewById(R.id.user);
        edtPassword         = (TextInputEditText) findViewById(R.id.password_createaccount);
        edtPasswordConfirm  = (TextInputEditText) findViewById(R.id.confirmPassword);
        progressBarCreateAccount  = (ProgressBar) findViewById(R.id.progressbarCreateAccount);

        hideProgressBar();

        callbackManager = CallbackManager.Factory.create();
        firebaseAuth = FirebaseAuth.getInstance();



        btnJoinUs.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                user = new User(
                    edtName.getText().toString(),
                    edtUser.getText().toString(),
                    edtEmail.getText().toString(),
                    edtPassword.getText().toString(),
                        String.valueOf(latitude),
                        String.valueOf(longitude)

                );



                if (user.getUseremail().trim().equals("")){
                    Toast.makeText(CreateAccountActivity.this, "El Email es obligatorio", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (user.getName().trim().equals("")){
                    Toast.makeText(CreateAccountActivity.this, "El Nombre es obligatorio", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (user.getUsername().trim().equals("")){
                    Toast.makeText(CreateAccountActivity.this, "El Username es obligatorio", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!user.getPassword().equals(edtPasswordConfirm.getText().toString())){
                    Toast.makeText(CreateAccountActivity.this, "Los Passwords deben ser iguales", Toast.LENGTH_SHORT).show();
                    return;
                }

                createAccount(user);
            }
        });

    }

    private void createAccount(User user) {

        presenter.createAccount(user, getBaseContext(),this,firebaseAuth);

    }

    public void showToolbar(String tittle, boolean upButton){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(tittle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);

    }


    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();

    }

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void showProgressBar() {
        progressBarCreateAccount.setVisibility(View.VISIBLE);

    }

    @Override
    public void hideProgressBar() {
        progressBarCreateAccount.setVisibility(View.GONE);
    }

    @Override
    public void createAccountError(String error) {
        Toast.makeText(this, "Ocurrió este error: " + error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void goHome() {
        Intent intent = new Intent(this, ContainerActivity.class);
        startActivity(intent);
    }

    public void getLocation()
    {
        if (ActivityCompat.checkSelfPermission(this, mPermission)
                != MockPackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{mPermission},
                    REQUEST_CODE_PERMISSION);


        }

        gps = new GPSTracker(CreateAccountActivity.this);

        // Verifica si está habilitado
        if(gps.canGetLocation()){

             latitude = gps.getLatitude();
             longitude = gps.getLongitude();

           /* Toast.makeText(getApplicationContext(), "Your Location is - \nLat: "
                    + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();*/
        }else{

            gps.showSettingsAlert();
        }

    }



}
