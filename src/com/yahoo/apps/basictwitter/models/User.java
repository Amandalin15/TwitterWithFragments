package com.yahoo.apps.basictwitter.models;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

public class User implements Serializable {
	
	private static final long serialVersionUID = 6842329406957944149L;
	private String name;
	private long uid;
	private String screenName;
	private String profileImageUrl;
    private Long followers;
	private Long following;
	private String description;

	// User.fromJSON(...)
	public static User fromJSON(JSONObject jsonObject) {
		User u = new User();
		try {
			u.name = jsonObject.getString("name");
			u.uid = jsonObject.getLong("id");
			u.screenName = jsonObject.getString("screen_name");
			u.profileImageUrl = jsonObject.getString("profile_image_url");
            u.followers=jsonObject.getLong("followers_count");
            u.following=jsonObject.getLong("friends_count");
            u.description=jsonObject.getString("description");
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
		return u;
	}

	public String getName() {
		return name;
	}

	public long getUid() {
		return uid;
	}

	public String getScreenName() {
		return screenName;
	}

	public String getProfileImageUrl() {
		return profileImageUrl;
	}

    public Long getFollowers() {
  		return followers;
  	}

  	public Long getFollowing() {
  		return following;
  	}

	public String getDescription() {
		return description;
	}
}
