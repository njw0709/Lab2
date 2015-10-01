package com.example.jong.imagesaver;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;


import com.example.jong.imagesaver.db.Dbsetup;
import com.example.jong.imagesaver.db.FeedReaderDbHelper;

import java.util.ArrayList;


public class Image_query extends Fragment {
    private WebView webView;
    private EditText editText;
    public static ArrayList<String> listurl = new ArrayList<>();
    private int pointer;
    private OnFragmentInteractionListener mListener;
    private int dbposition=1;
    FeedReaderDbHelper mDbHelper = MainActivity.mDbHelper;
    SQLiteDatabase writabledb = mDbHelper.getWritableDatabase();

    //Setting up for writable database

       public static Image_query newInstance(String param1, String param2) {
        Image_query fragment = new Image_query();
        Bundle args = new Bundle();
         return fragment;
    }

    public Image_query() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
     }



    public void create_button(View v, String button, final FeedReaderDbHelper mDbHelper, final SQLiteDatabase writabledb){
        switch(button){
            case("save"):{
                Button Save;
                Save = (Button) v.findViewById(R.id.save_button);
                Save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String imgurl = listurl.get(pointer);
                        mDbHelper.writeToDatabase(imgurl, dbposition, writabledb);
                        Log.d("dbposition", String.valueOf(dbposition));
                        dbposition++;
                        int i = mDbHelper.CountItemsdb(writabledb);
                        Log.d("Saving image", "URL:"+imgurl+String.valueOf(i));

                    }
                });
            }
            case("previous"):{
                Button Previous;
                Previous = (Button) v.findViewById(R.id.prev_image);
                Previous.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(listurl.size()>0){
                            pointer--;
                        }
                        else{
                            pointer=0;
                        }
                        reload_webview();
                    }
                });
            }
            case("next"):{
                Button Next;
                Next = (Button) v.findViewById(R.id.next_image);
                Next.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        pointer++;
                        reload_webview();
                    }
                });
            }
            case("search"):{
                ImageButton Search;
                Search = (ImageButton) v.findViewById(R.id.search_button);
                Search.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String search = editText.getText().toString();
                        Log.d("search keyword", search);
                        google_search(search);
                    }
                });
            }
        }

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View wholeview = inflater.inflate(R.layout.fragment_image_query, container, false);
        create_button(wholeview,"save",mDbHelper,writabledb);
        create_button(wholeview,"previous",mDbHelper,writabledb);
        create_button(wholeview,"next",mDbHelper,writabledb);
        create_button(wholeview,"search",mDbHelper,writabledb);

        editText = (EditText)wholeview.findViewById(R.id.editText);
        create_webview(wholeview);
        return wholeview;
    }

//    // TODO: Rename method, update argument and hook method into UI event
//    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//            mListener.onFragmentInteraction(uri);
//        }
//    }

    public void create_webview(View view){
        webView = (WebView)view.findViewById(R.id.webview);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        String link;
        link = "http://www.usnews.com/img/college-photo_10842..jpg";
        webView.loadUrl(link);
    }
    public void reload_webview(){
        String link;
        if (listurl.size()>0) {
            link = listurl.get(pointer % listurl.size());
            Log.d("pointer", String.valueOf(pointer));
        }
        else{
            link ="https://encrypted-tbn2.gstatic.com/images?q=tbn:ANd9GcSxcHH0hEVgrJwH9hpE247tfKc9eEXX6fNL21J1e1ybRyO08TExoGoQtL4";
            Log.e("reload_webview failed", "listurl size zero");
        }
        webView.loadUrl(link);
    }



    public void google_search(String search) {
        pointer = 1;
        listurl.clear();
        Google_search gsearch = new Google_search(getActivity());
        gsearch.searchWithCallback(new SuccessCallback() {
            @Override
            public void callback(ArrayList<String> urlList) {
                if (urlList.size()>0){
                    listurl=urlList;
                    Log.d("Success",urlList.toString());
                    reload_webview();
                }
                else{
                    Log.d("Failure",urlList.toString());
                }

            }
        },search);

    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}

