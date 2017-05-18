package com.adviser121.hanodale;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.tekinarslan.sample.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by Administrator on 6/8/2016.
 */
public class PdfActivity extends Activity {
    String path;
    String file_name;
    final private int REQUEST_CODE_ASK_PERMISSIONS = 118;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        Intent getintent = getIntent();
        path = getintent.getStringExtra("filepath");
        FrameLayout fraggment = (FrameLayout) findViewById(R.id.content_frame);

        if (path != null) {

          //   filetobyteconversion(path);

            PdfFragment fragment = new PdfFragment();
            Bundle args = new Bundle();
            args.putString("filepath", path);
            fragment.setArguments(args);
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
            /*


            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) +
                        ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {

                    // Should we show an explanation?
                    ActivityCompat.requestPermissions((Activity) getApplicationContext(),
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                    Manifest.permission.READ_EXTERNAL_STORAGE},
                            REQUEST_CODE_ASK_PERMISSIONS);
                    // file_position=position;
                } else {
                    PdfFragment fragment = new PdfFragment();
                    Bundle args = new Bundle();
                    args.putString("filepath", path);
                    fragment.setArguments(args);
                    FragmentManager fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
                }

            } else {
                PdfFragment fragment = new PdfFragment();
                Bundle args = new Bundle();
                args.putString("filepath", path);
                fragment.setArguments(args);
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
            }*/

        } else {
            Toast.makeText(getApplicationContext(), "Something Went Wrong", Toast.LENGTH_SHORT).show();
        }
    }


    String filetobyteconversion(String path) {

        String File_bytes = null;
        FileInputStream fileInputStream = null;

        File file = new File(path);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] buf = new byte[1024];

        try {
            fileInputStream = new FileInputStream(file);
            for (int readNum; (readNum = fileInputStream.read(buf)) != -1; ) {
                bos.write(buf, 0, readNum); //no doubt here is 0
            }

            byte[] bytes = bos.toByteArray();
            File_bytes = Base64.encodeToString(bytes, Base64.DEFAULT);

        } catch (FileNotFoundException e) {
            System.out.println("File Not Found.");

            e.printStackTrace();
        } catch (IOException e1) {
            System.out.println("Error Reading The File.");
            e1.printStackTrace();
        } finally {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return File_bytes;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.email:
                // write your code here

                if (path != null) {
                    try {
                        Intent shareIntent = new Intent(Intent.ACTION_SENDTO);
                        shareIntent.setType("application/pdf");
                        shareIntent.setData(Uri.parse("mailto:"));
                        shareIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{""});
                        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "");
                        shareIntent.putExtra(Intent.EXTRA_TEXT, "");
                        shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse("file://" + path));
                        if (shareIntent.resolveActivity(getPackageManager()) != null) {
                            startActivity(shareIntent);
                        }
                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(), "No Apps To Send PDF", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "You Don't Have PDF", Toast.LENGTH_SHORT).show();
                }
                return true;
            case R.id.prnt:

                if (path != null) {
                    file_name = path.substring(path.lastIndexOf("/") + 1);
                    Intent printIntent = new Intent(this, PrintDialogActivity.class);
                    printIntent.setDataAndType(Uri.parse("file://" + path), "application/pdf");
                    printIntent.putExtra("title", file_name);
                    startActivity(printIntent);
                } else {
                    Toast.makeText(getApplicationContext(), "You Don't Have PDF", Toast.LENGTH_SHORT).show();
                }
                return true;
            case R.id.close:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {

        if (path !=null) {
            File file = new File(path);
            if (file.exists()) {
                boolean deleted = file.delete();
            }
        }
        super.onBackPressed();
    }

}
