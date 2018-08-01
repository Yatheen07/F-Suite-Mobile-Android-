package com.example.kmy07.samplru;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;

import java.util.HashMap;

public class Prediction extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prediction);


        Button button = (Button)findViewById(R.id.idda);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataTransmitter sender = new DataTransmitter("Some Message",getApplicationContext());
                sender.newServer("http://192.168.43.116:7345/");
                String url = "http://192.168.43.116:7345/get_prediction/";
                try {
                    RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    Log.i("Infou","Success"+response);
                                    Toast.makeText(Prediction.this, "Poda dei! "+response, Toast.LENGTH_SHORT).show();
                                }
                            }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e("Erroru","Failue : "+error.getMessage());

                        }
                    });

                    queue.add(stringRequest);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });







    }
}
