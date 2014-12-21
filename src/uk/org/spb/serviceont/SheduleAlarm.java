package uk.org.spb.serviceont;

import java.util.Calendar;
import java.util.GregorianCalendar;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
	    AlarmManager service = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
	    Intent i = new Intent(context, MyStartServiceReceiver.class);
	    PendingIntent pending = PendingIntent.getBroadcast(context, 0, i, PendingIntent.FLAG_CANCEL_CURRENT);
	    Calendar cal = setTime();
	    // fetch every 30 seconds
	    // InexactRepeating allows Android to optimize the energy
	    // consumption
	    Log.d("SheduleAlarm", "setting alarm on:"+cal.getTime().toGMTString());
	    service.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), REPEAT_TIME, pending);
	    Log.d("SheduleAlarm", "setAlarm activated");
	} else {

	    Log.d("SheduleAlarm", "setAlarm - alarm is not set");
	}
    }

    private Calendar setTime() {

	Calendar cur_cal = new GregorianCalendar();
	cur_cal.setTimeInMillis(System.currentTimeMillis());

	Calendar cal = new GregorianCalendar();
	cal.add(Calendar.DAY_OF_YEAR, cur_cal.get(Calendar.DAY_OF_YEAR));
	cal.set(Calendar.DATE, cur_cal.get(Calendar.DATE));
	cal.set(Calendar.MONTH, cur_cal.get(Calendar.MONTH));
	cal.set(Calendar.HOUR_OF_DAY, alarmhour);
	cal.set(Calendar.MINUTE, alarmminute);
	cal.set(Calendar.SECOND, 0);
	cal.set(Calendar.MILLISECOND, 0);
	Log.d("SheduleAlarm", "setTime: " + cal.getTime().toGMTString());
	return cal;
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
