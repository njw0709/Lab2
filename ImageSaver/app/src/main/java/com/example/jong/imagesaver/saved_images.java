package com.example.jong.imagesaver;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.jong.imagesaver.db.Dbsetup;
import com.example.jong.imagesaver.db.FeedReaderDbHelper;

import java.sql.SQLException;
import java.util.ArrayList;


public class saved_images extends Fragment {

    private OnFragmentInteractionListener mListener;
    private WebView webView;
    int position=1;
    FeedReaderDbHelper mDbHelper = MainActivity.mDbHelper;
    SQLiteDatabase readabledb = mDbHelper.getReadableDatabase();


     public static saved_images newInstance(String param1, String param2) {
        saved_images fragment = new saved_images();
        Bundle args = new Bundle();
         fragment.setArguments(args);
        return fragment;
    }

    public saved_images() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        //Setting up for DB



        super.onCreate(savedInstanceState);
        }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View wholeview = inflater.inflate(R.layout.fragment_saved_images, container, false);
        create_button(wholeview,"previous",mDbHelper,readabledb);
        create_button(wholeview,"next",mDbHelper,readabledb);
        create_webview(wholeview);
        return wholeview;
    }

    public void create_button(View v, String button, final FeedReaderDbHelper mDbHelper, final SQLiteDatabase readabledb) {
        switch (button) {

            case ("previous"): {
                Button Previous;
                Previous = (Button) v.findViewById(R.id.prev_image);
                Previous.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (position > 1) {
                            position--;
                            Log.d("Position--", String.valueOf(position));
                        } else {
                            position = mDbHelper.CountItemsdb(readabledb);
                        }
                        reload_webview(mDbHelper,readabledb);
                    }
                });
            }
            case ("next"): {
                Button Next;
                Next = (Button) v.findViewById(R.id.next_image);
                Next.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        position++;
                        if(position <=mDbHelper.CountItemsdb(readabledb)){
                            reload_webview(mDbHelper,readabledb);
                            Log.d("Position++", String.valueOf(position));
                        }
                        else{
                            position = 1;
                            reload_webview(mDbHelper,readabledb);
                            Log.d("endofsavedimage",String.valueOf(position));
                        }

                    }
                });
            }
        }
    }

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
    public void reload_webview(FeedReaderDbHelper mDbHelper, SQLiteDatabase readabledb){
        String link;
        if (mDbHelper.CountItemsdb(readabledb)-1>0) {
            Log.d("readingfrom", String.valueOf(position));
            link = mDbHelper.Readfromdb(position, readabledb);
        }
        else{
            link ="https://encrypted-tbn2.gstatic.com/images?q=tbn:ANd9GcSxcHH0hEVgrJwH9hpE247tfKc9eEXX6fNL21J1e1ybRyO08TExoGoQtL4";
            Log.d("NoImageSaved", String.valueOf(mDbHelper.CountItemsdb(readabledb)));
        }
        webView.loadUrl(link);
    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}
