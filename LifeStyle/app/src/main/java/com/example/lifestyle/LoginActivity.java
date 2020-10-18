package com.example.lifestyle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText mEtUsername;
    private EditText mEtPassword;
    private Button mButtonLogin;
    private Button mButtonSignup;
    private UserViewModel mUserViewModel;
    private Map<String, String> credentials = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

        mEtUsername = (EditText) findViewById(R.id.et_login_username);
        mEtPassword = (EditText) findViewById(R.id.et_login_password);
        mButtonLogin = (Button) findViewById(R.id.button_login);
        mButtonSignup = (Button) findViewById(R.id.button_signup);

        mButtonLogin.setOnClickListener(this);
        mButtonSignup.setOnClickListener(this);
        mUserViewModel = new ViewModelProviders().of(this).get(UserViewModel.class);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_signup: {
                String username = mEtUsername.getText().toString();
                String password = mEtPassword.getText().toString();
                System.out.println("Current username is: " + username);
                System.out.println("Current password is: " + password);
                UserSession.getInstance().setSessionId(username);

                // todo: check if a user with this username already exists in the database, if so, throw a toast
//                LiveData<UserData> userWithGivenUsername = mUserViewModel.getData();
//                if (userWithGivenUsername.getValue() != null &&
//                        userWithGivenUsername.getValue().getId().equals(username)) {
//                    Toast.makeText(this, "Username already exists", Toast.LENGTH_SHORT).show();
//                }
//                else {
//                    // set the userSessionId and insert a new user into the database
//                    UserData userData = new UserData();
//                    userData.setId(username);
//                    userData.setPassword(password);
//                    mUserViewModel.setUserData(userData);
//                    Intent mainIntent = new Intent(this, MainActivity.class);
//                    this.startActivity(mainIntent);
//                }
                if (credentials.containsKey(username)) {
                    Toast.makeText(this, "Username already exists", Toast.LENGTH_SHORT).show();
                }
                else {
                    UserData userData = new UserData();
                    userData.setId(username);
                    userData.setPassword(password);
                    mUserViewModel.setUserData(userData);
                    credentials.put(username, password);
                    Intent mainIntent = new Intent(this, MainActivity.class);
                    this.startActivity(mainIntent);
                }

                break;
            }
            case R.id.button_login: {
                String username = mEtUsername.getText().toString();
                String password = mEtPassword.getText().toString();
                System.out.println("Current username is: " + username);
                System.out.println("Current password is: " + password);
                UserSession.getInstance().setSessionId(username);

                // todo: check if a user with this username exists, if not, throw a toast
//                LiveData<UserData> userWithGivenUsername = mUserViewModel.getData();
//                if (userWithGivenUsername.getValue() == null ||
//                        (!userWithGivenUsername.getValue().getPassword().equals(password)||
//                        !userWithGivenUsername.getValue().getId().equals(username))) {
//                    Toast.makeText(this, "Username or password maybe wrong", Toast.LENGTH_SHORT).show();
//                }
//                else {
//                    Intent mainIntent = new Intent(this, MainActivity.class);
//                    this.startActivity(mainIntent);
//                }
                if (credentials.containsKey(username) && credentials.get(username).equals(password)) {
                    Intent mainIntent = new Intent(this, MainActivity.class);
                    this.startActivity(mainIntent);
                }
                else {
                    Toast.makeText(this, "Username or password maybe wrong", Toast.LENGTH_SHORT).show();
                }

                break;
            }
        }
    }



}