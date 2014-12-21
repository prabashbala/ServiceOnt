package uk.org.spb.serviceont;


import android.app.ListActivity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends ListActivity {
  private LocalWordService s;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_main);
  }

  @Override
  protected void onResume() {
    super.onResume();
    Intent intent= new Intent(this, LocalWordService.class);
    bindService(intent, mConnection,
        Context.BIND_AUTO_CREATE);
  }

  @Override
  protected void onPause() {
    super.onPause();
    unbindService(mConnection);
  }

  private ServiceConnection mConnection = new ServiceConnection() {

    public void onServiceConnected(ComponentName className, 
        IBinder binder) {
      LocalWordService.MyBinder b = (LocalWordService.MyBinder) binder;
      s = b.getService();
      Toast.makeText(MainActivity.this, "Connected", Toast.LENGTH_SHORT)
          .show();
    }

    public void onServiceDisconnected(ComponentName className) {
      s = null;
    }
  };

  public void onClick(View view) {
    
  }
} 