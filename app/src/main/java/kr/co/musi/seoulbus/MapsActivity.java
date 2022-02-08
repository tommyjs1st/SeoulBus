package kr.co.musi.seoulbus;

import androidx.fragment.app.FragmentActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.CameraAnimation;
import com.naver.maps.map.CameraUpdate;
import com.naver.maps.map.MapFragment;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.overlay.Marker;

import java.util.ArrayList;

import kr.co.musi.seoulbus.databinding.ActivityMapsBinding;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private NaverMap mMap;
    private Marker marker;
    private ActivityMapsBinding binding;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = getApplicationContext();

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        MapFragment mapFragment = (MapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        EditText edBusNo = findViewById(R.id.etBusNo);
        Button btnCall = findViewById(R.id.btnCall);
        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayBusStop(edBusNo.getText().toString());
            }
        });
    }

    @Override
    public void onMapReady(NaverMap naverMap) {
        mMap = naverMap;

        // Add a marker in Sydney and move the camera
        LatLng kcycle = new LatLng(37.466878,126.8430466);
        marker = new Marker();
        marker.setPosition(kcycle);
        marker.setCaptionText("광명경륜장");
        marker.setMap(mMap);

        CameraUpdate cameraUpdate = CameraUpdate.scrollTo(kcycle).animate(CameraAnimation.Fly, 1000);
        naverMap.moveCamera(cameraUpdate);
    }
    private void displayBusStop(String busNo) {
        String busRouteId = Sqlite.getBusRouteId(busNo);
        StaionsByRouteList staionsByRouteList = new StaionsByRouteList(mContext);

        //if not exist, get a xml data and save in database
        if (Sqlite.countRoutePath(busRouteId) == 0) {
            int saveCnt = staionsByRouteList.saveStaionsByRouteList(busRouteId);
        }
        // read Data from database
        ArrayList<StationByRoute> listStationByRoute;
        listStationByRoute = Sqlite.selectStationByRoute(mContext, busRouteId);


    }
}