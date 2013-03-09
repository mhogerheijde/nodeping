package net.hogerheijde.nodeping.api;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.PasswordAuthentication;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.hogerheijde.nodeping.api.dto.Checks;
import net.hogerheijde.nodeping.domain.Server;
import net.hogerheijde.nodeping.domain.ServerState;

import org.json.JSONObject;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;

import com.fasterxml.jackson.databind.ObjectMapper;

public class NodepingManager {

	private String apiKey;
	private List<Server> serverList;
	private final Context context;
	private List<NodepingUpdateListener> updateListeners;

	private static final String API_ENDPOINT_CHECKS = "https://api.nodeping.com/api/1/checks/";

	public NodepingManager(Context context, String apiKey) {
		this.context = context;
		this.apiKey = apiKey;
		serverList = new ArrayList<Server>();
		updateListeners = new ArrayList<NodepingUpdateListener>();
		
		setAuthenticationHeader();
		
		Log.d("test", "NodePing manager created");
	}

	public void addNodepingUpdateListener(NodepingUpdateListener listener) {
		updateListeners.add(listener);
	}

	public boolean update() {
		serverList.clear();
		if (isOnline()) {
			// fetch data
			//randomListf();
			Log.d("test", "Update && isOnline()");

			JSONObject checks = new RetreiveChecksTask().doInBackground("");
//			Log.i("test", checks.toString());
//			Log.i("test", "Amount of checks found: " + checks.getChecks().size());
//			showErrorAlert("Checks: " + checks.getChecks().size());
			callUpdateListeners();
		} else {
			Log.d("test", "Update && !isOnline()");
			showErrorAlert("No connection!");
			return false;
		}
		return true;
	}

	private void setAuthenticationHeader() {
		Authenticator.setDefault(new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(apiKey, "".toCharArray());
			};
		});
	}

	private Checks fetchChecks() throws FailedConnectingToApiException {
		return null;
	}

	public boolean isOnline() {
		ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
		return (networkInfo != null && networkInfo.isConnected());
	}  


	private void randomList() {
		for(int i = 0; i < 20; i++) {
			Server server = new Server("ServerViewXOXOX " + i);

			int pick = new Random().nextInt(ServerState.values().length);
			server.setState(ServerState.values()[pick]);

			serverList.add(server);
		}
	}

	private void showErrorAlert(String message) {
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

		// set title
		alertDialogBuilder.setTitle("Error");

		// set dialog message
		alertDialogBuilder
		.setMessage(message)
		.setCancelable(true)
		.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog,int id) {
				// if this button is clicked, just close
				// the dialog box and do nothing
				dialog.cancel();
			}
		});

		// create alert dialog
		AlertDialog alertDialog = alertDialogBuilder.create();

		// show it
		alertDialog.show();
	}

	private void callUpdateListeners() {
		for (NodepingUpdateListener listener : updateListeners) {
			listener.onUpdate();
		}
	}

	public List<Server> getServerList() {
		return serverList;
	}


	public class FailedConnectingToApiException extends Exception {
		private static final long serialVersionUID = -5289991351488901377L;

		public FailedConnectingToApiException() {
			super("Coudn't connect to the NodePing API");
		}
		public FailedConnectingToApiException(Throwable cause) {
			super("Coudn't connect to the NodePing API", cause);
		}
	}
	
	class RetreiveChecksTask extends AsyncTask<String, Void, JSONObject> {

		private Exception exception;

		protected JSONObject doInBackground(String... urls) {
			try {
				URL url = new URL(API_ENDPOINT_CHECKS);
				HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
				InputStream in = new BufferedInputStream(urlConnection.getInputStream());
				
				BufferedReader r = new BufferedReader(new InputStreamReader(in));
				
				String x = r.readLine();
				String total = "";

				while(x!= null){
					total += x;
					x = r.readLine();
				}
				
				Log.d("test", total);
				return (new ObjectMapper()).readValue(total, JSONObject.class);
			} catch (Exception e) {
				Log.e("test", e.toString());
				this.exception = e;
				return null;
			}
		}

		protected void onPostExecute(JSONObject checks) {
			Log.d("test", "onPostExecute!!");
			// TODO: check this.exception 
			// TODO: do something with the feed
		}
	}
	
}
