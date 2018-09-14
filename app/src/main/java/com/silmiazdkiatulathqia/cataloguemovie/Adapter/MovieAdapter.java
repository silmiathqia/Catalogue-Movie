package com.silmiazdkiatulathqia.cataloguemovie.Adapter;
import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.silmiazdkiatulathqia.cataloguemovie.Model.Movie;
import com.silmiazdkiatulathqia.cataloguemovie.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MovieAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;
    private ArrayList<Movie> myMovie = new ArrayList<>();

    public MovieAdapter(Context context){
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setData(ArrayList<Movie> movie){
        myMovie = movie;
        notifyDataSetChanged();
    }

    public void addItem(Movie movie){
        myMovie.add(movie);
        notifyDataSetChanged();
    }

    public void clearData(){
        myMovie.clear();
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public int getCount() {
        if (myMovie==null) return 0;
        return myMovie.size();
    }

    @Override
    public Movie getItem(int i) {
        return myMovie.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @SuppressLint("InflateParams")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView==null){
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.activity_movie, null);
            holder.judulFilm = convertView.findViewById(R.id.judul_film);
            holder.overview = convertView.findViewById(R.id.overview_film);
            holder.gambarFilm = convertView.findViewById(R.id.poster_film);
            convertView.setTag(holder);

        } else{
            holder = (ViewHolder) convertView.getTag();
        }

        holder.judulFilm.setText(myMovie.get(position).getJudul());
        holder.overview.setText(myMovie.get(position).getOverview());

        //url poster image
        String url = "https://image.tmdb.org/t/p/w92/"+myMovie.get(position).getUrl_gambar();
        //set image with poster
        Picasso.with(convertView.getContext()).load(url).into(holder.gambarFilm);

        return convertView;
    }

    private static class ViewHolder{
        TextView judulFilm, overview;
        ImageView gambarFilm;
    }
}
