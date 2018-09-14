package com.silmiazdkiatulathqia.cataloguemovie.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.silmiazdkiatulathqia.cataloguemovie.Model.Movie;
import com.silmiazdkiatulathqia.cataloguemovie.R;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

public class DetailActivity extends AppCompatActivity {
    public static String EXTRAS_JSON = "";

    private JSONObject json;

    private TextView title, year, overview;
    private ImageView backdrop, poster;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        title = findViewById(R.id.movie_title);
        year = findViewById(R.id.movie_year);
        overview = findViewById(R.id.overview);

        backdrop = findViewById(R.id.backdrop_image);
        poster = findViewById(R.id.poster_image);

        EXTRAS_JSON = getIntent().getStringExtra(EXTRAS_JSON);

        if (!EXTRAS_JSON.equals("")){
            try {
                json = new JSONObject(EXTRAS_JSON);
                Movie film = new Movie(json);
                getImage(film);
                title.setText(film.getJudul());
                year.setText(film.getYear());
                overview.setText(film.getOverview());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }

    private void getImage(Movie movie){
        if (movie.getUrl_gambar()!=null){
            String url_poster = "https://image.tmdb.org/t/p/w92/"+movie.getUrl_gambar();
            Picasso.with(this).load(url_poster).into(poster);
        }
        if (movie.getBackdrop()!=null){
            String url_backdrop = "https://image.tmdb.org/t/p/w300/"+movie.getBackdrop();
            Picasso.with(this).load(url_backdrop).into(backdrop);
        }
    }
}
