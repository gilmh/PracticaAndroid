package org.hectordam.proyectohector.secundarios;

import org.hectordam.proyectohector.R;

import android.os.Bundle;
import android.preference.PreferenceActivity;


public class Preferencia extends PreferenceActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.preferencias);
    }
}
