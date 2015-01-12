package uk.org.spb.serviceont;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.app.Activity;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class TimePickerActivity extends Activity {

    public static final String ALARM_DATA = "alarmdata";
    /** Private members of the class */
    private ImageView displayTime;
    private ImageView setAlarm;
    private ListView listview;

    private int pHour;
    private int pMinute;
    private boolean isalarmset = false;
    
    private List<AlarmData> alarmdata = new ArrayList<AlarmData>();
    /**
     * This integer will uniquely define the dialog to be used for displaying
     * time picker.
     */
    static final int TIME_DIALOG_ID = 0;

    /** Callback received when the user "picks" a time in the dialog */
    private TimePickerDialog.OnTimeSetListener mTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
	public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
	    pHour = hourOfDay;
	    pMinute = minute;
	    alarmdata.add(new AlarmData(pHour,pMinute));
	    updateDisplay();
	    displayToast();
	    Log.d("TimePickerActivity:onTimeSet", "setting alarm data");
	}
    };

    /** Updates the time in the TextView */
    private void updateDisplay() {
	Log.d("TimePickerActivity:updateDisplay", "updating display");
	//displayTime.setText(new StringBuilder().append(pad(pHour)).append(":").append(pad(pMinute)));
    }

    /** Displays a notification when the time is updated */
    private void displayToast() {
	Log.d("TimePickerActivity:displayToast", "displaying tost message");
	/*Toast.makeText(this, new StringBuilder().append("Time choosen is ").append(displayTime.getText()),
		Toast.LENGTH_SHORT).show();
*/
    }

    /** Add padding to numbers less than ten */
    private static String pad(int c) {
	if (c >= 10)
	    return String.valueOf(c);
	else
	    return "0" + String.valueOf(c);
    }

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_main);
	getSavedTime();

	/** Capture our View elements */
	displayTime = (ImageView) findViewById(R.id.alarm_add_alarm);
	displayTime.layout(150, 150, 500, 150);
	//setAlarm = (ImageView) findViewById(R.id.action_icon);
	getSavedTime();
	listview = (ListView) findViewById(R.id.alarms_list);
	String[] values = new String[] { pHour+":"+pMinute, 
                "Elemento 2",
                "Elemento 3",
                "Elemento 4", 
                "Elemento 5", 
                "Elemento 6", 
                "Elemento 7", 
                "Elemento 8" 
               };
	final ArrayList<String> list = new ArrayList<String>();
	    for (int i = 0; i < values.length; ++i) {
	      list.add(values[i]);
	    }
	    
	ArrayAdapter<String> adapter = new ArrayAdapter(this,
	          R.layout.rowlayout,R.id.label, list);
	listview.setAdapter(adapter); 
	
	
	/** Listener for click event of the button */
	displayTime.setOnClickListener(new View.OnClickListener() {
	    public void onClick(View v) {
		Log.d("TimePickerActivity:onCreate:onClick", "Showing time picker controll");
		showDialog(TIME_DIALOG_ID);
	    }
	});

	/** Listener for click event of the button */
	/*setAlarm.setOnClickListener(new View.OnClickListener() {
	    public void onClick(View v) {
		Log.d("TimePickerActivity:onCreate:setOnClickListener", "setting alarm data");
		saveTime(pHour, pMinute);
		Log.d("TimePickerActivity", "setOnClickListener");
		AlarmShedulerAction shedulealarm = new AlarmShedulerAction(getApplicationContext());
		shedulealarm.setAlarm();
	    }
	});
*/
	if (isalarmset) {
	    /** Get the current time */
	    Log.d("TimePickerActivity:onCreate:isalarmset", "Previous alarm data could not be found");
	} else {
	    /** Get the current time */
	    Log.d("TimePickerActivity:onCreate:isalarmset", "Previous alarm data found");
	    final Calendar cal = Calendar.getInstance();
	    pHour = cal.get(Calendar.HOUR_OF_DAY);
	    pMinute = cal.get(Calendar.MINUTE);
	}

	/** Display the current time in the TextView */
	updateDisplay();
    }

    /** Create a new dialog for time picker */

    @Override
    protected Dialog onCreateDialog(int id) {
	Log.d("TimePickerActivity:onCreateDialog", "Creating new dialog for time picker");
	switch (id) {
	case TIME_DIALOG_ID:
	    return new TimePickerDialog(this, mTimeSetListener, pHour, pMinute, false);
	}
	return null;
    }

    private void saveTime(int hour, int minute) {
	SharedPreferences settings = getSharedPreferences(ALARM_DATA, 0);
	SharedPreferences.Editor editor = settings.edit();
	editor.putInt("hourvalue", hour);
	editor.putInt("minutevalue", minute);
	editor.putBoolean("alarmenabled", true);
	// Commit the edits!
	editor.commit();
	Log.d("TimePickerActivity:saveTime", "Alarm data commited");
    }

    private void getSavedTime() {

	SharedPreferences prefs = getSharedPreferences(ALARM_DATA, Context.MODE_PRIVATE);
	pHour = prefs.getInt("hourvalue", 1);
	pMinute = prefs.getInt("minutevalue", 1);
	isalarmset = prefs.getBoolean("alarmenabled", false);
	Log.d("TimePickerActivity:getSavedTime", "Read alarm data: Alarm enabled :" + isalarmset + " hour: " + pHour
		+ " minute: " + pMinute);
    }
    
    class AlarmData{
	
	private String id;
	private int iHour;
	private int iMinute;
	
	public AlarmData(int iHour, int iMinute) {
	    super();
	    this.iHour = iHour;
	    this.iMinute = iMinute;
	}
	public String getId() {
	    return id;
	}
	public void setId(String id) {
	    this.id = id;
	}
	public int getiHour() {
	    return iHour;
	}
	public void setiHour(int iHour) {
	    this.iHour = iHour;
	}
	public int getiMinute() {
	    return iMinute;
	}
	public void setiMinute(int iMinute) {
	    this.iMinute = iMinute;
	}
	
    }
}