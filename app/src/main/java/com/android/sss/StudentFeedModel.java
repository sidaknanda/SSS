package com.android.sss;

/**
 * Created by OPTIMUSDOM ubuntu151 on 13/10/15.
 */
public class StudentFeedModel {

    public String RFID;
    public int Flag;
    public String Date;
    public String Time;

    public String getRFID() {
        return RFID;
    }

    public void setRFID(String RFID) {
        this.RFID = RFID;
    }

    public int getFlag() {
        return Flag;
    }

    public void setFlag(int flag) {
        Flag = flag;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }
}
