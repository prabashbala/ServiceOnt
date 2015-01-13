package uk.org.spb.serviceont;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

import uk.org.spb.serviceont.data.AlarmData;
import uk.org.spb.serviceont.util.ObjectSerializer;
import android.app.Activity;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TimePicker;

public class TimePickerActivity extends Activity {

    public static final String ALARM_DATA = "alarmdata";
    /** Private members of the class */
    private ImageView displayTime;
    private ImageView setAlarm;
    private ListView listview;

    private int pHour;
    private int pMinute;
    private boolean isalarmset = false;

    private ArrayList<AlarmData> alarmdata = new ArrayList<AlarmData>();
    /**
     * This integer will uniquely define the dialog to be used for displaying
     * time picker.
     */
    static final int TIME_DIALOG_ID = 0;

    /** Updates the time in the TextView */
    private void updateDisplay() {
	Log.d("TimePickerActivity:updateDisplay", "updating display");
	if (listview != null) {
	    try {
		alarmdata = getSavedTimeList();
	    } catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }
	    ArrayAdapter<String> adapter = new ArrayAdapter(this, R.layout.rowlayout, R.id.label, alarmdata);
	    listview.setAdapter(adapter);
	    Log.d("TimePickerActivity:updateDisplay", "display updated");
	}
    }

    /** Displays a notification when the time is updated */
    private void displayToast() {
	Log.d("TimePickerActivity:displayToast", "displaying tost message");
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
	displayTime = (ImageView) findViewById(R.id.alarm_add_alarm);
	displayTime.layout(150, 150, 500, 150);
	listview = (ListView) findViewById(R.id.alarms_list);

	try {
	    alarmdata = getSavedTimeList();
	} catch (IOException e) {
	    e.printStackTrace();
	}

	ArrayAdapter<String> adapter = new ArrayAdapter(this, R.layout.rowlayout, R.id.label, alarmdata);
	listview.setAdapter(adapter);

	/** Listener for click event of the button */
	displayTime.setOnClickListener(new View.OnClickListener() {
	    public void onClick(View v) {
		Log.d("TimePickerActivity:onCreate:onClick", "Showing time picker controll");
		showDialog(TIME_DIALOG_ID);
	    }
	});

	if (isalarmset) {
	    Log.d("TimePickerActivity:onCreate:isalarmset", "Previous alarm data could not be found");
	} else {
	    Log.d("TimePickerActivity:onCreate:isalarmset", "Previous alarm data found");
	    final Calendar cal = Calendar.getInstance();
	    pHour = cal.get(Calendar.HOUR_OF_DAY);
	    pMinute = cal.get(Calendar.MINUTE);
	}
	updateDisplay();
    }

    /** Create a new dialog for time picker */

    @Override
    protected Dialog onCreateDialog(int id) {
	Log.d("TimePickerActivity:onCreateDialog", "Creating new dialog for time picker");
	switch (id) {
	case TIME_DIALOG_ID:
	    return new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
		//int callCount = 0;

		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

		    if (view.isShown()) { // On second call -work around of a
					  // bug
			pHour = hourOfDay;
			pMinute = minute;
			alarmdata.add(new AlarmData(pHour, pMinute));
			saveTimeList(alarmdata);
			updateDisplay();
			displayToast();
			Log.d("TimePickerActivity:onTimeSet", "setting alarm data");
			// callCount=0;
		    }
		    // callCount++; // Incrementing call coun
		}

	    }, pHour, pMinute, false);
	}
	return null;
    }

    private void saveTimeList(ArrayList<AlarmData> alarmdata) {
	SharedPreferences settings = getSharedPreferences(ALARM_DATA, 0);
	SharedPreferences.Editor editor = settings.edit();
	try {
	    editor.putString("alarmobject", ObjectSerializer.serialize(alarmdata));
	    editor.commit();
	} catch (IOException e) {
	    e.printStackTrace();
	}
	Log.d("TimePickerActivity:saveTime", "Alarm data commited");
    }

    private ArrayList<AlarmData> getSavedTimeList() throws IOException {
	SharedPreferences prefs = getSharedPreferences(ALARM_DATA, Context.MODE_PRIVATE);
	return (ArrayList<AlarmData>) ObjectSerializer.deserialize(prefs.getString("alarmobject",
		ObjectSerializer.serialize(new ArrayList<AlarmData>())));
    }

}