package uk.org.spb.serviceont;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class MyScheduleReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
	 Log.d("MyScheduleReceiver", "OnReceive alarm is set");
	SheduleAlarm alarmset = new SheduleAlarm(context);
	alarmset.setAlarm();
    }

}