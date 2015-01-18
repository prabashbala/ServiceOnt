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
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class BackgroundMusicService extends Service {
    private final IBinder mBinder = new MyBinder();
    // media player
    private MediaPlayer player;
    private NotificationManager mNM;

    private static final String NOTIFICATION_DELETED_ACTION = "NOTIFICATION_DELETED";

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
	Log.d("BackgroundMusicService:onStartCommand", "Playing audio file");
	
	if(){
	    
	}
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

    public void setNotification() {

	Log.d("BackgroundMusicService:oncreate", "Initialize BackgroundMusicService notification");

	final BroadcastReceiver receiver = new BroadcastReceiver() {

	    @Override
	    public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		Log.d("BroadcastReceiver:onReceive", "Notification received");
		if (player.isPlaying()) {
		    Log.d("BroadcastReceiver:onReceive", "Innerclass stoping player");
		    player.stop();
		}
		unregisterReceiver(this);
	    }
	};
	mNM = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
	Intent intent = new Intent(NOTIFICATION_DELETED_ACTION);
	PendingIntent pendintIntent = PendingIntent.getBroadcast(this, 0, intent, 0);
	registerReceiver(receiver, new IntentFilter(NOTIFICATION_DELETED_ACTION));
	Notification n = new Notification.Builder(this).setAutoCancel(true)
		.setContentTitle("Piritha Chanting Scheduler").setContentText("Now playing pirith")
		.setDeleteIntent(pendintIntent).setSmallIcon(R.drawable.ic_stat_notify_msg).build();
	mNM.notify(0, n);
	Log.d("BackgroundMusicService:oncreate", "Notification set");

    }

    // play a song
    public void playSong() {
	Log.d("BackgroundMusicService:playSong", "Playing audio file");
	// play
	if (player != null) {
	    Log.d("BackgroundMusicService:playSong", "Player is not null");
	    player.reset();
	} else {
	    Log.d("BackgroundMusicService:playSong", "Player is null");
	    player = new MediaPlayer();
	}
	// set the data source
	try {
	    AssetFileDescriptor mp3file = getAssets().openFd("Ruwan.mp3");
	    player.setDataSource(mp3file.getFileDescriptor(), mp3file.getStartOffset(), mp3file.getLength());
	    player.prepare();
	    
	    setNotification();
	    player.start();

	    player.setOnCompletionListener(new OnCompletionListener() {

		@Override
		public void onCompletion(MediaPlayer mp) {
		    // TODO Auto-generated method stub
		    Log.d("BackgroundMusicService:oncreate", "Clearing Notification");
		    if(mNM!=null)
		    mNM.cancel(0);
		    Log.d("BackgroundMusicService:oncreate", "Notification cleared");
		}
	    });

	    Log.d("BackgroundMusicService:playSong", "MediaManager activity started");
	} catch (Exception e) {
	    Log.e("BackgroundMusicService:playSong", "Error starting the media manager", e);
	}
    }

}