package kalia.bhaskar.myplaylists;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

public class SongsCheckListAdapter extends BaseAdapter{

	//get data source
	public String[] songsList ;
	public String[] pathList ;
	public int count;
	Context context ;
	public boolean[] checked ;
	
	private SongsManager manager ;
	
	public SongsCheckListAdapter(Context context){
		this.context=context;
		this.getDataSource();
		checked = new boolean[count];
		for(int i=0;i<count;i++) {
			checked[i]=false;
		}
	}
	
	public void getDataSource() {
		manager = new SongsManager(context);
		manager.getLists();
		songsList=manager.songsList;
		pathList=manager.pathList;
		count=songsList.length;
	}
	
	@Override
	public int getCount() {
		
		return this.count;
	}

	@Override
	public Object getItem(int arg0) {
		
		return songsList[arg0];
	}

	@Override
	public long getItemId(int arg0) {
		
		return arg0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		
		if(arg1==null) {
			LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			arg1 = inflater.inflate(R.layout.custom_list_view,arg2,false);
		}
		//this.getDataSource();
		TextView songsHeader = (TextView)arg1.findViewById(R.id.textViewHeaderAddSong);
		songsHeader.setText(songsList[arg0]);
		
		CheckBox c = (CheckBox)arg1.findViewById(R.id.checkBoxAddSong);
		
		
		final int pos = arg0;
		c.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View view) {
				
				if(checked[pos]==false)  {
				checked[pos]=true;
				}else {
					checked[pos]=false;
				}
				
			}
		});
		
		c.setChecked(checked[arg0]);
		
		//c.setFocusable(false);
		//c.setFocusableInTouchMode(false);
		return arg1;
	}
	
	

}
