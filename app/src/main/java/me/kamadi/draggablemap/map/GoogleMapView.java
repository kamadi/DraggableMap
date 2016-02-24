package me.kamadi.draggablemap.map;

import android.content.Context;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import me.kamadi.draggablemap.R;


/**
 * Created by Madiyar on 02.02.2015.
 */
public class GoogleMapView implements LocationListener {
    LocationManager locationManager;
    private CustomMapFragment mapFragment;
    private Context context;
    private GoogleMap map;

    public GoogleMapView(Context context, CustomMapFragment customMapFragment) {
        this.context = context;
        mapFragment = customMapFragment;

//        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
    }


    public void goCurrentPosition() {


        Criteria criteria = new Criteria();
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

        String provider = locationManager.getBestProvider(criteria, true);


        Location myLocation = getLocation();
        if (myLocation != null) {
            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(new LatLng(myLocation.getLatitude(), myLocation.getLongitude()))
                    .zoom(16).build();

            map.animateCamera(CameraUpdateFactory
                    .newCameraPosition(cameraPosition));

        } else {
            Toast.makeText(context, R.string.gps_on, Toast.LENGTH_LONG).show();
        }
    }

    public Location getLocation() {
        Location location = null;
        try {
            locationManager = (LocationManager) context
                    .getSystemService(Context.LOCATION_SERVICE);

            // getting GPS status
            boolean isGPSEnabled = locationManager
                    .isProviderEnabled(LocationManager.GPS_PROVIDER);

            // getting network status
            boolean isNetworkEnabled = locationManager
                    .isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if (!isGPSEnabled && !isNetworkEnabled) {
                // no network provider is enabled
            } else {
                boolean canGetLocation = true;
                if (isNetworkEnabled) {
                    locationManager.requestLocationUpdates(
                            LocationManager.NETWORK_PROVIDER,
                            1,
                            0, this);
                    Log.d("Network", "Network Enabled");
                    if (locationManager != null) {
                        location = locationManager
                                .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

                    }
                }
                // if GPS Enabled get lat/long using GPS Services
                if (isGPSEnabled) {
                    if (location == null) {
                        locationManager.requestLocationUpdates(
                                LocationManager.GPS_PROVIDER,
                                1,
                                0, this);
                        Log.d("GPS", "GPS Enabled");
                        if (locationManager != null) {
                            location = locationManager
                                    .getLastKnownLocation(LocationManager.GPS_PROVIDER);

                        }
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return location;
    }

    public void goToLocation(double latitude, double longitude) {


        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(new LatLng(latitude, longitude))
                .zoom(16).build();

        map.animateCamera(CameraUpdateFactory
                .newCameraPosition(cameraPosition));
    }


    public String[] drawPath(Object routes) {
        final String[] info = new String[2];


        List<List<HashMap<String, String>>> result = (List<List<HashMap<String, String>>>) routes;
        ArrayList<LatLng> points = null;
        PolylineOptions lineOptions = null;

        // Traversing through all the routes
        for (int i = 0; i < result.size(); i++) {
            points = new ArrayList<LatLng>();
            lineOptions = new PolylineOptions();

            // Fetching i-th route
            List<HashMap<String, String>> path = result.get(i);

            // Fetching all the points in i-th route
            for (int j = 0; j < path.size(); j++) {
                HashMap<String, String> point = path.get(j);

                if (j == 0) {    // Get distance from the list
                    info[0] = point.get("distance");
                    continue;
                } else if (j == 1) { // Get duration from the list
                    info[1] = point.get("duration");

                    continue;
                }


                double lat = Double.parseDouble(point.get("lat"));
                double lng = Double.parseDouble(point.get("lng"));

                LatLng position = new LatLng(lat, lng);
                points.add(position);

            }

            // Adding all the points in the route to LineOptions
            lineOptions.addAll(points);
            lineOptions.width(15);
            lineOptions.color(Color.BLACK);

        }


        // Drawing polyline in the Google Map for the i-th route
        map.addPolyline(lineOptions);


        return info;

    }


    @Override
    public void onLocationChanged(Location location) {

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

    public void setMap(GoogleMap map) {
        this.map = map;
    }
}
