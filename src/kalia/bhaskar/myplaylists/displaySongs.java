package kalia.bhaskar.myplaylists;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class displaySongs extends Activity {

	private MediaPlayer mMediaPlayer;
	private String[] songs = null; // to store songs names
	private String[] path = null; // to store songs path
	private int count = 0; // to get total songs count

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.display_songs);

		mMediaPlayer = new MediaPlayer();
		Intent i = this.getIntent();
		final String pName = i.getStringExtra("pName");

		TextView pText = (TextView) findViewById(R.id.textViewDisplaySongs);
		pText.setText(pName);

		

		/**
		 * check if file pName.txt exists if exists read songs path , parse to
		 * get names and dispaly in list else display "No Songs Added Yet"
		 */

		String fileName = pName + ".txt";
		File file = getBaseContext().getFileStreamPath(fileName);
		if (file.exists()) {

			BufferedReader reader = null;
			try {

				FileInputStream inputstream = openFileInput(fileName);
				reader = new BufferedReader(new InputStreamReader(inputstream));
				String line = "";
				line = reader.readLine();
				String content = "";
				while (line != null) {
					// get total count
					content = content + line + "\n";
					count++;
					line = reader.readLine();
				}

				path = new String[count];
				songs = new String[count];
				path = content.split("\n");

				// parsing names from paths
				for (int j = 0; j < count; j++) {
					String[] splitArray = path[j].split("/");
					songs[j] = splitArray[splitArray.length - 1];
				}

			} catch (Exception e) {

			} finally {
				try {
					reader.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			ListView songsList = (ListView) findViewById(R.id.listSongs);
			ArrayAdapter<String> adapter_for_this_list = new ArrayAdapter<String>(
					this, android.R.layout.simple_list_item_1,
					android.R.id.text1, songs);
			songsList.setAdapter(adapter_for_this_list);

			songsList.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
/*
					//play songs here 
					try {
						playSong(path[position]);
					} catch (IllegalArgumentException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalStateException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
*/
					Intent i = new Intent(getApplicationContext(),songPlayer.class);
					i.putExtra("filename", songs[position]);
					i.putExtra("filepath", path[position]);
					startActivity(i);
					//Toast.makeText(getApplicationContext(),songs[position]+"\n"+path[position], Toast.LENGTH_SHORT).show();
				}
			});

		} else {
			songs = new String[1];
			songs[0] = "No Songs Added Yet";
			ListView songsList = (ListView) findViewById(R.id.listSongs);
			ArrayAdapter<String> adapter_for_this_list = new ArrayAdapter<String>(
					this, android.R.layout.simple_list_item_1,
					android.R.id.text1, songs);
			songsList.setAdapter(adapter_for_this_list);
		}

		Button addSongs = (Button) findViewById(R.id.buttonAddSongs);

		addSongs.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// launch addSongs activity

				Intent i = new Intent(getApplicationContext(), addSongs.class);
				i.putExtra("pName", pName);
				startActivity(i);
				displaySongs.this.finish();

			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	private void playSong(String path) throws IllegalArgumentException,
			IllegalStateException, IOException {

		mMediaPlayer.reset();
		mMediaPlayer.setDataSource(path);
		// mMediaPlayer.setLooping(true);
		mMediaPlayer.prepare();
		mMediaPlayer.start();
	}

}
