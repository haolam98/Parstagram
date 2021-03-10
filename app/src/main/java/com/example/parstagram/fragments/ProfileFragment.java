package com.example.parstagram.fragments;

import android.util.Log;

import com.example.parstagram.models.Post;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

public class ProfileFragment extends PostFragment{
    @Override
    protected void queryPost() {
        Log.i(TAG,"Querying Posts data....");
        // Specify which class to query
        ParseQuery<Post> query = ParseQuery.getQuery(Post.class);
        query.include(Post.KEY_USER); // include full info of user who made the Post
        query.whereEqualTo(Post.KEY_USER, ParseUser.getCurrentUser()); // just get User's post ONLY
        query.setLimit(20);
        query.addDescendingOrder(Post.KEY_DATE); // get most recent post
        query.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> posts, ParseException e) {
                //if e == null means the data is populated correctly
                if (e!=null)
                {
                    Log.e(TAG, "ERROR: Issue with getting posts", e);
                }
                else
                {
                    for (Post post: posts)
                    {
                        Log.i(TAG,"Post: "+ post.getDescription() +" -username:"+post.getUser().getUsername()+ " -dateCreated:");
                    }
                    Log.i(TAG,"Add to Post List: "+ posts.size());
                    allPosts.addAll(posts);
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }
}
