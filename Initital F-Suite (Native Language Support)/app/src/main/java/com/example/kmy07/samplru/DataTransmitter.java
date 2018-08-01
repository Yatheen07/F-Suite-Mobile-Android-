package com.example.kmy07.samplru;

import android.content.Context;
import android.content.pm.PackageManager;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;

import java.util.ArrayList;

public class DataTransmitter {

    private ArrayList<String> user = new ArrayList<String>();
    private String data_to_send;
    Context context;
    private String serverAddress;
    private boolean status = true;
    private String message;
    String responseStatus;
    //Constructor ---> with ArrayList

    public DataTransmitter(ArrayList<String> userDetails, Context context){
        this.user = userDetails;
        this.context = context;

        this.processInput();
    }

    //Constructor [OverLoading] ---> with String

    public DataTransmitter(String message, Context context){
        this.message = message;
        this.context = context;

    }

    // Register the server Address
    public void newServer(String url){
        this.serverAddress = url;
    }

    //Make the data hypen seperated!
    private void processInput() {
        StringBuffer result = new StringBuffer();
        for(String s : user){
            result.append(s+"-");
        }
        data_to_send = result.toString().replace("\\s","");
    }

    // Login to send data through volley
    public String sendData(String url){
        RequestQueue queue = Volley.newRequestQueue(context);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("Infou","Success"+response);
                        responseStatus = response;
                        Toast.makeText(context, "Resultuu: "+response, Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Erroru","Failue : "+error.getMessage());

            }
        });

        queue.add(stringRequest);
        return responseStatus;
    }


    public boolean register_farmer() throws JSONException {

        String requiredPermission = "android.permission.INTERNET";
        int checkVal = context.checkCallingOrSelfPermission(requiredPermission);

        if (!(checkVal== PackageManager.PERMISSION_GRANTED)){
            return false;
        }
        else {
            // Instantiate the RequestQueue.
            String url = serverAddress + "register_farmer/" + data_to_send+"/";
            sendData(url);
        }
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return true;
    }

    public String handShake() throws JSONException{
        status = true;
        String url = serverAddress + "handshake/";
        return sendData(url);

    }

    public void imageUploaded() throws JSONException{
        String url= serverAddress + "image-uploaded/";
        sendData(url);

    }
    public String getStatus(){
        if(responseStatus.isEmpty()){
            return "Not Yet Baby!";
        }
        else{
            return responseStatus;
        }

    }
    public void sendArea(String area) throws JSONException{
        String url = serverAddress + "production_prediction/"+area+"/";
        String a = sendData(url);
    }
    public String getPrediction() throws JSONException{
        String url = serverAddress + "get_prediction/";
        return sendData(url);
    }

}
