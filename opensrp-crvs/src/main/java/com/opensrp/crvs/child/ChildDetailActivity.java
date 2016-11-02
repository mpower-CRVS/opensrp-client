package com.opensrp.crvs.child;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Html;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.opensrp.crvs.R;

import org.ei.opensrp.Context;
import org.ei.opensrp.commonregistry.AllCommonsRepository;
import org.ei.opensrp.commonregistry.CommonPersonObject;
import org.ei.opensrp.commonregistry.CommonPersonObjectClient;
import org.ei.opensrp.domain.Alert;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import static org.ei.opensrp.util.StringUtil.humanize;

/**
 * Created by raihan on 5/11/15.
 */
public class ChildDetailActivity extends Activity {

    //image retrieving
    private static final String TAG = "ImageGridFragment";
    private static final String IMAGE_CACHE_DIR = "thumbs";

    private static int mImageThumbSize;
    private static int mImageThumbSpacing;





    //image retrieving

    public static CommonPersonObjectClient ChildClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Context context = Context.getInstance();
        setContentView(R.layout.child_detail_activity);
        TextView name = (TextView) findViewById(R.id.name_english);
        TextView nameinbengali = (TextView) findViewById(R.id.name_in_bengali);
        TextView dob = (TextView) findViewById(R.id.dob);
        TextView nid = (TextView) findViewById(R.id.nid);
        TextView nationality = (TextView) findViewById(R.id.nationality);
        TextView placeofbirth = (TextView) findViewById(R.id.placeofbirth);
        TextView presentaddress = (TextView) findViewById(R.id.presentaddress);
        TextView permanentaddress = (TextView) findViewById(R.id.permanentaddress);

        TextView father_name = (TextView) findViewById(R.id.father_name_english);
        TextView father_name_bengali = (TextView) findViewById(R.id.father_name_bengali);
        TextView father_dob = (TextView) findViewById(R.id.father_dob);
        TextView father_nid = (TextView) findViewById(R.id.father_nid);
        TextView mother_name = (TextView) findViewById(R.id.mother_name_english);
        TextView mother_name_bengali = (TextView) findViewById(R.id.mother_name_bengali);
        TextView mother_dob = (TextView) findViewById(R.id.mother_dob);
        TextView mother_nid = (TextView) findViewById(R.id.mother_nid);
        final ImageView childpic = (ImageView)findViewById(R.id.householdprofileview);
        childpic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent(childpic);
            }
        });
        if ((ChildClient.getDetails().get("gender") != null ? ChildClient.getDetails().get("gender") : "").equalsIgnoreCase("Female")) {
            childpic.setImageResource(R.drawable.child_girl_infant);
//                newborn_or_fp.setText("Family Planning");
        } else {
            childpic.setImageResource(R.drawable.child_boy_infant);
//                newborn_or_fp.setVisibility(View.INVISIBLE);
        }
        try {
            if (ChildClient.getDetails().get("profilepic") != null) {
                ChildDetailActivity.setImagetoHolder(this, ChildClient.getDetails().get("profilepic"), childpic, R.drawable.child_boy_infant);
            }
        }catch (Exception e){

        }



        ImageButton back = (ImageButton) findViewById(org.ei.opensrp.R.id.btn_back_to_home);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        name.setText(humanize((ChildClient.getColumnmaps().get("name_Fname") != null ? ChildClient.getColumnmaps().get("name_Fname") : "").replace("+", "_")));
        nameinbengali.setText(humanize((ChildClient.getDetails().get("name_bengali") != null ? ChildClient.getDetails().get("name_bengali") : "").replace("+", "_")));
        dob.setText(((ChildClient.getColumnmaps().get("child_dob") != null ? ChildClient.getColumnmaps().get("child_dob") : "").replace("+", "_")));
        nid.setText(((ChildClient.getDetails().get("child_nid") != null ? ChildClient.getDetails().get("child_nid") : "").replace("+", "_")));
        nationality.setText(((ChildClient.getDetails().get("nationality") != null ? ChildClient.getDetails().get("nationality") : "").replace("+", "_")));
        placeofbirth.setText(humanize((ChildClient.getDetails().get("place_of_birth") != null ? ChildClient.getDetails().get("place_of_birth") : "").replace("+", "_")));
        presentaddress.setText(humanize((ChildClient.getDetails().get("present_address") != null ? ChildClient.getDetails().get("present_address") : "").replace("+", "_")));
        permanentaddress.setText(humanize((ChildClient.getDetails().get("permanent_address") != null ? ChildClient.getDetails().get("permanent_address") : "").replace("+", "_")));

        father_name.setText(humanize((ChildClient.getColumnmaps().get("father_name_english") != null ? ChildClient.getColumnmaps().get("father_name_english") : "").replace("+", "_")));
        father_name_bengali.setText(humanize((ChildClient.getDetails().get("father_name_bengali") != null ? ChildClient.getDetails().get("father_name_bengali") : "").replace("+", "_")));
        father_dob.setText(((ChildClient.getDetails().get("father_dob") != null ? ChildClient.getDetails().get("father_dob") : "").replace("+", "_")));
        father_nid.setText(((ChildClient.getDetails().get("father_nid") != null ? ChildClient.getDetails().get("father_nid") : "").replace("+", "_")));
        mother_name.setText(humanize((ChildClient.getColumnmaps().get("mother_name_english") != null ? ChildClient.getColumnmaps().get("mother_name_english") : "").replace("+", "_")));
        mother_name_bengali.setText(humanize((ChildClient.getDetails().get("mother_name_bengali") != null ? ChildClient.getDetails().get("mother_name_bengali") : "").replace("+", "_")));
        mother_dob.setText(((ChildClient.getDetails().get("mother_dob") != null ? ChildClient.getDetails().get("mother_dob") : "").replace("+", "_")));
        mother_nid.setText(((ChildClient.getDetails().get("mother_nid") != null ? ChildClient.getDetails().get("mother_nid") : "").replace("+", "_")));









    }

    private Long age(CommonPersonObjectClient ancclient) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date edd_date = format.parse(ancclient.getDetails().get("FWBNFDOB")!=null?ancclient.getDetails().get("FWBNFDOB"):"");
            Calendar thatDay = Calendar.getInstance();
            thatDay.setTime(edd_date);

            Calendar today = Calendar.getInstance();

            long diff = today.getTimeInMillis() - thatDay.getTimeInMillis();

            long days = diff / (24 * 60 * 60 * 1000);

            return days;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }

    }

    String mCurrentPhotoPath;

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = "file:" + image.getAbsolutePath();
        return image;
    }
    static final int REQUEST_TAKE_PHOTO = 1;
   static ImageView mImageView;
    static File currentfile;
    static String bindobject;
    static String entityid;
    private void dispatchTakePictureIntent(ImageView imageView) {
        mImageView = imageView;
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File

            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                currentfile = photoFile;
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                        Uri.fromFile(photoFile));
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
//            Bundle extras = data.getExtras();
//            String imageBitmap = (String) extras.get(MediaStore.EXTRA_OUTPUT);
//            Toast.makeText(this,imageBitmap,Toast.LENGTH_LONG).show();
            HashMap <String,String> details = new HashMap<String,String>();
            details.put("profilepic",currentfile.getAbsolutePath());
            saveimagereference(bindobject,entityid,details);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
            Bitmap bitmap = BitmapFactory.decodeFile(currentfile.getPath(), options);
            mImageView.setImageBitmap(bitmap);
        }
    }
    public void saveimagereference(String bindobject,String entityid,Map<String,String> details){
        Context.getInstance().allCommonsRepositoryobjects(bindobject).mergeDetails(entityid,details);
//                Elcoclient.entityId();
//        Toast.makeText(this,entityid,Toast.LENGTH_LONG).show();
    }
    public static void setImagetoHolder(Activity activity,String file, ImageView view, int placeholder){
        mImageThumbSize = 300;
        mImageThumbSpacing = Context.getInstance().applicationContext().getResources().getDimensionPixelSize(R.dimen.image_thumbnail_spacing);


//        ImageCache.ImageCacheParams cacheParams =
//                new ImageCache.ImageCacheParams(activity, IMAGE_CACHE_DIR);
//             cacheParams.setMemCacheSizePercent(0.50f); // Set memory cache to 25% of app memory
//        mImageFetcher = new ImageFetcher(activity, mImageThumbSize);
//        mImageFetcher.setLoadingImage(placeholder);
//        mImageFetcher.addImageCache(activity.getFragmentManager(), cacheParams);
////        Toast.makeText(activity,file,Toast.LENGTH_LONG).show();
//        mImageFetcher.loadImage("file:///"+file,view);

//        Uri.parse(new File("/sdcard/cats.jpg")






//        BitmapFactory.Options options = new BitmapFactory.Options();
//        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
//        Bitmap bitmap = BitmapFactory.decodeFile(file, options);
//        view.setImageBitmap(bitmap);
    }
}
