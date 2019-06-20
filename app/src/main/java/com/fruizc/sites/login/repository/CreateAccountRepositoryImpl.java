package com.fruizc.sites.login.repository;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;

import com.fruizc.sites.modeldb.User;
import com.fruizc.sites.utilitarios.Perfil;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.fruizc.sites.login.presenter.CreateAccountPresenter;



public class CreateAccountRepositoryImpl implements CreateAccountRepository {

    private static final String TAG = "CreateAccountRepository";
    private CreateAccountPresenter presenter;
    private Perfil perfil_gestion;

    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

    public CreateAccountRepositoryImpl(CreateAccountPresenter presenter) {
        this.presenter = presenter;


    }
    
    public void createAccount(final User useFire, Context context,  Activity activity, FirebaseAuth firebaseAuth) {
        perfil_gestion = new Perfil(context);

        firebaseAuth.createUserWithEmailAndPassword(useFire.getUseremail(),useFire.getPassword())
                .addOnCompleteListener( activity,new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()){

                            FirebaseFirestore db = FirebaseFirestore.getInstance();

                            User newuser = new User(useFire.getName(),useFire.getUsername(),useFire.getUseremail(),useFire.getPassword(),useFire.getLatitud(),useFire.getLongitud());
                            db.collection("userSites").document().set(newuser);

                            /****** GUARDAR DATOS PARA PERFIL ******/

                            perfil_gestion.crearPerfil(useFire.getName(), useFire.getUseremail());


                            /**** OBTIENE LOS DATOS PARA EL PERFIL ****/
                            perfil_gestion.setCorreo(useFire.getUseremail());
                            perfil_gestion.seNombre(useFire.getName());
                            presenter.createAccountSuccess();


                        }else {

                            presenter.createAccountError("Ocurrió un error al crear la cuenta");
                        }

                    }
                });


    }





}
