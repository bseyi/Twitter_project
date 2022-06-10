package com.codepath.apps.restclienttemplate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.codepath.apps.restclienttemplate.models.Tweet;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;

import org.json.JSONException;
import org.parceler.Parcels;

import okhttp3.Headers;

public class CommentActivity extends AppCompatActivity {

    EditText CommentReply;
    ImageView ProfilePic;
    TwitterClient client;
    Button replyBtn;
    Tweet tweet;




    public static final String TAG = "CommentActivity";
    public static final int MAX_TWEET_LENGTH = 140;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        client = TwitterApp.getRestClient(this);


        CommentReply = findViewById(R.id.comment_reply);
        replyBtn = findViewById(R.id.reply_button);
        tweet =(Tweet) Parcels.unwrap(getIntent().getParcelableExtra(Tweet.class.getSimpleName()));

        replyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tweetContent = CommentReply.getText().toString();
                if(tweetContent.isEmpty()){
                    Toast.makeText(CommentActivity.this, "Sorry your reply cannot be empty", Toast.LENGTH_LONG).show();
                    return;
                }
                if (tweetContent.length()>MAX_TWEET_LENGTH){
                    Toast.makeText(CommentActivity.this, "Sorry, your reply is too long ", Toast.LENGTH_LONG).show();
                    return;
                }
                client.replyToTweet(tweetContent, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Headers headers, JSON json) {
                        Intent i = new Intent(CommentActivity.this, TimelineActivity.class);
                        startActivity(i);
                    }

                    @Override
                    public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                        Toast.makeText(CommentActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                    }
                }, tweet.tweet_id);

            }
        });





    }


}
