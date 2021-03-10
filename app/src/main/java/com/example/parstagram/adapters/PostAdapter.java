package com.example.parstagram.adapters;

import android.content.Context;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.parstagram.R;
import com.example.parstagram.models.Post;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {
    private Context  context;
    private List<Post> postList;

    public PostAdapter(@NonNull Context context, List<Post> posts) {
        this.postList = posts;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.post_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Post post = postList.get(position);
        holder.bind(post);
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView iv_imageView;
        TextView tv_username;
        TextView tv_description;
        TextView tv_dateCreated;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_description = itemView.findViewById(R.id.tv_post_description);
            iv_imageView = itemView.findViewById(R.id.iv_post_image);
            tv_username = itemView.findViewById(R.id.tv_post_username);
            tv_dateCreated = itemView.findViewById(R.id.tv_dateCreated);
        }

        public void bind(Post post) {
            tv_description.setText(post.getDescription());
            tv_username.setText(post.getUser().getUsername());

            if (post.getImage() != null) {
                Glide.with(context).load(post.getImage().getUrl()).into(iv_imageView);
            }

        }
    }
}
