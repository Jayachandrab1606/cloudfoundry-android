package org.cloudfoundry.android.cfdroid.applications;

import java.util.List;

import org.cloudfoundry.android.cfdroid.CloudFoundry;
import org.cloudfoundry.android.cfdroid.support.AsyncLoader;
import org.cloudfoundry.client.lib.InstanceStats;

import android.app.Activity;
import android.database.ContentObserver;

public class InstanceStatsLoader extends AsyncLoader<List<InstanceStats>>{

	private CloudFoundry client;
	private ContentObserver observer = this.new ForceLoadContentObserver();
	private boolean force = true;
	
	private String appName;
	
	public InstanceStatsLoader(Activity activity, CloudFoundry client, String appName) {
		super(activity);
		this.client = client;
		this.appName = appName;
		client.listenForApplicationsUpdates(observer);
	}

	@Override
	protected void onAbandon() {
		client.stopListeningForApplicationUpdates(observer);
		super.onAbandon();
	}

	@Override
	public List<InstanceStats> loadInBackground() {
		return client.getApplicationStats(appName);
	}

}
