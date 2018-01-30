package com.jasonrovis.brewdog;

import android.content.Context;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jasonrovis.brewdog.MainActivity.MainActivityPresenter;
import com.jasonrovis.brewdog.models.Beer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.sql.SQLOutput;
import java.util.ArrayList;

/**
 * Created by jasonrovis on 29/01/2018.
 */

public class DataService {

    private static DataService instance = new DataService();

    public static DataService getInstance() {
        return instance;
    }

    private DataService() {

    }

    private static final String url = "https://api.punkapi.com/v2/beers?food=";

    public void getMatchingBeers (Context context, String foodType, final MainActivityPresenter.BeersDownloadedListener listener) {

        final ArrayList<Beer> matchingBeers = new ArrayList<>();

        String jsonString = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext()).getString(foodType, null);

        /**
         * If jsonString is null, we retrieve our data from the API
         *
         * If not null, we deserialize the String to Arraylist<Beer>
         */

        if (jsonString == null) {

            final String finalUrl = url + foodType;

            final JsonArrayRequest getBeers = new JsonArrayRequest(Request.Method.GET, finalUrl, null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {

                    try {

                        JSONArray beers = response;
                        for (int x = 0; x < beers.length(); x++) {

                            JSONObject beer = beers.getJSONObject(x);

                            String name = beer.getString("name");
                            String tagline = beer.getString("tagline");
                            String description = beer.getString("description");
                            Double abv = beer.getDouble("abv");
                            String imageUrlString = beer.getString("image_url");

                            Beer newBeer = new Beer(name,tagline, description, abv, imageUrlString);
                            matchingBeers.add(newBeer);

                        }

                    } catch (JSONException e){
                        Log.v("JSON", "EXC" + e.getLocalizedMessage());
                    }

                    listener.success(true, matchingBeers, false);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.v("API", "Err" + error.getLocalizedMessage());
                    listener.success(false, null, false);
                }
            });

            Volley.newRequestQueue(context).add(getBeers);

        } else { //From shared preferences

            System.out.println("From Shared Pref");

            Gson gson = new Gson();
            Type type = new TypeToken<ArrayList<Beer>>(){}.getType();
            ArrayList<Beer> gsonBeers= gson.fromJson(jsonString, type);
            matchingBeers.addAll(gsonBeers);

            listener.success(true,matchingBeers,true);

        }

    }


}
