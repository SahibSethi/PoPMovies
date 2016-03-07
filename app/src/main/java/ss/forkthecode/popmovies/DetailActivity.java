package ss.forkthecode.popmovies;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.io.FileDescriptor;
import java.io.PrintWriter;

import Model.Movie;
import extras.Constant;

public class DetailActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Intent intent = getIntent();
        Bundle b = intent.getExtras();
        Movie movie = (Movie) b.getSerializable(Constant.MOVIE_INTENT_KEY);
        ActionBar ab = getActionBar();
        if(ab != null){
            ab.setDisplayHomeAsUpEnabled(true);
            ab.setTitle(movie != null ? movie.getTitle() : "Movie Details");
        }
        DetailsFragment fragment = new DetailsFragment();
        fragment.setArguments(b);
        if(savedInstanceState == null){
            getFragmentManager().beginTransaction().add(R.id.container , fragment).commit();
        }

    }



}
