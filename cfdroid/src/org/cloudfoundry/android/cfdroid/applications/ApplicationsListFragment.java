package org.cloudfoundry.android.cfdroid.applications;

import java.util.List;

import org.cloudfoundry.android.cfdroid.Clients;
import org.cloudfoundry.android.cfdroid.R;
import org.cloudfoundry.android.cfdroid.support.ItemListAdapter;
import org.cloudfoundry.android.cfdroid.support.masterdetail.AbstractMasterPane;
import org.cloudfoundry.client.lib.CloudApplication;

import android.os.Bundle;
import android.support.v4.content.Loader;
import android.view.View;

import com.google.inject.Inject;

public class ApplicationsListFragment extends
		AbstractMasterPane<CloudApplication> {

	@Inject
	private Clients clients;

	@Override
	public Loader<List<CloudApplication>> onCreateLoader(int arg0, Bundle arg1) {
		return new ApplicationsListLoader(getActivity(), clients);
	}

	
	@Override
	public void onLoadFinished(Loader<List<CloudApplication>> loader,
			List<CloudApplication> items) {
		super.onLoadFinished(loader, items);
		communicationChannel().onNewData(items);
	}
	
	
	@Override
	protected ItemListAdapter<CloudApplication, ?> adapterFor(
			List<CloudApplication> items) {
		return new ItemListAdapter<CloudApplication, ApplicationView>(
				R.layout.application_list_item, getActivity()
						.getLayoutInflater(), items) {
			@Override
			protected ApplicationView createView(View view) {
				return new ApplicationView(view);
			}
		};
	}
}
