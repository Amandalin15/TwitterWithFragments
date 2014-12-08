package com.yahoo.apps.basictwitter;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.yahoo.apps.basictwitter.fragments.HomeTimelineFragment;
import com.yahoo.apps.basictwitter.fragments.MentionsTimelineFragment;
import com.yahoo.apps.basictwitter.listeners.FragmentTabListener;

public class TimelineActivity extends FragmentActivity {
	// private TweetsListFragment fragmentTweetsList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timeline);
		// fragmentTweetsList = (TweetsListFragment)getSupportFragmentManager().findFragmentById(R.id.fragment_timeline);
		// populateTimeline();
		setTitle("Home");
		setupTabs();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.home, menu);
		return true;
	}
	
	public void onCompose(MenuItem mi) {
		Intent intent = new Intent(this, ComposeActivity.class);
		startActivityForResult(intent, 1);
	}

	public void onProfile(MenuItem mi) {
		Intent intent = new Intent(this, ProfileActivity.class);
		startActivity(intent);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == 1 && resultCode == RESULT_OK) {
			HomeTimelineFragment fragment=(HomeTimelineFragment)getSupportFragmentManager().findFragmentByTag("home");
			fragment.getATweets().clear();
			fragment.populateTimeline();
		}
	}

	private void setupTabs() {
		ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionBar.setDisplayShowTitleEnabled(true);

		// actionBar.setStackedBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.gray)));
		
		Tab tab1 = actionBar
				.newTab()
				.setText("Home")
				.setIcon(R.drawable.ic_home)
				.setTag("HomeLineFragment")
				.setTabListener(
						new FragmentTabListener<HomeTimelineFragment>(
								R.id.flContainer, this, "home",
								HomeTimelineFragment.class));
		

		actionBar.addTab(tab1);
		actionBar.selectTab(tab1);

		Tab tab2 = actionBar
				.newTab()
				.setText("Mentions")
				.setIcon(R.drawable.ic_mentions)
				.setTag("MentionsTimelineFragment")
				.setTabListener(
						new FragmentTabListener<MentionsTimelineFragment>(
								R.id.flContainer, this, "mentions",
								MentionsTimelineFragment.class));

		actionBar.addTab(tab2);
	}
}
