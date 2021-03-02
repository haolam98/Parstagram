package com.example.parstagram;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.parstagram.models.Post;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.File;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";
    public static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 42;
    EditText et_description;
    Button bttn_takePic;
    Button bttn_submit;
    ImageView ivPreview;
    private ImageView iv_logout;
    private File photoFile;
    public String photoFileName = "photo.jpg";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_description = findViewById(R.id.et_description);
        bttn_submit = findViewById(R.id.bttn_submitPic);
        bttn_takePic = findViewById(R.id.bttn_takePic);
        ivPreview = (ImageView) findViewById(R.id.iv_photoCapture);
        iv_logout = findViewById(R.id.iv_logout);

        iv_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handle_logout();
            }
        });
        
       // queryPost();

    }

    private void queryPost() {
        Log.i(TAG,"Querying Posts data....");
        // Specify which class to query
        ParseQuery<Post> query = ParseQuery.getQuery(Post.class);
        query.include(Post.KEY_USER); // include full info of user who made the Post
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
                        Log.i(TAG,"Post: "+ post.getDescription() +" -username:"+post.getUser().getUsername());
                    }
                }
            }
        });
    }

    public void handle_submitPost(View view) {
        Log.i(TAG,"Submit button clicked! Handling new post...");
        String des = et_description.getText().toString();
        if (des.isEmpty())
        {
            Toast.makeText(this, "No description on your post. Please type something...",Toast.LENGTH_SHORT).show();
            return;
        }
        if (photoFile == null || ivPreview.getDrawable()== null)
        {
            Toast.makeText(this, "No image on your post...",Toast.LENGTH_SHORT).show();
            return;
        }
        ParseUser currentUser = ParseUser.getCurrentUser();
        save_submitPost(currentUser, des, photoFile);
    }

    private void save_submitPost(ParseUser currentUser, String description, File photoFile) {
        //Create  new post
        Post post = new Post();
        post.setDescription(description);
        post.setImage(new ParseFile(photoFile));
        post.setUser(currentUser);
        post.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e!=null)
                {
                    Log.e(TAG,"Error while saving post",e);
                }
                else
                {
                    Log.i(TAG,"Sucessfully saving the post!");
                    et_description.setText(""); // make sure we don't save the save post twice
                    ivPreview.setImageResource(0);
                }
            }
        });

    }

    public void handle_takePic(View view) {
        Log.i(TAG,"Camera Button Clicked....");
        launchCamera();
    }

    private void launchCamera() {
        Log.i(TAG,"Launching camera....");

        // create Intent to take a picture and return control to the calling application
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Create a File reference for future access
        photoFile = getPhotoFileUri(photoFileName);

        // wrap File object into a content provider
        // required for API >= 24
        // See https://guides.codepath.com/android/Sharing-Content-with-Intents#sharing-files-with-api-24-or-higher
        Uri fileProvider = FileProvider.getUriForFile(MainActivity.this, "com.codepath.fileprovider", photoFile);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileProvider);

        // If you call startActivityForResult() using an intent that no app can handle, your app will crash.
        // So as long as the result is not null, it's safe to use the intent.
        if (intent.resolveActivity(getPackageManager()) != null) {
            // Start the image capture intent to take photo
            startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
        }
    }
    // Returns the File for a photo stored on disk given the fileName
    public File getPhotoFileUri(String fileName) {
        Log.i(TAG,"Processing output photo....");
        // Get safe storage directory for photos
        // Use `getExternalFilesDir` on Context to access package-specific directories.
        // This way, we don't need to request external read/write runtime permissions.
        File mediaStorageDir = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), TAG);

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()){
            Log.d(TAG, "failed to create directory");
        }

        // Return the file target for the photo based on filename
        File file = new File(mediaStorageDir.getPath() + File.separator + fileName);

        return file;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        //get photo
        super.onActivityResult(requestCode, resultCode, data);
        Log.i(TAG,"OnActivityResult: Retrieving photo to app....");
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                // by this point we have the camera photo on disk
                Bitmap takenImage = BitmapFactory.decodeFile(photoFile.getAbsolutePath());
                // RESIZE BITMAP, see section below
                // Load the taken image into a preview
                ImageView ivPreview = (ImageView) findViewById(R.id.iv_photoCapture);
                ivPreview.setImageBitmap(takenImage);
            } else { // Result was a failure
                Toast.makeText(this, "Picture wasn't taken!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void handle_logout() {
        Log.i(TAG,"Logining user out....");
        ParseUser.logOut();
        ParseUser currentUser = ParseUser.getCurrentUser();
        if (currentUser==null)
        {
            Log.i(TAG,"Successfully logged out. Exit activity....");
        }
        finish();
    }
}