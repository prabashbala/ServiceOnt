package uk.co.balasuriya.serviceont;

import java.util.Calendar;

import uk.co.balasuriya.serviceont.data.AlarmData;
import uk.co.balasuriya.serviceont.util.AlarmDataHandler;
import android.app.Service;
import android.content.Intent;
import android.util.Log;

public class BackgroundMusicService extends PlayNowMusicService {

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
	Log.d("BackgroundMusicService:onStartCommand", "Playing audio file and intent is: " + intent);

	Calendar rightNow = Calendar.getInstance();
	int hour = rightNow.get(Calendar.HOUR_OF_DAY);
	int mintue = rightNow.get(Calendar.MINUTE);
	AlarmData alarmdata = new AlarmData("",rightNow.getTimeInMillis(), hour, mintue);
	AlarmData foundrecord =AlarmDataHandler.isAnAlarmSetTime(alarmdata);
	if (foundrecord!=null) {
	    Log.d("BackgroundMusicService:onStartCommand", "song ready to play: " + foundrecord);
	    playSong(foundrecord.getFilename());
	}
	return Service.START_NOT_STICKY;
    }

}