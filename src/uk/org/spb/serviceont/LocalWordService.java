package uk.org.spb.serviceont;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

public class LocalWordService extends Service {
    private final IBinder mBinder = new MyBinder();
    // media player
    private MediaPlayer player;
    
    private static final String NOTIFICATION_DELETED_ACTION = "NOTIFICATION_DELETED";

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
	Log.d("LocalWordService","onStartCommand*************");
	playSong();
	
	return Service.START_STICKY;
    }

    @Override
    public IBinder onBind(Intent arg0) {
	return mBinder;
    }

    public class MyBinder extends Binder {
	LocalWordService getService() {
	    return LocalWordService.this;
	}
    }

    @Override
    public void onCreate(){

	Log.d("LocalWordService","oncreate notification*************");
       /*NotificationManager mNM = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
       Intent intent1 = new Intent(this.getApplicationContext(), LocalWordService.class);
       //PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent1, 0);
       PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent1, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_ONE_SHOT);
         Notification mNotify = new NotificationCompat.Builder(this)
               .setAutoCancel(true)
               .setContentTitle("Playing Pirith Chanting audio")
               .setContentText("Pirith Chanting app now playing pirith audio file")
               .setSmallIcon(R.drawable.ic_stat_notify_msg)
               //.setContentIntent(pIntent)
               .setDeleteIntent(pIntent)
               .build();

       mNM.notify(1, mNotify);
       Log.d("LocalWordService","done oncreate notification*************");
       */
       final BroadcastReceiver receiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
		    // TODO Auto-generated method stub
		    Log.d("BroadcastReceiver","innerclass");
		   if(player.isPlaying()){
		       Log.d("BroadcastReceiver","innerclass stoping player");
		       player.stop();
		   }
	            unregisterReceiver(this);
		}
	    };
	    NotificationManager mNM = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
	    Intent intent = new Intent(NOTIFICATION_DELETED_ACTION);
	        PendingIntent pendintIntent = PendingIntent.getBroadcast(this, 0, intent, 0);
	        registerReceiver(receiver, new IntentFilter(NOTIFICATION_DELETED_ACTION));
	        Notification n = new Notification.Builder(this)
	        	 .setAutoCancel(true)
	                 .setContentTitle("Playing Pirith Chanting audio")
	                 .setContentText("Pirith Chanting app now playing pirith audio file")
	                 .setDeleteIntent(pendintIntent)
	                 .setSmallIcon(R.drawable.ic_stat_notify_msg)
	         . build();
	        mNM.notify(0, n);
	        Log.d("LocalWordService","notification set");
     
    }
    // play a song
    public void playSong() {
	Log.d("LocalWordService","playSong*************");
	// play
	if(player!=null){
	    Log.d("LocalWordService","player is not null");
	    player.reset();
	}else{
	    Log.d("LocalWordService","player is null");
	    player= new MediaPlayer();
	}
	// set the data source
	try {
	    AssetFileDescriptor mp3file = getAssets().openFd("Ruwan.mp3");
	    player.setDataSource(mp3file.getFileDescriptor(),mp3file.getStartOffset(),mp3file.getLength());
	    player.prepare();
	    player.start();
	    Log.d("LocalWordService","playSong comleted*************");
	} catch (Exception e) {
	    Log.e("LocalWordService", "Error setting data source", e);
	}
    }

}