package com.jomblo_terhormat.badjigurrestopelayan.networking;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import com.jomblo_terhormat.referensifilm.activity.MainActivity;
import com.jomblo_terhormat.referensifilm.entity.Film;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by GOODWARE1 on 8/30/2017.
 */

public class FilmLoader extends AsyncTaskLoader<List<List<Film>>> {

    private static final String LOG_TAG = FilmLoader.class.getName();
    private String mURl[] = new String[3];


    public FilmLoader(Context context, String[] URl) {
        super(context);
        this.mURl = URl;
    }

    /**
     * Subclasses must implement this to take care of loading their data,
     * as per {@link #startLoading()}.  This is not called by clients directly,
     * but as a result of a call to {@link #startLoading()}.
     */
    @Override
    protected void onStartLoading() {
        if (MainActivity.mFilms == null)
            forceLoad();
        Log.v(LOG_TAG, "On Start Loading");
    }

    @Override
    public List<List<Film>> loadInBackground() {
        if (mURl == null) {
            return null;
        }
        Log.v(LOG_TAG, "Loading Background");

        List<List<Film>> lists = new ArrayList<>();

        lists.add(QueryUtils.fetchData(mURl[0]));
        lists.add(QueryUtils.fetchData(mURl[1]));
        lists.add(QueryUtils.fetchData(mURl[2]));


        return lists;
    }
}