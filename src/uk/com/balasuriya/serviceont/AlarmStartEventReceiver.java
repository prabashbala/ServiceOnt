package uk.com.balasuriya.serviceont;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class AlarmStartEventReceiver extends BroadcastReceiver {

  @Override
  public void onReceive(Context context, Intent intent) {
	  Log.d("AlarmStartEventReceiver:OnReceive","Alarm went off");
    Intent service = new Intent(context, BackgroundMusicService.class);
    context.startService(service);
  }
} 
