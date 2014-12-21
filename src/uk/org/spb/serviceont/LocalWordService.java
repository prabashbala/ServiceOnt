package uk.org.spb.serviceont;

import android.app.Service;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class LocalWordService extends Service {
    private final IBinder mBinder = new MyBinder();
    // media player
    private MediaPlayer player;

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
	    Log.e("MUSIC SERVICE", "Error setting data source", e);
	}
    }

}