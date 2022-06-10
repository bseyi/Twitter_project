package com.codepath.apps.restclienttemplate.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

@Parcel
public class Tweet {
    public String body;
    public String createdAt;
    public User user;
    public String imageUrl;
    public String Retweet_count;
    public String Reply_count;
    public String Like_count;
    public String time;
    public long tweet_id;

    public Tweet(){}

    public static Tweet fromJson(JSONObject jsonObject) throws JSONException {
        Tweet tweet = new Tweet();
        tweet.body = jsonObject.getString("text");
        tweet.createdAt = jsonObject.getString("created_at");
        tweet.user = User.fromJson(jsonObject.getJSONObject("user"));
        tweet.Retweet_count = jsonObject.getString("retweet_count");
        tweet.Reply_count = jsonObject.getString("favorite_count");
        tweet.Like_count = jsonObject.getString("favorite_count");
        tweet.tweet_id = jsonObject.getLong("id");

        tweet.imageUrl = "";
        if(jsonObject.has("full_text")){
            tweet.body = jsonObject.getString("full_text");
        }
        else{
            tweet.body = jsonObject.getString("text");
        }
        tweet.imageUrl = getEntity(jsonObject.getJSONObject("entities"));

        return tweet;
    }

    public static String getEntity(JSONObject jsonObject) throws JSONException{
        JSONArray allMedia = jsonObject.has("media") ? jsonObject.getJSONArray("media"):null;
        String url = "";
        if (allMedia != null){
            url = allMedia.getJSONObject(0).getString("media_url_https");
        }
        return url;
    }

    public static List<Tweet> fromJsonArray(JSONArray jsonArray) throws JSONException {
        List<Tweet> tweets = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++){
            tweets.add(fromJson(jsonArray.getJSONObject(i)));
        }
        return tweets;
    }
}
