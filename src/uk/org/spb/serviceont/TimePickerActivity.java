package uk.org.spb.serviceont;

import java.io.IOException;
import java.util.ArrayList;

import uk.org.spb.serviceont.data.AlarmData;
import uk.org.spb.serviceont.util.ObjectSerializer;
import uk.org.spb.serviceont.util.SwipeDismissListViewTouchListener;
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
    private ListView listview;
    ArrayAdapter<AlarmData> adapter =null;

    private int pHour;
    private int pMinute;

    private ArrayList<AlarmData> alarmdata = new ArrayList<AlarmData>();
    /**
     * This integer will uniquely define the dialog to be used for displaying
     * time picker.
     */
    static final int TIME_DIALOG_ID = 0;

    /** Updates the time in the TextView */
    private void updateDisplay() {
	Log.d("TimePickerActivity:updateDisplay", "updating display");
	try {
	    alarmdata = getSavedTimeList();
	    adapter.clear();
	    adapter.addAll(alarmdata);
	    //final ArrayAdapter<AlarmData> adapter = new ArrayAdapter(this, R.layout.rowlayout, R.id.label, alarmdata);
	    listview.setAdapter(adapter);
	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	Log.d("TimePickerActivity:updateDisplay", "display updated");
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
	listview = (ListView) findViewById(R.id.alarms_list);
	displayTime = (ImageView) findViewById(R.id.alarm_add_alarm);

	try {
	    alarmdata = getSavedTimeList();
	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	/** Listener for click event of the button */
	displayTime.setOnClickListener(new View.OnClickListener() {
	    public void onClick(View v) {
		Log.d("TimePickerActivity:onCreate:onClick", "Showing time picker controll");
		showDialog(TIME_DIALOG_ID);
	    }
	});

	//final ArrayAdapter<AlarmData> adapter = new ArrayAdapter(this, R.layout.rowlayout, R.id.label, alarmdata);
	adapter = new ArrayAdapter<AlarmData>(this, R.layout.rowlayout, R.id.label, alarmdata);
	listview.setAdapter(adapter);
	SwipeDismissListViewTouchListener touchListener = new SwipeDismissListViewTouchListener(listview,
		new SwipeDismissListViewTouchListener.DismissCallbacks() {
		    public void onDismiss(ListView listView, int[] reverseSortedPositions) {
			Log.d("SwipeDismissListViewTouchListener:touchListener", "onDismiss");
			for (int position : reverseSortedPositions) {

			    AlarmData almdta = adapter.getItem(position);
			    alarmdata.remove(almdta);
			    saveTimeList(alarmdata);
			    adapter.clear();
			    adapter.addAll(alarmdata);
			   // adapter.remove(almdta);
			    Log.d("SwipeDismissListViewTouchListener:touchListener", "position:" + position + " Alarm:"
				    + almdta);
			}
			adapter.notifyDataSetChanged();
		    }

		    @Override
		    public boolean canDismiss(int position) {
			// TODO Auto-generated method stub
			Log.d("SwipeDismissListViewTouchListener:touchListener", "canDismiss position" + position+" Adapter Count"+adapter.getCount());
			return position <= adapter.getCount() - 1;
		    }
		});
	listview.setOnTouchListener(touchListener);
	listview.setOnScrollListener(touchListener.makeScrollListener());
    }

    /** Create a new dialog for time picker */

    @Override
    protected Dialog onCreateDialog(int id) {
	Log.d("TimePickerActivity:onCreateDialog", "Creating new dialog for time picker");
	switch (id) {
	case TIME_DIALOG_ID:
	    return new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
		// int callCount = 0;

		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

		    if (view.isShown()) { // On second call -work around of a
					  // bug
			pHour = hourOfDay;
			pMinute = minute;
			alarmdata.add(new AlarmData(pHour, pMinute));
			saveTimeList(alarmdata);
			updateDisplay();
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