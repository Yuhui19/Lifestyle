package com.example.lifestyle;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EditFragment extends Fragment implements View.OnClickListener{

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
    private int hasName = 0;

    //Define a request code for the camera
    static final int REQUEST_IMAGE_CAPTURE = 1;

    //Define a bitmap
    Bitmap mThumbnailImage;
    String imagePath;

    public EditFragment() {
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit, container, false);

        mEtName = (EditText) view.findViewById(R.id.et_edit_name);
        mSpinnerGender = (Spinner) view.findViewById(R.id.spin_edit_gender);
        mEtAge = (EditText) view.findViewById(R.id.et_edit_age);
        mEtHeight = (EditText) view.findViewById(R.id.et_edit_height);
        mEtWeight = (EditText) view.findViewById(R.id.et_edit_weight);
        mEtCountry = (EditText) view.findViewById(R.id.et_edit_country);
        mEtCity = (EditText) view.findViewById(R.id.et_edit_city);
        mButtonCamera = (Button) view.findViewById(R.id.button);
        mIvProfileImage = (ImageView) view.findViewById(R.id.iv_user_profile);
        mButtonCamera.setOnClickListener(this);
        setupSpinner();
        return view;
    }

    private void setupSpinner() {
        // Create adapter for spinner. The list options are from the String array it will use
        // the spinner will use the default layout
        ArrayAdapter genderSpinnerAdapter = ArrayAdapter.createFromResource(getActivity(),
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

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_edit_profile, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            // Respond to a click on the "save" menu option
            case R.id.action_save:
                // save the data and send back to MainActivity
                if (isTablet()) {
                    FragmentTransaction fTrans = getActivity().getSupportFragmentManager().beginTransaction();
                    fTrans.replace(R.id.module_info_fragment_tablet, new ProfileFragment(), "frag_profile");
                    fTrans.addToBackStack(null);
                    fTrans.commit();
                }
                else {
                    Intent messageIntent = new Intent(getActivity(), MainActivity.class);

                    //name input check
                    String name = mEtName.getText().toString();
                    if (name.matches("")) {
//                    Toast.makeText(this,"Enter name",Toast.LENGTH_SHORT).show();
                        mEtName.setBackgroundColor(getResources().getColor(R.color.colorWarningLight));
                        mEtName.setHint("your name is required");
                    } else {
//                    String[] splitStrings = name.split("\\s+");
//                    if(splitStrings.length != 2){
//                        Toast.makeText(this,"Enter valid first and last name",Toast.LENGTH_SHORT).show();
//                    }
//                    else{
//                        messageIntent.putExtra("ET_NAME", name);
//                    }
                        if (name.length() > 0 && name.length() < 50) {
                            hasName = 1;
                            messageIntent.putExtra("ET_NAME", name);
                        } else
                            Toast.makeText(getActivity(), "Enter a shorter name", Toast.LENGTH_SHORT).show();
                    }

                    //gender input
                    messageIntent.putExtra("ET_GENDER", mGender);


                    //age input check
                    String age = mEtAge.getText().toString();
                    if (age.matches("")) {
                        Toast.makeText(getActivity(), "Enter age", Toast.LENGTH_SHORT).show();
                    } else {
                        try {
                            int ageValue = Integer.parseInt(age);
                            if (0 <= ageValue && ageValue <= 200)
                                messageIntent.putExtra("ET_AGE", age);
                            else
                                Toast.makeText(getActivity(), "Enter a valid age.", Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            Toast.makeText(getActivity(), "Enter a valid age.", Toast.LENGTH_SHORT).show();
                        }
                    }

                    //height input check
                    String height = mEtHeight.getText().toString();
                    if (height.matches("")) {
                        Toast.makeText(getActivity(), "Enter height", Toast.LENGTH_SHORT).show();
                    } else {
                        try {
                            double heightValue = Double.parseDouble(height);
                            if (0 <= heightValue && heightValue <= 10)
                                messageIntent.putExtra("ET_HEIGHT", height);
                            else
                                Toast.makeText(getActivity(), "Enter a valid height.", Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            Toast.makeText(getActivity(), "Enter a valid height.", Toast.LENGTH_SHORT).show();
                        }
                    }

                    //weight input check
                    String weight = mEtWeight.getText().toString();
                    if (weight.matches("")) {
                        Toast.makeText(getActivity(), "Enter weight", Toast.LENGTH_SHORT).show();
                    } else {
                        try {
                            double weightValue = Double.parseDouble(weight);
                            if (0 <= weightValue && weightValue <= 1800)
                                messageIntent.putExtra("ET_WEIGHT", weight);
                            else
                                Toast.makeText(getActivity(), "Enter a valid weight.", Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            Toast.makeText(getActivity(), "Enter a valid weight.", Toast.LENGTH_SHORT).show();
                        }
                    }

                    //country input
                    String country = mEtCountry.getText().toString();
                    if (country.matches("")) {
                        Toast.makeText(getActivity(), "Enter country", Toast.LENGTH_SHORT).show();
                    } else {
                        if (country.length() > 0 && country.length() < 20) {
                            messageIntent.putExtra("ET_COUNTRY", country);
                        } else
                            Toast.makeText(getActivity(), "Enter a valid country name", Toast.LENGTH_SHORT).show();
                    }

                    //city input
                    String city = mEtCity.getText().toString();
                    if (country.matches("")) {
                        Toast.makeText(getActivity(), "Enter city", Toast.LENGTH_SHORT).show();
                    } else {
                        if (country.length() > 0 && country.length() < 20) {
                            messageIntent.putExtra("ET_CITY", city);
                        } else
                            Toast.makeText(getActivity(), "Enter a valid city name", Toast.LENGTH_SHORT).show();
                    }

                    //image input
                    messageIntent.putExtra("IMAGE_PATH", imagePath);

                    // edit input
                    messageIntent.putExtra("EDIT", "true");

                    // back to main activity
                    if (hasName == 1) {
                        this.startActivity(messageIntent);
                    }
                }

                return true;
            // Respond to a click on the "cancel" menu option
            case R.id.action_cancel:
                // don't modify user profile
                // return profile fragment
                if (isTablet()) {
                    FragmentTransaction fTrans = getActivity().getSupportFragmentManager().beginTransaction();
                    fTrans.replace(R.id.module_info_fragment_tablet, new ProfileFragment(), "frag_profile");
                    fTrans.addToBackStack(null);
                    fTrans.commit();
                }
                else {
                    getActivity().finish();
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }



    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button: {
                //The button press should open a camera
                System.out.println("The user click the take picture button");
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if(cameraIntent.resolveActivity(getActivity().getPackageManager())!=null){
                    startActivityForResult(cameraIntent, REQUEST_IMAGE_CAPTURE);
                }
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == getActivity().RESULT_OK) {

            mIvProfileImage = (ImageView) getActivity().findViewById(R.id.iv_user_profile);

            //Get the bitmap
            Bundle extras = data.getExtras();
//            mThumbnailImage = (Bitmap) extras.get("data");
            Bitmap photo = (Bitmap) extras.get("data");
            System.out.println("get image!!!!!");
            System.out.println("displaying the image");

            //Open a file and write to it
            if (isExternalStorageWritable()) {
                try {
                    imagePath = saveImage(photo);
                } catch (IOException e) {
                    e.printStackTrace();
                }
//                mDisplayIntent.putExtra("imagePath", filePathString);
            } else {
                Toast.makeText(getActivity(), "External storage not writable.", Toast.LENGTH_SHORT).show();
            }
            System.out.println(imagePath);

            Bitmap image = BitmapFactory.decodeFile(imagePath);
            mIvProfileImage.setImageBitmap(image);

        }
    }

    private String saveImage(Bitmap finalBitmap) throws IOException {

//        if (file.exists()) file.delete ();
        File file = createImageFile();
        try {
            FileOutputStream out = new FileOutputStream(file);
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();
            Toast.makeText(getActivity(),"file saved!",Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return file.getAbsolutePath();
    }

    String currentPhotoPath;
    private File createImageFile() throws IOException, IOException {
//        // Create an image file name
        String directory = getActivity().getFilesDir().getAbsolutePath();
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_.jpg";
        File file = new File(directory, imageFileName);
        return file;
    }


    private boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        Toast.makeText(getActivity(),"Cannot access external storage.",Toast.LENGTH_SHORT).show();
        return false;
    }

    public boolean isTablet() {
        return getResources().getBoolean(R.bool.isTablet);
    }
}
