package kr.co.musi.seoulbus;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class BusPosByRtidList {
    private static String TAG = "BusPosByRtidList";
    static Context mContext;

    public BusPosByRtidList(Context context) {
        mContext = context;
    }


    public int selectStaionsByRouteList(String routeid) {

        StringBuffer strBuffer = new StringBuffer();

        new Thread(new Runnable() {
            public void run() {
                String strData = getBusPosByRtid(routeid);

                Bundle bun = new Bundle();
                bun.putString("HTML_DATA", strData);

                Message msg = handler.obtainMessage();
                msg.setData(bun);
                handler.sendMessage(msg);
            }

            private final Handler handler = new Handler() {
                public void handleMessage(Message msg) {
                    String htmlData = msg.getData().getString("HTML_DATA");
                    int saveCnt = parsingBusPosByRtid(htmlData);
                    if (saveCnt > 0) callMapsActivity(saveCnt);
                }
            };
        }).start();
        return 0;
    }


    private  String getBusPosByRtid(String routeid) {

        Log.d(TAG, "getBusPosByRtid");
        StringBuffer strBuffer = new StringBuffer();

        try {

            String servieId = "getBusPosByRtid";
            String urlStr = Util.urlBusPostion + servieId;
            urlStr += "?ServiceKey="+Util.serviceKey;
            urlStr += "&busRouteId="+routeid;
            //urlStr += "&resultType=xml";
            Log.d(TAG, urlStr);

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


    public  int parsingBusPosByRtid(String htmlData) {
        // XML Parsing + setting ArrayList
        if (htmlData.length() == 0) return -1;
        Log.d(TAG, "parsingBusPosByRtid:");
        ArrayList<BusPosByRtid> listBusPosByRtid = new ArrayList<BusPosByRtid>();

        String sectOrd;     //구간순번
        String fullSectDist;    //정류소간 거리
        String sectDist;    //구간옵셋거리(km)
        String rtDist;      //노선옵셋거리(km)
        String stopFlag;    //정류소도착여부(0:운행중, 1:도착)
        String sectionId;   //구간ID
        String dataTm;      //제공시간
        String gpsX;        //경도(126.910807)
        String gpsY;        //위도(37.614178)
        String vehId;       //버스ID
        String plainNo;     //차량번호(서울74사2576)
        String busType;     //차량유형(0:일반버스, 1:저상버스, 2:굴절버스)
        String lastStTm;    //종점도착소요시간
        String nextStTm;    //다음정류소도착소요시간
        String isRunYn;     //해당차량 운행여부(0:운행종료, 1:운행)
        String trnstnId;    //회차지 정류소ID
        String isLastYn;    //막차여부(0:막차아님, 1:막차)
        String isFullFlag;  //만차여부(0:만차아님, 1:만차)
        String lastStnId;   //최종정류장ID
        String congetion;   //혼잡도(0:없음, 3:여유, 4:보통, 5:혼잡, 6:매우혼잡)
        String nextStId;    //다음정류소ID
        int saveCnt = 0;

        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            InputStream istream = new ByteArrayInputStream(htmlData.getBytes("utf-8"));
            org.w3c.dom.Document doc = dBuilder.parse(istream);
            doc.getDocumentElement().normalize();

            NodeList nList = doc.getElementsByTagName("itemList");

            for (int nCnt = 0; nCnt < nList.getLength(); nCnt++) {
                Node nNode = nList.item(nCnt);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;

                    sectOrd = eElement.getElementsByTagName("sectOrd") .item(0).getTextContent();
                    fullSectDist = eElement.getElementsByTagName("fullSectDist") .item(0).getTextContent();
                    sectDist = eElement.getElementsByTagName("sectDist") .item(0).getTextContent();
                    rtDist = eElement.getElementsByTagName("rtDist") .item(0).getTextContent();
                    stopFlag = eElement.getElementsByTagName("stopFlag") .item(0).getTextContent();
                    sectionId = eElement.getElementsByTagName("sectionId") .item(0).getTextContent();
                    dataTm = eElement.getElementsByTagName("dataTm") .item(0).getTextContent();
                    gpsX = eElement.getElementsByTagName("gpsX") .item(0).getTextContent();
                    gpsY = eElement.getElementsByTagName("gpsY") .item(0).getTextContent();
                    fullSectDist = eElement.getElementsByTagName("fullSectDist") .item(0).getTextContent();
                    vehId = eElement.getElementsByTagName("vehId") .item(0).getTextContent();
                    plainNo = eElement.getElementsByTagName("plainNo") .item(0).getTextContent();
                    busType = eElement.getElementsByTagName("busType") .item(0).getTextContent();
                    lastStTm = eElement.getElementsByTagName("lastStTm") .item(0).getTextContent();
                    trnstnId = eElement.getElementsByTagName("trnstnid") .item(0).getTextContent();
                    nextStTm = eElement.getElementsByTagName("nextStTm") .item(0).getTextContent();
                    isRunYn = eElement.getElementsByTagName("isrunyn") .item(0).getTextContent();       // 대소문자 주의 전부 소문자임
                    isFullFlag = eElement.getElementsByTagName("isFullFlag") .item(0).getTextContent();
                    isLastYn = eElement.getElementsByTagName("islastyn") .item(0).getTextContent(); // 대소문자 주의
                    lastStnId = eElement.getElementsByTagName("lastStnId") .item(0).getTextContent();
                    congetion = eElement.getElementsByTagName("congetion") .item(0).getTextContent();
                    nextStId = eElement.getElementsByTagName("nextStId") .item(0).getTextContent();
                    //Log.d(TAG,sectOrd+","+fullSectDist+","+sectDist+","+plainNo);

                    BusPosByRtid busPosByRtid = new BusPosByRtid(sectOrd,fullSectDist,sectDist,rtDist,stopFlag,sectionId,
                            dataTm,gpsX,gpsY,vehId,plainNo,busType,lastStTm,nextStTm,
                            isRunYn,trnstnId,isLastYn,isFullFlag,lastStnId,congetion, nextStId);
                    listBusPosByRtid.add(busPosByRtid);
                    //Log.d(TAG, busPosByRtid.toString());
                    saveCnt ++;
                }
            }
            Util.listBusPosByRtid = listBusPosByRtid;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return saveCnt;
    }
    private void callMapsActivity(int saveCnt) {
        Intent intent = new Intent(mContext, MapsActivity.class);
        intent.putExtra("CNT", saveCnt);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_SINGLE_TOP|Intent.FLAG_ACTIVITY_CLEAR_TOP);
        mContext.startActivity(intent);
    }
}
