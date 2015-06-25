package kalia.bhaskar.myplaylists;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;

public class SongsManager {
	public String[] songsList ;
	public String[] pathList ;
	Context context;
	
	public  SongsManager(Context context) {
		this.context=context;
	}
	
	public  void getLists() {
		//set the pathList and songsList in this method
		 final Cursor mCursor = context.getContentResolver().query(
		            MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
		            new String[] { MediaStore.Audio.Media.DISPLAY_NAME, MediaStore.Audio.Media.DATA }, null, null,
		            "LOWER(" + MediaStore.Audio.Media.TITLE + ") ASC");
		 int count = mCursor.getCount();
		 int i=0;
		 this.songsList = new String[count];
		 this.pathList = new String[count];
		 if (mCursor.moveToFirst()) {
		        do {
		            songsList[i] = mCursor.getString(mCursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME));
		            pathList[i] = mCursor.getString(mCursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA));
		            i++;
		        } while (mCursor.moveToNext());
		 } 
		 
		 mCursor.close();
		 
	}

}
