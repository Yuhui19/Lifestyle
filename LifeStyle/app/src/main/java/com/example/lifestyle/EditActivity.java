package com.example.lifestyle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EditActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText mEtName;
    private EditText mEtGender;
    private EditText mEtAge;
    private EditText mEtHeight;
    private EditText mEtWeight;
    private EditText mEtCountry;
    private EditText mEtCity;
    private Button mButtonCamera;
    private ImageView mIvProfileImage;

    //Define a request code for the camera
    static final int REQUEST_IMAGE_CAPTURE = 1;

    //Define a bitmap
    Bitmap mThumbnailImage;
    String imagePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        // set the activity label
        setTitle("Edit Profile");

        mEtName = (EditText) findViewById(R.id.et_edit_name);
        mEtGender = (EditText) findViewById(R.id.et_edit_gender);
        mEtAge = (EditText) findViewById(R.id.et_edit_age);
        mEtHeight = (EditText) findViewById(R.id.et_edit_height);
        mEtWeight = (EditText) findViewById(R.id.et_edit_weight);
        mEtCountry = (EditText) findViewById(R.id.et_edit_country);
        mEtCity = (EditText) findViewById(R.id.et_edit_city);
        mButtonCamera = (Button) findViewById(R.id.button);
        mIvProfileImage = (ImageView) findViewById(R.id.iv_user_profile);

        if (savedInstanceState != null) {
            mEtName.setText(savedInstanceState.getString("ET_NAME"));
            mEtGender.setText(savedInstanceState.getString("ET_GENDER"));
        }

        mButtonCamera.setOnClickListener(this);
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
                String name = mEtName.getText().toString();
                String gender = mEtGender.getText().toString();
                String age = mEtAge.getText().toString();
                String height = mEtHeight.getText().toString();
                String weight = mEtWeight.getText().toString();
                String country = mEtCountry.getText().toString();
                String city = mEtCity.getText().toString();


                // back to main activity
                Intent messageIntent = new Intent(this, MainActivity.class);
                messageIntent.putExtra("ET_NAME", name);
                messageIntent.putExtra("ET_GENDER", gender);
                messageIntent.putExtra("ET_AGE", age);
                messageIntent.putExtra("ET_HEIGHT", height);
                messageIntent.putExtra("ET_WEIGHT", weight);
                messageIntent.putExtra("ET_COUNTRY", country);
                messageIntent.putExtra("ET_CITY", city);
                messageIntent.putExtra("IMAGE_PATH", imagePath);
                this.startActivity(messageIntent);

                return true;
            // Respond to a click on the "cancel" menu option
            case R.id.action_cancel:
                // don't modify user profile
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Save the user's current state
        mEtName = (EditText) findViewById(R.id.et_edit_name);
        mEtGender = (EditText) findViewById(R.id.et_edit_gender);

        // savedInstanceState.putInt(KEY, VALUE);
        savedInstanceState.putString("ET_NAME", mEtName.getText().toString());
        savedInstanceState.putString("ET_GENDER", mEtGender.getText().toString());

        // Always call the superclass so it can save any view hierarchy
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        mEtName = (EditText) findViewById(R.id.et_edit_name);
        mEtGender = (EditText) findViewById(R.id.et_edit_gender);

        mEtName.setText(savedInstanceState.getString("ET_NAME"));
        mEtGender.setText(savedInstanceState.getString("ET_GENDER"));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button: {
                //The button press should open a camera
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if(cameraIntent.resolveActivity(getPackageManager())!=null){
                    startActivityForResult(cameraIntent, REQUEST_IMAGE_CAPTURE);
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {

            //Get the bitmap
            Bundle extras = data.getExtras();
            mThumbnailImage = (Bitmap) extras.get("data");
            System.out.println("get image!!!!!");
            mIvProfileImage.setImageBitmap(mThumbnailImage);

            //Open a file and write to it
            if (isExternalStorageWritable()) {
                imagePath = saveImage(mThumbnailImage);
//                mDisplayIntent.putExtra("imagePath", filePathString);
            } else {
                Toast.makeText(this, "External storage not writable.", Toast.LENGTH_SHORT).show();
            }

        }
    }

    private String saveImage(Bitmap finalBitmap) {

        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/saved_images");
        myDir.mkdirs();

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String fname = "Thumbnail_"+ timeStamp +".jpg";

        File file = new File(myDir, fname);
        if (file.exists()) file.delete ();
        try {
            FileOutputStream out = new FileOutputStream(file);
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();
            Toast.makeText(this,"file saved!",Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return file.getAbsolutePath();
    }

    private boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        System.out.println("cannot use external storeage");
        return false;
    }
}