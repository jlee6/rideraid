package com.jnj.android.rideraid.ui.preferences;

import android.os.Bundle;
import android.preference.PreferenceActivity;

import com.jnj.android.rideraid.R;

public class MainPreferenceActivity extends PreferenceActivity {
    @Override
    protected void onCreate(Bundle savedInsatanceState) {
        super.onCreate(savedInsatanceState);
        getFragmentManager().beginTransaction().replace(android.R.id.content, new PreferenceFragment()).commit();
    }

    public static class PreferenceFragment extends android.preference.PreferenceFragment {
        @Override
        public void onCreate(final Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            addPreferencesFromResource(R.xml.preferences);
        }
    }
}
