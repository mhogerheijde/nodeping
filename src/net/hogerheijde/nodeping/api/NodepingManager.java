package net.hogerheijde.nodeping.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import net.hogerheijde.nodeping.domain.Check;
import net.hogerheijde.nodeping.domain.Error;
import net.hogerheijde.nodeping.domain.Server;
import net.hogerheijde.nodeping.domain.ServerState;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
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

			AsyncTask<String, Void, Map<String, Check>> result = new RetreiveChecksTask().execute("");
			
		} else {
			Log.d("test", "Update && !isOnline()");
			showErrorAlert("No connection!");
			return false;
		}
		return true;
	}

	//	private void setAuthenticationHeader() {
	//		Authenticator.setDefault(new Authenticator() {
	//			protected PasswordAuthentication getPasswordAuthentication() {
	//				return new PasswordAuthentication(apiKey, "".toCharArray());
	//			};
	//		});
	//	}

	private Error fetchChecks() throws FailedConnectingToApiException {
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

	class RetreiveChecksTask extends AsyncTask<String, Void, Map<String, Check>> {

		private Exception exception;

		protected Map<String, Check> doInBackground(String... urls) {

			HttpResponse response;
			try {
				URL url = new URL(API_ENDPOINT_CHECKS);
				DefaultHttpClient httpClient = new DefaultHttpClient();

				HttpGet pageGet = new HttpGet(url.toURI());

				// As per NodePings API documentations request: set api-key token as username, leave passwd blank
				String auth = apiKey + ":";
				String authorisationHeaderValue = "Basic " + Base64.encodeToString(auth.getBytes(), Base64.DEFAULT & Base64.NO_WRAP).trim();
				pageGet.addHeader("Authorization", authorisationHeaderValue);

				response = httpClient.execute(pageGet);

			} catch (URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				exception = e;
				return null;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				exception = e;
				return null;
			}


			InputStream in = null;
			if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				try {
					in = response.getEntity().getContent();
			
					BufferedReader buffer = new BufferedReader(new InputStreamReader(in));
					String total = buffer.readLine();
					String line = null;
					while((line = buffer.readLine()) != null) {
						total += line;
					}
					Log.d("json", total);
					
					Map<String, Check> checks = new ObjectMapper().readValue(total, new TypeReference<Map<String, Check>>() {});
					return checks;
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					exception = e;
					return null;
				}

			}

			// Error!
			try {
				Error error = (new ObjectMapper()).readValue(in, Error.class);
				exception = new NodePingApiException(error.getError());
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				exception = e;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				exception = e;
			}
			
			return null;

		}

		protected void onPostExecute(Map<String, Check> checks) {
			Log.d("test", "onPostExecute!!");
			if(exception != null) {
				showErrorAlert(exception.getMessage());
				return;
			}
			
			Iterator<Entry<String, Check>> iterator = checks.entrySet().iterator();
			while(iterator.hasNext()) {
				Entry<String, Check> next = iterator.next();
				Check currentCheck = next.getValue();
				Log.d("checks", currentCheck.getId());
				serverList.add(new Server(currentCheck.getLabel()));
				
			}
			callUpdateListeners();
		}
	}

}
