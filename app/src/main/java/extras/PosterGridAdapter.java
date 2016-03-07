package extras;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import Model.Movie;
import ss.forkthecode.popmovies.R;

/**
 * Created by Sahib Sethi on 2/9/2016.
 */
public class PosterGridAdapter extends BaseAdapter {

    
    private ArrayList<Movie> movies;
    private Context mContext;

    public PosterGridAdapter(Context c,ArrayList<Movie> movies) {
        mContext = c;
        this.movies = movies;
    }

    @Override
    public int getCount() {
        return movies.size();
    }

    @Override
    public Object getItem(int position) {
        return movies.get(position);
    }

    @Override
    public long getItemId(int position) {
        Movie m = (Movie) getItem(position);
        return m.getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        View output = convertView;
        if(output == null){
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            output = inflater.inflate(R.layout.grid_item , parent, false);
        }
        imageView = (ImageView) output.findViewById(R.id.gridItemImageView);
        Movie movie = (Movie) getItem(position);
        String url = Constant.MOVIE_POSTER_BASE_URL + Constant.POSTER_SIZE + movie.getPosterPath();
        Picasso.with(mContext).load(url).into(imageView);
        return output;
    }


}
