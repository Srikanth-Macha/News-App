package com.example.newsapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.Vector;

public class NewsAdapter extends RecyclerView.Adapter<NewsViewHolder> {

    private final Vector<News> data;
    private final Context context;
    private int position;

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            String UrlLink = data.get(position).getUrl();
            Intent intent = new Intent(Intent.ACTION_VIEW);

            intent.setData(Uri.parse(UrlLink));
            context.startActivity(intent);
        }
    };

    public NewsAdapter(Vector<News> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.items_news, parent, false);

        view.setOnClickListener(onClickListener);

        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
        this.position = holder.getAdapterPosition();
        holder.authorName.setText("Author : " + data.get(position).getAuthor());
        holder.title.setText(data.get(position).getTitle());


        Glide.with(context).load(data.get(position).getUrlToImage()).listener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                Toast.makeText(context, "Something went wrong", Toast.LENGTH_LONG).show();
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                return false;
            }
        }).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}

class NewsViewHolder extends RecyclerView.ViewHolder {
    TextView title, authorName;
    ImageView imageView;

    public NewsViewHolder(@NonNull View itemView) {
        super(itemView);

        title = itemView.findViewById(R.id.titleID);
        authorName = itemView.findViewById(R.id.authorID);
        imageView = itemView.findViewById(R.id.imageID);
    }
}