package com.yahoo.apps.basictwitter.fragments;

import org.json.JSONArray;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.yahoo.apps.basictwitter.EndlessScrollListener;
import com.yahoo.apps.basictwitter.TwitterApplication;
import com.yahoo.apps.basictwitter.TwitterClient;
import com.yahoo.apps.basictwitter.models.Tweet;

import eu.erikw.PullToRefreshListView;

public class HomeTimelineFragment extends TweetsListFragment {
	private TwitterClient client;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		client = TwitterApplication.getRestClient();
		populateTimeline();
	}

	public void populateTimeline() {
		client.getHomeTimeline(new JsonHttpResponseHandler(){
			@Override
			public void onSuccess(JSONArray json) {
				aTweets.addAll(Tweet.fromJSONArray(json));
				lvTweets.onRefreshComplete();
			}
			
			@Override
			public void onFailure(Throwable e, String s) {
				Log.d("debug", e.toString());
				Log.d("debug", s.toString());
			}
		});
	}

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View v =super.onCreateView(inflater, container, savedInstanceState);
		lvTweets.setOnRefreshListener(new PullToRefreshListView.OnRefreshListener() {
			@Override
			public void onRefresh() {
				aTweets.clear();
				populateTimeline();
			}
		});
		
		lvTweets.setOnScrollListener(new EndlessScrollListener() {
			@Override
			public void onLoadMore(int page, int totalItemsCount) {
				if (totalItemsCount >= 2) {
					Log.d("total", totalItemsCount + "");
					getMoreData(String.valueOf(aTweets.getItem(
							totalItemsCount - 2).getUid()));
				}
			}
		});

		return v;
	}

	public void getMoreData(String maxId) {
		client.getMoreData(maxId, new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONArray jsonArray) {
				aTweets.addAll(Tweet.fromJSONArray(jsonArray));
			}

			@Override
			public void onFailure(Throwable e, String s) {
				Log.d("debug", e.toString());
				Log.d("debug", s);
			}
		});
	}
}
