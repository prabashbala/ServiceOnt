package uk.co.balasuriya.serviceont;

import java.util.ArrayList;
import java.util.Date;

import uk.co.balasuriya.serviceont.data.AlarmData;
import uk.co.balasuriya.serviceont.util.AlarmDataHandler;
import uk.co.balasuriya.serviceont.util.SwipeDismissListViewTouchListener;
import android.app.Activity;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;

public class TimePickerActivity extends Activity {

    public static final String ALARM_DATA = "alarmdata";
    /** Private members of the class */
    private ImageView displayTime;
    private ListView listview;
    private TextView noAlarmBackgroundImage;

    private int pHour;
    private int pMinute;

    /**
     * This integer will uniquely define the dialog to be used for displaying
     * time picker.
     */
    static final int TIME_DIALOG_ID = 0;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_main);
	listview = (ListView) findViewById(R.id.alarms_list);
	displayTime = (ImageView) findViewById(R.id.alarm_add_alarm);
	noAlarmBackgroundImage = (TextView) findViewById(R.id.alarms_empty_view);

	ArrayList<AlarmData> ialarmdata = getSavedTimeList();
	Log.d("TimePickerActivity:onCreate:onClick", "alarmdata udated: " + ialarmdata.size());

	if (ialarmdata.size() > 0) {
	    showNoAlarmBackground(false);
	}

	/** Listener for click event of the button */
	displayTime.setOnClickListener(new View.OnClickListener() {
	    public void onClick(View v) {
		Log.d("TimePickerActivity:onCreate:onClick", "Showing time picker controll");
		showDialog(TIME_DIALOG_ID);
	    }
	});

	final ArrayAdapter<AlarmData> adapter = new ArrayAdapter<AlarmData>(this, R.layout.rowlayout, R.id.label,
		ialarmdata);
	Log.d("SwipeDismissListViewTouchListener:", "alarmdata:" + ialarmdata + " Adaptor:" + adapter.getCount());
	listview.setAdapter(adapter);
	SwipeDismissListViewTouchListener touchListener = new SwipeDismissListViewTouchListener(listview,
		new SwipeDismissListViewTouchListener.DismissCallbacks() {

		    public void onDismiss(ListView listView, int[] reverseSortedPositions) {
			Log.d("SwipeDismissListViewTouchListener:touchListener:", "onDismiss");
			for (int position : reverseSortedPositions) {
			    adjustAlarmData(true, (AlarmData) ((ArrayAdapter) listview.getAdapter()).getItem(position));
			    Log.d("SwipeDismissListView:TouchListener: removing", "alarm position: " + position);
			}
			Log.d("SwipeDismissListView:TouchListener: updating view: ", " Adaptor: " + adapter.getCount());
		    }

		    @Override
		    public boolean canDismiss(int position) {
			// TODO Auto-generated method stub
			Log.d("SwipeDismissListViewTouchListener:touchListener", "canDismiss position: " + position
				+ " Adapter Count" + adapter.getCount());
			return position <= adapter.getCount() - 1;
			// return true;
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

		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

		    if (view.isShown()) { // On second call -work around of a
					  // bug
			pHour = hourOfDay;
			pMinute = minute;
			adjustAlarmData(false, new AlarmData(new Date().getTime(), hourOfDay, minute));
			Log.d("TimePickerActivity:TimePickerDialog:onTimeSet", "setting alarm data");
		    }
		}

	    }, pHour, pMinute, false);
	}
	return null;
    }

    /**
     * 
     * @param isRemoval
     *            if it is to remove alarm time pass true. else false.
     * @param AlarmData
     *            : alarm data to be added or removed
     */
    private void adjustAlarmData(boolean isRemoval, AlarmData alarmdata) {
	Log.d("SwipeDismissListView:adjustAlarmData:", " fuction is Removal: " + isRemoval);
	ArrayAdapter iadapter = (ArrayAdapter) listview.getAdapter();
	ArrayList<AlarmData> ialarmdata = getSavedTimeList();
	if (isRemoval) {
	    iadapter.remove(alarmdata);
	    ialarmdata.remove(alarmdata);
	    removeScheduledAlarm(alarmdata);

	    Log.d("SwipeDismissListView:adjustAlarmData: Remove", "Alarm: " + alarmdata + " from alarmdata size: "
		    + ialarmdata.size());
	} else {
	    ialarmdata.add(alarmdata);
	    iadapter.add(alarmdata);
	    scheduleNewAlarm(alarmdata);
	    Log.d("TimePickerActivity:adjustAlarmData: Add", "Alarm:" + ialarmdata.size());
	}

	saveTimeList(ialarmdata);
	if(iadapter.getCount()>0){
	    showNoAlarmBackground(false);
	}else{
	    showNoAlarmBackground(true);
	}
	iadapter.notifyDataSetChanged();
	// trigerAlarmScheduler();
    }

    /**
     * Save alarm selection data to Shared preference
     * 
     * @param aldta
     */
    private void saveTimeList(ArrayList<AlarmData> aldta) {
	AlarmDataHandler.saveTimeList(aldta);
    }

    /**
     * get the saved alarm data from the shared preference
     * 
     * @return
     */
    private ArrayList<AlarmData> getSavedTimeList() {
	return AlarmDataHandler.getSavedTimeList();
    }

    /**
     * finally set the alarm background trigger service
     */
    private void scheduleNewAlarm(AlarmData alarm) {
	Log.d("TimePickerActivity:scheduleNewAlarm", "adding single alarm");
	AlarmShedulerAction shedulealarm = new AlarmShedulerAction();
	shedulealarm.addSingleAlarm(alarm);
    }

    /**
     * finally set the alarm background trigger service
     */
    private void removeScheduledAlarm(AlarmData alarm) {
	Log.d("TimePickerActivity:removeScheduledAlarm", "remove single alarm");
	AlarmShedulerAction shedulealarm = new AlarmShedulerAction();
	shedulealarm.removeSingleAlarm(alarm);
    }

    private void showNoAlarmBackground(boolean isshow) {
	if (isshow)
	    noAlarmBackgroundImage.setVisibility(View.VISIBLE);
	else
	    noAlarmBackgroundImage.setVisibility(View.INVISIBLE);
    }

}