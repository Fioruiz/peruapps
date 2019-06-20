package com.fruizc.sites.view;

import android.Manifest;
import android.content.Intent;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.test.mock.MockPackageManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.fruizc.sites.login.view.CreateAccountActivity;
import com.fruizc.sites.modeldb.Place;
import com.fruizc.sites.utilitarios.GPSTracker;
import com.fruizc.sites.utilitarios.Perfil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.fruizc.sites.R;
import com.fruizc.sites.login.view.LoginActivity;
import com.fruizc.sites.post.view.HomeFragment;
import com.fruizc.sites.view.fragment.ProfileFragment;
import com.fruizc.sites.view.fragment.SearchFragment;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import java.util.Timer;
import java.util.TimerTask;

public class ContainerActivity extends AppCompatActivity {
    private static final String TAG = "ContainerActivity";
    HomeFragment homeFragment;
    ProfileFragment profileFragment;
    SearchFragment searchFragment;
    private String userEmail;
    Perfil perfil;
    Bundle b;
    FirebaseFirestore db;


    private static final int REQUEST_CODE_PERMISSION = 2;
    private String mPermission = Manifest.permission.ACCESS_FINE_LOCATION;
    private double latitude;
    private double longitude;
    // GPSTracker class
    GPSTracker gps;

    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container);

        b = new Bundle();
        userEmail = b.getString("useremail");
        perfil = new Perfil(this);

        firebaseInitialize();

        BottomBar bottomBar = (BottomBar) findViewById(R.id.bottombar);
        bottomBar.setDefaultTab(R.id.home);

        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                Bundle b  = new Bundle();
                switch (tabId){
                    case R.id.home:
                        HomeFragment homeFragment = new HomeFragment();

                        getSupportFragmentManager().beginTransaction().replace(R.id.container, homeFragment)
                                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                                .addToBackStack(null).commit();



                        break;
                    case R.id.profile:
                        ProfileFragment profileFragment = new ProfileFragment();


                        getSupportFragmentManager().beginTransaction().replace(R.id.container, profileFragment)
                                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                                .addToBackStack(null).commit();
                        break;
                    case R.id.search:
                        SearchFragment searchFragment = new SearchFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, searchFragment)
                                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                                .addToBackStack(null).commit();
                        break;
                }
            }
        });

        db = FirebaseFirestore.getInstance();




        //Declare the timer
        Timer t = new Timer();
        //Set the schedule function and rate
        t.scheduleAtFixedRate(new TimerTask() {

                                  @Override
                                  public void run() {
                                      //Called each time when 1000 milliseconds (1 second) (the period parameter)
                                      System.out.println("Fiorelaa");
                                      getPlacerUser();
                                  }

                              },
                    //Set how long before to start calling the TimerTask (in milliseconds)
                0,
                //Set the amount of time between each execution (in milliseconds)
                1800000);

    }

    private void firebaseInitialize(){
        firebaseAuth = FirebaseAuth.getInstance();
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                if (firebaseUser != null){
                    Log.w(TAG, "Usuario logeado 1" + firebaseUser.getEmail() +"id: "+firebaseUser.getUid());
                    userEmail = firebaseUser.getEmail();


                }else {
                    Log.w(TAG, "Usuario No logeado ");
                }
            }
        };
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_opciones,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            
            case R.id.mSignOut:
                firebaseAuth.signOut();

                if (LoginManager.getInstance() != null){
                    LoginManager.getInstance().logOut();
                }
                Toast.makeText(this, "Se cerró la sesión", Toast.LENGTH_SHORT).show();

                Intent i = new Intent(ContainerActivity.this, LoginActivity.class);
                startActivity(i);
                break;
            
            case R.id.mAbout:
                Toast.makeText(this, "Proyecto de Localización", Toast.LENGTH_SHORT).show();
                break;
            
        }

        return super.onOptionsItemSelected(item);
    }

    public void getPlacerUser(){

        db.collection("userSites")
                .whereEqualTo("useremail", perfil.getCorreo())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + "user => " + document.getData());
                                getLocation();
                                updateLongitud(document.getId());
                                updateLatitud(document.getId());
                            }

                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });

    }

    public void updateLongitud(String idUser){

        DocumentReference longitudRef = db.collection("userSites").document(idUser);
        longitudRef
                .update("longitud", longitude)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully updated!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error updating document", e);
                    }
                });

    }
    public void updateLatitud(String idUser){

        DocumentReference latitudRef = db.collection("userSites").document(idUser);
        latitudRef
                .update("latitud", latitude)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully updated!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error updating document", e);
                    }
                });


    }


    public void getLocation()
    {
        if (ActivityCompat.checkSelfPermission(this, mPermission)
                != MockPackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{mPermission},
                    REQUEST_CODE_PERMISSION);


        }

        gps = new GPSTracker(ContainerActivity.this);

        // check if GPS enabled
        if(gps.canGetLocation()){

            latitude = gps.getLatitude();
            longitude = gps.getLongitude();

            Toast.makeText(getApplicationContext(), "Your Location is - \nLat: "
                    + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
        }else{

            gps.showSettingsAlert();
        }

    }
}
