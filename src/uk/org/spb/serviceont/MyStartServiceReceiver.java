package uk.org.spb.serviceont;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class MyStartServiceReceiver extends BroadcastReceiver {

  @Override
  public void onReceive(Context context, Intent intent) {
	  Log.d("MyStartServiceReceiver","OnReceive MyStartServiceReceiver");
    Intent service = new Intent(context, LocalWordService.class);
    context.startService(service);
  }
} 
