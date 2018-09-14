package com.silmiazdkiatulathqia.cataloguemovie.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.silmiazdkiatulathqia.cataloguemovie.Adapter.MovieAdapter;
import com.silmiazdkiatulathqia.cataloguemovie.Model.Movie;
import com.silmiazdkiatulathqia.cataloguemovie.R;
import android.app.ProgressDialog;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{


    private EditText fieldSearch;
    private Button search;
    private ImageButton clear;
    private ListView listView;
    private String API_KEY = "e344092d40e171a33e47d8eb7cfdeb7a";
    String TAG = "CatalogueMovie";
    private ProgressDialog progress;
    private TextView status;

    private MovieAdapter adapter;

    private JSONObject json;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fieldSearch = findViewById(R.id.search);
        search = findViewById(R.id.btn_search);
        listView = findViewById(R.id.list_movie);
        clear = findViewById(R.id.btn_clear);
        status = findViewById(R.id.status_list);

        progress = new ProgressDialog(this);
        progress.setCancelable(true);
        progress.setTitle("Catalogue Movie App");
        progress.setMessage("Mengambil data...");

        adapter = new MovieAdapter(this);

        search.setOnClickListener(this);
        clear.setOnClickListener(this);

        String url = "https://api.themoviedb.org/3/discover/movie?api_key="+API_KEY+"&sort_by=popularity.desc";
        progress.show();
        getMovie(url);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                Intent intent = new Intent(getApplicationContext(), DetailActivity.class);
                String send = "";
                try {
                    send = json.getJSONArray("results").getJSONObject(position).toString();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                intent.putExtra(DetailActivity.EXTRAS_JSON, send);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onClick(View view) {
        if (view.getId()==R.id.btn_search){
            String query = fieldSearch.getText().toString();
            if (!TextUtils.isEmpty(query)){
                String url = "https://api.themoviedb.org/3/search/movie?api_key="+API_KEY;
                progress.show();
                getMovie(url, query);
            }
        }
        if (view.getId()==R.id.btn_clear){
            fieldSearch.getText().clear();
            fieldSearch.clearFocus();
        }
    }

    private void getMovie(String alamat, final String query) {
        adapter.clearData();
        AsyncHttpClient client = new AsyncHttpClient();
        final ArrayList<Movie> list_movie = new ArrayList<>();
        Log.d(TAG, "getFilmByQuery: Running");
        String url = alamat+"&language=en-US&query="+query;

        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try{
                    String result = new String(responseBody);
                    Log.d(TAG, "result: "+result);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray getResult = responseObject.getJSONArray("results");
                    json = responseObject;

                    for (int i = 0; i < getResult.length(); i++) {
                        JSONObject json =  getResult.getJSONObject(i);
                        Movie movie = new Movie(json);
                        list_movie.add(movie);
                        Log.d(TAG, "year: "+list_movie.get(i).getYear());
                        Log.d(TAG, "backdrop: "+list_movie.get(i).getBackdrop());
                    }
                    adapter.setData(list_movie);
                    Log.d(TAG, "size adapter: "+adapter.getCount());
                    listView.setAdapter(adapter);
                    status.setText("Displaying result for "+query);
                    progress.dismiss();
                } catch(Exception e){
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }

    private void getMovie(String url) {
        adapter.clearData();
        AsyncHttpClient client = new AsyncHttpClient();
        final ArrayList<Movie> list_movie = new ArrayList<>();
        Log.d(TAG, "getFilmByQuery: Running");

        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try{
                    String result = new String(responseBody);
                    Log.d(TAG, "result: "+result);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray getResult = responseObject.getJSONArray("results");
                    json = responseObject;

                    for (int i = 0; i < getResult.length(); i++) {
                        JSONObject json =  getResult.getJSONObject(i);
                        Movie movie = new Movie(json);
                        list_movie.add(movie);
                        Log.d(TAG, "year: "+list_movie.get(i).getYear());
                        Log.d(TAG, "backdrop: "+list_movie.get(i).getBackdrop());
                    }
                    adapter.setData(list_movie);
                    Log.d(TAG, "size adapter: "+adapter.getCount());
                    listView.setAdapter(adapter);
                    progress.dismiss();
                } catch(Exception e){
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }
}
