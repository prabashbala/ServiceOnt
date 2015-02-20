package uk.com.balasuriya.serviceont;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class BootCompletedEventReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
	 Log.d("BootCompletedEventReceiver:OnReceive", "Boot completed event triggerd");
	AlarmShedulerAction alarmset = new AlarmShedulerAction(context);
	alarmset.setAlarm();
    }

}