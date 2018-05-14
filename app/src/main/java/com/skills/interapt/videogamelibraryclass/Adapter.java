package com.skills.interapt.videogamelibraryclass;

import android.content.Context;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    private AdapterCallback adapterCallback;
    List<VideoGame> videoGameList;

    public Adapter(List<VideoGame> videoGameList) {

        this.videoGameList = videoGameList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_video_game, parent, false);


        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(position);
        holder.gameTitle.setOnClickListener(holder.onItemClick(videoGameList.get(position)));
        holder.itemView.setOnLongClickListener(holder.onItemLongClick(videoGameList.get(position)));
    }

    @Override
    public int getItemCount() {
        return videoGameList.size();
    }

    public void updateList(List<VideoGame> videoGames) {
        this.videoGameList = videoGames;
        notifyDataSetChanged();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView gameTitle;
        private TextView gameGenre;
        //private TextView dueDate;

        public ViewHolder(View itemView) {
            super(itemView);
            gameTitle = itemView.findViewById(R.id.item_title);
            gameGenre = itemView.findViewById(R.id.item_genre);
            //dueDate = itemView.findViewById(R.id.item_date);
            itemView.setOnClickListener(this);
        }

        public void bind(int position) {

            gameTitle.setText(videoGameList.get(position).getGameTitle());
            gameGenre.setText(videoGameList.get(position).getGameGenre());
            //dueDate.setText(videoGameList.get(position).getDate().toString());

        }

        @Override
        public void onClick(View v) {
            //example of onclick method usage
            videoGameList.get(getAdapterPosition()).setGameTitle("New title");

            notifyDataSetChanged();
        }


        public View.OnClickListener onItemClick(VideoGame videoGame) {
            return new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(itemView.getContext(), gameTitle.getText(), Toast.LENGTH_SHORT).show();
                }
            };
        }

        public View.OnLongClickListener onItemLongClick(VideoGame videoGame) {
            return new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    return false;
                }
            };
        }
    }

    public void rowClicked(VideoGame videoGame) {
        adapterCallback.rowClicked(videoGame);
    }

//    public Context getContext() {
//        return adapterCallback.getContext();
//    }
//
//    public void addClicked() {
//        adapterCallback.addClicked();
//    }

    public interface AdapterCallback {
        void rowClicked(VideoGame videoGame);
        //Context getContext();
        //void addClicked();
    }
}
