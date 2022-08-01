/*
* Copyright 2013 The Android Open Source Project
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*     http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/


package com.example.android.recyclerview;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import com.example.android.common.logger.Log;
import com.google.android.material.tabs.TabLayout;
import com.kwabenaberko.openweathermaplib.implementation.OpenWeatherMapHelper;
import com.kwabenaberko.openweathermaplib.implementation.callback.CurrentWeatherCallback;
import com.kwabenaberko.openweathermaplib.model.currentweather.CurrentWeather;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * A simple launcher activity containing a summary sample description, sample log and a custom
 * {@link Fragment} which can display a view.
 * <p>
 * For devices with displays with a width of 720dp or greater, the sample log is always visible,
 * on other devices it's visibility is controlled by an item on the Action Bar.
 */
public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";

    // Whether the Log Fragment is currently shown
    private boolean mLogShown;
    TabLayout tabLayout;
    ViewPager viewPager;
    Button creatTask;
    Boolean asVolunteer;
    String userName;
    TextView userNameView;
    TextView userRoleView;
    TextView location;
    TextView weather;
    MyAdapter adapter;
    int LOCATION_REFRESH_TIME = 15000; // 15 seconds to update
    int LOCATION_REFRESH_DISTANCE = 0; // 500 meters to update

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();
        if (null != intent) { //Null Checking
            userName = intent.getStringExtra("userName");
            asVolunteer = intent.getBooleanExtra("userRole", false);
        }

        creatTask=(Button)findViewById(R.id.createNewTask);
        tabLayout=(TabLayout)findViewById(R.id.tabLayout);
        viewPager=(ViewPager)findViewById(R.id.viewPager);
        userNameView=(TextView)findViewById(R.id.userNameInMain);
        userRoleView=(TextView)findViewById(R.id.userRoleInMain);
        location=(TextView)findViewById(R.id.locationContent);
        weather=(TextView)findViewById(R.id.weatherContent);

        userNameView.setText(userName);
        RoleWrapper.getInstance().setRoleIsVolunteer(asVolunteer);
        if (asVolunteer) {
            userRoleView.setText("[Volunteer]");
        } else {
            userRoleView.setText("[LandOwner]");
        }

        tabLayout.addTab(tabLayout.newTab().setText("Tasks Created"));
        tabLayout.addTab(tabLayout.newTab().setText("Tasks In Progress"));
        tabLayout.addTab(tabLayout.newTab().setText("Tasks Finished"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        adapter = new MyAdapter(this,getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                RoleWrapper.getInstance().setTaskMode(tab.getPosition());
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        if (asVolunteer) {
            creatTask.setVisibility(View.GONE);
        } else {
            // starts a new activity to create a new task
            creatTask.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getApplication(), CreateTaskActivity.class);
                    startActivity(intent);
                }
            });
        }

        LocationListener mLocationListener = new LocationListener() {
            @Override
            public void onLocationChanged(final Location location) {
                //your code here
                if (location.getAltitude() == 5.0) {
                    registerWeatherLocation(55.85806, -4.25889);
                }
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) ==
                        PackageManager.PERMISSION_GRANTED) {

            LocationManager mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

            mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, LOCATION_REFRESH_TIME,
                    LOCATION_REFRESH_DISTANCE, mLocationListener);

        } else {
            ActivityCompat.requestPermissions(this, new String[] {
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION },
                    0);
            LocationManager mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

            mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, LOCATION_REFRESH_TIME,
                    LOCATION_REFRESH_DISTANCE, mLocationListener);
        }
    }

    private void registerWeatherLocation(double lat, double lon) {
        OpenWeatherMapHelper helper = new OpenWeatherMapHelper("fe280a48196925671f24f3bd141e76ab");
        helper.getCurrentWeatherByGeoCoordinates(lat, lon, new CurrentWeatherCallback() {
            @Override
            public void onSuccess(CurrentWeather currentWeather) {
                System.out.println("Coordinates: " + currentWeather.getCoord().getLat() + ", "+currentWeather.getCoord().getLon() +"\n"
                        +"Weather Description: " + currentWeather.getWeather() + "\n"
                        +"Temperature: " + currentWeather.getMain().getTempMax()+"\n"
                        +"Wind Speed: " + currentWeather.getWind().getSpeed() + "\n"
                        +"City, Country: " + currentWeather.getName() + ", " + currentWeather.getSys().getCountry()
                );
                double currTemp =  currentWeather.getMain().getTempMax() - 273.15;
                String weatherStr = currentWeather.getWeather().get(0).getDescription();
                if (currTemp > 30) {
                    WeatherWrapper.getInstance().setHotStatus();
                } else if (weatherStr.contains("rain")) {
                    WeatherWrapper.getInstance().setColdStatus();
                }
                weather.setText(weatherStr + ", " + String.format("%.2f", currTemp) + "℃");
                if (currentWeather.getName().equals("")) {
                    location.setText("Glasgow");
                } else {
                    location.setText(currentWeather.getName());
                }
            }

            @Override
            public void onFailure(Throwable throwable) {
                Log.v(TAG, throwable.getMessage());
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        ((RecyclerViewFragment)adapter.getItem(viewPager.getCurrentItem())).initDataset();
    }

}
