package uk.org.spb.serviceont.data;

import java.io.Serializable;

public class AlarmData implements Serializable {

    private static final long serialVersionUID = 1L;
    private String id;
    private int iHour;
    private int iMinute;

    public AlarmData(int iHour, int iMinute) {
	super();
	this.iHour = iHour;
	this.iMinute = iMinute;
    }

    public String getId() {
	return id;
    }

    public void setId(String id) {
	this.id = id;
    }

    public int getiHour() {
	return iHour;
    }

    public void setiHour(int iHour) {
	this.iHour = iHour;
    }

    public int getiMinute() {
	return iMinute;
    }

    public void setiMinute(int iMinute) {
	this.iMinute = iMinute;
    }

    @Override
    public String toString() {
	// TODO Auto-generated method stub
	return iHour + ":" + iMinute;
    }

}
