package com.fruizc.sites.post.view;

import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.transition.Fade;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.firebase.storage.StorageReference;
import com.fruizc.sites.AppController;
import com.fruizc.sites.R;
import com.squareup.picasso.Picasso;

public class PictureDetailActivity extends AppCompatActivity {

    private ImageView imageHeader;
    private AppController app;
    StorageReference storageReference;
    private Bundle b;
    private  String namePhoto,description,name;

    private String TAG = "PictureDetailActivity";
    private TextView txvContentImage,txvnamePlace;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture_detail);

        b= getIntent().getExtras();

        namePhoto = b.getString("picture");
        description = b.getString("description");
        name = b.getString("name");


        imageHeader = (ImageView) findViewById(R.id.imageHeader);
        txvContentImage = (TextView) findViewById(R.id.txvContentImage);
        txvnamePlace = (TextView) findViewById(R.id.txvnamePlace);

        showToolbar("", true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            getWindow().setStatusBarColor(Color.TRANSPARENT);
            getWindow().setEnterTransition(new Fade());
        }


        Picasso.with(PictureDetailActivity.this).load(namePhoto).into(imageHeader);
        txvContentImage.setText(description);
        txvnamePlace.setText(name);




    }

    public void showToolbar(String tittle, boolean upButton){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(tittle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);
        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsingToolbar);

    }



}
