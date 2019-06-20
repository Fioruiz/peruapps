package com.fruizc.sites.view.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fruizc.sites.R;
import com.fruizc.sites.modeldb.Place;
import com.fruizc.sites.modeldb.PlaceUser;
import com.fruizc.sites.modeldb.PlaceUserDao;
import com.fruizc.sites.modeldb.User;
import com.google.firebase.firestore.FirebaseFirestore;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment {


    private String TAG ="SearchFragment";



    public SearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);


        return view;
    }

}
