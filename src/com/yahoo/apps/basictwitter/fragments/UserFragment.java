package com.yahoo.apps.basictwitter.fragments;

import org.json.JSONArray;

import android.os.Bundle;
import android.util.Log;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.yahoo.apps.basictwitter.TwitterApplication;
import com.yahoo.apps.basictwitter.TwitterClient;
import com.yahoo.apps.basictwitter.models.Tweet;


public class UserFragment extends TweetsListFragment {
	private TwitterClient client;
	private String userId;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		client = TwitterApplication.getRestClient();
		popUserTimeline();
	}

	public void popUserTimeline() {
		client.getUserTimeline(userId,new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONArray jsonArray) {
				aTweets.addAll(Tweet.fromJSONArray(jsonArray));
				lvTweets.onRefreshComplete();
			}

			@Override
			public void onFailure(Throwable e, String s) {
				Log.d("debug", e.toString());
				Log.d("debug", s);
			}
		});
	}
	
	public void setUserId(String id){
		userId = id;
	}
}
