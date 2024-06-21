package com.jds.eduTech;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.jds.eduTech.adapters.AddonTextValueSpinnerAdapter;
import com.jds.eduTech.beans.TextValue;
import com.jds.eduTech.services.EduTechServices;
import com.jds.eduTech.services.Global;
import com.jds.eduTech.services.MyRetrofit;
import com.jds.eduTech.util.EditTextChangedListener;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewProfileActivity extends AppCompatActivity {

    TextView schoolNameHeader;
    TextView mandal,editDetails;
    EditText schoolId;
    EditText schoolName;
    String apiValue = "";
    EditText hmName,headMasterContact,alternatePersonName,alternatePersonContactNumber,teachers,students,boys,
            girls,classrooms,address,pincode,latitude,longitude;
    ImageView menu_drawer;
    Button submit;
    Spinner schoolCategory;
    ProgressBar pBar;
    MyRetrofit retrofit;
    Global global;
    EduTechServices eduTechServices;
    String categoryValue = "";
    EditTextChangedListener lschoolId,lschoolName,lhmName,lhmMobile,lalternateName,lalternateMobile,lteachers,lstudents,lboys,lgirls,lclassrooms,laddress,lpincode,llatitude,llongitude;
    private static final int MAP_ACTIVITY_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_profile);
        schoolNameHeader = findViewById(R.id.schoolNameHeader);
        schoolId = findViewById(R.id.schoolId);
        schoolName = findViewById(R.id.schoolName);
        schoolCategory = findViewById(R.id.schoolCategory);
        hmName = findViewById(R.id.hmName);
        headMasterContact = findViewById(R.id.headMasterContact);
        alternatePersonName = findViewById(R.id.alternatePersonName);
        alternatePersonContactNumber = findViewById(R.id.alternatePersonContactNumber);
        teachers = findViewById(R.id.teachers);
        students = findViewById(R.id.students);
        boys = findViewById(R.id.boys);
        girls = findViewById(R.id.girls);
        classrooms = findViewById(R.id.classrooms);
        address = findViewById(R.id.address);
        pincode = findViewById(R.id.pincode);
        latitude = findViewById(R.id.latitude);
        longitude = findViewById(R.id.longitude);
        submit = findViewById(R.id.submit);
        editDetails = findViewById(R.id.editDetails);
        menu_drawer = findViewById(R.id.menu_drawer);
        pBar = findViewById(R.id.pBar);
        retrofit = MyRetrofit.getInstance();
        eduTechServices = retrofit.getEduTechServices();
        global = Global.getInstance(NewProfileActivity.this);

        menu_drawer.setOnClickListener(v->{
            finish();
        });

        getSchoolDetails();

        List<ValidationEntry> firstNameValidations = new ArrayList<>();
        firstNameValidations.add(new ValidationEntry(".+", "This field is required"));
        firstNameValidations.add(new ValidationEntry("^[a-zA-Z_ ]*$", "Special symbols and numeric values are not allowed"));
        firstNameValidations.add(new ValidationEntry("^[a-zA-Z ]{2,60}$", "2 to 60 characters are required"));
        lhmName = new EditTextChangedListener(hmName, firstNameValidations);
        hmName.addTextChangedListener(lhmName);

        List<ValidationEntry> alternateNameValidations = new ArrayList<>();
        alternateNameValidations.add(new ValidationEntry(".+", "This field is required"));
        alternateNameValidations.add(new ValidationEntry("^[a-zA-Z_ ]*$", "Special symbols and numeric values are not allowed"));
        alternateNameValidations.add(new ValidationEntry("^[a-zA-Z ]{2,60}$", "2 to 60 characters are required"));
        lalternateName = new EditTextChangedListener(alternatePersonName, alternateNameValidations);
        alternatePersonName.addTextChangedListener(lalternateName);

        List<ValidationEntry> mobileValidations = new ArrayList<>();
        mobileValidations.add(new ValidationEntry(".+", "This field is required"));
        mobileValidations.add(new ValidationEntry("\\d{10}", "Please enter valid mobile number"));
        lhmMobile = new EditTextChangedListener(headMasterContact, mobileValidations);
        headMasterContact.addTextChangedListener(lhmMobile);

        List<ValidationEntry> alternatemobileValidations = new ArrayList<>();
        alternatemobileValidations.add(new ValidationEntry(".+", "This field is required"));
        alternatemobileValidations.add(new ValidationEntry("\\d{10}", "Please enter valid mobile number"));
        lalternateMobile = new EditTextChangedListener(alternatePersonContactNumber, alternatemobileValidations);
        alternatePersonContactNumber.addTextChangedListener(lalternateMobile);

        List<ValidationEntry> teachersValidation = new ArrayList<>();
        teachersValidation.add(new ValidationEntry(".+", "This field is required"));
        lteachers = new EditTextChangedListener(teachers, teachersValidation);
        teachers.addTextChangedListener(lteachers);

        List<ValidationEntry> schoolIdValidation = new ArrayList<>();
        schoolIdValidation.add(new ValidationEntry(".+", "This field is required"));
        lschoolId = new EditTextChangedListener(schoolId, schoolIdValidation);
        schoolId.addTextChangedListener(lschoolId);

        List<ValidationEntry> studentsValidation = new ArrayList<>();
        studentsValidation.add(new ValidationEntry(".+", "This field is required"));
        lstudents = new EditTextChangedListener(students, studentsValidation);
        students.addTextChangedListener(lstudents);

        List<ValidationEntry> boysValidation = new ArrayList<>();
        boysValidation.add(new ValidationEntry(".+", "This field is required"));
        lboys = new EditTextChangedListener(boys, boysValidation);
        boys.addTextChangedListener(lboys);

        List<ValidationEntry> girlsValidation = new ArrayList<>();
        girlsValidation.add(new ValidationEntry(".+", "This field is required"));
        lgirls = new EditTextChangedListener(girls, girlsValidation);
        girls.addTextChangedListener(lgirls);

        List<ValidationEntry> classRoomsValidation = new ArrayList<>();
        classRoomsValidation.add(new ValidationEntry(".+", "This field is required"));
        lclassrooms = new EditTextChangedListener(classrooms, classRoomsValidation);
        classrooms.addTextChangedListener(lclassrooms);

        List<ValidationEntry> addressValidation = new ArrayList<>();
        addressValidation.add(new ValidationEntry(".+", "This field is required"));
        laddress = new EditTextChangedListener(address, addressValidation);
        address.addTextChangedListener(laddress);

        List<ValidationEntry> pincodeValidation = new ArrayList<>();
        pincodeValidation.add(new ValidationEntry(".+", "This field is required"));
        lpincode = new EditTextChangedListener(pincode, pincodeValidation);
        pincode.addTextChangedListener(lpincode);

        List<ValidationEntry> latitudeValidation = new ArrayList<>();
        latitudeValidation.add(new ValidationEntry(".+", "This field is required"));
        llatitude = new EditTextChangedListener(latitude, latitudeValidation);
        latitude.addTextChangedListener(llatitude);

        List<ValidationEntry> longitudeValidation = new ArrayList<>();
        longitudeValidation.add(new ValidationEntry(".+", "This field is required"));
        llongitude = new EditTextChangedListener(longitude, longitudeValidation);
        longitude.addTextChangedListener(llongitude);



        hmName.setEnabled(false);
        schoolId.setEnabled(false);
        schoolName.setEnabled(false);
        schoolCategory.setEnabled(false);
        headMasterContact.setEnabled(false);
        alternatePersonName.setEnabled(false);
        alternatePersonContactNumber.setEnabled(false);
        teachers.setEnabled(false);
        students.setEnabled(false);
        boys.setEnabled(false);
        girls.setEnabled(false);
        classrooms.setEnabled(false);
        address.setEnabled(false);
        pincode.setEnabled(false);
        latitude.setEnabled(false);
        longitude.setEnabled(false);

        submit.setVisibility(View.GONE);

        editDetails.setOnClickListener(v->{
            hmName.setEnabled(true);
            schoolId.setEnabled(true);
            schoolName.setEnabled(false);
            schoolCategory.setEnabled(true);
            headMasterContact.setEnabled(true);
            alternatePersonName.setEnabled(true);
            alternatePersonContactNumber.setEnabled(true);
            teachers.setEnabled(true);
            students.setEnabled(true);
            boys.setEnabled(true);
            girls.setEnabled(true);
            classrooms.setEnabled(true);
            address.setEnabled(true);
            pincode.setEnabled(true);
            latitude.setEnabled(true);
            longitude.setEnabled(true);

            editDetails.setVisibility(View.GONE);
            submit.setVisibility(View.VISIBLE);

            submit.setOnClickListener(v1->{
                hmName.setTag(true);
                lhmName.showError();
                schoolId.setTag(true);
                lschoolId.showError();
                headMasterContact.setTag(true);
                lhmMobile.showError();
                alternatePersonName.setTag(true);
                lalternateName.showError();
                alternatePersonContactNumber.setTag(true);
                lalternateMobile.showError();
                teachers.setTag(true);
                lteachers.showError();
                students.setTag(true);
                lstudents.showError();
                classrooms.setTag(true);
                lclassrooms.showError();
                boys.setTag(true);
                lboys.showError();
                girls.setTag(true);
                lgirls.showError();
                address.setTag(true);
                laddress.showError();
                pincode.setTag(true);
                lpincode.showError();
                latitude.setTag(true);
                llatitude.showError();
                longitude.setTag(true);
                llongitude.showError();

                if(lschoolId.isValid() && lhmMobile.isValid() && lhmName.isValid() && lalternateName.isValid() && lalternateMobile.isValid() && lteachers.isValid() && lstudents.isValid() && lclassrooms.isValid() && lboys.isValid() && lgirls.isValid() && laddress.isValid() && lpincode.isValid() && llatitude.isValid() && llongitude.isValid()){
                    updateSchoolDetails();
                }else {
                    Toast.makeText(this, "Please fill the form correctly", Toast.LENGTH_SHORT).show();
                }


            });
        });


    }

    private void updateSchoolDetails() {
        pBar.setVisibility(View.VISIBLE);
        JsonObject schoolObj = new JsonObject();
        schoolObj.addProperty("school_id",global.getSchoolId());
        schoolObj.addProperty("school_category",categoryValue);
        schoolObj.addProperty("school_code","");
        schoolObj.addProperty("latitude",latitude.getText().toString());
        schoolObj.addProperty("longitude",longitude.getText().toString());
        schoolObj.addProperty("hm_name",hmName.getText().toString());
        schoolObj.addProperty("hm_contact_num",headMasterContact.getText().toString());
        schoolObj.addProperty("eng_name",alternatePersonName.getText().toString());
        schoolObj.addProperty("eng_contact",alternatePersonContactNumber.getText().toString());
        schoolObj.addProperty("no_of_teachers",teachers.getText().toString());
        schoolObj.addProperty("no_of_class_rooms",classrooms.getText().toString());
        schoolObj.addProperty("school_address",address.getText().toString());
        schoolObj.addProperty("pin_code",pincode.getText().toString());
        schoolObj.addProperty("no_of_boys",boys.getText().toString());
        schoolObj.addProperty("no_of_girls",girls.getText().toString());
        schoolObj.addProperty("total_strength",students.getText().toString());
        eduTechServices.updateschoolprofile(schoolObj).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                pBar.setVisibility(View.VISIBLE);
                runOnUiThread(()->{
                    try{
                        if(response.body()!=null){
                            if(response.body().getAsJsonObject().get("status").getAsString().equals("200")){
                                Toast.makeText(NewProfileActivity.this, "School Details Updated Successfully.", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        }

                    }catch (Exception e){
                        System.out.println("Exception in Update School Profile::"+e.getCause());
                    }
                });
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });


    }

    public void getSchoolDetails(){

        pBar.setVisibility(View.VISIBLE);
        JsonObject schoolObj = new JsonObject();
        schoolObj.addProperty("school_id",global.getSchoolId());
        eduTechServices.getSchoolDetails(schoolObj).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                pBar.setVisibility(View.GONE);

                runOnUiThread(()->{
                    try{
                        if(response.body()!=null){
                            if(response.body().getAsJsonObject().get("status").getAsString().equals("200")){

                                try {
                                    JsonObject schoolsObject = response.body().getAsJsonObject().get("schools").getAsJsonObject();
                                    if (schoolsObject.has("school_category")) {
                                        String schoolCategoryValue = schoolsObject.get("school_category").getAsString();
                                        apiValue = (String.valueOf(schoolCategoryValue));
                                    } else {
                                        Log.e("NewProfileActivity", "school_name key not found in JSON");
                                    }
                                } catch (Exception e) {
                                    Log.e("NewProfileActivity", "Error setting school_name text: " + e.getMessage());
                                }

                                List<TextValue> addonTextValues = new ArrayList<>();
                                TextValue addonTextValue = new TextValue("Primary", "1");
                                addonTextValues.add(addonTextValue);
                                TextValue addonTextValueB = new TextValue("Secondary", "2");
                                addonTextValues.add(addonTextValueB);
                                TextValue addonTextValueC = new TextValue("Supreme", "3");
                                addonTextValues.add(addonTextValueC);

                                AddonTextValueSpinnerAdapter registrationTypeAdapter = new AddonTextValueSpinnerAdapter(NewProfileActivity.this,addonTextValues);
                                schoolCategory.setAdapter(registrationTypeAdapter);


                                int preselectedPosition = -1;
                                for (int i = 0; i < addonTextValues.size(); i++) {
                                    if (addonTextValues.get(i).getValue().equals(apiValue)) {
                                        preselectedPosition = i;
                                        break;
                                    }
                                }

                                if (preselectedPosition != -1) {
                                    schoolCategory.setSelection(preselectedPosition);
                                }

                                schoolCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                        categoryValue = addonTextValues.get(i).getValue().toString();
                                        System.out.println("valueee:::"+addonTextValues.get(i).getValue());
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> adapterView) {

                                    }
                                });



                                schoolId.setText(global.getSchoolId());



                                schoolNameHeader.setText(response.body().getAsJsonObject().get("schools").getAsJsonObject().get("school_name").getAsString());
                              //  schoolName.setText(response.body().getAsJsonObject().get("schools").getAsJsonObject().get("school_name").getAsString());
                               // latitude.setText(response.body().getAsJsonObject().get("schools").getAsJsonObject().get("latitude").getAsString());
                              //  longitude.setText(response.body().getAsJsonObject().get("schools").getAsJsonObject().get("longitude").getAsString());
                               // hmName.setText(response.body().getAsJsonObject().get("schools").getAsJsonObject().get("hm_name").getAsString());
                              //  headMasterContact.setText(response.body().getAsJsonObject().get("schools").getAsJsonObject().get("hm_contact_num").getAsString());
                                try {
                                    JsonObject schoolsObject = response.body().getAsJsonObject().get("schools").getAsJsonObject();
                                    if (schoolsObject.has("school_name")) {
                                        String schoolNameValue = schoolsObject.get("school_name").getAsString();
                                        schoolName.setText(String.valueOf(schoolNameValue));
                                    } else {
                                        Log.e("NewProfileActivity", "school_name key not found in JSON");
                                    }
                                } catch (Exception e) {
                                    Log.e("NewProfileActivity", "Error setting school_name text: " + e.getMessage());
                                }

                                try {
                                    JsonObject schoolsObject = response.body().getAsJsonObject().get("schools").getAsJsonObject();
                                    if (schoolsObject.has("latitude")) {
                                        String latitudeValue = schoolsObject.get("latitude").getAsString();
                                        latitude.setText(String.valueOf(latitudeValue));
                                    } else {
                                        Log.e("NewProfileActivity", "latitude key not found in JSON");
                                    }
                                } catch (Exception e) {
                                    Log.e("NewProfileActivity", "Error setting latitude text: " + e.getMessage());
                                }

                                try {
                                    JsonObject schoolsObject = response.body().getAsJsonObject().get("schools").getAsJsonObject();
                                    if (schoolsObject.has("longitude")) {
                                        String longitudeValue = schoolsObject.get("longitude").getAsString();
                                        longitude.setText(String.valueOf(longitudeValue));
                                    } else {
                                        Log.e("NewProfileActivity", "longitude key not found in JSON");
                                    }
                                } catch (Exception e) {
                                    Log.e("NewProfileActivity", "Error setting longitude text: " + e.getMessage());
                                }

                                try {
                                    JsonObject schoolsObject = response.body().getAsJsonObject().get("schools").getAsJsonObject();
                                    if (schoolsObject.has("hm_name")) {
                                        String hmNameValue = schoolsObject.get("hm_name").getAsString();
                                        hmName.setText(String.valueOf(hmNameValue));
                                    } else {
                                        Log.e("NewProfileActivity", "hm_name key not found in JSON");
                                    }
                                } catch (Exception e) {
                                    Log.e("NewProfileActivity", "Error setting hm_name text: " + e.getMessage());
                                }

                                try {
                                    JsonObject schoolsObject = response.body().getAsJsonObject().get("schools").getAsJsonObject();
                                    if (schoolsObject.has("hm_contact_num")) {
                                        String hmContact = schoolsObject.get("hm_contact_num").getAsString();
                                        headMasterContact.setText(String.valueOf(hmContact));
                                    } else {
                                        Log.e("NewProfileActivity", "hm_contact_num key not found in JSON");
                                    }
                                } catch (Exception e) {
                                    Log.e("NewProfileActivity", "Error setting hm_contact_num text: " + e.getMessage());
                                }

                                try {
                                    JsonObject schoolsObject = response.body().getAsJsonObject().get("schools").getAsJsonObject();
                                    if (schoolsObject.has("total_strength")) {
                                        int totalStrength = schoolsObject.get("total_strength").getAsInt();
                                        students.setText(String.valueOf(totalStrength));
                                    } else {
                                        Log.e("NewProfileActivity", "total_strength key not found in JSON");
                                    }
                                } catch (Exception e) {
                                    Log.e("NewProfileActivity", "Error setting students text: " + e.getMessage());
                                }

                                try {
                                    JsonObject schoolsObject = response.body().getAsJsonObject().get("schools").getAsJsonObject();
                                    if (schoolsObject.has("no_of_boys")) {
                                        int totalboys = schoolsObject.get("no_of_boys").getAsInt();
                                        boys.setText(String.valueOf(totalboys));
                                    } else {
                                        Log.e("NewProfileActivity", "no_of_boys key not found in JSON");
                                    }
                                } catch (Exception e) {
                                    Log.e("NewProfileActivity", "Error setting boys text: " + e.getMessage());
                                }

                                try {
                                    JsonObject schoolsObject = response.body().getAsJsonObject().get("schools").getAsJsonObject();
                                    if (schoolsObject.has("no_of_girls")) {
                                        int totalGirls = schoolsObject.get("no_of_girls").getAsInt();
                                        girls.setText(String.valueOf(totalGirls));
                                    } else {
                                        Log.e("NewProfileActivity", "no_of_girls key not found in JSON");
                                    }
                                } catch (Exception e) {
                                    Log.e("NewProfileActivity", "Error setting no_of_girls text: " + e.getMessage());
                                }

                                try {
                                    JsonObject schoolsObject = response.body().getAsJsonObject().get("schools").getAsJsonObject();
                                    if (schoolsObject.has("eng_contact")) {
                                        String alternatePersonContactNumberValue = schoolsObject.get("eng_contact").getAsString();
                                        alternatePersonContactNumber.setText(String.valueOf(alternatePersonContactNumberValue));
                                    } else {
                                        Log.e("NewProfileActivity", "eng_contact key not found in JSON");
                                    }
                                } catch (Exception e) {
                                    Log.e("NewProfileActivity", "Error setting eng_contact text: " + e.getMessage());
                                }

                                try {
                                    JsonObject schoolsObject = response.body().getAsJsonObject().get("schools").getAsJsonObject();
                                    if (schoolsObject.has("eng_name")) {
                                        String alternatePersonContactNumberValue = schoolsObject.get("eng_name").getAsString();
                                        alternatePersonName.setText(String.valueOf(alternatePersonContactNumberValue));
                                    } else {
                                        Log.e("NewProfileActivity", "eng_name key not found in JSON");
                                    }
                                } catch (Exception e) {
                                    Log.e("NewProfileActivity", "Error setting eng_name text: " + e.getMessage());
                                }

                               // boys.setText(response.body().getAsJsonObject().get("schools").getAsJsonObject().get("no_of_boys").getAsInt());
                               // girls.setText(response.body().getAsJsonObject().get("schools").getAsJsonObject().get("no_of_girls").getAsInt());
                              //  alternatePersonContactNumber.setText(response.body().getAsJsonObject().get("schools").getAsJsonObject().get("eng_contact").getAsString());
                            //    alternatePersonName.setText(response.body().getAsJsonObject().get("schools").getAsJsonObject().get("eng_name").getAsString());

                                try {
                                    JsonObject schoolsObject = response.body().getAsJsonObject().get("schools").getAsJsonObject();
                                    if (schoolsObject.has("no_of_teachers")) {
                                        int totalteachers = schoolsObject.get("no_of_teachers").getAsInt();
                                        teachers.setText(String.valueOf(totalteachers));
                                    } else {
                                        Log.e("NewProfileActivity", "no_of_teachers key not found in JSON");
                                    }
                                } catch (Exception e) {
                                    Log.e("NewProfileActivity", "Error setting no_of_teachers text: " + e.getMessage());
                                }

                                try {
                                    JsonObject schoolsObject = response.body().getAsJsonObject().get("schools").getAsJsonObject();
                                    if (schoolsObject.has("no_of_class_rooms")) {
                                        int totalClassRooms = schoolsObject.get("no_of_class_rooms").getAsInt();
                                        classrooms.setText(String.valueOf(totalClassRooms));
                                    } else {
                                        Log.e("NewProfileActivity", "no_of_class_rooms key not found in JSON");
                                    }
                                } catch (Exception e) {
                                    Log.e("NewProfileActivity", "Error setting no_of_class_rooms text: " + e.getMessage());
                                }

                                try {
                                    JsonObject schoolsObject = response.body().getAsJsonObject().get("schools").getAsJsonObject();
                                    if (schoolsObject.has("pin_code")) {
                                        int totalpincode = schoolsObject.get("pin_code").getAsInt();
                                        pincode.setText(String.valueOf(totalpincode));
                                    } else {
                                        Log.e("NewProfileActivity", "pin_code key not found in JSON");
                                    }
                                } catch (Exception e) {
                                    Log.e("NewProfileActivity", "Error setting pin_code text: " + e.getMessage());
                                }

                                //teachers.setText(response.body().getAsJsonObject().get("schools").getAsJsonObject().get("no_of_teachers").getAsInt());
                               // classrooms.setText(response.body().getAsJsonObject().get("schools").getAsJsonObject().get("no_of_class_rooms").getAsInt());
                               // pincode.setText(response.body().getAsJsonObject().get("schools").getAsJsonObject().get("pin_code").getAsInt());

                                try {
                                    JsonObject schoolsObject = response.body().getAsJsonObject().get("schools").getAsJsonObject();
                                    if (schoolsObject.has("school_address")) {
                                        String addressString = schoolsObject.get("school_address").getAsString();
                                        address.setText(String.valueOf(addressString));
                                    } else {
                                        Log.e("NewProfileActivity", "school_address key not found in JSON");
                                    }
                                } catch (Exception e) {
                                    Log.e("NewProfileActivity", "Error setting school_address text: " + e.getMessage());
                                }
                               // address.setText(response.body().getAsJsonObject().get("schools").getAsJsonObject().get("school_address").getAsString());
                            }
                        }
                    }catch (Exception e){
                        System.out.println("Exception in get school Details:::"+e.getCause());
                    }
                });
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });
    }
}