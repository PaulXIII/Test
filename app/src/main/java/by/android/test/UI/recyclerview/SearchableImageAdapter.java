package by.android.test.UI.recyclerview;

import android.graphics.Movie;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import by.android.test.R;
import by.android.test.UI.GIFView;
import by.android.test.network.downloading.CachingGifs;

/**
 * Created by Павел on 29.06.2016.
 */
public class SearchableImageAdapter extends RecyclerView.Adapter<SearchableImageAdapter.SearchableImageAdapterHolder> {


    private List<String> mGifs = new ArrayList<>();

    public List<String> getmGifs() {
        return mGifs;
    }

    public void setmGifs(List<String> mGifs) {
        this.mGifs = mGifs;

    }

    public void updateList(List<String> list) {
        mGifs.clear();
        mGifs.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public SearchableImageAdapterHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_images_item, parent, false);
        return new SearchableImageAdapterHolder(view);
    }

    @Override
    public void onBindViewHolder(SearchableImageAdapterHolder holder, int position) {
        Log.d("TAG", "onBindViewHolder");
        String key = mGifs.get(position);
        Movie gifFromCache = CachingGifs.getInstance().getMovieFromMemoryCache(key);
        if (gifFromCache == null) {
            Log.d("TAG", "gifFromCache is null");
        }
        holder.image.setGif(gifFromCache);

    }

    @Override
    public int getItemCount() {
        return mGifs.size();
    }

    public static class SearchableImageAdapterHolder extends RecyclerView.ViewHolder {

        View view;
        public GIFView image;


        public SearchableImageAdapterHolder(View itemView) {
            super(itemView);
            view = itemView;
            image = (GIFView) itemView.findViewById(R.id.image_view_item);

        }
    }


}
