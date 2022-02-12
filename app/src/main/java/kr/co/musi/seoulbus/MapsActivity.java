package kr.co.musi.seoulbus;

import androidx.fragment.app.FragmentActivity;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.CameraAnimation;
import com.naver.maps.map.CameraUpdate;
import com.naver.maps.map.MapFragment;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.overlay.Align;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.util.MarkerIcons;

import java.util.ArrayList;

import kr.co.musi.seoulbus.databinding.ActivityMapsBinding;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private static String TAG = "MapsActivity";
    private NaverMap mMap;
    private ArrayList<Marker> busStopMarkers;
    private ArrayList<Marker> busMarkers;
    private ActivityMapsBinding binding;
    private Context mContext;
    private StaionsByRouteList staionsByRouteList;
    private String mBusRouteId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = getApplicationContext();
        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        busStopMarkers = new ArrayList<>();
        busMarkers = new ArrayList<>();

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        MapFragment mapFragment = (MapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        EditText edBusNo = findViewById(R.id.etBusNo);
        Button btnCall = findViewById(R.id.btnCall);
        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getBusStopInfo(edBusNo.getText().toString());
                displayBusStop();
                displayBusLocation();
            }
        });
    }

    @Override
    public void onMapReady(NaverMap naverMap) {
        mMap = naverMap;
    }
    private void getBusStopInfo(String busNo) {
        SQLiteDatabase database = Sqlite.openDataBase(mContext);
        if (database == null) {
            System.err.println("Database open error!");
            return;
        }
        if (Sqlite.countRouteinfo() == 0) {
            Sqlite.insertRouteInfo(mContext);
        }
        mBusRouteId = Sqlite.getBusRouteId(busNo);
        Util.busRouteId = mBusRouteId;

        //if not exist, get a xml data and save in database
        if (Sqlite.countRoutePath(mBusRouteId) == 0) {
            String servieId = "getStaionByRoute";
            String urlStr = Util.urlBusStopInfo + servieId;
            urlStr += "?ServiceKey="+Util.serviceKey;
            urlStr += "&busRouteId="+mBusRouteId;
            urlStr += "&resultType=xml";
            staionsByRouteList = new StaionsByRouteList(mContext);
            int saveCnt = staionsByRouteList.saveStaionsByRouteList(mBusRouteId);
        } else {
            // read Data from database
            Util.listStationByRoute = Sqlite.selectStationByRouteList(mContext, mBusRouteId);
        }

    }
    private void displayBusStop() {
        if (Util.listStationByRoute.size() == 0) return;

        for (int i=0;i<busStopMarkers.size();i++) {
            Marker marker = busStopMarkers.get(i);
            marker.setMap(null);
        }
        busStopMarkers.clear();

        ArrayList<StationByRoute> listStationByRoute = Util.listStationByRoute;
        //Marker[] busStopMarkers = new Marker[listStationByRoute.size()];

        LatLng latLng = null;
        for (int i=0;i<listStationByRoute.size();i++) {
            latLng = new LatLng(Double.parseDouble(listStationByRoute.get(i).getGpsY()), Double.parseDouble(listStationByRoute.get(i).getGpsX()));
            if (mMap != null) {
                Marker marker = new Marker();
                marker.setZIndex(i);
                marker.setCaptionText(listStationByRoute.get(i).getStationNm());
                marker.setCaptionTextSize(14);
                marker.setCaptionAligns(Align.Top);
                marker.setPosition(latLng);
                marker.setIcon(MarkerIcons.BLUE);
                marker.setMap(mMap);
                busStopMarkers.add(marker);
                Log.d(TAG, listStationByRoute.get(i).getSeq()+","+listStationByRoute.get(i).getRouteType()+","+listStationByRoute.get(i).getDirection()+","+ listStationByRoute.get(i).getStationNm()+","+listStationByRoute.get(i).getStationNo());
            }
        }
        CameraUpdate cameraUpdate = CameraUpdate.scrollTo(latLng).animate(CameraAnimation.Fly, 1000);
        mMap.moveCamera(cameraUpdate);
    }
    private void displayBusLocation() {
        BusPosByRtidList busPosByRtidList = new BusPosByRtidList(mContext);
        busPosByRtidList.selectStaionsByRouteList(mBusRouteId);     //url call + parsing + save in ArrayList
    }

    @Override
    protected void onNewIntent(Intent intent) {
        Log.d(TAG, "onNewIntent");
        super.onNewIntent(intent);

        if (intent != null) {
            displayBusPos();
        }
    }

    private void displayBusPos() {
        if (Util.listBusPosByRtid.size() == 0) return;
        Log.d(TAG, "displayBusPos:");

        for (int i=0;i<busMarkers.size();i++) {
            Marker marker = busMarkers.get(i);
            marker.setMap(null);
        }
        busMarkers.clear();

        ArrayList<BusPosByRtid> listBusPosByRtid = Util.listBusPosByRtid;

        LatLng latLng = null;
        for (int i=0;i<listBusPosByRtid.size();i++) {
            latLng = new LatLng(Double.parseDouble(listBusPosByRtid.get(i).getGpsY()), Double.parseDouble(listBusPosByRtid.get(i).getGpsX()));
            if (mMap != null) {
                Marker marker = new Marker();
                marker.setZIndex(i);
                marker.setCaptionText(listBusPosByRtid.get(i).getPlainNo());
                marker.setCaptionTextSize(14);
                marker.setCaptionAligns(Align.Top);
                marker.setPosition(latLng);
                marker.setIcon(MarkerIcons.RED);
                marker.setMap(mMap);
                busMarkers.add(marker);
                Log.d(TAG, listBusPosByRtid.get(i).getSectOrd()+","+listBusPosByRtid.get(i).getStopFlag()+","+listBusPosByRtid.get(i).getPlainNo()+","+ listBusPosByRtid.get(i).getNextStTm()+","+listBusPosByRtid.get(i).getCongetion());
            }
        }
        //CameraUpdate cameraUpdate = CameraUpdate.scrollTo(latLng).animate(CameraAnimation.Fly, 1000);
        //mMap.moveCamera(cameraUpdate);
    }
}