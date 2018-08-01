package com.example.kmy07.samplru;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

public class HomeScreen extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static HashMap<String, String> news;

    TextToSpeech t1;

    private static ArrayList<String> newsTitle = new ArrayList<String>();
    private static ArrayList<String> links = new ArrayList<String>();

    private TextView title1, title2, title3;

    private void handleHashMap() {
        newsTitle.clear();
        links.clear();
        for (String key : news.keySet()) {
            newsTitle.add(key);
        }
        for (String val : news.values()) {
            links.add(val);
        }
        Toast.makeText(getApplicationContext(), String.valueOf(newsTitle), Toast.LENGTH_SHORT).show();
    }


    public void shareData(View v) {
        String message = "";
        if (v.getId() == R.id.share_button1) {
            message = newsTitle.get(0) + "\n" + links.get(0);
        }
        if (v.getId() == R.id.share_button2) {
            message = newsTitle.get(1) + "\n" + links.get(1);
        }
        if (v.getId() == R.id.share_button3) {
            message = newsTitle.get(2) + "\n" + links.get(2);
        }
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        //String shareBody = "Here is the share content body";
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject Here");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, message);
        startActivity(Intent.createChooser(sharingIntent, "Share via"));
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        t1 = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    t1.setLanguage(Locale.US);
                }
                t1.setSpeechRate(0.6f);
                t1.setPitch(1.2f);
            }
        });


        Button more = (Button) findViewById(R.id.more);
        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(getApplicationContext(),predictionScreen.class));
            }
        });

        title1 = (TextView) findViewById(R.id.title1);
        title2 = (TextView) findViewById(R.id.title2);
        title3 = (TextView) findViewById(R.id.title3);


        DataTransmitter sender = new DataTransmitter("Send me the current trends!", getApplicationContext());
        sender.newServer("http://192.168.43.116:7345/");

        try {
            String response = sender.handShake();
            title1.setText(response);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("latest_news");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                news = (HashMap<String, String>) dataSnapshot.getValue();
                handleHashMap();
                fillNews();


            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("error", "Failed to read value.", error.toException());
            }
        });

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home_screen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressLint("MissingPermission")
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
            startActivity(new Intent(getApplicationContext(), DetailsRegistration.class));
        } else if (id == R.id.nav_slideshow) {
            Toast.makeText(getApplicationContext(), "U Clicked me1!", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getApplicationContext(), Cropimage.class));
        } else if (id == R.id.nav_manage) {
            startActivity(new Intent(getApplicationContext(), Prediction.class));
        } else if (id == R.id.nav_share) {
            String number = "7010882689";
            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel:" +number));
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void fillNews() {
        title1.setText(newsTitle.get(0));
        title2.setText(newsTitle.get(1));
        title3.setText(newsTitle.get(2));
    }

    public void openLink(View v){
        String link = "";
        if (v.getId() == R.id.more_btn1) {
            link = links.get(0);
        }
        if (v.getId() == R.id.more_btn2) {
            link = links.get(1);
        }
        if (v.getId() == R.id.more_btn3) {
            link = links.get(2);
        }
        Uri uri = Uri.parse(link); // missing 'http://' will cause crashed
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

    public void onPause(){
        if(t1 !=null){
            t1.stop();
            t1.shutdown();
        }
        super.onPause();
    }

    public void speakNews(View view){
        if(view.getId() == R.id.sp1){
            String toSpeak = getResources().getString(R.string.mes1);
            t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
        }
    }
}
