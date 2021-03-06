package com.example.parstagram.models;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

import java.text.SimpleDateFormat;
import java.util.Date;

@ParseClassName("Post") // same name entity with the Parse Dashboard
public class Post extends ParseObject {

    //column name in 'Post' table on Parse Dashboard
    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY_IMAGE = "image";
    public static final String KEY_USER = "user";
    public static final String KEY_DATE = "createdAt";

    public  String getDescription() {
        return getString(KEY_DESCRIPTION);
    }


    public ParseFile getImage() {
        return getParseFile(KEY_IMAGE);
    }

    public ParseUser getUser() {
        return getParseUser(KEY_USER);
    }

    public void setDescription(String description)
    {
        put(KEY_DESCRIPTION,description);
    }
    public void setImage(ParseFile image)
    {
        put(KEY_IMAGE,image);
    }
    public void setUser(ParseUser user)
    {
        put(KEY_USER,user);
    }


}
