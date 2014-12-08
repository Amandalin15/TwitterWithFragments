package com.yahoo.apps.basictwitter;

import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.widget.ImageView;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.yahoo.apps.basictwitter.fragments.UserFragment;
import com.yahoo.apps.basictwitter.models.User;

public class ProfileActivity extends FragmentActivity {
	UserFragment userFragment;
	User user;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);
		Intent i = getIntent();
	    user =(User) i.getSerializableExtra("user");
		if(user !=null)
			popUserInfo(user);
		else
			loadProfileInfo();
		
		loadFragment();
		
	}
	
	public void loadProfileInfo(){
		TwitterApplication.getRestClient().getInfo(new JsonHttpResponseHandler(){
			@Override
			public void onSuccess(JSONObject json) {
				User u = User.fromJSON(json);
				setTitle("@"+u.getScreenName());
				popUserInfo(u);
			}
		});
	}
	
	public void loadFragment(){
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		if (userFragment == null)
			userFragment = new UserFragment();
		if(user!=null){
			String s = Long.toString(user.getUid()); // .toString(user.getUid());
			userFragment.setUserId(s);
		}
		ft.replace(R.id.userFragment, userFragment);
		ft.commit();
	}
	
	public void popUserInfo(User u){
		ImageView ivProfile = (ImageView) findViewById(R.id.ivUserProfile);
		TextView tvTagline  = (TextView)findViewById(R.id.tvUserTagline);
		TextView tvName  = (TextView)findViewById(R.id.tvUserName);
		TextView tvFollower  = (TextView)findViewById(R.id.tvUserFollower);
		TextView tvFollowing  = (TextView)findViewById(R.id.tvUserFollowing);
		ImageLoader.getInstance().displayImage(u.getProfileImageUrl(), ivProfile);
		tvName.setText(u.getName());
		tvTagline.setText(u.getDescription());
		tvFollower.setText(u.getFollowers() +" Followers");
		tvFollowing.setText(u.getFollowing().toString() +" Following");
	}
}
