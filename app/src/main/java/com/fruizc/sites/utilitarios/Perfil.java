package com.fruizc.sites.utilitarios;

import android.content.Context;
import android.content.SharedPreferences;

public class Perfil {

    // Shared Preferences
    SharedPreferences pref;

    // Editor
    SharedPreferences.Editor editor;

    // Context
    Context context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Sharedpref nombre
    private static final String PREF_NAME = "PerPref";

    // nombre
    public static final String KEY_NAME = "nombre";

    //  correo
    public static final String KEY_CORREO = "correo";

    // Constructor
    public Perfil(Context context){
        this.context = context;
        pref = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();


    }

    public void crearPerfil(String nombre, String correo){
        editor.putString(KEY_NAME, nombre);
        editor.putString(KEY_CORREO, correo);

        editor.commit();

    }
    public void seNombre(String nombre){
        editor.putString(KEY_NAME, nombre);
        editor.commit();
    }

    public void setCorreo(String correo){
        editor.putString(KEY_CORREO, correo);
        editor.commit();
    }
    public String getNombre(){
        return pref.getString(KEY_NAME, "");
    }

    public String getCorreo(){
        return pref.getString(KEY_CORREO, "");
    }

    public void borrarPefil(){
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();

    }


}
