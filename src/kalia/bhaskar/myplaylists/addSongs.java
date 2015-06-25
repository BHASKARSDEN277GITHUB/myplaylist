package kalia.bhaskar.myplaylists;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class addSongs extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_songs);

		Intent i = this.getIntent();
		final String pName = i.getStringExtra("pName");

		final SongsCheckListAdapter adapter = new SongsCheckListAdapter(
				getApplicationContext());
		ListView songsList = (ListView) findViewById(R.id.listAddSongs);

		songsList.setAdapter(adapter);

		Button done = (Button) findViewById(R.id.buttonDone);

		done.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// make entries to playlist file

				/**
				 * check if the file pName.txt exists if yes then add newly
				 * selected songs to the list else create a new file and add
				 * paths of selected songs to that list
				 */

				String fileName = pName + ".txt";

				File file = getBaseContext().getFileStreamPath(fileName);
				if (file.exists()) {

					String content = "";
					BufferedReader reader = null;

					try {

						FileInputStream inputstream = openFileInput(fileName);
						reader = new BufferedReader(new InputStreamReader(
								inputstream));
						String line = reader.readLine();
						while (line != null) {
							content = content + line + "\n";
							line = reader.readLine();
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

					// now append selected songs to the existing content
					int selected = 0;
					for (int i = 0; i < adapter.count; i++) {
						if (adapter.checked[i]) {
							content = content + adapter.pathList[i] + "\n";
							selected++;
						}
					}

					Toast.makeText(getApplicationContext(),
							selected + " Songs Added Successfully", Toast.LENGTH_LONG).show();

					// now open file for output
					BufferedWriter writer = null;
					try {
						FileOutputStream outputstream = openFileOutput(
								fileName, Context.MODE_PRIVATE);
						writer = new BufferedWriter(new OutputStreamWriter(
								outputstream));
						writer.write("");
						writer.write(content);

					} catch (Exception e) {

					} finally {
						try {
							writer.close();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
						
					//launch display songs activity
					Intent i = new Intent(getApplicationContext(), displaySongs.class);
					i.putExtra("pName", pName);
					startActivity(i);
					addSongs.this.finish();
					
				} else {

					BufferedWriter writer = null;
					try {
						FileOutputStream outputstream = openFileOutput(
								fileName, Context.MODE_PRIVATE);
						writer = new BufferedWriter(new OutputStreamWriter(
								outputstream));
						// String str2 = bunks.getText().toString();
						// get content list
						String content = "";
						int selected = 0;
						for (int i = 0; i < adapter.count; i++) {
							if (adapter.checked[i]) {
								content = content + adapter.pathList[i] + "\n";
								selected++;
							}
						}

						Toast.makeText(getApplicationContext(),
								selected + " Songs Added Successfully", Toast.LENGTH_LONG)
								.show();

						writer.write(content);
						// bunks.setText(str2+"done");

					} catch (Exception e) {

					} finally {
						try {
							writer.close();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					
					//launch display songs activity
					Intent i = new Intent(getApplicationContext(), displaySongs.class);
					i.putExtra("pName", pName);
					startActivity(i);
					addSongs.this.finish();
				}

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