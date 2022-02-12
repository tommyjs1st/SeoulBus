package kr.co.musi.seoulbus;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String TAG = "DBH";
    Context mContext;

    public DatabaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        Log.d(TAG, "onCreated:");
        String sql = "";
        sql = "create table if not exists routeinfo ( " +
                "routeid varchar," +
                "routenm varchar," +
                "useyn varchar," +
                "primary key('routeid') )";
        sqLiteDatabase.execSQL(sql);

        sql = "create table if not exists routepath ( " +
                "busrouteid varchar," +
                "num int," +
                "gpsX varchar," +
                "gpsY varchar," +
                "primary key('busrouteid', 'num') )";
        sqLiteDatabase.execSQL(sql);

        sql = "create table if not exists routestationpath ( " +
                "busrouteid varchar," +
                "seq int," +
                "busroutenm varchar," +
                "section varchar," +
                "station varchar," +
                "stationnm varchar," +
                "gpsx varchar," +
                "gpsy varchar," +
                "direction varchar," +
                "fullsectdist int," +
                "stationno int," +
                "routetype int," +
                "begintm varchar," +
                "lasttm varchar," +
                "trnstnid varchar," +
                "sectspd int," +
                "arsid varchar," +
                "transyn varchar," +
                "primary key('busrouteid', 'seq') )";
        sqLiteDatabase.execSQL(sql);

        Log.d(TAG, "tables are created.");

        initializeTable();
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

        String sqlStr = "";
        if (oldVersion < 1) {
            String tableName = "";
            for (int cnt =0; cnt < Sqlite.arrTableList.length; cnt++ ) {
                tableName = Sqlite.arrTableList[cnt];
                sqlStr = new StringBuilder().append("drop table if exists ").append(tableName).toString();
                sqLiteDatabase.execSQL(sqlStr);
            }
            onCreate(sqLiteDatabase);
        }
    }

    private void initializeTable() {
        Log.d(TAG, "initializeTable:");

        //insert data into routeinfo table
        Sqlite.insertRouteInfo(mContext);
    }
}
