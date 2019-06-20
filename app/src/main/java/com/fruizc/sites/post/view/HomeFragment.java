package com.fruizc.sites.post.view;



import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.fruizc.sites.modeldb.Place;
import com.fruizc.sites.modeldb.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.fruizc.sites.R;
import com.fruizc.sites.adapter.PictureAdapterRecyclerView;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 *
 */
public class HomeFragment extends Fragment {

    private static final int REQUEST_CAMERA = 1;
    private static final String TAG = "firebase ";
    private FloatingActionButton fabCamera;
    private String photoPathTemp = "";
    FirebaseFirestore db;
    private String useremail;

    PictureAdapterRecyclerView pictureAdapterRecyclerView;
    RecyclerView picturesRecycler;
    ArrayList<Place> listaPlace;



    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       // FirebaseCrash.report(new Exception("Reporte desde HomeFragment"));
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        showToolbar(getResources().getString(R.string.tab_home), false, view);

        fabCamera = (FloatingActionButton) view.findViewById(R.id.fabCamera);

        picturesRecycler = (RecyclerView) view.findViewById(R.id.pictureRecycler);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        picturesRecycler.setLayoutManager(linearLayoutManager);
        db = FirebaseFirestore.getInstance();

        listaPlace = new ArrayList<>();
        getPlaces();


        fabCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                takePicture();
            }
        });


        return view;
    }

    public void getPlaces(){
        db.collection("placeSites")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());

                                Place newplace = document.toObject(Place.class);
                                listaPlace.add(newplace);
                                //places.add(new Place((long)newplace.getIdPlace(), newplace.getPlaceName(),newplace.getDescPlace(),newplace.getCreatedAt(),newplace.getPicture()));

                                Log.d(TAG, document.getId() + " => " + newplace.getPlaceName());
                            }

                            pictureAdapterRecyclerView = new PictureAdapterRecyclerView(listaPlace, R.layout.cardview_picture, getActivity());
                            picturesRecycler.setAdapter(pictureAdapterRecyclerView);


                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });

    }

    private void takePicture() {

        Intent intentTakePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intentTakePicture.resolveActivity(getActivity().getPackageManager()) != null){

            File photoFile = null;

            try {
                photoFile = createImageFile();

            }catch (Exception e){
                e.printStackTrace();
            }

            if (photoFile != null){
                Uri photoUri = FileProvider.getUriForFile(getActivity(), "com.fruizc.sites", photoFile);
                intentTakePicture.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                startActivityForResult(intentTakePicture, REQUEST_CAMERA);
            }


        }

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQUEST_CAMERA && resultCode == getActivity().RESULT_OK){
            Log.d("HomeFragment", "CAMERA OK!! :)");
            Intent i = new Intent(getActivity(), NewPostActivity.class);
            i.putExtra("PHOTO_PATH_TEMP", photoPathTemp);
            startActivity(i);
        }
    }



    public void showToolbar(String tittle, boolean upButton, View view){
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(tittle);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);


    }
    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HH-mm-ss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        File photo = File.createTempFile(imageFileName, ".jpg", storageDir);

        photoPathTemp = "file:" + photo.getAbsolutePath();

        return photo;

    }

}
