package com.example.jong.imagesaver;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.jong.imagesaver.db.FeedReaderDbHelper;

public class MainActivity extends AppCompatActivity implements Image_query.OnFragmentInteractionListener, saved_images.OnFragmentInteractionListener {

    static FeedReaderDbHelper mDbHelper;
    Fragment Im_qu;
    Fragment saved_im;


    public void replaceFrag(Fragment fr, String tag){
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.Frag_layout, fr, tag);
        ft.addToBackStack(null);
        ft.commit();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDbHelper = new FeedReaderDbHelper(this);
        Im_qu = new Image_query();
        saved_im = new saved_images();
        setContentView(R.layout.activity_main);
        replaceFrag(Im_qu,"@string/Image_query_tag");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        int id = item.getItemId();

        switch (id){

            case R.id.action_settings: {
                return true;
            }
            case R.id.to_saved_image: {
                Fragment fr = (Fragment)getFragmentManager().findFragmentByTag("@string/Image_query_tag");
                if (fr!=null && fr.isVisible()) {
                    replaceFrag(saved_im, "@string/Saved_image_tag");
                }
                else {
                    replaceFrag(Im_qu, "@string/Image_query_tag");
                }
                return true;
            }

            default:
                return super.onOptionsItemSelected(item);
        }

        //noinspection SimplifiableIfStatement
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
