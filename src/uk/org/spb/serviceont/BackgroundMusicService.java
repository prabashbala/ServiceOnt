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
import android.util.Log;

public class BackgroundMusicService extends Service {
    private final IBinder mBinder = new MyBinder();
    // media player
    private MediaPlayer player;
    
    private static final String NOTIFICATION_DELETED_ACTION = "NOTIFICATION_DELETED";

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
	Log.d("BackgroundMusicService:onStartCommand","Playing audio file");
	playSong();
	
	return Service.START_STICKY;
    }

    @Override
    public IBinder onBind(Intent arg0) {
	return mBinder;
    }

    public class MyBinder extends Binder {
	BackgroundMusicService getService() {
	    return BackgroundMusicService.this;
	}
    }

    @Override
    public void onCreate(){

	Log.d("BackgroundMusicService:oncreate","Initialize BackgroundMusicService notification");
      
       final BroadcastReceiver receiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
		    // TODO Auto-generated method stub
		    Log.d("BroadcastReceiver:onReceive","innerclass");
		   if(player.isPlaying()){
		       Log.d("BroadcastReceiver:onReceive","innerclass stoping player");
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
	                 .setContentTitle("Piritha Chanting Scheduler")
	                 .setContentText("Now playing pirith")
	                 .setDeleteIntent(pendintIntent)
	                 .setSmallIcon(R.drawable.ic_stat_notify_msg)
	         . build();
	        mNM.notify(0, n);
	        Log.d("BackgroundMusicService:oncreate","Notification set");
     
    }
    // play a song
    public void playSong() {
	Log.d("BackgroundMusicService:playSong","playing audio file");
	// play
	if(player!=null){
	    Log.d("BackgroundMusicService:playSong","player is not null");
	    player.reset();
	}else{
	    Log.d("BackgroundMusicService:playSong","player is null");
	    player= new MediaPlayer();
	}
	// set the data source
	try {
	    AssetFileDescriptor mp3file = getAssets().openFd("Ruwan.mp3");
	    player.setDataSource(mp3file.getFileDescriptor(),mp3file.getStartOffset(),mp3file.getLength());
	    player.prepare();
	    player.start();
	    Log.d("BackgroundMusicService:playSong","media manager activity started");
	} catch (Exception e) {
	    Log.e("BackgroundMusicService:playSong", "Error starting the media manager", e);
	}
    }

}