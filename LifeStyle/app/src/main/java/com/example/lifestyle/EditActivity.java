package com.example.lifestyle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EditActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText mEtName;
    private EditText mEtAge;
    private EditText mEtHeight;
    private EditText mEtWeight;
    private EditText mEtCountry;
    private EditText mEtCity;
    private Button mButtonCamera;
    private ImageView mIvProfileImage;

    private Spinner mSpinnerGender;
    private String mGender;
    private String GENDER_MALE = "Male";
    private String GENDER_FEMALE = "Female";
    private String GENDER_OTHERS = "Others";
    private String GENDER_UNKNOWN = "Unknown";

    private String mHeightReceived;
    private String mWeightReceived;
    private String mAgeReceived;
    private String mGenderReceived;
    private String mNameReceived;
    private String mCountryReceived;
    private String mCityReceived;

    int hasName = 0;


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
        mSpinnerGender = (Spinner) findViewById(R.id.spin_edit_gender);
        mEtAge = (EditText) findViewById(R.id.et_edit_age);
        mEtHeight = (EditText) findViewById(R.id.et_edit_height);
        mEtWeight = (EditText) findViewById(R.id.et_edit_weight);
        mEtCountry = (EditText) findViewById(R.id.et_edit_country);
        mEtCity = (EditText) findViewById(R.id.et_edit_city);
        mButtonCamera = (Button) findViewById(R.id.button);
        mIvProfileImage = (ImageView) findViewById(R.id.iv_user_profile);


        //Get the intent that created this activity.
        Intent receivedIntent = getIntent();

        //Get the string data and change the profile textView if data is not null
        if (receivedIntent.getStringExtra("HEIGHT") != null) {
            mHeightReceived = receivedIntent.getStringExtra("HEIGHT");
            if (!mHeightReceived.equals("-------"))
                mEtHeight.setText(mHeightReceived);
        }
        if (receivedIntent.getStringExtra("WEIGHT") != null) {
            mWeightReceived = receivedIntent.getStringExtra("WEIGHT");
            if (!mWeightReceived.equals("-------"))
                mEtWeight.setText(mWeightReceived);
        }
        if (receivedIntent.getStringExtra("NAME") != null) {
            mNameReceived = receivedIntent.getStringExtra("NAME");
            if (!mNameReceived.equals("-------"))
                mEtName.setText(mNameReceived);
        }
        if (receivedIntent.getStringExtra("AGE") != null) {
            mAgeReceived = receivedIntent.getStringExtra("AGE");
            if (!mAgeReceived.equals("-------"))
                mEtAge.setText(mAgeReceived);
        }
//        if (receivedIntent.getStringExtra("COUNTRY") != null) {
//            mCountryReceived = receivedIntent.getStringExtra("COUNTRY");
//            mEtCountry.setText(mCountryReceived);
//        }
//        if (receivedIntent.getStringExtra("CITY") != null) {
//            mCityReceived = receivedIntent.getStringExtra("CITY");
//            mEtCity.setText(mCountryReceived);
//        }

        mButtonCamera.setOnClickListener(this);
        setupSpinner();
    }

    private void setupSpinner() {
        // Create adapter for spinner. The list options are from the String array it will use
        // the spinner will use the default layout
        ArrayAdapter genderSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.array_gender_options, android.R.layout.simple_spinner_item);

        // Specify dropdown layout style - simple list view with 1 item per line
        genderSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        // Apply the adapter to the spinner
        mSpinnerGender.setAdapter(genderSpinnerAdapter);

        // Set the integer mSelected to the constant values
        mSpinnerGender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selection)) {
                    if (selection.equals(getString(R.string.gender_male))) {
                        mGender = GENDER_MALE;
                    } else if (selection.equals(getString(R.string.gender_female))) {
                        mGender = GENDER_FEMALE;
                    } else if (selection.equals(getString(R.string.gender_others))){
                        mGender = GENDER_OTHERS;
                    } else {
                        mGender = GENDER_UNKNOWN;
                    }
                }
            }

            // Because AdapterView is an abstract class, onNothingSelected must be defined
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mGender = GENDER_UNKNOWN;
            }
        });
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
                Intent messageIntent = new Intent(this, MainActivity.class);

                //name input check
                String name = mEtName.getText().toString();
                if (name.matches("")){
//                    Toast.makeText(this,"Enter name",Toast.LENGTH_SHORT).show();
                    mEtName.setBackgroundColor(getResources().getColor(R.color.colorWarningLight));
                    mEtName.setHint("your name is required");
                }
                else {
//                    String[] splitStrings = name.split("\\s+");
//                    if(splitStrings.length != 2){
//                        Toast.makeText(this,"Enter valid first and last name",Toast.LENGTH_SHORT).show();
//                    }
//                    else{
//                        messageIntent.putExtra("ET_NAME", name);
//                    }
                    if (name.length() > 0 && name.length() < 100) {
                        hasName = 1;
                        messageIntent.putExtra("ET_NAME", name);
                    } else
                        Toast.makeText(this,"Enter a shorter name",Toast.LENGTH_SHORT).show();
                }

                //gender input
                messageIntent.putExtra("ET_GENDER", mGender);


                //age input check
                String age = mEtAge.getText().toString();
                if (age.matches("")) {
                    Toast.makeText(this,"Enter age",Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        Integer.parseInt(age);
                        messageIntent.putExtra("ET_AGE", age);
                    } catch (Exception e) {
                        Toast.makeText(this, "Enter a valid age.", Toast.LENGTH_SHORT).show();
                    }
                }

                //height input check
                String height = mEtHeight.getText().toString();
                if (height.matches("")) {
                    Toast.makeText(this,"Enter height",Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        Double.parseDouble(height);
                        messageIntent.putExtra("ET_HEIGHT", height);
                    } catch (Exception e) {
                        Toast.makeText(this, "Enter a valid height.", Toast.LENGTH_SHORT).show();
                    }
                }

                //weight input check
                String weight = mEtWeight.getText().toString();
                if (weight.matches("")) {
                    Toast.makeText(this,"Enter weight",Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        Double.parseDouble(weight);
                        messageIntent.putExtra("ET_WEIGHT", weight);
                    } catch (Exception e) {
                        Toast.makeText(this, "Enter a valid weight.", Toast.LENGTH_SHORT).show();
                    }
                }

                //country input
                String country = mEtCountry.getText().toString();
                messageIntent.putExtra("ET_COUNTRY", country);

                //city input
                String city = mEtCity.getText().toString();
                messageIntent.putExtra("ET_CITY", city);

                //image input
                messageIntent.putExtra("IMAGE_PATH", imagePath);

                // edit input
                messageIntent.putExtra("EDIT", "true");

                // back to main activity
                if (hasName == 1) {
                    this.startActivity(messageIntent);
                }

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
        mEtAge = (EditText) findViewById(R.id.et_edit_age);
        mEtHeight = (EditText) findViewById(R.id.et_edit_height);
        mEtWeight = (EditText) findViewById(R.id.et_edit_weight);
        mEtCountry = (EditText) findViewById(R.id.et_edit_country);
        mEtCity = (EditText) findViewById(R.id.et_edit_city);

        // savedInstanceState.putInt(KEY, VALUE);
        savedInstanceState.putString("ET_NAME", mEtName.getText().toString());
//        savedInstanceState.putString("ET_GENDER", mEtGender.getText().toString());
        savedInstanceState.putString("ET_AGE", mEtAge.getText().toString());
        savedInstanceState.putString("ET_HEIGHT", mEtHeight.getText().toString());
        savedInstanceState.putString("ET_WEIGHT", mEtWeight.getText().toString());
        savedInstanceState.putString("ET_COUNTRY", mEtCountry.getText().toString());
        savedInstanceState.putString("ET_CITY", mEtCity.getText().toString());

        // Always call the superclass so it can save any view hierarchy
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        mEtName = (EditText) findViewById(R.id.et_edit_name);
//        mEtGender = (EditText) findViewById(R.id.et_edit_gender);
        mEtAge = (EditText) findViewById(R.id.et_edit_age);
        mEtHeight = (EditText) findViewById(R.id.et_edit_height);
        mEtWeight = (EditText) findViewById(R.id.et_edit_weight);
        mEtCountry = (EditText) findViewById(R.id.et_edit_country);
        mEtCity = (EditText) findViewById(R.id.et_edit_city);

        mEtName.setText(savedInstanceState.getString("ET_NAME"));
//        mEtGender.setText(savedInstanceState.getString("ET_GENDER"));
        mEtAge.setText(savedInstanceState.getString("ET_AGE"));
        mEtHeight.setText(savedInstanceState.getString("ET_HEIGHT"));
        mEtWeight.setText(savedInstanceState.getString("ET_WEIGHT"));
        mEtCountry.setText(savedInstanceState.getString("ET_COUNTRY"));
        mEtCity.setText(savedInstanceState.getString("ET_CITY"));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button: {
                //The button press should open a camera
                System.out.println("The user click the take picture button");
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
            System.out.println("Image bytecount is: " + mThumbnailImage.getByteCount());

            mIvProfileImage.setImageBitmap(Bitmap.createScaledBitmap(mThumbnailImage, 120, 120, false));
            System.out.println("displaying the image");

            //Open a file and write to it
            if (isExternalStorageWritable()) {
                try {
                    imagePath = saveImage(mThumbnailImage);
                } catch (IOException e) {
                    e.printStackTrace();
                }
//                mDisplayIntent.putExtra("imagePath", filePathString);
            } else {
                Toast.makeText(this, "External storage not writable.", Toast.LENGTH_SHORT).show();
            }
            System.out.println(imagePath);

        }
    }

    private String saveImage(Bitmap finalBitmap) throws IOException {

//        String root = Environment.getExternalStorageDirectory().toString();
////        File myDir = new File(root + "/saved_images");
////        myDir.mkdirs();
//
//        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
//        String fname = "Thumbnail_"+ timeStamp +".jpg";
//
//        String fpath = root + "/" + fname;
//        System.out.println("path to store the image is : " + fpath);
//        File file = new File(fpath);
//        if (file.exists()) file.delete ();
        File file = createImageFile();
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

    String currentPhotoPath;
    private File createImageFile() throws IOException, IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }


    private boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        Toast.makeText(this,"Cannot access external storage.",Toast.LENGTH_SHORT).show();
        return false;
    }
}