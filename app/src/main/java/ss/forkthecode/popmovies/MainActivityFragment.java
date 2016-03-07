package ss.forkthecode.popmovies;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Properties;

import Model.Movie;
import extras.Constant;
import extras.PosterGridAdapter;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
/**
 * Created by Sahib Sethi on 2/10/2016.
 */
public class MainActivityFragment extends Fragment {


    ArrayList<Movie> moviesForAdapter;
    PosterGridAdapter adapter;
    GridView gridView;
    ProgressDialog pd;

    public MainActivityFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View output = inflater.inflate(R.layout.fragment_main , container , false);
        moviesForAdapter = new ArrayList<>();
        gridView = (GridView) output.findViewById(R.id.gridview);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent detailActivityIntent = new Intent(getActivity(),DetailActivity.class);
                Bundle b = new Bundle();
                Movie movie = moviesForAdapter.get(position);
                b.putSerializable(Constant.MOVIE_INTENT_KEY , movie);
                detailActivityIntent.putExtras(b);
                startActivity(detailActivityIntent);
            }
        });
        pd = new ProgressDialog(getActivity());
        pd.setMessage("Rukk bc....");
        pd.show();
        FetchMoviesTask task = new FetchMoviesTask(getActivity());
        task.execute();
        return output;

    }



    public class FetchMoviesTask extends AsyncTask<String , Void , ArrayList<Movie>> {

        Context context;
        FetchMoviesTask(Context context){
            this.context = context;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if(!isOnline(context)){
                cancel(true);
                Toast.makeText(context,"No internet connection",Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(context,"Internet Working",Toast.LENGTH_SHORT).show();
            }
        }

        protected boolean isOnline(Context context){
            ConnectivityManager connMan = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

            NetworkInfo networkInfo = connMan.getActiveNetworkInfo();
            return (networkInfo != null && networkInfo.isConnected());
        }

        @Override
        protected ArrayList<Movie> doInBackground(String... params) {

            try {
                OkHttpClient client = new OkHttpClient();
                URL url = new URL(Constant.POPULAR_MOVIES_LIST_BASE_URL + Constant.API_KEY);
                Request request = new Request.Builder()
                        .url(url)
                        .build();
                Response response = client.newCall(request).execute();
                String responseJSONString = response.body().string();
                if(response.isSuccessful()){
                    ArrayList<Movie> movieList = new ArrayList<>();
                    JSONObject responseJSON = new JSONObject(responseJSONString);
                    JSONArray resultJSONArray = responseJSON.getJSONArray("results");
                    for(int i =0;i < resultJSONArray.length();i++){
                        JSONObject movieJSON = resultJSONArray.getJSONObject(i);
                        long id = movieJSON.getLong("id");
                        String title = movieJSON.getString("title");
                        String plot = movieJSON.getString("overview");
                        String releaseDate = movieJSON.getString("release_date");
                        String posterPath = movieJSON.getString("poster_path");
                        Double userRating = movieJSON.getDouble("vote_average");
                        Double popularity = movieJSON.getDouble("popularity");
                        Movie movie = new Movie(id,title,plot,releaseDate,posterPath,userRating,popularity);
                        movieList.add(movie);
                    }
                    return movieList;
                }
                else{
                    Log.v("Response","not 200");
                    return null;
                }
            }  catch (Exception  e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(ArrayList<Movie> movies) {
            super.onPostExecute(movies);
            moviesForAdapter = movies;
            adapter = new PosterGridAdapter(getActivity(),moviesForAdapter);
            pd.dismiss();
            gridView.setAdapter(adapter);
        }
    }
}
