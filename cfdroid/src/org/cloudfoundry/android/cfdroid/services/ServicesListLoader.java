package org.cloudfoundry.android.cfdroid.services;

import java.util.List;

import org.cloudfoundry.android.cfdroid.CloudFoundry;
import org.cloudfoundry.android.cfdroid.support.AsyncLoader;
import org.cloudfoundry.client.lib.CloudService;

import android.app.Activity;
import android.database.ContentObserver;

public class ServicesListLoader extends AsyncLoader<List<CloudService>>{

	private CloudFoundry client;
	
	private ContentObserver contentObserver = this.new ForceLoadContentObserver();
	
	private boolean force = true;
	
	public ServicesListLoader(Activity activity, CloudFoundry client) {
		super(activity);
		this.client = client;
		client.listenForServicesUpdates(contentObserver);
	}
	
	@Override
	protected void onAbandon() {
		client.stopListeningForServicesUpdates(contentObserver);
		super.onAbandon();
	}
	
	@Override
	public List<CloudService> loadInBackground() {
		List<CloudService> services = client.getServices(force);
		force = false;
		return services;
	}
	

}
