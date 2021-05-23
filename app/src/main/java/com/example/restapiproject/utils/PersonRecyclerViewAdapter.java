package com.example.restapiproject.utils;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.restapiproject.Models.Datum;
import com.example.restapiproject.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PersonRecyclerViewAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private static final int VIEW_TYPE_LOADING = 0;
    private static final int VIEW_TYPE_NORMAL = 1;
    private boolean isLoaderVisible = false;

    private List<Datum> personList;
    private OnRecycleClickListner onRecycleClickListner;


    public PersonRecyclerViewAdapter(List<Datum> personList, OnRecycleClickListner onRecycleClickListner) {
        this.personList = personList;
        this.onRecycleClickListner = onRecycleClickListner;
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        switch (viewType)
        {
            case VIEW_TYPE_NORMAL:
                return new ViewHolder(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.recycleitem,parent,false));
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {

        holder.onBind(position);

    }

    @Override
    public int getItemCount() {
        return personList == null ? 0 : personList.size();
    }

    public void addItems(List<Datum> movies) {
        personList.addAll(movies);
        notifyDataSetChanged();
    }

    public void addLoading() {
        isLoaderVisible = true;
        personList.add(new Datum());
        notifyItemInserted(personList.size() - 1);
    }

    public void removeLoading() {
        isLoaderVisible = false;
        int position = personList.size() - 1;
        Datum movie = getItem(position);

        if (movie != null) {
            personList.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void clear()
    {
        personList.clear();
        notifyDataSetChanged();
    }



    public Datum getItem(int position)
    {
        return personList.get(position);
    }



    public class ProgressHolder extends BaseViewHolder
    {


        public ProgressHolder(@NonNull View itemView) {
            super(itemView);

            ButterKnife.bind(this,itemView);
        }

        @Override
        protected void clear() {

        }
    }


    public class ViewHolder extends BaseViewHolder implements View.OnClickListener{

        @BindView(R.id.personImage)
        ImageView pImage;

        @BindView(R.id.personName)
        TextView pName;

        OnRecycleClickListner onRecycleClickListner;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);


            ButterKnife.bind(this,itemView);
            this.onRecycleClickListner = onRecycleClickListner;
        }

        @Override
        public void onClick(View v) {


        }

        @Override
        protected void clear() {

        }

        @Override
        public void onBind(int position) {
            super.onBind(position);

            Datum datum = personList.get(position);
            Glide.with(itemView).load(datum.getAvatar()).fitCenter().into(pImage);

            pName.setText(datum.getFirstName());
            itemView.setOnClickListener(this);
        }
    }

    public interface OnRecycleClickListner
    {
        void onPersonClick(Datum datum);
    }
}
