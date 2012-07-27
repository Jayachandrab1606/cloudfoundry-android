package org.cloudfoundry.android.cfdroid.applications;

import java.util.List;

import org.cloudfoundry.android.cfdroid.CloudFoundry;
import org.cloudfoundry.android.cfdroid.support.AsyncLoader;
import org.cloudfoundry.client.lib.CloudApplication;

import android.app.Activity;
import android.database.ContentObserver;

public class ApplicationsListLoader extends AsyncLoader<List<CloudApplication>>{

	private CloudFoundry client;
	private ContentObserver observer = this.new ForceLoadContentObserver();
	private boolean force = true;
	
	public ApplicationsListLoader(Activity activity, CloudFoundry client) {
		super(activity);
		this.client = client;
		client.listenForApplicationsUpdates(observer);
	}
	
	@Override
	protected void onAbandon() {
		client.stopListeningForApplicationUpdates(observer);
		super.onAbandon();
	}

	@Override
	public List<CloudApplication> loadInBackground() {
		List<CloudApplication> applications = client.getApplications(force);
		force = false;
		return applications;
	}
	
}
