package uk.co.balasuriya.serviceont;

import java.util.ArrayList;
import java.util.List;

import uk.co.balasuriya.serviceont.util.ApplicationContext;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

public class SelectAudioFileActivity extends Activity {

    public static final String ALARM_DATA = "alarmdata";
    /** Private members of the class */
    private ListView listview;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_main1);
	listview = (ListView) findViewById(R.id.audio_file_list);

	String[] values = new String[] { "Seth Piritha", "Sabbha papassa gatha", "Rathnamali gatha", "Seevalie piritha" };

	final ArrayList<String> list = new ArrayList<String>();
	for (int i = 0; i < values.length; ++i) {
	    list.add(values[i]);
	}
	final ListViewArrayAdapter adapter = new ListViewArrayAdapter(this, R.layout.audiolistlayout,
		R.id.play_audio_file, list);
	Log.d("onCreate:", "alarmdata:" + " Adaptor:" + adapter.getCount());
	listview.setAdapter(adapter);

    }

    class ViewHolder {
	ImageButton button;
	TextView title;
    }

    class ListViewArrayAdapter extends ArrayAdapter<String> {
	
	List<String> audiolist;

	public ListViewArrayAdapter(Context context, int resource, int textViewResourceId, List<String> objects) {
	    super(context, resource, textViewResourceId, objects);
	    audiolist=objects;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
	    super.getView(position, convertView, parent);
	    View v = convertView;
	    ViewHolder viewHolder;

	    if (v == null) {
		LayoutInflater inflater = (LayoutInflater) ApplicationContext.getAppContext().getSystemService(
			Context.LAYOUT_INFLATER_SERVICE);
		v = inflater.inflate(R.layout.audiolistlayout, null);
		viewHolder = new ViewHolder();
		viewHolder.button = (ImageButton) v.findViewById(R.id.play_audio);
		viewHolder.title = (TextView) v.findViewById(R.id.play_audio_file);
		v.setTag(viewHolder);
	    } else {
		viewHolder = (ViewHolder) v.getTag();
	    }

	    if (viewHolder.title != null) {
		viewHolder.title.setText(audiolist.get(position));
		viewHolder.title.setTextColor(Color.BLACK);
	    }

	    viewHolder.button.setOnClickListener(new OnClickListener() {

		@Override
		public void onClick(View v) {
		    Intent service = new Intent(ApplicationContext.getAppContext(), BackgroundMusicService.class);
		    ApplicationContext.getAppContext().startService(service);
		    Log.d("onCreate:", "");
		}
	    });

	    v.setOnClickListener(new OnClickListener() {

		@Override
		public void onClick(View v) {
		    Intent intent = new Intent(ApplicationContext.getAppContext(), TimePickerActivity.class);
		    intent.putExtra("id", v.getId());
		    startActivity(intent);
		    Log.d("view onCreate:", "");
		}
	    });

	    return v;
	}

    }

}