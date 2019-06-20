package com.fruizc.sites.post.view;

import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.fruizc.sites.modeldb.Place;
import com.fruizc.sites.modeldb.PlaceUser;
import com.fruizc.sites.utilitarios.Perfil;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.fruizc.sites.AppController;
import com.fruizc.sites.R;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class NewPostActivity extends AppCompatActivity {

    private static final String TAG = "NewPostActivity";
    private ImageView imgPhoto;
    private Button btnCreatePost;
    private TextInputLayout edtTitle, edtDescription;
    private Perfil perfil;

    private String photoPath;
    private AppController app;
    private StorageReference storageReference;
    private   UploadTask uploadTask;
    FirebaseFirestore db;
    private List<Place> listaPlaces;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_post);
        app = (AppController) getApplicationContext();
        storageReference = app.getStorageReference();
        db = FirebaseFirestore.getInstance();

        perfil = new Perfil(this);
        getPlaces();

        edtTitle = (TextInputLayout) findViewById(R.id.edtTitle);
        edtDescription =(TextInputLayout) findViewById(R.id.edtDescription);


        imgPhoto = (ImageView) findViewById(R.id.imgPhoto);
        btnCreatePost = (Button) findViewById(R.id.btnCreatePost);

        if (getIntent().getExtras() != null){
            photoPath = getIntent().getExtras().getString("PHOTO_PATH_TEMP");
            showPhoto();
        }

        btnCreatePost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadPhoto();

            }
        });

    }



    private void uploadPhoto() {
        imgPhoto.setDrawingCacheEnabled(true);
        imgPhoto.buildDrawingCache();

        Bitmap bitmap = imgPhoto.getDrawingCache();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);

        byte[] photoByte = baos.toByteArray();
        String photoName = photoPath.substring(photoPath.lastIndexOf("/")+1, photoPath.length());

        final StorageReference photoReference = storageReference.child("images/"+photoName);

        uploadTask = photoReference.putBytes(photoByte);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w(TAG, "Error al subir la foto " + e.toString());
                e.printStackTrace();

            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                Uri uriPhoto = taskSnapshot.getUploadSessionUri();
                String photoURL = uriPhoto.toString();
                Log.w(TAG, "URL Photo > " + photoURL);

                getUrlDownload(photoReference);

                finish();
            }
        });

    }
    public void getUrlDownload(final  StorageReference photoReference){

        Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }

                // Continue with the task to get the download URL
                return photoReference.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    Uri downloadUri = task.getResult();

                    System.out.println("url descarga : "+downloadUri);
                    createPost(downloadUri);


                } else {


                    // Handle failures
                    // ...
                }
            }
        });


    }

    public void createPost( Uri downloadUri){

        String name = edtTitle.getEditText().getText().toString();
        String description = edtDescription.getEditText().getText().toString();
        String fecha = getDate();


        if(listaPlaces.size()!= 0){

           long newIdPlace= listaPlaces.size()+1;

            Place place2 = new Place(newIdPlace,name,description,fecha,downloadUri.toString());
            db.collection("placeSites").document().set(place2);

            PlaceUser placeUser5 = new PlaceUser(newIdPlace,perfil.getCorreo());
            db.collection("placesUser").document().set(placeUser5);
        }



    }

    public String  getDate(){

        SimpleDateFormat formateador = new SimpleDateFormat(
                "dd 'de' MMMM 'de' yyyy", new Locale("es_ES"));

        Date fechaDate = new Date();
        String fecha = formateador.format(fechaDate);

        return  fecha;
    }


    private void showPhoto(){
        Picasso.with(this).load(photoPath).into(imgPhoto);
    }

    public void getPlaces(){

        listaPlaces = new ArrayList<>();
        db.collection("placeSites")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());

                                Place newplace = document.toObject(Place.class);
                                listaPlaces.add(newplace);
                                Log.d(TAG, document.getId() + " => " + newplace.getPlaceName());
                            }



                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });


    }


}
