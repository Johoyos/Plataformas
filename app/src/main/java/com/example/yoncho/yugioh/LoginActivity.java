package com.example.yoncho.yugioh;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class LoginActivity  extends AppCompatActivity {
    private EditText username, password;
    private Button btn_login;
    private TextView link_register;
    private ProgressBar loading;
    private static String server_url  = "http://54.68.18.251:3000/auth/login"  ;



    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loading = findViewById(R.id.loading);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        btn_login = findViewById(R.id.btn_login);
        link_register = findViewById(R.id.link_register);



        btn_login.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String musername = username.getText().toString().trim();
                String mPass = password.getText().toString().trim();

                if (!musername.isEmpty() || !mPass.isEmpty()) {
                    Login(musername, mPass);
                } else {
                    username.setError("Ingresa ID correctamente");
                    password.setError("Please inerte ppassword");
                }
            }
        });
        link_register.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });
    }
    private void Login(final String username, final String password){


        loading.setVisibility(View.VISIBLE);
        btn_login.setVisibility(View.GONE);
        RequestQueue queue = Volley.newRequestQueue(this);

        Map<String, String> params = new HashMap();
        params.put("username", username);
        params.put("password", password);


        JSONObject parameters = new JSONObject(params);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST,
                server_url,
                parameters,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //TODO
                        try {
                            String ok = response.getString("message");
                            if(ok.equals("OK")) {

                                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
//                                intent.putExtra("name", username);
//                                intent.putExtra("username", password);
                                startActivity(intent);
                                loading.setVisibility(View.GONE);

                            } else {
                                showMessage("Wrong username or password");
                            }

                            showMessage(response.toString());
                        }catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO: Handle error
                error.printStackTrace();
                Toast.makeText(LoginActivity.this, " Errror " +error.toString(), Toast.LENGTH_SHORT).show();
                loading.setVisibility(View.GONE);
                btn_login.setVisibility(View.VISIBLE);
            }
        });
        queue.add(jsonObjectRequest);


//        StringRequest stringRequest = new StringRequest(Request.Method.POST, server_url,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        try {
//                            JSONObject jsonObject = new JSONObject(response);
//                            String success = jsonObject.getString("success");
//                            JSONArray jsonArray = jsonObject.getJSONArray("login");
//
//                            if (success.equals("OK")){
//                                for (int i=0; i<jsonArray.length(); i++){
//                                    JSONObject object = jsonArray.getJSONObject(i);
//
//                                    String name = object.getString("name").trim();
//                                    String username = object.getString("username").trim();
//
//
//                                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
//                                    intent.putExtra("name", name);
//                                    intent.putExtra("username", username);
//                                    startActivity(intent);
//                                    loading.setVisibility(View.GONE);
//                                }
//                            }
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                            loading.setVisibility(View.GONE);
//                            btn_login.setVisibility(View.VISIBLE);
//                            Toast.makeText(LoginActivity.this, " Errror " +e.toString(), Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        loading.setVisibility(View.GONE);
//                        btn_login.setVisibility(View.VISIBLE);
//                        Toast.makeText(LoginActivity.this, " Errror " +error.toString(), Toast.LENGTH_SHORT).show();
//
//                    }
//                })
//
//
//        {
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError{
//                Map<String, String> params = new HashMap<>();
//                params.put("username", username);
//                params.put("password" , password);
//                return params;
//            }
//
//        };
//
//        RequestQueue requestQueue = Volley.newRequestQueue(this);
//        requestQueue.add(stringRequest);
    }

}
