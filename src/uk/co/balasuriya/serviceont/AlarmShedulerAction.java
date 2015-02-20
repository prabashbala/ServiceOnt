package uk.co.balasuriya.serviceont;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import uk.co.balasuriya.serviceont.data.AlarmData;
import uk.co.balasuriya.serviceont.util.AlarmDataHandler;
import uk.co.balasuriya.serviceont.util.ApplicationContext;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class AlarmShedulerAction {

    private static final String ALARM_DATA = "alarmdata";
    private Context context = ApplicationContext.getAppContext();
    private ArrayList<AlarmData> alarmlist = null;

    /**
     * Set alarm trigger back ground service
     */
    public void setAlarm() {
	alarmlist = getSavedTimeList();
	Log.d("AlarmShedulerAction:setAlarm", "Setting alarm tigger activity");
	// readAlarmData(context);

	for (AlarmData almdta : alarmlist) {
	    Log.d("AlarmShedulerAction:setAlarm", "Time to activate alarm is set");
	    Intent i = new Intent(context, AlarmStartEventReceiver.class);
	    PendingIntent pintent = PendingIntent.getBroadcast(context, almdta.getRequestCode(), i,
		    PendingIntent.FLAG_CANCEL_CURRENT);
	    AlarmManager alarm = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

	    Calendar calendar = Calendar.getInstance();
	    calendar.setTimeInMillis(System.currentTimeMillis());
	    calendar.set(Calendar.HOUR_OF_DAY, almdta.getiHour());
	    calendar.set(Calendar.MINUTE, almdta.getiMinute());
	    calendar.set(Calendar.SECOND, 0);
	    calendar.set(Calendar.MILLISECOND, 0);
	    Log.d("AlarmShedulerAction:setAlarm", "setting alarm on:" + calendar.getTimeInMillis());

	    alarm.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pintent);

	    Log.d("AlarmShedulerAction:setAlarm", "Repeating alarm was activated successfully");
	}
    }

    public void addSingleAlarm(AlarmData alarmdata) {
	Log.d("AlarmShedulerAction:addSingleAlarm", "inside addSingleAlarm");
	Intent i = new Intent(context, AlarmStartEventReceiver.class);
	PendingIntent pintent = PendingIntent.getBroadcast(context, alarmdata.getRequestCode(), i,
		PendingIntent.FLAG_CANCEL_CURRENT);
	AlarmManager alarm = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

	Calendar calendar = Calendar.getInstance();
	calendar.setTimeInMillis(System.currentTimeMillis());
	calendar.set(Calendar.HOUR_OF_DAY, alarmdata.getiHour());
	calendar.set(Calendar.MINUTE, alarmdata.getiMinute());
	calendar.set(Calendar.SECOND, 0);
	calendar.set(Calendar.MILLISECOND, 0);
	alarm.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pintent);
	SimpleDateFormat dt1 = new SimpleDateFormat("HH:mm:ss");
	Log.d("AlarmShedulerAction:addSingleAlarm", "successfully adding new alarm for:" + dt1.format(calendar.getTimeInMillis())+ "with code"+alarmdata.getRequestCode());
    }

    public void removeSingleAlarm(AlarmData alarmdata) {
	Log.d("AlarmShedulerAction:removeSingleAlarm", "inside removeSingleAlarm");
	Intent i = new Intent(context, AlarmStartEventReceiver.class);
	PendingIntent pintent = PendingIntent.getBroadcast(context, alarmdata.getRequestCode(), i,
		PendingIntent.FLAG_CANCEL_CURRENT);
	AlarmManager alarm = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
	alarm.cancel(pintent);
	Log.d("AlarmShedulerAction:removeSingleAlarm", "Repeating alarm was removed successfully "+alarmdata.getiHour()+":"+alarmdata.getiMinute()+ "with code"+alarmdata.getRequestCode());
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
     * 
     * @return ArrayList of AlarmData from the saved shared preference 
     * 
     */
    private ArrayList<AlarmData> getSavedTimeList() {
	return AlarmDataHandler.getSavedTimeList();
    }

}
