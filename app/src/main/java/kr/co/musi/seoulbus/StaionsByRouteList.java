package kr.co.musi.seoulbus;

import android.app.Activity;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import androidx.annotation.NonNull;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


/* 참고할 것 : https://mailmail.tistory.com/13*/
public class StaionsByRouteList {
    private static String TAG = "StaionsByRouteList";
    static Context mContext;
    public StaionsByRouteList(Context context) {
        mContext = context;
    }

    public int saveStaionsByRouteList(String routeid) {

        StringBuffer strBuffer = new StringBuffer();

        new Thread(new Runnable() {
            public void run() {
                String strData = getStaionsByRouteList(routeid);

                Bundle bun = new Bundle();
                bun.putString("HTML_DATA", strData);

                Message msg = handler.obtainMessage();
                msg.setData(bun);
                handler.sendMessage(msg);
            }

            private final Handler handler = new Handler() {
                public void handleMessage(Message msg) {
                    String htmlData = msg.getData().getString("HTML_DATA");
                    int saveCnt = insertRoutePathList(htmlData);
                    if (saveCnt > 0) Util.listStationByRoute = Sqlite.selectStationByRouteList(mContext, Util.busRouteId);
                }
            };
        }).start();
        return 0;
    }

    private  String getStaionsByRouteList(String routeid) {

        Log.d(TAG, "getStaionsByRouteList");
        StringBuffer strBuffer = new StringBuffer();

        try {

            String servieId = "getStaionByRoute";
            String urlStr = Util.urlBusStopInfo + servieId;
            urlStr += "?ServiceKey="+Util.serviceKey;
            urlStr += "&busRouteId="+routeid;
            urlStr += "&resultType=xml";
            System.out.println(urlStr);

            URL obj = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection)obj.openConnection();
            conn.setRequestMethod("GET");

            int resCode = conn.getResponseCode();
            if (resCode == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));

                String line = null;
                while ((line = reader.readLine()) != null) {
                    //Log.d(TAG,line);
                    strBuffer.append(line);
                }
                reader.close();
            }
            conn.disconnect();
        } catch(Exception e) {
            e.printStackTrace();
        }
        return strBuffer.toString();
    }

    public  int insertRoutePathList(String htmlData) {
        // XML Parsing + db insert
        if (htmlData.length() == 0) return -1;

        SQLiteDatabase database = null;
        String busRouteId = "";
        String seq = "";
        String busRouteNm = "";
        String section = "";
        String station = "";
        String stationNm = "";
        String gpsX = "";
        String gpsY = "";
        String direction = "";
        String fullSectDist = "";
        String stationNo = "";
        String routeType = "";
        String beginTm = "";
        String lastTm = "";
        String trnstnId = "";
        String sectSpd = "";
        String arsId = "";
        String transYn = "";
        int saveCnt = 0;
        System.out.println(busRouteId+","+seq+","+busRouteNm+","+stationNm);

        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            InputStream istream = new ByteArrayInputStream(htmlData.getBytes("utf-8"));
            org.w3c.dom.Document doc = dBuilder.parse(istream);
            doc.getDocumentElement().normalize();

            NodeList nList = doc.getElementsByTagName("itemList");

            database = Sqlite.getDatabase(mContext);
            String sqlStr = "INSERT INTO routeStationPath (busrouteid, seq, busRouteNm, section, station, stationNm, gpsX, gpsY"
                    + ",direction, fullSectDist, stationNo, routeType, beginTm, lastTm, trnstnId, sectSpd, arsId, transYn)"
                    + " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            SQLiteStatement statement = database.compileStatement(sqlStr);
            database.beginTransaction();

            for (int nCnt = 0; nCnt < nList.getLength(); nCnt++) {
                Node nNode = nList.item(nCnt);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;

                    busRouteId = eElement.getElementsByTagName("busRouteId") .item(0).getTextContent();
                    seq = eElement.getElementsByTagName("seq") .item(0).getTextContent();
                    busRouteNm = eElement.getElementsByTagName("busRouteNm") .item(0).getTextContent();
                    section = eElement.getElementsByTagName("section") .item(0).getTextContent();
                    station = eElement.getElementsByTagName("station") .item(0).getTextContent();
                    stationNm = eElement.getElementsByTagName("stationNm") .item(0).getTextContent();
                    gpsX = eElement.getElementsByTagName("gpsX") .item(0).getTextContent();
                    gpsY = eElement.getElementsByTagName("gpsY") .item(0).getTextContent();
                    direction = eElement.getElementsByTagName("direction") .item(0).getTextContent();
                    fullSectDist = eElement.getElementsByTagName("fullSectDist") .item(0).getTextContent();
                    stationNo = eElement.getElementsByTagName("stationNo") .item(0).getTextContent();
                    routeType = eElement.getElementsByTagName("routeType") .item(0).getTextContent();
                    beginTm = eElement.getElementsByTagName("beginTm") .item(0).getTextContent();
                    lastTm = eElement.getElementsByTagName("lastTm") .item(0).getTextContent();
                    trnstnId = eElement.getElementsByTagName("trnstnid") .item(0).getTextContent();
                    sectSpd = eElement.getElementsByTagName("sectSpd") .item(0).getTextContent();
                    arsId = eElement.getElementsByTagName("arsId") .item(0).getTextContent();
                    transYn = eElement.getElementsByTagName("transYn") .item(0).getTextContent();
                    System.out.println(busRouteId+","+seq+","+busRouteNm+","+stationNm);
                    try {
                        Integer.parseInt(stationNo);
                    } catch(NumberFormatException e) {
                        stationNo = "0";
                    }

                    statement.clearBindings();
                    statement.bindString(1, busRouteId);
                    statement.bindLong(2, Long.parseLong(seq));
                    statement.bindString(3, busRouteNm);
                    statement.bindString(4, section);
                    statement.bindString(5, station);
                    statement.bindString(6, stationNm);
                    statement.bindString(7, gpsX);
                    statement.bindString(8, gpsY);
                    statement.bindString(9, direction);
                    statement.bindLong(10, Long.parseLong(fullSectDist));
                    statement.bindLong(11, Long.parseLong(stationNo));
                    statement.bindLong(12, Long.parseLong(routeType));
                    statement.bindString(13, beginTm);
                    statement.bindString(14, lastTm);
                    statement.bindString(15, trnstnId);
                    statement.bindLong(16, Long.parseLong(sectSpd));
                    statement.bindString(17, arsId);
                    statement.bindString(18, transYn);
                    statement.execute();
                }
                if (nCnt%500 ==0) database.setTransactionSuccessful();
                saveCnt ++;
            }
            database.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            database.endTransaction();
        }
        return saveCnt;
    }
}
