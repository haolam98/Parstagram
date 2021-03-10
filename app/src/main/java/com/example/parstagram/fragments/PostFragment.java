package com.example.parstagram.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.parstagram.R;
import com.example.parstagram.adapters.PostAdapter;
import com.example.parstagram.models.Post;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;


public class PostFragment extends Fragment {

    private RecyclerView recyclerView;
    public static final String TAG = "PostFragment";
    protected List<Post> allPosts;
    protected PostAdapter adapter;
    public PostFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_post, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.rvPost);
        allPosts = new ArrayList<>();
        adapter = new PostAdapter(getContext(),allPosts);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        queryPost();


    }
    protected void queryPost() {
        Log.i(TAG,"Querying Posts data....");
        // Specify which class to query
        ParseQuery<Post> query = ParseQuery.getQuery(Post.class);
        query.include(Post.KEY_USER); // include full info of user who made the Post
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
                        Log.i(TAG,"Post: "+ post.getDescription() +" -username:"+post.getUser().getUsername() );
                    }
                    Log.i(TAG,"Add to Post List: "+ posts.size());
                    allPosts.addAll(posts);
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }

}