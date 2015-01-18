package uk.org.spb.serviceont;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

import uk.org.spb.serviceont.data.AlarmData;
import uk.org.spb.serviceont.util.ApplicationContext;
import uk.org.spb.serviceont.util.ObjectSerializer;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

public class AlarmShedulerAction {

    private static final String ALARM_DATA = "alarmdata";
    private Context context=ApplicationContext.getAppContext();
    private ArrayList<AlarmData> alarmlist =null;
    
/**
 * Set alarm trigger back ground service
 */
    public void setAlarm() {
	alarmlist=getSavedTimeList();
	Log.d("AlarmShedulerAction:setAlarm", "Setting alarm tigger activity");
	// readAlarmData(context);
	
	for(AlarmData almdta : alarmlist){
	    Log.d("AlarmShedulerAction:setAlarm", "Time to activate alarm is set");	    
	    Intent i = new Intent(context, AlarmStartEventReceiver.class);
	    PendingIntent pintent = PendingIntent.getBroadcast(context, 0, i, PendingIntent.FLAG_CANCEL_CURRENT);
	    AlarmManager alarm = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
	    
	    Calendar calendar = Calendar.getInstance();
	    calendar.setTimeInMillis(System.currentTimeMillis());
	    calendar.set(Calendar.HOUR_OF_DAY, almdta.getiHour());
	    calendar.set(Calendar.MINUTE, almdta.getiMinute());
	    calendar.set(Calendar.SECOND, 0);
	    calendar.set(Calendar.MILLISECOND, 0);
	    Log.d("AlarmShedulerAction:setAlarm", "setting alarm on:"+calendar.getTimeInMillis());

	    alarm.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
	            AlarmManager.INTERVAL_DAY, pintent);
	    
	    
	    
	    Log.d("AlarmShedulerAction:setAlarm", "Repeating alarm was activated successfully");
	}
    }
    
    
    public AlarmShedulerAction(Context context) {
	Log.d("AlarmShedulerAction", "Setting alarm shedule job");
	this.context = context;
    }
    
    public AlarmShedulerAction() {
   	Log.d("AlarmShedulerAction", "AlarmShedulerAction inside default contstructor");
       }
    
    /**
     * Get alarm time data from the shared preference
     * @return ArrayList of AlarmData from the saved shared preference
     * TODO: moved this method to a utility class with the getsavedTime method.
     */
    private ArrayList<AlarmData> getSavedTimeList() {
	SharedPreferences prefs = context.getSharedPreferences(ALARM_DATA, Context.MODE_PRIVATE);
	ArrayList<AlarmData> alarmlist = null;
	try {
	    Log.d("TimePickerActivity:saveTime", "Reading Alarm data");
	    alarmlist = (ArrayList<AlarmData>) ObjectSerializer.deserialize(prefs.getString("alarmobject",
		    ObjectSerializer.serialize(new ArrayList<AlarmData>())));
	} catch (IOException e) {
	    Log.e("TimePickerActivity:saveTime", "Error occured while reading alarm data:" + e.getMessage());
	}
	return alarmlist;
    }	
    

}
