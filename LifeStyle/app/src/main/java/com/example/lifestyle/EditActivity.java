package com.example.lifestyle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class EditActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        // set the activity label
        setTitle("Edit Profile");
    }

    // this method will inflate menu items
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_edit_profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            // Respond to a click on the "save" menu option
            case R.id.action_save:
                // save the data and send back to MainActivity
                return true;
            // Respond to a click on the "cancel" menu option
            case R.id.action_cancel:
                // don't modify user profile
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}