package ss.forkthecode.popmovies;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import Model.Movie;
import extras.Constant;

/**
 * Created by Sahib Sethi on 3/6/2016.
 */
public class DetailsFragment extends Fragment {

    ImageView moviePoster;
    TextView movieName;
    TextView overview;
    TextView userRating;
    TextView releaseDate;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_details, container, false);
        moviePoster = (ImageView) view.findViewById(R.id.movie_poster);
        movieName = (TextView) view.findViewById(R.id.movie_name);
        overview = (TextView) view.findViewById(R.id.overview);
        userRating = (TextView) view.findViewById(R.id.user_rating);
        releaseDate = (TextView) view.findViewById(R.id.release_date);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Movie movie = (Movie) getArguments().getSerializable(Constant.MOVIE_INTENT_KEY);
        setData(movie);
    }

    public void setData(Movie movie){
        movieName.setText(movie.getTitle());
        overview.setText(movie.getPlot());
        userRating.setText(""+((int) movie.getUserRating()));
        releaseDate.setText(movie.getReleaseDate());
        String url = Constant.MOVIE_POSTER_BASE_URL + Constant.POSTER_SIZE + movie.getPosterPath();
        Picasso.with(getActivity()).load(url).into(moviePoster);


    }
}
