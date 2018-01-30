package com.jasonrovis.brewdog.holder;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jasonrovis.brewdog.MainActivity.MainActivity;
import com.jasonrovis.brewdog.R;
import com.jasonrovis.brewdog.models.Beer;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.URL;

/**
 * Created by jasonrovis on 29/01/2018.
 */

public class BeerHolder extends RecyclerView.ViewHolder {

    private TextView beerName;
    private TextView beerTagline;
    private TextView beerDescription;
    private TextView beerAbv;
    private ImageView beerImage;

    public BeerHolder (View itemView) {
        super(itemView);

        this.beerName =(TextView)itemView.findViewById(R.id.nameTxv);
        this.beerTagline = (TextView)itemView.findViewById(R.id.taglineTxv);
        this.beerDescription = (TextView)itemView.findViewById(R.id.descriptionTxv);
        this.beerAbv = (TextView)itemView.findViewById(R.id.abvTxv);
        this.beerImage = (ImageView)itemView.findViewById(R.id.imageCardView);
    }

    public void updateUI(Beer beer, Context context){

        beerName.setText(beer.getName());
        beerTagline.setText(beer.getTagline());
        beerDescription.setText(beer.getDescription());
        final String abvText = beer.getAbv() + "%";
        beerAbv.setText(abvText);

        Picasso.with(context).setLoggingEnabled(true);
        Picasso.with(context)
                .load(beer.getImageUrlString())
                .placeholder(R.drawable.beerglass)
                .error(R.drawable.beerglass)
                .into(beerImage);

    }
}
