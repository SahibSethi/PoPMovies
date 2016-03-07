package Model;

import java.io.Serializable;

/**
 * Created by Sahib Sethi on 2/9/2016.
 */
public class Movie implements Serializable {

    private String posterPath;
    private String plot;
    private long id;
    private String title;
    private double userRating;
    private String releaseDate;
    private double popularity;

    public Movie(long id,String title,String plot,String releaseDate,String posterPath, Double userRating,Double popularity){
        this.posterPath = posterPath;
        this.plot = plot;
        this.id = id;
        this.title = title;
        this.userRating = userRating;
        this.releaseDate = releaseDate;
        this.popularity = popularity;
    }

    public long getId(){
        return id;
    }
    public String getPosterPath(){
        return posterPath;
    }
    public String getTitle(){
        return title;
    }
    public String getReleaseDate(){
        return releaseDate;
    }
    public double getUserRating(){
        return userRating;
    }
    public double getPopularity(){
        return popularity;
    }
    public String getPlot(){
        return plot;
    }

}
