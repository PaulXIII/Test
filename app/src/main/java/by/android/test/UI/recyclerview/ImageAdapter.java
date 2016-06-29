package by.android.test.UI.recyclerview;

import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import by.android.test.R;
import by.android.test.UI.GIFView;

/**
 * Created by Павел on 29.06.2016.
 */
public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageAdapterHolder> {


    private List<Drawable> mDrawables;

    @Override
    public ImageAdapterHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_images_item, parent, false);

        ImageAdapterHolder imageAdapterHolder = new ImageAdapterHolder(view);
        return imageAdapterHolder;
    }

    @Override
    public void onBindViewHolder(ImageAdapterHolder holder, int position) {
        Drawable drawable = mDrawables.get(position);
//        holder.image.
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class ImageAdapterHolder extends RecyclerView.ViewHolder {

        View view;
        public GIFView image;


        public ImageAdapterHolder(View itemView) {
            super(itemView);
            view = itemView;
            image = (GIFView) itemView.findViewById(R.id.image_view);

        }
    }



}
