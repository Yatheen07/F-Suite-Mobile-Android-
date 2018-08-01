package com.example.kmy07.samplru;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONException;

public class DetailsRegistration extends AppCompatActivity {

    Spinner spinner;
    EditText editText;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_registration);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        spinner = (Spinner) findViewById(R.id.districts_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.districts_array,R.layout.my_spinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        editText = (EditText) findViewById(R.id.farmArea);
        button = (Button) findViewById(R.id.registerfarmer);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String area = editText.getText().toString();
                DataTransmitter sender = new DataTransmitter("Hey Baby!", getApplicationContext());
                sender.newServer("http://192.168.43.116:7345/");
                String response = "";
                try {
                    sender.sendArea(area);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Intent intent = new Intent(getApplicationContext(), Prediction.class);
                startActivity(intent);
            }

        });
    }
}
