package kr.co.musi.seoulbus;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.CameraAnimation;
import com.naver.maps.map.CameraUpdate;
import com.naver.maps.map.MapFragment;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.overlay.Align;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.overlay.Overlay;
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
                if (mMap != null) clearMarkers();

                Util.listStationByRoute = new ArrayList<StationByRoute>();
                Util.listBusPosByRtid = new ArrayList<BusPosByRtid>();

                getBusStopInfo(edBusNo.getText().toString());
                displayBusStop();
                displayBusLocation();
            }
        });
    }

    @Override
    public void onMapReady(NaverMap naverMap) {
        Log.d(TAG, "onMapReady:");
        mMap = naverMap;
        displayBusStop();
        displayBusPos();
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
            staionsByRouteList = new StaionsByRouteList(mContext);
            int saveCnt = staionsByRouteList.saveStaionsByRouteList(mBusRouteId);
        } else {
            // read Data from database
            Util.listStationByRoute = Sqlite.selectStationByRouteList(mContext, mBusRouteId);
        }

    }
    private void displayBusStop() {
        Log.d(TAG, "displayBusStop:");
        if (Util.listStationByRoute == null) return;
        if (Util.listStationByRoute.size() == 0) return;

        ArrayList<StationByRoute> listStationByRoute = Util.listStationByRoute;
        //Marker[] busStopMarkers = new Marker[listStationByRoute.size()];

        LatLng latLng = null;
        for (int i=0;i<listStationByRoute.size();i++) {
            StationByRoute stationByRoute = listStationByRoute.get(i);
            latLng = new LatLng(Double.parseDouble(stationByRoute.getGpsY()), Double.parseDouble(stationByRoute.getGpsX()));
            if (mMap != null) {
                Marker marker = new Marker();
                marker.setOnClickListener(new Overlay.OnClickListener() {
                    @Override
                    public boolean onClick(@NonNull Overlay overlay) {
                        displayMessage(marker.getTag().toString(), "S", "L");
                        return false;
                    }
                });
                marker.setZIndex(i);
                //marker.setCaptionText(stationByRoute.getStationNm());
                marker.setCaptionTextSize(14);
                marker.setCaptionAligns(Align.Top);
                marker.setPosition(latLng);
                String tagStr = stationByRoute.getStationNm()+", "+ stationByRoute.getDirection() +" 방향, 정류장번호:"+
                        stationByRoute.getStationNo();
                marker.setTag(tagStr);
                if (listStationByRoute.get(i).getDirection().equals(Util.routeDirection)) marker.setIcon(MarkerIcons.BLUE);
                else marker.setIcon(MarkerIcons.GREEN);
                marker.setMap(mMap);
                busStopMarkers.add(marker);
                Log.d(TAG, listStationByRoute.get(i).getBusRouteId()+","+listStationByRoute.get(i).getRouteType()+","+listStationByRoute.get(i).getDirection()+","+ listStationByRoute.get(i).getStationNm()+","+listStationByRoute.get(i).getStationNo());
            }
        }
        CameraUpdate cameraUpdate = CameraUpdate.scrollTo(latLng).animate(CameraAnimation.Fly, 1000);
        mMap.moveCamera(cameraUpdate);
    }
    private void displayBusLocation() {
        Log.d(TAG, "displayBusLocation");
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
        if (Util.listBusPosByRtid == null) return;
        if (Util.listBusPosByRtid.size() == 0) return;
        Log.d(TAG, "displayBusPos:");

        ArrayList<BusPosByRtid> listBusPosByRtid = Util.listBusPosByRtid;

        LatLng latLng = null;
        for (int i=0;i<listBusPosByRtid.size();i++) {
            BusPosByRtid busPosByRtid = listBusPosByRtid.get(i);
            latLng = new LatLng(Double.parseDouble(busPosByRtid.getGpsY()), Double.parseDouble(busPosByRtid.getGpsX()));
            if (mMap != null) {
                Marker marker = new Marker();
                marker.setOnClickListener(new Overlay.OnClickListener() {
                    @Override
                    public boolean onClick(@NonNull Overlay overlay) {
                        displayMessage(marker.getTag().toString(), "S", "L");
                        return false;
                    }
                });
                String lstStnId = busPosByRtid.getLastStnId();
                Log.d(TAG, "["+lstStnId+"]");
                String stationNm = "";
                for (int j=0;j<Util.listStationByRoute.size();j++) {
                    Log.d(TAG, Util.listStationByRoute.get(j).getStation());
                    if (lstStnId.equals(Util.listStationByRoute.get(j).getStation())) {
                        stationNm = Util.listStationByRoute.get(j).getStationNm();
                        break;
                    }
                }
                String tagStr = busPosByRtid.getPlainNo()+", "+
                        stationNm +" 도착 "+String.valueOf(Integer.parseInt(busPosByRtid.getNextStTm())/60)+"분전"+
                        busPosByRtid.getstopFlagNm()+busPosByRtid.getIsFullFlagNm()+busPosByRtid.getIsLastYnNm();
                marker.setTag(tagStr);
                marker.setZIndex(i+100);
                marker.setCaptionText(busPosByRtid.getPlainNo());
                marker.setCaptionTextSize(14);
                marker.setCaptionAligns(Align.Top);
                marker.setPosition(latLng);
                marker.setIcon(MarkerIcons.RED);
                marker.setMap(mMap);
                busMarkers.add(marker);
                //Log.d(TAG, listBusPosByRtid.get(i).getSectOrd()+","+listBusPosByRtid.get(i).getStopFlag()+","+listBusPosByRtid.get(i).getPlainNo()+","+ listBusPosByRtid.get(i).getNextStTm()+","+listBusPosByRtid.get(i).getCongetion());
            }
        }
        //CameraUpdate cameraUpdate = CameraUpdate.scrollTo(latLng).animate(CameraAnimation.Fly, 1000);
        //mMap.moveCamera(cameraUpdate);

    }


    private void displayMessage(String mesgCont, String mesgType, String mesgDurt) {
        // mesgType이 NULL이면 Toast방식으로, mesgDurt가 NULL이면 LENGTH_SHOT으로 표시
        int duration = 0;
        if ("S".equals(mesgType)) {  //  SnackBar
            if("L".equals(mesgDurt)) {
                duration = Snackbar.LENGTH_LONG;
            } else if("I".equals(mesgDurt)) {
                duration = Snackbar.LENGTH_INDEFINITE;
            } else {
                duration = Snackbar.LENGTH_SHORT;
            }
            // 스낵바의 배경색을 변경(기본값:검은색)
            Snackbar mysnack = Snackbar.make(findViewById(R.id.mainContainer), mesgCont, duration);
            TypedValue typedValue = new TypedValue();
            getTheme().resolveAttribute(R.attr.colorPrimaryDark, typedValue, true);
            final int color = typedValue.data;
            mysnack.getView().setBackgroundColor(color);
            mysnack.show();
        } else  { // Toast
            if("L".equals(mesgDurt)) {
                duration = Toast.LENGTH_LONG;
            } else {
                duration = Toast.LENGTH_SHORT;
            }
            Toast.makeText(getApplicationContext(), mesgCont, duration).show();

        }
    }
    private void clearMarkers() {
        for (int i=0;i<busStopMarkers.size();i++) {
            Marker marker = busStopMarkers.get(i);
            marker.setMap(null);
        }
        busStopMarkers.clear();

        for (int i=0;i<busMarkers.size();i++) {
            Marker marker = busMarkers.get(i);
            marker.setMap(null);
        }
        busMarkers.clear();
    }
}