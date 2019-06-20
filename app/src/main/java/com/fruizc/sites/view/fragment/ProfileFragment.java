package com.fruizc.sites.view.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fruizc.sites.R;
import com.fruizc.sites.adapter.PictureAdapterRecyclerView;
import com.fruizc.sites.modeldb.Place;
import com.fruizc.sites.utilitarios.Perfil;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {


    private String TAG = "ProfileFragment";
    private TextView userProfile,txvNameTitle;
    private Perfil perfil;PictureAdapterRecyclerView pictureAdapterRecyclerView;
    RecyclerView picturesRecycler;
    ArrayList<Place> listaPlace;
    FirebaseFirestore db;


    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        showToolbar("", false, view);
        perfil = new Perfil(getContext());

        listaPlace = new ArrayList<>();


        userProfile = (TextView) view.findViewById(R.id.userProfile);
        txvNameTitle = (TextView) view.findViewById(R.id.txvNameTitle);

        userProfile.setText(perfil.getNombre());
        txvNameTitle.setText(perfil.getNombre());

        picturesRecycler = (RecyclerView) view.findViewById(R.id.picturesProfileRecycler);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        picturesRecycler.setLayoutManager(linearLayoutManager);

        db = FirebaseFirestore.getInstance();
        getPlacerUser();
        return view;
    }

    public void getPlacerUser(){

        db.collection("placesUser")
                .whereEqualTo("useremail", perfil.getCorreo())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + "user => " + document.getData());

                                Place place = document.toObject(Place.class);
                                getPlace(place.getIdPlace());
                            }

                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });

    }

    public void getPlace(long idPlace){

        db.collection("placeSites")
                .whereEqualTo("idPlace", idPlace)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                listaPlace.add(document.toObject(Place.class));
                            }

                            pictureAdapterRecyclerView = new PictureAdapterRecyclerView(listaPlace, R.layout.cardview_picture, getActivity());
                            picturesRecycler.setAdapter(pictureAdapterRecyclerView);

                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }

                    }
                });

    }




    public void showToolbar(String tittle, boolean upButton, View view){
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(tittle);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);

    }

}
