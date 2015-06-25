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
import android.widget.EditText;
import android.widget.Toast;

public class addPlaylist extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_playlist);

		final EditText name = (EditText) findViewById(R.id.editNewPlaylist);
		Button addP = (Button) findViewById(R.id.buttonNewPlaylist);

		addP.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {

				// check if name is valid
				if (name.getText().toString().length() > 0) {

					/**
					 * check if playlist file already exist if not create new
					 * playlist file make an entry to it with the first playlist
					 * name
					 * 
					 */
					File file = getBaseContext().getFileStreamPath(
							"playlists.txt");
					if (file.exists()) {
						// create an read the contents of file to a string
						// add new entry to string
						// write back the new string to file
						String content = "";
						BufferedReader reader = null;
						try {

							FileInputStream inputstream = openFileInput("playlists.txt");
							reader = new BufferedReader(new InputStreamReader(
									inputstream));
							String line = "";
							line = reader.readLine();
							while (line != null) {
								content = content + line+"\n";
								
								line = reader.readLine();
								
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

						// now write this content to playlists.txt
						BufferedWriter writer = null;
						try {
							FileOutputStream outputstream = openFileOutput(
									"playlists.txt", Context.MODE_PRIVATE);
							writer = new BufferedWriter(new OutputStreamWriter(
									outputstream));
							String str2 = name.getText().toString();
							content = content + str2+"\n";
							writer.write("");
							writer.write(content);
							//writer.close();
							//Toast.makeText( getApplicationContext(),content,Toast.LENGTH_LONG).show();

							// load the main activity
							// close this activity

							Intent i = new Intent(getApplicationContext(),
									MainActivity.class);
							addPlaylist.this.startActivity(i);
							addPlaylist.this.finish();

						} catch (Exception e) {

						} finally {
							try {
								writer.close();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}

					} else {
						// create file
						// make an entry to the file

						BufferedWriter writer = null;
						try {
							FileOutputStream outputstream = openFileOutput(
									"playlists.txt", Context.MODE_PRIVATE);
							writer = new BufferedWriter(new OutputStreamWriter(
									outputstream));
							String str2 = name.getText().toString();
							writer.write(str2+"\n");
							//writer.close();

							// load the main activity
							// close this activity

							Intent i = new Intent(getApplicationContext(),
									MainActivity.class);
							addPlaylist.this.startActivity(i);
							addPlaylist.this.finish();
							//Toast.makeText(getApplicationContext(),
								//	str2,
									//Toast.LENGTH_SHORT).show();

						} catch (Exception e) {

						} finally {
							try {
								writer.close();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}

				} else {
					Toast.makeText(getApplicationContext(),
							"Name Too Short \nPlease Try Another Name",
							Toast.LENGTH_SHORT).show();
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
