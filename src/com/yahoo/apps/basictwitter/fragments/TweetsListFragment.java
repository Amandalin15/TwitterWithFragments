package com.yahoo.apps.basictwitter.fragments;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yahoo.apps.basictwitter.R;
import com.yahoo.apps.basictwitter.TweetArrayAdapter;
import com.yahoo.apps.basictwitter.models.Tweet;

import eu.erikw.PullToRefreshListView;

public class TweetsListFragment extends Fragment {
	private ArrayList<Tweet> tweets;
	TweetArrayAdapter aTweets;
	PullToRefreshListView lvTweets;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// Non-view initialization
		tweets = new ArrayList<Tweet>();
		aTweets = new TweetArrayAdapter(getActivity(), tweets);
	}

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_tweets_list, container,false);

		lvTweets = (PullToRefreshListView)v.findViewById(R.id.lvTweets);
		lvTweets.setAdapter(aTweets);
		// return the layout
		return v;
	}
	
	public void addAll(ArrayList<Tweet> tweets) {
		aTweets.addAll(tweets);
	}

	public TweetArrayAdapter getATweets(){
		return aTweets;
	}
	
}
