package com.example.retriveprofile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
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

public class Login extends AppCompatActivity {
    EditText mEmail,mpassword;
    Button mLoginbtn;
    TextView mCreatebtn;
    FirebaseAuth fAuth;
    private APIService mAPIService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mEmail = findViewById(R.id.emaillg);
        mpassword = findViewById(R.id.passwordlg);
        mLoginbtn =findViewById(R.id.loginbtn);
        mCreatebtn =findViewById(R.id.textView4);

        mAPIService = ApiUtils.getAPIService();

       // fAuth = FirebaseAuth.getInstance();

        mLoginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mEmail.getText().toString().trim();
                String password = mpassword.getText().toString().trim();

                if (TextUtils.isEmpty(email)){
                    mEmail.setError("Email is required");
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    mpassword.setError("password is required");
                    return;
                }
                if(password.length()<6){
                    mpassword.setError("password must be >= 6 characters");
                    return;
                }

                try {
                    authUser(email,password);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //login using loopj mysql
              /*  RequestParams rp =new RequestParams();
                rp.put("email",email);
                rp.put("password",password);
                AsyncHttpClient client = new AsyncHttpClient();
                client.post(CommonUtil.base_url+"authenticateUser",rp,new JsonHttpResponseHandler(){
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        super.onSuccess(statusCode, headers, response);
                        try {
                            if(response.getString("res").equals("success")){
                                Toast.makeText(Login.this, "Login Success", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(Login.this, MainActivity.class)
                                        .putExtra("email",email));
                                finish();
                            }else{
                                Toast.makeText(Login.this, "Authentication Failed", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });*/

                //login with firestore
              /*  fAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                            Toast.makeText(Login.this, "User Created", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                    }
                });*/
            }
        });
        mCreatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Register.class));
            }
        });

}

    public void authUser(String email, String password) throws IOException {

        Call<JsonObject> call = mAPIService.getAuthUser(email,password);

        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                Toast.makeText(Login.this, "Login Success", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Login.this, MainActivity.class)
                        .putExtra("email",email));
                finish();
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

                Toast.makeText(Login.this, "Error" + t.getMessage().toString(), Toast.LENGTH_SHORT).show();

            }
        });

    }

}