package com.jasonrovis.brewdog.MainActivity;

import android.content.Context;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.jasonrovis.brewdog.DataService;
import com.jasonrovis.brewdog.R;
import com.jasonrovis.brewdog.models.Beer;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * Created by jasonrovis on 29/01/2018.
 */

public class MainActivityPresenter {


    private MainActivity mainActivity;
    private ArrayList<Beer> beers;

    private final Comparator<Beer> comparator = new Comparator<Beer>() {
        @Override
        public int compare(Beer o1, Beer o2) {

            if (MainActivity.isAscending()) {
                if (o1.getAbv() > o2.getAbv()) {
                    return 1;
                } else if (o1.getAbv() < o2.getAbv()) {
                    return -1;
                }
            } else {

                if (o1.getAbv() > o2.getAbv()) {
                    return -1;
                } else if (o1.getAbv() < o2.getAbv()) {
                    return 1;
                }
            }

            return 0;
        }
    };

    public ArrayList<Beer> getBeers() {
        return beers;
    }

    public MainActivityPresenter(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    public void getBeersList(final String foodtype) {

        BeersDownloadedListener listener = new BeersDownloadedListener() {
            @Override
            public void success(Boolean success, ArrayList<Beer> matchingBeers, Boolean fromSharedPref) {

                mainActivity.removeProgressDialog();

                if (success) {
                    beers = matchingBeers;
                    if (beers.size() == 0) {
                        mainActivity.loadTextFragment(mainActivity.getResources().getString(R.string.no_beers_message) + " \"" + foodtype.replace("_", " ") + "\"", true);
                    } else {
                        setUpRecyclerView();
                    }

                    if (!fromSharedPref) {
                        saveBeersToSharedPref(beers, foodtype);
                    }
                } else {
                    mainActivity.loadTextFragment(mainActivity.getResources().getString(R.string.error), true);
                }
            }
        };

        mainActivity.showProgressDialog();
        DataService.getInstance().getMatchingBeers(mainActivity, foodtype, listener);
    }

    public void setUpRecyclerView () {

        orderBeers();

        mainActivity.loadRecyclerFragment();
    }

    public void orderBeers () {

        beers.sort(comparator);

    }


    public interface BeersDownloadedListener {
        void success(Boolean success, ArrayList<Beer> matchingBeers, Boolean fromSharedPref);
    }

    public void saveBeersToSharedPref(ArrayList<Beer> beers, String foodtype) {

        Gson gson = new Gson();
        String json = gson.toJson(beers);
        PreferenceManager.getDefaultSharedPreferences(mainActivity.getApplicationContext()).edit().
                putString(foodtype, json).
                apply();



    }

}
