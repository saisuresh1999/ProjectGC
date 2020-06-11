package com.android.projectgc.ui.discover;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;


import com.android.projectgc.R;
import com.android.projectgc.discover;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.firestore.GeoPoint;

import java.util.ArrayList;
import java.util.HashMap;

public class DiscoverFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap mMap;
    private DiscoverViewModel discoverViewModel;
    private static ArrayList<GeoPoint> CstartLatLon = new ArrayList<com.google.firebase.firestore.GeoPoint>();
    private static ArrayList<Double> percentage = new ArrayList<>();
    private static ArrayList<String> CstartArea = new ArrayList<>();

    private static ArrayList<Marker> CremoveMarker = new ArrayList<>();

    private HashMap<LatLng, Marker> toRemoveStart = new HashMap<LatLng, Marker>();

    private Marker CstartMarker, CstopMarker;
    public static DiscoverFragment newInstance() {
        return new DiscoverFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        discoverViewModel = ViewModelProviders.of(this).get(DiscoverViewModel.class);

        View root = inflater.inflate(R.layout.discover_fragment, container, false);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment)getChildFragmentManager()
                .findFragmentById(R.id.map);

            mapFragment.getMapAsync(this);



        discoverViewModel.getDiscoverData().observe(this, new Observer<discover>() {
            @Override
            public void onChanged(discover discover) {
               if(!CstartLatLon.contains(discover.getLocation())){
                    CstartArea.add(discover.getArea());
                    CstartLatLon.add(discover.getLocation());
                    percentage.add(discover.getPerc());

                }
                else{

                    int i =CstartArea.indexOf(discover.getArea());
                    System.out.println(i);
                        LatLng latLng=new LatLng(discover.getLocation().getLatitude(),discover.getLocation().getLongitude());

                        if (!toRemoveStart.containsKey(latLng)) {
                            LatLng l=new LatLng(CstartLatLon.get(i).getLatitude(),CstartLatLon.get(i).getLongitude());
                            System.out.println(l);
                            toRemoveStart.remove(l);
                            CstartLatLon.remove(i);
                            CstartArea.remove(i);
                            percentage.remove(i);
                            CstartArea.add(discover.getArea());
                            CstartLatLon.add(discover.getLocation());
                            percentage.add(discover.getPerc());
                        }

                    }
                /*CstartArea.add(discover.getArea());
                CstartLatLon.add(discover.getLocation());
                percentage.add(discover.getPerc());*/

                if(mMap!=null)
                    onMapReady(mMap);

                }







        });



        return root;

    }



    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        discoverViewModel = ViewModelProviders.of(this).get(DiscoverViewModel.class);
        // TODO: Use the ViewModel

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        //mMap.clear();
       addMarkersToMap();

    }


    private void addMarkersToMap() {

        float zoomLevel = 16.0f; //This goes up to 21


        for(int i=CstartLatLon.size()-1;i>=0;i--){
            LatLng place = new LatLng(CstartLatLon.get(i).getLatitude(), CstartLatLon.get(i).getLongitude());
            if(percentage.get(i)<=75.0) {
                CstartMarker = mMap.addMarker(new MarkerOptions()
                        .position(place).title(CstartArea.get(i)).snippet("Percentage Filled: " + percentage.get(i)+"%")
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(place));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(place, zoomLevel));
                toRemoveStart.put(place, CstartMarker);
            }
            else{
                CstartMarker = mMap.addMarker(new MarkerOptions()
                        .position(place).title(CstartArea.get(i)).snippet("Percentage Filled: " + percentage.get(i)+"%")
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(place));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(place, zoomLevel));
                toRemoveStart.put(place, CstartMarker);

            }
            //refresh(5000);
            /*mMap.addMarker(new MarkerOptions().position(place).title("60%") .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(place));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(place, zoomLevel));*/
        }



    }

    private void refresh(int milliseconds) {
        final Handler handler=new Handler();
        final Runnable runnable=new Runnable() {
            @Override
            public void run() {
                addMarkersToMap();
            }
        };
        handler.postDelayed(runnable,milliseconds);
    }
}
