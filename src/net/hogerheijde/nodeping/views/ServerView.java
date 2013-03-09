package net.hogerheijde.nodeping.views;

import net.hogerheijde.nodeping.R;
import net.hogerheijde.nodeping.domain.Server;
import net.hogerheijde.nodeping.domain.ServerState;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ServerView extends RelativeLayout {

	private TextView serverName;
	private TextView serverState;
	
	private Server server;
	
	
	public ServerView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.init();
		this.initAttr(attrs);
	}

	public ServerView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.init();
		this.initAttr(attrs);
	}

	public ServerView(Context context) {
		super(context);
		this.init();
	}
	
	private void init() {
		LayoutInflater.from(getContext()).inflate(R.layout.server_view, this, true);
		serverName = (TextView) findViewById(R.id.server_name);
		serverState = (TextView) findViewById(R.id.server_state);
	}

	public void setServer(Server server) {
		this.server = server;
		
		serverName.setText(server.getName());
		serverState.setText(server.getState().toString());
		
		if(ServerState.FAIL.equals(server.getState())) {
			serverState.setTextColor(Color.RED);
		} else if (ServerState.PASS.equals(server.getState())) {
			// Darkish green
			serverState.setTextColor(Color.parseColor("#008800"));
		}

	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}
	
	
	// Helper functions
	
	private void initAttr(AttributeSet attrs){  
	    TypedArray a = getContext().obtainStyledAttributes(attrs,R.styleable.ServerView);
	    
	    //Use a
	    String server_name = a.getString(R.styleable.ServerView_server_name);
		Log.i("test", server_name);

	    //Don't forget this
	    a.recycle();
	}

}
