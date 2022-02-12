package kr.co.musi.seoulbus;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.PreparedStatement;
import java.util.ArrayList;

public class Sqlite {

    private static String TAG = "SQLITE";
    public static SQLiteDatabase database = null;
    public static String databaseName = "busRoute.db";
    public static String[] arrTableList = {"routeinfo", "routepath", "routestationpath"};
    private static final int version = 1;
    private static final int maxSaveTerm = -30;

    ///
    public static SQLiteDatabase openDataBase(Context context) {
        DatabaseHelper helper = new DatabaseHelper(context, databaseName, null, version);
        try {
            database = helper.getWritableDatabase();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return database;
    }
    public static SQLiteDatabase getDatabase(Context context) {
        if (database == null) openDataBase(context);
        return database;
    }

    public static void closeDataBase() {
        database.close();
    }

    public static void insertRouteInfo(Context context)  {
        Log.d(TAG,"Sqlite.insertRouteInfo:");
        String useYn = "Y";
        String routenm = "";
        String routeid = "";

        database = getDatabase(context);
        if (database != null) {

            try {
                for(int i=0;i< Util.arrRouteInfo.length;i++) {
                    routenm = Util.arrRouteInfo[i][0];
                    routeid = Util.arrRouteInfo[i][1];
                    String sql = "insert into routeinfo(routeid, routenm, useyn)  values(?, ?, ?)";
                    Object[] params = {routeid, routenm, useYn};
                    database.execSQL(sql, params);
                    //Log.d(TAG, routeid+","+routenm);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static int deleteAllRouteInfo() {

        if (database != null) {
            try {
                String sql = "delete from routeinfo";
                database.execSQL(sql);
            } catch (Exception e) {
                e.printStackTrace();
                return  -1;
            }
        } else {
            Log.e(TAG, "deleteAllRouteInfo:database not opened.");
            return  -1;
        }
        return 0;
    }

    @SuppressLint("Range")
    public static int countRouteinfo() {
        if (database == null) return -1;
        int rowCnt = 0;

        String sqlStr = "";
        sqlStr  = "select count(*) cnt ";
        sqlStr += "from   routeinfo ";
        Cursor cursor = database.rawQuery(sqlStr, null);
        if (cursor != null && cursor.moveToFirst()) {
            rowCnt = cursor.getInt(cursor.getColumnIndex("cnt"));
        }
        cursor.close();
        return rowCnt;
    }

    @SuppressLint("Range")
    public static String getBusRouteId(String routeNm) {
        if (database == null) return "";
        String BusRouteId = "";

        String sqlStr = "";
        sqlStr  = "select routeid ";
        sqlStr += "from   routeinfo ";
        sqlStr += "where  routenm = ?";
        String[] args = {routeNm};
        Cursor cursor = database.rawQuery(sqlStr, args);
        if (cursor != null && cursor.moveToNext()) {
            BusRouteId = cursor.getString(cursor.getColumnIndex("routeid"));
        }
        cursor.close();
        return BusRouteId;
    }

    @SuppressLint("Range")
    public static int countRoutePath(String busrouteid) {
        if (database == null) return -1;
        int rowCnt = 0;

        String sqlStr = "";
        sqlStr  = "select count(*) cnt ";
        sqlStr += "from   routestationpath ";
        sqlStr += "where  busrouteid = ? ";
        String[] args = {busrouteid};
        Cursor cursor = database.rawQuery(sqlStr, args);
        if (cursor != null && cursor.moveToFirst()) {
            rowCnt = cursor.getInt(cursor.getColumnIndex("cnt"));
        }
        cursor.close();
        return rowCnt;
    }

    public static ArrayList<StationByRoute> selectStationByRouteList(Context context, String busRouteId) {
        Log.d(TAG, "selectStationByRouteList:busRouteId["+busRouteId+"]");
        ArrayList<StationByRoute> listStationByRoute = new ArrayList<StationByRoute>();
        StationByRoute stationByRoute;
        String direction = "";

        if (database == null) return null;

        String sqlStr = "";
        sqlStr  = "select busrouteid, seq, busroutenm, section, station, stationnm, gpsx, " +
                         "gpsy, direction, fullsectdist,  stationno, routetype, begintm, " +
                         "lasttm, trnstnid, sectspd, arsid, transyn ";
        sqlStr += "from   routeStationPath ";
        sqlStr += "where  busrouteid = ? ";
        String[] args = {busRouteId};

        Cursor cursor = database.rawQuery(sqlStr, args);
        if (cursor != null && cursor.moveToNext()) {
            do {
                direction = cursor.getString(cursor.getColumnIndex("direction"));
                Util.routeDirection = direction;

                stationByRoute = new StationByRoute(
                        busRouteId,
                        cursor.getInt(cursor.getColumnIndex("seq")),
                        cursor.getString(cursor.getColumnIndex("busroutenm")),
                        cursor.getString(cursor.getColumnIndex("section")),
                        cursor.getString(cursor.getColumnIndex("station")),
                        cursor.getString(cursor.getColumnIndex("stationnm")),
                        cursor.getString(cursor.getColumnIndex("gpsx")),
                        cursor.getString(cursor.getColumnIndex("gpsy")),
                        cursor.getString(cursor.getColumnIndex("direction")),
                        cursor.getInt(cursor.getColumnIndex("fullsectdist")),
                        cursor.getInt(cursor.getColumnIndex("stationno")),
                        cursor.getInt(cursor.getColumnIndex("routetype")),
                        cursor.getString(cursor.getColumnIndex("begintm")),
                        cursor.getString(cursor.getColumnIndex("lasttm")),
                        cursor.getString(cursor.getColumnIndex("trnstnid")),
                        cursor.getInt(cursor.getColumnIndex("sectspd")),
                        cursor.getString(cursor.getColumnIndex("arsid")),
                        cursor.getString(cursor.getColumnIndex("transyn")));
                listStationByRoute.add(stationByRoute);
                //Log.d(TAG, stationByRoute.toString());
            } while(cursor.moveToNext());
        }
        cursor.close();
        //Log.d(TAG, "count of listStationByRoute is " + listStationByRoute.size());

        return listStationByRoute;
    }

}
