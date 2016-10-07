package com.jnj.android.rideraid.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.jnj.android.rideraid.R;
import com.jnj.android.rideraid.ui.fragment.Telemetry;
import com.jnj.android.rideraid.ui.preferences.MainPreferenceActivity;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_fragment_placeholder, new Telemetry()).commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_preferences:
                startActivity(new Intent(this, MainPreferenceActivity.class));
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
