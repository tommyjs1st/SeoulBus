package kr.co.musi.seoulbus;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Sqlite {

    private static String TAG = "SQLITE";

    public static SQLiteDatabase database = null;
    public static String databaseName = "busRoute.db";
    public static String[] arrTableList = {"routeinfo", "routepath", "routestationpath"};
    private static final int version = 0;
    private static final int maxSaveTerm = -30;

    ///
    public static void openDataBase(Context context) {
        DatabaseHelper helper = new DatabaseHelper(context, databaseName, null, version);
        try {
            database = helper.getWritableDatabase();

            // 기준위치 정보가 없으면 넣어준다.


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void closeDataBase() {
        database.close();
    }

    public static void insertRouteInfo(Context context) {
        if (database == null) openDataBase(context);

        if (database != null) {
            //routeinfo.csv 파일을 열어서 DB에 저장한다.

            String latitude = Double.toString(intent.getDoubleExtra("latitude", 0.0));
            String longitude = Double.toString(intent.getDoubleExtra("longitude", 0.0));
            String keepgoing = intent.getStringExtra("keepgoing");
            String gpsReturnCnt = Integer.toString(intent.getIntExtra("gpsreturncnt", 0));
            String maxReturnCnt = Integer.toString(intent.getIntExtra("maxreturncnt", 0));
            String notiDate = intent.getStringExtra("notidate");
            String batteryPct = Integer.toString(intent.getIntExtra("batterypct", 0));
            String accuracy = Float.toString(intent.getFloatExtra("accuracy", 0));

            String busstopno = "";

            notiDate = notiDate.replaceAll("/", "-");

            String sql = "insert into busloc(latitude, longitude, keepgoing, notidate, gpsreturncnt, batterypct, reqcnt, accuracy, busstopno) "+
                    "values(?, ?, ?, ?, ?, ?, ?, ?, ?)";
            Object[] params = {latitude, longitude, keepgoing, notiDate, gpsReturnCnt, batteryPct, maxReturnCnt, accuracy, busstopno};
            database.execSQL(sql, params);
        }
    }

    public static int deleteOldData(int days) {

        if (days >= 0) days = maxSaveTerm;
        if (database != null) {
            try {
                String sql = "delete from busloc where notidate < datetime(datetime('now','localtime'), ? ||' days')";
                String[] params = {String.valueOf(days)};
                database.execSQL(sql, params);
            } catch (Exception e) {
                e.printStackTrace();
                return  -1;
            }
        } else {
            Log.e(TAG, "deleteOldData:database not opened.");
            return  -1;
        }
        return 0;
    }

    public static void deleteDataAll() {

        if (database != null) {
            String sql = "delete from busloc";
            database.execSQL(sql);
        } else {
            Log.e(TAG, "deleteDataAll:database not opened.");
        }
        return ;
    }


}
