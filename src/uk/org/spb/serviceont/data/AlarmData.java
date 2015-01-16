package uk.org.spb.serviceont.data;

import java.io.Serializable;

public class AlarmData implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long id;
    private int iHour;
    private int iMinute;

    public AlarmData(Long id ,int iHour, int iMinute) {
	super();
	this.id=id;
	this.iHour = iHour;
	this.iMinute = iMinute;
    }

    public Long getId() {
	return id;
    }

    public void setId(Long id) {
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
    
    @Override
    public boolean equals(Object o) {
        // TODO Auto-generated method stub
        return ((AlarmData)o).getId().equals(this.getId());
    }

}
