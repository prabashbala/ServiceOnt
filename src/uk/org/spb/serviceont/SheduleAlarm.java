package uk.org.spb.serviceont;

import java.util.Calendar;
import java.util.GregorianCalendar;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.SystemClock;
import android.util.Log;

public class SheduleAlarm {

    // restart service every 24 hours
    private static final long REPEAT_TIME = 30000;
    //private static final long REPEAT_TIME = 86400000;
    public static final String ALARM_DATA = "alarmdata";
    public boolean alarmenabled = false;
    int alarmhour;
    int alarmminute;
    Context context;

    public void setAlarm() {

	Log.d("SheduleAlarm", "setAlarm");
	// readAlarmData(context);

	if (alarmenabled) {
	    Log.d("SheduleAlarm", "setAlarm - alarm is set");	    
	    Intent i = new Intent(context, MyStartServiceReceiver.class);
	    PendingIntent pintent = PendingIntent.getBroadcast(context, 0, i, PendingIntent.FLAG_CANCEL_CURRENT);
	    AlarmManager alarm = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
	    
	    Calendar calendar = Calendar.getInstance();
	    calendar.setTimeInMillis(System.currentTimeMillis());
	    calendar.set(Calendar.HOUR_OF_DAY, alarmhour);
	    calendar.set(Calendar.MINUTE, alarmminute);
	    //calendar.set(Calendar.SECOND, 0);
	   // calendar.set(Calendar.MILLISECOND, 0);
	    Log.d("SheduleAlarm", "setting alarm on:"+calendar.getTimeInMillis());

	    alarm.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
	            AlarmManager.INTERVAL_DAY, pintent);
	    
	    Log.d("SheduleAlarm", "setAlarm activated");
	} else {

	    Log.d("SheduleAlarm", "setAlarm - alarm is not set");
	}
    }
    
    public SheduleAlarm(Context context) {
	this.context = context;
	SharedPreferences prefs = context.getSharedPreferences(ALARM_DATA, Context.MODE_PRIVATE);
	alarmhour = prefs.getInt("hourvalue", 1);
	alarmminute = prefs.getInt("minutevalue", 1);
	alarmenabled = prefs.getBoolean("alarmenabled", false);
	Log.d("SheduleAlarm", "read alarm data: Alarm enabled :" + alarmenabled + " hour: " + alarmhour
		+ " minute: " + alarmminute);
    }

}
