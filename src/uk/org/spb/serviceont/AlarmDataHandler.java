package uk.org.spb.serviceont;

import java.io.IOException;
import java.util.ArrayList;

import uk.org.spb.serviceont.data.AlarmData;
import uk.org.spb.serviceont.util.ApplicationContext;
import uk.org.spb.serviceont.util.ObjectSerializer;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class AlarmDataHandler {
    
    private static final String ALARM_DATA = "alarmdata";
    private static SharedPreferences prefs = ApplicationContext.getAppContext().getSharedPreferences(ALARM_DATA, Context.MODE_PRIVATE);

    /**
     * Save alarm selection data to Shared preference
     * 
     * @param aldta
     */
    private static void saveTimeList(ArrayList<AlarmData> aldta) {
	SharedPreferences.Editor editor = prefs.edit();
	try {
	    Log.d("TimePickerActivity:saveTime", "Saving Alarm data size: " + aldta.size() + " Items: " + aldta);
	    editor.putString("alarmobject", ObjectSerializer.serialize(aldta));
	    editor.commit();
	} catch (IOException e) {
	    Log.e("TimePickerActivity:saveTime", "Error occured while saving alarm data:" + e.getMessage());
	}

    }

    /**
     * get the saved alarm data from the shared preference
     * 
     * @return
     */
    private static ArrayList<AlarmData> getSavedTimeList() {
	ArrayList<AlarmData> alarmlist = null;
	try {
	    Log.d("TimePickerActivity:saveTime", "Reading Alarm data");
	    alarmlist = (ArrayList<AlarmData>) ObjectSerializer.deserialize(prefs.getString("alarmobject",
		    ObjectSerializer.serialize(new ArrayList<AlarmData>())));
	} catch (IOException e) {
	    Log.e("TimePickerActivity:saveTime", "Error occured while reading alarm data:" + e.getMessage());
	}
	return alarmlist;
    }

}
