package me.kamadi.draggablemap.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;

import me.kamadi.draggablemap.R;
import me.kamadi.draggablemap.map.CustomMapFragment;
import me.kamadi.draggablemap.map.GoogleMapView;

public class MainActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnCameraChangeListener {

    private GoogleMap map;
    private CustomMapFragment customMapFragment;
    private GoogleMapView googleMapView;
    private LatLng center;
    private TextView location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        location = (TextView) findViewById(R.id.location);
        customMapFragment = (CustomMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        customMapFragment.getMapAsync(this);
        googleMapView = new GoogleMapView(this, customMapFragment);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_maps_place);
        customMapFragment.getMapWrapperLayout().setCurrentImage(bitmap);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        googleMapView.setMap(map);
        googleMapView.goCurrentPosition();
        map.setOnCameraChangeListener(this);
    }

    @Override
    public void onCameraChange(CameraPosition cameraPosition) {
        center = map.getCameraPosition().target;
        location.setText(center.latitude + "," + center.longitude);
    }
}
