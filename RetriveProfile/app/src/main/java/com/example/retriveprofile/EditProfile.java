package com.example.retriveprofile;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.JsonObject;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import cz.msebera.android.httpclient.Header;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfile extends AppCompatActivity {

    public static final String TAG = "TAG";
    EditText profileFullName, profileAddress,profilePhone;
    Button saveBtn;
    private APIService mAPIService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        Intent data = getIntent();
        final String fullName = data.getStringExtra("fullName");
        String address = data.getStringExtra("address");
        String phone = data.getStringExtra("phone");
        
       /* fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        user = fAuth.getCurrentUser();
        storageReference = FirebaseStorage.getInstance().getReference();*/

        profileFullName = findViewById(R.id.profileFullName);
        profileAddress = findViewById(R.id.profileAddress);
        profilePhone = findViewById(R.id.profilePhoneNo);

        saveBtn = findViewById(R.id.saveProfileInfo);

        mAPIService = ApiUtils.getAPIService();



        
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(profileFullName.getText().toString().isEmpty() || profileAddress.getText().toString().isEmpty() || profilePhone.getText().toString().isEmpty()){
                    Toast.makeText(EditProfile.this, "One or Many fields are empty.", Toast.LENGTH_SHORT).show();
                    return;
                }

                try {
                    sendPost(profileFullName.getText().toString(),getIntent().getStringExtra("myemail"),profilePhone.getText().toString(),profileAddress.getText().toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
               /* RequestParams rp = new RequestParams();
                rp.put("email",getIntent().getStringExtra("myemail"));
                rp.put("fullname", profileFullName.getText().toString());
                rp.put("phonenumber", profilePhone.getText().toString());
                rp.put("address", profileAddress.getText().toString());
                AsyncHttpClient client = new AsyncHttpClient();
                client.post(CommonUtil.base_url+"updateUser",rp,new JsonHttpResponseHandler(){
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        super.onSuccess(statusCode, headers, response);
                        try {
                            if(response.getString("res").equals("success")){
                                startActivity(new Intent(EditProfile.this,Login.class));
                                finish();
                            }
                            Toast.makeText(EditProfile.this, response.getString("message"), Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                        super.onFailure(statusCode, headers, responseString, throwable);
                        Toast.makeText(EditProfile.this, "Error", Toast.LENGTH_SHORT).show();
                    }
                });*/

              /*  final String email = profileEmail.getText().toString();
                user.updateEmail(email).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        DocumentReference docRef = fStore.collection("users").document(user.getUid());
                        Map<String, Object> edited = new HashMap<>();
                        edited.put("email",email);
                        edited.put("fName",profileFullName.getText().toString());
                        edited.put("phone",profilePhone.getText().toString());
                        docRef.update(edited).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(EditProfile.this, "Profile Updated", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                                finish();
                            }
                        });
                        Toast.makeText(EditProfile.this, "Email is changed.", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(EditProfile.this,   e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
*/

            }
        });

        profileAddress.setText(address);
        profileFullName.setText(fullName);
        profilePhone.setText(phone);

        Log.d(TAG, "onCreate: " + fullName + " " + address + " " + phone);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @androidx.annotation.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1000){
            if(resultCode == Activity.RESULT_OK){
                Uri imageUri = data.getData();

                //profileImage.setImageURI(imageUri);

               // uploadImageToFirebase(imageUri);


            }
        }

    }

  /*  private void uploadImageToFirebase(Uri imageUri) {
        // uplaod image to firebase storage
        final StorageReference fileRef = storageReference.child("users/"+fAuth.getCurrentUser().getUid()+"/profile.jpg");
        fileRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                       // Picasso.get().load(uri).into(profileImageView);
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "Failed.", Toast.LENGTH_SHORT).show();
            }
        });

    }*/

    public void sendPost(String fullname, String email, String phonenumber,String address) throws IOException {

        Call<JsonObject> call = mAPIService.updateUsers(fullname, email, phonenumber,address);

        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                Toast.makeText(EditProfile.this, "Data Updated", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(EditProfile.this,Login.class));
                finish();
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

                Toast.makeText(EditProfile.this, "Error" + t.getMessage().toString(), Toast.LENGTH_SHORT).show();

            }
        });

    }

}
