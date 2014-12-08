package com.yahoo.apps.basictwitter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.content.Intent;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.yahoo.apps.basictwitter.models.Tweet;
import com.yahoo.apps.basictwitter.models.User;

public class TweetArrayAdapter extends ArrayAdapter<Tweet> {
	TextView tvTime, tvScreenName;
	public TweetArrayAdapter(Context context, List<Tweet> objects) {
		super(context, 0, objects);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Tweet tweet = getItem(position);
		
		View v;
		if (convertView == null) {
			LayoutInflater inflater = LayoutInflater.from(getContext());
			v = inflater.inflate(R.layout.tweet_item, parent, false);
		} else {
			v = convertView;
		}
		
		ImageView ivProfileImage = (ImageView)v.findViewById(R.id.ivProfileImage);
		TextView tvUserName = (TextView)v.findViewById(R.id.tvUserName);
		TextView tvBody = (TextView)v.findViewById(R.id.tvBody);
		tvTime = (TextView)v.findViewById(R.id.tvTime);
		
		ivProfileImage.setImageResource(android.R.color.transparent);
        tvScreenName = (TextView)v.findViewById(R.id.tvScreenName);
		ImageLoader imageLoader = ImageLoader.getInstance();
        final User user = tweet.getUser();
		// populate views with tweet data
		imageLoader.displayImage(tweet.getUser().getProfileImageUrl(), ivProfileImage);
        tvScreenName.setText("@"+user.getScreenName());
		tvUserName.setText(tweet.getUser().getScreenName());
		tvBody.setText(tweet.getBody());
		tvTime.setText(getRelativeTimeAgo(tweet.getCreatedAt()));
		
        ivProfileImage.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(getContext(),ProfileActivity.class);
				i.putExtra("user", user);
				getContext().startActivity(i);
			}
		});
		
		return v;
	}

    public static String getRelativeTimeAgo(String rawJsonDate) {
        String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
        SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
        sf.setLenient(true);

        String relativeDate = "";
        try {
            long dateMillis = sf.parse(rawJsonDate).getTime();
            relativeDate = DateUtils.getRelativeTimeSpanString(dateMillis,
                    System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();
            int index = relativeDate.indexOf("ago");
            if(index!=-1){
                return relativeDate.substring(0,index-1);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return relativeDate;
    }
}
