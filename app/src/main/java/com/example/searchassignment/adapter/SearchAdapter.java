package com.example.searchassignment.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.searchassignment.R;
import com.example.searchassignment.model.ImagesModel;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder>  {
    private ArrayList<ImagesModel> imgList;
    Context context;

    public SearchAdapter(Context context, ArrayList<ImagesModel> list) {
        this.context = context;
        this.imgList = list;
    }
    @NonNull
    @Override
    public SearchAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchAdapter.ViewHolder holder, int position) {
        ImagesModel item = imgList.get(position);

        holder.txt1.setText(item.getBlur_hash());
        holder.txt2.setText(item.getUpdated_at());

        AsyncTask<String, Void, Bitmap> myImg = new loadImage().execute(item.getUrlsModel().getRegular());
        try {
            holder.img.setImageBitmap(myImg.get());
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return imgList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txt1;
        TextView txt2;
        ImageView img;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt1 = itemView.findViewById(R.id.txt1);
            txt2 = itemView.findViewById(R.id.txt2);
            img = itemView.findViewById(R.id.imageView);

        }
    }
    //Convert url image to Bitmap
    public class loadImage extends AsyncTask<String, Void, Bitmap> {
        Bitmap image;
        @Override
        protected Bitmap doInBackground(String... params) {
            URL url = null;
            try {
                url = new URL(params[0]);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            try {
                image = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }

            return image;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);

        }
    }

}
