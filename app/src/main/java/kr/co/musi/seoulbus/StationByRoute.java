package kr.co.musi.seoulbus;

public class StationByRoute {

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

    public StationByRoute(String busRouteId, String seq, String busRouteNm, String section, String station, String stationNm, String gpsX, String gpsY, String direction, String fullSectDist, String stationNo, String routeType, String beginTm, String lastTm, String trnstnId, String sectSpd, String arsId, String transYn) {
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

    public String getSeq() {
        return seq;
    }

    public void setSeq(String seq) {
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

    public String getFullSectDist() {
        return fullSectDist;
    }

    public void setFullSectDist(String fullSectDist) {
        this.fullSectDist = fullSectDist;
    }

    public String getStationNo() {
        return stationNo;
    }

    public void setStationNo(String stationNo) {
        this.stationNo = stationNo;
    }

    public String getRouteType() {
        return routeType;
    }

    public void setRouteType(String routeType) {
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

    public String getSectSpd() {
        return sectSpd;
    }

    public void setSectSpd(String sectSpd) {
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
                ", seq='" + seq + '\'' +
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
