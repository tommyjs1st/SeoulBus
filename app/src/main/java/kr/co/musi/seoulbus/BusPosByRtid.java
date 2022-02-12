package kr.co.musi.seoulbus;

public class BusPosByRtid {
    private String sectOrd;     //구간순번
    private String fullSectDist;    //정류소간 거리
    private String sectDist;    //구간옵셋거리(km)
    private String rtDist;      //노선옵셋거리(km)
    private String stopFlag;    //정류소도착여부(0:운행중, 1:도착)
    private String sectionId;   //구간ID
    private String dataTm;      //제공시간
    private String gpsX;        //경도(126.910807)
    private String gpsY;        //위도(37.614178)
    private String vehId;       //버스ID
    private String plainNo;     //차량번호(서울74사2576)
    private String busType;     //차량유형(0:일반버스, 1:저상버스, 2:굴절버스)
    private String lastStTm;    //종점도착소요시간
    private String nextStTm;    //다음정류소도착소요시간
    private String isRunYn;     //해당차량 운행여부(0:운행종료, 1:운행)
    private String trnstnId;    //회차지 정류소ID
    private String isLastYn;    //막차여부(0:막차아님, 1:막차)
    private String isFullFlag;  //만차여부(0:만차아님, 1:만차)
    private String lastStnId;   //최종정류장ID
    private String congetion;   //혼잡도(0:없음, 3:여유, 4:보통, 5:혼잡, 6:매우혼잡)
    private String nextStId;    //다음정류소아이디디

    public BusPosByRtid(String sectOrd, String fullSectDist, String sectDist, String rtDist,
                        String stopFlag, String sectionId, String dataTm, String gpsX, String gpsY,
                        String vehId, String plainNo, String busType, String lastStTm,
                        String nextStTm, String isRunYn, String trnstnId, String isLastYn,
                        String isFullFlag, String lastStnId, String congetion, String nextStId) {
        this.sectOrd = sectOrd;
        this.fullSectDist = fullSectDist;
        this.sectDist = sectDist;
        this.rtDist = rtDist;
        this.stopFlag = stopFlag;
        this.sectionId = sectionId;
        this.dataTm = dataTm;
        this.gpsX = gpsX;
        this.gpsY = gpsY;
        this.vehId = vehId;
        this.plainNo = plainNo;
        this.busType = busType;
        this.lastStTm = lastStTm;
        this.nextStTm = nextStTm;
        this.isRunYn = isRunYn;
        this.trnstnId = trnstnId;
        this.isLastYn = isLastYn;
        this.isFullFlag = isFullFlag;
        this.lastStnId = lastStnId;
        this.congetion = congetion;
        this.nextStId = nextStId;
    }

    public String getSectOrd() {
        return sectOrd;
    }

    public void setSectOrd(String sectOrd) {
        this.sectOrd = sectOrd;
    }

    public String getFullSectDist() {
        return fullSectDist;
    }

    public void setFullSectDist(String fullSectDist) {
        this.fullSectDist = fullSectDist;
    }

    public String getSectDist() {
        return sectDist;
    }

    public void setSectDist(String sectDist) {
        this.sectDist = sectDist;
    }

    public String getRtDist() {
        return rtDist;
    }

    public void setRtDist(String rtDist) {
        this.rtDist = rtDist;
    }

    public String getStopFlag() {
        return stopFlag;
    }

    public void setStopFlag(String stopFlag) {
        this.stopFlag = stopFlag;
    }

    public String getSectionId() {
        return sectionId;
    }

    public void setSectionId(String sectionId) {
        this.sectionId = sectionId;
    }

    public String getDataTm() {
        return dataTm;
    }

    public void setDataTm(String dataTm) {
        this.dataTm = dataTm;
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

    public String getVehId() {
        return vehId;
    }

    public void setVehId(String vehId) {
        this.vehId = vehId;
    }

    public String getPlainNo() {
        return plainNo;
    }

    public void setPlainNo(String plainNo) {
        this.plainNo = plainNo;
    }

    public String getBusType() {
        return busType;
    }

    public void setBusType(String busType) {
        this.busType = busType;
    }

    public String getLastStTm() {
        return lastStTm;
    }

    public void setLastStTm(String lastStTm) {
        this.lastStTm = lastStTm;
    }

    public String getNextStTm() {
        return nextStTm;
    }

    public void setNextStTm(String nextStTm) {
        this.nextStTm = nextStTm;
    }

    public String getIsRunYn() {
        return isRunYn;
    }

    public void setIsRunYn(String isRunYn) {
        this.isRunYn = isRunYn;
    }

    public String getTrnstnId() {
        return trnstnId;
    }

    public void setTrnstnId(String trnstnId) {
        this.trnstnId = trnstnId;
    }

    public String getIsLastYn() {
        return isLastYn;
    }

    public void setIsLastYn(String isLastYn) {
        this.isLastYn = isLastYn;
    }

    public String getIsFullFlag() {
        return isFullFlag;
    }

    public void setIsFullFlag(String isFullFlag) {
        this.isFullFlag = isFullFlag;
    }

    public String getLastStnId() {
        return lastStnId;
    }

    public void setLastStnId(String lastStnId) {
        this.lastStnId = lastStnId;
    }

    public String getCongetion() {
        return congetion;
    }

    public void setCongetion(String congetion) {
        this.congetion = congetion;
    }

    public String getNextStId() {
        return nextStId;
    }

    public void setNextStId(String nextStId) {
        this.nextStId = nextStId;
    }

    @Override
    public String toString() {
        return "BusPostByRtid{" +
                "sectOrd='" + sectOrd + '\'' +
                ", fullSectDist='" + fullSectDist + '\'' +
                ", sectDist='" + sectDist + '\'' +
                ", rtDist='" + rtDist + '\'' +
                ", stopFlag='" + stopFlag + '\'' +
                ", sectionId='" + sectionId + '\'' +
                ", dataTm='" + dataTm + '\'' +
                ", gpsX='" + gpsX + '\'' +
                ", gpsY='" + gpsY + '\'' +
                ", vehId='" + vehId + '\'' +
                ", plainNo='" + plainNo + '\'' +
                ", busType='" + busType + '\'' +
                ", lastStTm='" + lastStTm + '\'' +
                ", nextStTm='" + nextStTm + '\'' +
                ", isRunYn='" + isRunYn + '\'' +
                ", trnstnId='" + trnstnId + '\'' +
                ", isLastYn='" + isLastYn + '\'' +
                ", isFullFlag='" + isFullFlag + '\'' +
                ", lastStnId='" + lastStnId + '\'' +
                ", congetion='" + congetion + '\'' +
                ", nextStId='" + nextStId + '\'' +
                '}';
    }
}
