package kr.co.musi.seoulbus;

import androidx.fragment.app.FragmentActivity;
import android.os.Bundle;

import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.CameraAnimation;
import com.naver.maps.map.CameraUpdate;
import com.naver.maps.map.MapFragment;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.overlay.Marker;

import kr.co.musi.seoulbus.databinding.ActivityMapsBinding;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private NaverMap mMap;
    private Marker marker;
    private ActivityMapsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        MapFragment mapFragment = (MapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
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
}