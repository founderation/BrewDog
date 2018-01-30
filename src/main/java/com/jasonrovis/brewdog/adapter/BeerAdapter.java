package com.jasonrovis.brewdog.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jasonrovis.brewdog.R;
import com.jasonrovis.brewdog.holder.BeerHolder;
import com.jasonrovis.brewdog.models.Beer;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by jasonrovis on 29/01/2018.
 */

public class BeerAdapter extends RecyclerView.Adapter<BeerHolder> {

    private ArrayList<Beer> beers;
    private Context context;

    public BeerAdapter(ArrayList<Beer> beers, Context context) {
        this.beers = beers;
        this.context = context;
    }

    @Override
    public void onBindViewHolder(BeerHolder holder, int position) {

        final Beer beer = beers.get(position);

        holder.updateUI(beer, context );

    }

    @Override
    public int getItemCount() {
        return beers.size();
    }

    @Override
    public BeerHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View beerCard = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_beer, parent, false);

        return new BeerHolder(beerCard);
    }
}
