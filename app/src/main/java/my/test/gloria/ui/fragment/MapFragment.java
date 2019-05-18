package my.test.gloria.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import my.test.gloria.R;
import my.test.gloria.mvp.presenter.MapPresenter;
import my.test.gloria.mvp.view.MapView;

public class MapFragment extends MvpAppCompatFragment implements MapView, OnMapReadyCallback {

    private static final String ARG_LAT = "arg_lat";
    private static final String ARG_LON = "arg_lon";
    private static final String ARG_CITY = "arg_city";

    @InjectPresenter
    MapPresenter presenter;

    SupportMapFragment map;
    private GoogleMap googleMap;

    public MapFragment() {
    }

    public static MapFragment getInstance(String city, double lat, double lon) {
        MapFragment fragment = new MapFragment();
        Bundle args = new Bundle();
        args.putDouble(ARG_LAT, lat);
        args.putDouble(ARG_LON, lon);
        args.putString(ARG_CITY, city);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        initMap();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        Bundle bundle = getArguments();
        presenter.setCurrentPosition(bundle.getString(ARG_CITY), bundle.getDouble(ARG_LAT), bundle.getDouble(ARG_LON));
    }

    private void initMap() {
        map = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        if (map != null) {
            map.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        presenter.onMapReady();
    }

    @Override
    public void addPositionOnMap(String title, double lat, double lon) {
        if (googleMap == null) return;
        LatLng position = new LatLng(lat, lon);
        Marker marker = googleMap.addMarker(new MarkerOptions().position(position).title(title));
        marker.showInfoWindow();
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(position, 12));
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.onResume();
    }

    @Override
    public void onStop() {
        super.onStop();
        presenter.onStop();
    }

    public void onBackPressed() {
        presenter.onBackPressed();
    }
}
