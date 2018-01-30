package com.jasonrovis.brewdog.holder;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jasonrovis.brewdog.MainActivity.MainActivity;
import com.jasonrovis.brewdog.R;
import com.jasonrovis.brewdog.models.Beer;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

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

    public void updateUI(final Beer beer, final Context context){

        beerName.setText(beer.getName());
        beerTagline.setText(beer.getTagline());
        beerDescription.setText(beer.getDescription());
        final String abvText = beer.getAbv() + "%";
        beerAbv.setText(abvText);

        String imageString = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext()).getString(beer.getImageUrlString(), null);

        if (imageString == null) {

            Picasso.with(context).setLoggingEnabled(true);
            Picasso.with(context)
                    .load(beer.getImageUrlString())
                    .placeholder(R.drawable.beerglass)
                    .error(R.drawable.beerglass)
                    .into(beerImage, new Callback() {
                        @Override
                        public void onSuccess() {
                            //Saving image to shared preferences encoded to Base64
                            String img_str = imageToBase64(beerImage);

                            PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext()).edit().
                                    putString(beer.getImageUrlString(), img_str).
                                    apply();
                        }

                        @Override
                        public void onError() {

                        }
                    });

        } else {

            Bitmap bm = base64ToImage(imageString);
            beerImage.setImageBitmap(bm);

        }
    }

    public String imageToBase64(ImageView imageView) {

        BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();
        Bitmap bitmap = drawable.getBitmap();

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 90, stream);
        byte[] image=stream.toByteArray();

        return Base64.encodeToString(image, 0);

    }

    public Bitmap base64ToImage(String imageString) {

        byte[] decodedString = Base64.decode(imageString, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

        return decodedByte;

    }


}
