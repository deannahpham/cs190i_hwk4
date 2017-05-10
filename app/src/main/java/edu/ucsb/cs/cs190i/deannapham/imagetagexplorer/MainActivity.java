package edu.ucsb.cs.cs190i.deannapham.imagetagexplorer;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import static edu.ucsb.cs.cs190i.deannapham.imagetagexplorer.R.id.image;
import static edu.ucsb.cs.cs190i.deannapham.imagetagexplorer.R.id.imageView;

public class MainActivity extends AppCompatActivity {

    public FloatingActionButton camera;
    public FloatingActionButton gallery;

    public final String APP_TAG = "MyCustomApp";
    public final static int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1034;
    public String photoFileName = "photo0000.jpg";

    private static final int PICK_IMAGE_REQUEST = 9876;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        camera = (FloatingActionButton)findViewById(R.id.camera_fab);
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "In Camera!", Toast.LENGTH_LONG).show();
                onCameraClick(v);
            }
        });

        gallery = (FloatingActionButton)findViewById(R.id.gallery_fab);
        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onGalleryClick(v);
            }
        });


    }

    //HELP FROM: http://www.cs.ucsb.edu/~holl/CS190I/handouts/slides_camera-17.pdf
    // https://github.com/codepath/android_guides/wiki/Accessing-the-Camera-and-Stored-Media
    //http://stackoverflow.com/questions/5030565/multiple-onactivityresult-for-1-activity


    public void onCameraClick(View view) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //intent.putExtra(MediaStore.EXTRA_OUTPUT, getPhotoFileUri(photoFileName)); // set the image file name
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
        }
    }

    public void onGalleryClick(View view){
        Toast.makeText(this, "In Gallery", Toast.LENGTH_SHORT).show();
        Intent galleryIntent = new Intent();
        galleryIntent.setType("image/*");
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(galleryIntent, PICK_IMAGE_REQUEST);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch(requestCode) {
            case CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE:
                if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
                    if (resultCode == RESULT_OK) {
                        Toast.makeText(this, "Picture was taken!", Toast.LENGTH_SHORT).show();
                        //Uri takenPhotoUri = getPhotoFileUri(photoFileName);
                        //Bitmap takenImage = BitmapFactory.decodeFile(takenPhotoUri.getPath());
                        Uri imageUri = data.getData();
                        InputStream inputStream;
                        try {
                            inputStream = getContentResolver().openInputStream(imageUri);
                            Bitmap image = BitmapFactory.decodeStream(inputStream);
                            final ImageView imageView = (ImageView)findViewById(R.id.imageView);
                            imageView.setImageBitmap(image);

                        } catch (IOException ex) {
                            System.out.println(ex);
                            Toast.makeText(this, "Unable to open image", Toast.LENGTH_LONG).show();
                        }
                    } else { // Result was a failure
                        Toast.makeText(this, "Picture wasn't taken!", Toast.LENGTH_SHORT).show();
                    }
                }
            break;
            case PICK_IMAGE_REQUEST:
                if(resultCode == RESULT_OK) {
                    // if we are here, everything processed okay

                    if (requestCode == PICK_IMAGE_REQUEST) {
                        // if we are here, we are hearing back from image gallery
                        Uri imageUri = data.getData(); // can i use this instead of getphotouri? still URI ?
                        InputStream inputStream;
                        try {
                            //address of image on SD card
                            inputStream = getContentResolver().openInputStream(imageUri);
                            Bitmap image = BitmapFactory.decodeStream(inputStream);
                            final ImageView imageView = (ImageView)findViewById(R.id.imageView);
                            imageView.setImageBitmap(image);

                        } catch (IOException ex) {
                            System.out.println(ex);
                            Toast.makeText(this, "Unable to open image", Toast.LENGTH_LONG).show();
                        }
                    }
                }
                break;

        }
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        ImageTagDatabaseHelper.GetInstance().close();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        // Show your dialog here (this is called right after onActivityResult)
    }
}


//    // Returns the Uri for a photo stored on disk given the fileName
//    public Uri getPhotoFileUri(String fileName) {
//        Toast.makeText(this, "INSIDE!", Toast.LENGTH_SHORT).show();
//
//        // Only continue if the SD Card is mounted
//        if (isExternalStorageAvailable()) {
//            // Get safe storage directory for photos
//            // Use `getExternalFilesDir` on Context to access package-specific directories.
//            // This way, we don't need to request external read/write runtime permissions.
//            File mediaStorageDir = new File(
//                    getExternalFilesDir(Environment.DIRECTORY_PICTURES), APP_TAG);
//
//            // Create the storage directory if it does not exist
//            if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()){
//                Log.d(APP_TAG, "failed to create directory");
//            }
//
//            // Return the file target for the photo based on filename
//            File file = new File(mediaStorageDir.getPath() + File.separator + fileName);
//
//            // wrap File object into a content provider
//            // required for API >= 24
//            // See https://guides.codepath.com/Sharing-Content-with-Intents#sharing-files-with-api-24-or-higher
//            return FileProvider.getUriForFile(MainActivity.this, "com.codepath.fileprovider", file);
//        }
//        return null;
//    }
//
//    // Returns true if external storage for photos is available
//    private boolean isExternalStorageAvailable() {
//        String state = Environment.getExternalStorageState();
//        return state.equals(Environment.MEDIA_MOUNTED);
//    }


//        ImageTagDatabaseHelper.Initialize(this);
//
//        final TextView textView = (TextView)findViewById(R.id.textView);
//        final ImageView imageView = (ImageView)findViewById(R.id.imageView);
//
//        TaggedImageRetriever.getNumImages(new TaggedImageRetriever.ImageNumResultListener() {
//            @Override
//            public void onImageNum(int num) {
//                textView.setText(textView.getText() + "\n\n" + num);
//            }
//        });
//
//        TaggedImageRetriever.getTaggedImageByIndex(0, new TaggedImageRetriever.TaggedImageResultListener() {
//            @Override
//            public void onTaggedImage(TaggedImageRetriever.TaggedImage image) {
//                if (image != null) {
//                    try (FileOutputStream stream = openFileOutput("Test.jpg", Context.MODE_PRIVATE)){
//                        image.image.compress(Bitmap.CompressFormat.JPEG, 100, stream);
//                        image.image.recycle();
//                    } catch (IOException e) {
//                    }
//                    Picasso.with(MainActivity.this).load(getFileStreamPath("Test.jpg")).resize(500,500).centerCrop().into(imageView);
//                    // imageView.setImageBitmap(image.image);
//                    StringBuilder tagList = new StringBuilder();
//                    for (String p : image.tags) {
//                        tagList.append(p + "\n");
//                    }
//                    textView.setText(textView.getText() + "\n\n" + tagList.toString());
//                }
//            }
//        });