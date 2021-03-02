package com.example.parstagram;

import android.app.Application;

import com.example.parstagram.models.Post;
import com.parse.Parse;
import com.parse.ParseObject;
import com.parse.ParseUser;

public class ParseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        //register class before initialize
        ParseObject.registerSubclass(Post.class);
        //init Parse
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("r6yI3DCdi75pSWk8ucR5U4GVzc9CunG9ZIRWnaG1")
                .clientKey("pBwDCbt8VcEaQkRlcYLF7iDtOlCl2hdgcHYkpvAX")
                .server("https://parseapi.back4app.com")
                .build()
        );
    }
}
