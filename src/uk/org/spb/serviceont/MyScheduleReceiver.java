package uk.org.spb.serviceont;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class MyScheduleReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
	SheduleAlarm alarmset = new SheduleAlarm(context);
	alarmset.setAlarm();
    }

}