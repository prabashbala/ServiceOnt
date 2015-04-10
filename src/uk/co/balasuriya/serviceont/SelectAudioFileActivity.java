package uk.co.balasuriya.serviceont;

import java.util.ArrayList;

import uk.co.balasuriya.serviceont.util.ApplicationContext;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class SelectAudioFileActivity extends Activity {

    public static final String ALARM_DATA = "alarmdata";
    /** Private members of the class */
    private ImageView displayTime;
    private ListView listview;
    private TextView noAlarmBackgroundImage;


    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_main1);
	listview = (ListView) findViewById(R.id.audio_file_list);
	
	String[] values = new String[] { "Seth Piritha", "Sabbha papassa gatha", "Rathnamali gatha","Seevalie piritha"};
	
	final ArrayList<String> list = new ArrayList<String>();
	    for (int i = 0; i < values.length; ++i) {
	      list.add(values[i]);
	    }
	    final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.audiolistlayout, R.id.label,
		    list);
		Log.d("onCreate:", "alarmdata:" + " Adaptor:" + adapter.getCount());
		listview.setAdapter(adapter);
		
		listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

		    @Override
		    public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
			 final String item = (String) parent.getItemAtPosition(position);
			 Log.d("onItemClick:",item);      
			 Intent intent = new Intent(ApplicationContext.getAppContext(), TimePickerActivity.class);

			 intent.putExtra("id", item);

			 startActivity(intent);
		    }

		    });


	
    }

}