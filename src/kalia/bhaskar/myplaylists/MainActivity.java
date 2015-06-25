package kalia.bhaskar.myplaylists;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		String[] values;
		// check if file playlists exists
		File file = getBaseContext().getFileStreamPath("playlists.txt");

		if (file.exists()) {
			// read playlist name into array values

			BufferedReader reader = null;
			String content = "";
			int count = 0;
			try {

				FileInputStream inputstream = openFileInput("playlists.txt");
				reader = new BufferedReader(new InputStreamReader(inputstream));
				String line = "";
				line = reader.readLine();
				while (line != null) {
					
						content = content + line+"\n";
						line = reader.readLine();
						// count++;
					
				}

				//reader.close();

			} catch (Exception e) {

			} finally {
				try {
					reader.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			values = content.split("\n");
			//Toast.makeText(getApplicationContext(),content,Toast.LENGTH_LONG).show();
			ListView playlists = (ListView) findViewById(R.id.listPlaylists);
			ArrayAdapter<String> adapter_for_this_list = new ArrayAdapter<String>(
					this, android.R.layout.simple_list_item_1,
					android.R.id.text1, values);
			playlists.setAdapter(adapter_for_this_list);
			
			playlists.setOnItemClickListener(new OnItemClickListener(){
                @Override
                public void onItemClick(AdapterView<?>parent,View view,int position,long id){


                //launch activity displaysongs
                	Intent i = new Intent(getApplicationContext(),displaySongs.class);
                	i.putExtra("pName",(String)parent.getItemAtPosition(position));
                	startActivity(i);

                }
        });

		} else {
			// do nothing just display the add playlist button
			values = new String[1];
			values[0] = "No Playlist Added";
			ListView playlists = (ListView) findViewById(R.id.listPlaylists);
			ArrayAdapter<String> adapter_for_this_list = new ArrayAdapter<String>(
					this, android.R.layout.simple_list_item_1,
					android.R.id.text1, values);
			playlists.setAdapter(adapter_for_this_list);
		}

		Button addPlaylist = (Button) findViewById(R.id.buttonAddPlaylist);

		addPlaylist.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {

				// load activity add playlist
				Intent i = new Intent(getApplicationContext(),
						addPlaylist.class);
			startActivity(i);
				MainActivity.this.finish();

			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
