package com.sankalp.nytimestory;

import android.app.LauncherActivity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class newsAdapter  extends RecyclerView.Adapter<newsAdapter.ViewHolder> {

    private List<results> NewsList ;
    Context context;

    public newsAdapter(List<results> NewsList, Context context) {
        this.NewsList = NewsList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.news_list,viewGroup,false);


        return  new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
        results  results = NewsList.get(i);
        holder.title.setText(results.getTitle());
        holder.abstrac.setText(results.getAbstrac());
        holder.by.setText(results.getByline());

        Picasso.with(context).load(results.getMurl()).placeholder(R.mipmap.ic_launcher).fit().centerCrop().into(holder.storyPic);


    }

    @Override
    public int getItemCount() {
        return NewsList.size();
    }

    public  class ViewHolder extends RecyclerView.ViewHolder{

        TextView title;
        TextView abstrac;
        TextView by;
        ImageView storyPic;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.textView2);
            abstrac= itemView.findViewById(R.id.textView3);
            by=itemView.findViewById(R.id.textView4);
            storyPic = itemView.findViewById(R.id.imageView2);
        }
    }
}
