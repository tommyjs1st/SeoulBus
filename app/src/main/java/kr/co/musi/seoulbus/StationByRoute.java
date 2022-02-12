package kr.co.musi.seoulbus;

public class StationByRoute {

    private String busRouteId = "";
    private int seq = 0;
    private String busRouteNm = "";
    private String section = "";
    private String station = "";
    private String stationNm = "";
    private String gpsX = "";
    private String gpsY = "";
    private String direction = "";
    private int fullSectDist = 0;
    private int stationNo = 0;
    private int routeType = 0;
    private String beginTm = "";
    private String lastTm = "";
    private String trnstnId = "";
    private int sectSpd = 0;
    private String arsId = "";
    private String transYn = "";

    public StationByRoute(String busRouteId, int seq, String busRouteNm, String section, String station, String stationNm, String gpsX, String gpsY, String direction, int fullSectDist, int stationNo, int routeType, String beginTm, String lastTm, String trnstnId, int sectSpd, String arsId, String transYn) {
        this.busRouteId = busRouteId;
        this.seq = seq;
        this.busRouteNm = busRouteNm;
        this.section = section;
        this.station = station;
        this.stationNm = stationNm;
        this.gpsX = gpsX;
        this.gpsY = gpsY;
        this.direction = direction;
        this.fullSectDist = fullSectDist;
        this.stationNo = stationNo;
        this.routeType = routeType;
        this.beginTm = beginTm;
        this.lastTm = lastTm;
        this.trnstnId = trnstnId;
        this.sectSpd = sectSpd;
        this.arsId = arsId;
        this.transYn = transYn;
    }


    public String getBusRouteId() {
        return busRouteId;
    }

    public void setBusRouteId(String busRouteId) {
        this.busRouteId = busRouteId;
    }

    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

    public String getBusRouteNm() {
        return busRouteNm;
    }

    public void setBusRouteNm(String busRouteNm) {
        this.busRouteNm = busRouteNm;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }

    public String getStationNm() {
        return stationNm;
    }

    public void setStationNm(String stationNm) {
        this.stationNm = stationNm;
    }

    public String getGpsX() {
        return gpsX;
    }

    public void setGpsX(String gpsX) {
        this.gpsX = gpsX;
    }

    public String getGpsY() {
        return gpsY;
    }

    public void setGpsY(String gpsY) {
        this.gpsY = gpsY;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public int getFullSectDist() {
        return fullSectDist;
    }

    public void setFullSectDist(int fullSectDist) {
        this.fullSectDist = fullSectDist;
    }

    public int getStationNo() {
        return stationNo;
    }

    public void setStationNo(int stationNo) {
        this.stationNo = stationNo;
    }

    public int getRouteType() {
        return routeType;
    }

    public void setRouteType(int routeType) {
        this.routeType = routeType;
    }

    public String getBeginTm() {
        return beginTm;
    }

    public void setBeginTm(String beginTm) {
        this.beginTm = beginTm;
    }

    public String getLastTm() {
        return lastTm;
    }

    public void setLastTm(String lastTm) {
        this.lastTm = lastTm;
    }

    public String getTrnstnId() {
        return trnstnId;
    }

    public void setTrnstnId(String trnstnId) {
        this.trnstnId = trnstnId;
    }

    public int getSectSpd() {
        return sectSpd;
    }

    public void setSectSpd(int sectSpd) {
        this.sectSpd = sectSpd;
    }

    public String getArsId() {
        return arsId;
    }

    public void setArsId(String arsId) {
        this.arsId = arsId;
    }

    public String getTransYn() {
        return transYn;
    }

    public void setTransYn(String transYn) {
        this.transYn = transYn;
    }


    @Override
    public String toString() {
        return "StationByRoute{" +
                "busRouteId='" + busRouteId + '\'' +
                ", seq=" + String.valueOf(seq) +
                ", busRouteNm='" + busRouteNm + '\'' +
                ", section='" + section + '\'' +
                ", station='" + station + '\'' +
                ", stationNm='" + stationNm + '\'' +
                ", gpsX='" + gpsX + '\'' +
                ", gpsY='" + gpsY + '\'' +
                ", direction='" + direction + '\'' +
                ", fullSectDist='" + fullSectDist + '\'' +
                ", stationNo='" + stationNo + '\'' +
                ", routeType='" + routeType + '\'' +
                ", beginTm='" + beginTm + '\'' +
                ", lastTm='" + lastTm + '\'' +
                ", trnstnId='" + trnstnId + '\'' +
                ", sectSpd='" + sectSpd + '\'' +
                ", arsId='" + arsId + '\'' +
                ", transYn='" + transYn + '\'' +
                '}';
    }

}
