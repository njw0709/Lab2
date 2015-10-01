package com.example.jong.imagesaver;

import android.content.Context;
import android.media.Image;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * Created by jong on 9/29/15.
 */


public class Google_search {
    public RequestQueue queue;
    public Google_search(Context context){
        queue = Volley.newRequestQueue(context);
    }


    public void searchWithCallback(final SuccessCallback callback,String search){
        search = search.replace(" ","+");
        String URL = "https://www.googleapis.com/customsearch/v1?";
        URL+="key=AIzaSyBc_94uil9n3-8Cs7uAZGY57oz9kztF0cQ&";
        URL+="cx=001797433959247357755:zhyqlssgmei&";
        URL+="searchType=image&";
        URL+="q="+search;
        final ArrayList<String> urlList = new ArrayList<String>();


        JSONObject body = new JSONObject();

        JsonObjectRequest getRequest = new JsonObjectRequest(
                Request.Method.GET,
                URL,
                body,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // do something with response
                        Collections.addAll(urlList, parse_json(response));
                        callback.callback(urlList);
                        Log.d("Response", response.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Error", error.getMessage());
                    }
                }
        );
        queue.add(getRequest);

    }

    public String[] parse_json(JSONObject response) {

        try {
            JSONArray items = response.getJSONArray("items");
            String urlList[]=new String[items.length()];
            for (int i=0; i <items.length(); i+=1) {
                JSONObject item = (JSONObject) items.get(i);
                urlList[i]=item.getString("link");
                Log.d("Adding url to list",item.getString("link"));
            }
            return urlList;
        }
        catch (Exception error){
            String urlList[] = new String[0];
            Log.e("JSONException Error",error.getMessage());
            return urlList;
        }
    }
}


