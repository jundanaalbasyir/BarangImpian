package com.competition.bukalapak.barangimpian;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by macintosh on 5/28/17.
 */

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    public static final String URL = "https://api.bukalapak.com/v2/authenticate.json";

    public static final String KEY_USERNAME = "username";
    public static final String KEY_PASSWORD = "password";

    private EditText editTextUsername;
    private EditText editTextPassword;

    private Button buttonLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
        String id = pref.getString("user_id",null);
        String tkn = pref.getString("token",null);
//        Toast.makeText(LoginActivity.this, id+"dan"+tkn, Toast.LENGTH_LONG).show();
        if (tkn != null && id != null && !id.equals("null") && !tkn.equals("null")) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextUsername = (EditText) findViewById(R.id.editTextUsername);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);

        buttonLogin = (Button) findViewById(R.id.buttonLogin);

        buttonLogin.setOnClickListener(this);

    }

    private void loginUser() {
        final String username = editTextUsername.getText().toString().trim();
        final String password = editTextPassword.getText().toString().trim();
//        Toast.makeText(LoginActivity.this, username+"dan"+password, Toast.LENGTH_LONG).show();
        if (username.matches("") || password.matches("")){
            Toast.makeText(LoginActivity.this, "Username dan password tidak boleh kosong", Toast.LENGTH_LONG).show();
            return;
        }else {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
//                        Toast.makeText(LoginActivity.this, response, Toast.LENGTH_LONG).show();
                            JSONObject mainObject = null;
                            String user_id = null;
                            String token = null; String status = null;
                            try {
                                mainObject = new JSONObject(response);
                                user_id = mainObject.getString("user_id");
                                token = mainObject.getString("token");
                                status = mainObject.getString("status");

                                if (status.equals("OK")) {
                                    SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
                                    SharedPreferences.Editor editor = pref.edit();

                                    editor.putString("user_id", user_id);
                                    editor.putString("token", token);

                                    editor.apply();

                                    String Vuser_id = pref.getString("user_id", null);
                                    String Vtoken = pref.getString("token", null);

                                    if (Vtoken != null && Vuser_id != null && !Vuser_id.equals("null") && !Vtoken.equals("null")) {
                                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                }else if(status.equals("ERROR")){
                                    Toast.makeText(LoginActivity.this, "username atau password salah!", Toast.LENGTH_LONG).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(LoginActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put(KEY_USERNAME, username);
                    params.put(KEY_PASSWORD, password);
                    return params;
                }

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<String, String>();
                    // add headers <key,value>
                    String credentials = username + ":" + password;
                    String auth = "Basic "
                            + Base64.encodeToString(credentials.getBytes(),
                            Base64.NO_WRAP);
                    headers.put("Authorization", auth);
                    return headers;
                }
            };
            stringRequest.setShouldCache(false);
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);
        }
    }


    @Override
    public void onClick(View v) {
        if(v == buttonLogin){
            loginUser();
        }

    }

    public void register(View view){
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
    }
}