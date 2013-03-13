package net.hogerheijde.nodeping.views;

import net.hogerheijde.nodeping.api.NodepingManager;
import net.hogerheijde.nodeping.api.NodepingUpdateListener;
import net.hogerheijde.nodeping.domain.Server;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.ScrollView;

public class ServerListLayout extends ScrollView {

	private NodepingManager manager;
	private LinearLayout serverListLL;
	
	public ServerListLayout(Context context) {
		super(context);
		this.init();
	}

	public ServerListLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.init();
		initAttr(attrs);
	}
	
//	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public ServerListLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.init();
		initAttr(attrs);
	}

	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}
	
	
	// Helper functions
	private void init() {
		serverListLL = new LinearLayout(getContext());
		serverListLL.setOrientation(LinearLayout.VERTICAL);
		addView(serverListLL, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
	}
	
	private void initAttr(AttributeSet attrs){  
//	    TypedArray a = getContext().obtainStyledAttributes(attrs,R.styleable.ServerView);

	    //Don't forget this
//	    a.recycle();
	}

	
	public void setNodepingManager(NodepingManager manager) {
		this.manager = manager;
		this.manager.addNodepingUpdateListener(new UpdateListener());
	}

	public void update() {
		serverListLL.removeAllViews();
		for (Server server : manager.getServerList()) {
			CheckView view = new CheckView(getContext());
			view.setServer(server);
			serverListLL.addView(view);
		}
	}
	
	public class UpdateListener implements NodepingUpdateListener {
		
		@Override
		public void onUpdate() {
			update();
		}
		
	}
	
}
