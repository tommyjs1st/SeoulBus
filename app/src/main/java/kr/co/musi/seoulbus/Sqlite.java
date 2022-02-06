package kr.co.musi.seoulbus;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
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

    public static void insertRouteInfo(Context context)  {
        String useYn = "Y";
        if (database == null) openDataBase(context);

        if (database != null) {

            //routeinfo.csv 파일을 열어서 DB에 저장한다.
            try {
                BufferedReader reader = null;
                reader = new BufferedReader(
                        new FileReader("c:\\android\\seoulbus\\doc\\routeinfo.csv")
                );
                String str;
                while((str = reader.readLine()) != null) {
                    String[] values = str.split(",");
                    System.out.println(str+":"+values[0]+","+values[1]);
                    String sql = "insert into routeinfo(routeid, routenm, useyn) "+
                            "values(?, ?, ?)";
                    Object[] params = {values[1], values[0], useYn};
                    database.execSQL(sql, params);
                }
                reader.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
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

}
