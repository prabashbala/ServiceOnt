package uk.co.balasuriya.serviceont;

import java.util.List;

import uk.co.balasuriya.serviceont.util.APKExpansionFileHandler;
import uk.co.balasuriya.serviceont.util.ApplicationContext;
import uk.co.balasuriya.serviceont.util.ZipResourceFile;
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

	ZipResourceFile expansionFile = APKExpansionFileHandler.getAPKExpansionFiles();
	List<String> mp3files = expansionFile.getFileNamesList();

	final ListViewArrayAdapter adapter = new ListViewArrayAdapter(this, R.layout.audiolistlayout,
		R.id.play_audio_file, mp3files);
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
	    audiolist = objects;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
	    super.getView(position, convertView, parent);
	    View v = convertView;
	    final ViewHolder viewHolder;

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
		    String text =viewHolder.title.getText().toString();
		    Log.d("SelectAudioFileActivity:button onclick",text);

		    if (v != null) {

			Intent service = new Intent(ApplicationContext.getAppContext(), PlayNowMusicService.class);
			service.putExtra("audiofileid", text);
			ApplicationContext.getAppContext().startService(service);

			Log.d("SelectAudioFileActivity:button onclick", text);
		    }
		}
	    });

	    v.setOnClickListener(new View.OnClickListener() {

		@Override
		public void onClick(View v) {

		    if (v != null) {

			String label = ((TextView) v.findViewById(R.id.play_audio_file)).getText().toString();

			Intent intent = new Intent(ApplicationContext.getAppContext(), TimePickerActivity.class);
			intent.putExtra("audiofileid", label);
			startActivity(intent);
			Log.d("view onClick: view id", ": " + label);
		    }

		}
	    });

	    return v;
	}

    }

}