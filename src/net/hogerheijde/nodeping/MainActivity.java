package net.hogerheijde.nodeping;

import java.util.logging.Logger;

import net.hogerheijde.nodeping.api.NodepingManager;
import net.hogerheijde.nodeping.views.ServerListLayout;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.widget.TextView;

public class MainActivity extends Activity {

	public final static Logger LOG = Logger.getLogger("MainActivity");
	public final static String EXTRA_MESSAGE = "net.hogerheijde.nodeping.MESSAGE";

	private NodepingManager manager;
	private ServerListLayout serverListLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);

		manager = new NodepingManager(this, sharedPrefs.getString("nodeping_api_key", ""));

		TextView tv = (TextView) findViewById(R.id.nodeping_api_key_text_view);
		tv.setText(sharedPrefs.getString("nodeping_api_key", "NOT SET YET"));
		
		serverListLayout  = (ServerListLayout) findViewById(R.id.server_list_layout);
		serverListLayout.setNodepingManager(manager);
//		serverListLayout.update();

		manager.update();
		
	}

	
//	@Override
//	public void onDraw() {
//		
//	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		menu.getItem(0).setIntent(new Intent(this, SettingsActivity.class));
		menu.getItem(1).setOnMenuItemClickListener(new UpdateClickListener());
		
		return true;
	}
	
	private class UpdateClickListener implements OnMenuItemClickListener {

		@Override
		public boolean onMenuItemClick(MenuItem item) {
			return manager.update();
		}
		
	}

}
