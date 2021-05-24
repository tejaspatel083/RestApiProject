package com.example.restapiproject.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.restapiproject.Models.Data;
import com.example.restapiproject.R;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private ArrayList<Data> arrayList;
    private Context context;
    private ItemClickListner mItemClickListener;



    public RecyclerAdapter(ArrayList<Data> arrayList, Context context,ItemClickListner itemClickListener) {
        this.arrayList = arrayList;
        this.context = context;
        this.mItemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater1 = LayoutInflater.from(parent.getContext());
        View inflate1 = layoutInflater1.inflate(R.layout.recycleitem1,null);

        ViewHolder viewHolder1 = new ViewHolder(inflate1);

        return viewHolder1;

    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {


        Data data = arrayList.get(position);
        holder.title.setText(data.getTitle());
        holder.message.setText(data.getMessage());
//      holder.img.setImageResource(Integer.parseInt(data.getImage()));

        //Picasso.with(context).load(data.getImage()).into(holder.img);

        Glide.with(context).load(data.getImage()).fitCenter()
                .into(holder.img);

        holder.itemView.setOnClickListener(v -> {
            mItemClickListener.onItemClick(arrayList.get(position));
        });

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        TextView message;
        ImageView img;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.personName1);
            message = itemView.findViewById(R.id.personName2);
            img = itemView.findViewById(R.id.personImage);


        }
    }


    public interface ItemClickListner{
        void onItemClick(Data data);
    }

}
