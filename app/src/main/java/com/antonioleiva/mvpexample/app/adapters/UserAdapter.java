package com.antonioleiva.mvpexample.app.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.antonioleiva.mvpexample.app.R;
import com.antonioleiva.mvpexample.app.model.User;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by pt_mobile_dev on 05/09/2016.
 */
public class UserAdapter extends RecyclerView.Adapter<UserAdapter.PlanetViewHolder>   {

    public List<User> planetList;
    private final OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(User item, int position);
        void onLongClick(int position);
    }


    public UserAdapter(List<User> planetList, OnItemClickListener listener) {
        this.planetList = planetList;
        this.listener = listener;
    }

    @Override
    public UserAdapter.PlanetViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.planet_row, parent, false);
        PlanetViewHolder viewHolder = new PlanetViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(UserAdapter.PlanetViewHolder holder, int position) {
        holder.bind(planetList.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return planetList.size();
    }

    public class PlanetViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.image_id) ImageView image;
        @BindView(R.id.text_id) TextView text;

        public PlanetViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
        public void bind(final User item, final OnItemClickListener listener) {
                image.setImageResource(item.image);
                text.setText(item.name);
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override public void onClick(View v) {
                        listener.onItemClick(item, getAdapterPosition());
                    }
                });
                itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        listener.onLongClick(getAdapterPosition());
                        return true;
                    }
                });
        }
    }
}
