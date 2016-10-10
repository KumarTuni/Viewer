package com.adviser121.hanodale;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.tekinarslan.sample.R;

/**
 * Created by Administrator on 6/8/2016.
 */
public class PdfActivity extends Activity {
    String path;
    String file_name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        Intent getintent=getIntent();
         path=getintent.getStringExtra("filepath");
        FrameLayout fraggment=(FrameLayout) findViewById(R.id.content_frame);

        if(path != null) {
            PdfFragment fragment = new PdfFragment();
            Bundle args = new Bundle();
            args.putString("filepath", path);
            fragment.setArguments(args);
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
        }
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

                onBackPressed();

               /* try {
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
                }catch (Exception e){
                    Toast.makeText(getApplicationContext(),"No Apps To Send PDF",Toast.LENGTH_SHORT).show();
                }
                return true;
            case R.id.prnt:

                file_name=path.substring(path.lastIndexOf("/")+1);
                Intent printIntent = new Intent(this, PrintDialogActivity.class);
                printIntent.setDataAndType(Uri.parse("file://" + path), "application/pdf");
                printIntent.putExtra("title",file_name);
                startActivity(printIntent);
                return true;*/

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }
}
