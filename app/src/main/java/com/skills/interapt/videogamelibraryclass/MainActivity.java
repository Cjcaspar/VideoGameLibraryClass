package com.skills.interapt.videogamelibraryclass;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements Adapter.AdapterCallback{

    private RecyclerView recyclerView;
    private List<VideoGame> videoGameList;
    private Adapter adapter;

    @BindView(R.id.add_game_button)
    private Button addGameButton;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        recyclerView = findViewById(R.id.games_recycler_view);
        videoGameList = new ArrayList<>();

        setupList();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setupList() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);

        adapter = new Adapter(videoGameList);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(linearLayoutManager);

        VideoGame videoGame = new VideoGame("League of Legends", "MOBA");

        videoGameList.add(videoGame);

        adapter.notifyDataSetChanged();
    }

    public View.OnClickListener onClick(final VideoGame videoGame) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rowClicked(videoGame);
            }
        };
    }

    public Context getContext() {
        return getApplicationContext();
    }

    public void rowClicked(VideoGame videoGame) {
        videoGame.isCheckedOut() ? checkGameBackIn(videoGame) : checkGameOut(videoGame);
    }

    public void checkGameBackIn(final VideoGame videoGame) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Check-in game?").setMessage("Are you sure you want to check-in this game?").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                videoGame.setCheckedOut(false);
                videoGameDatabase.videoGameDao().updateVideoGame(videoGame);
                adapter.updateList(videoGameDatabase.videoGameDao().getVideoGames());
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
    }

    private void checkGameOut(final VideoGame videoGame) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.checkout_game).setMessage(R.string.you_sure).setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                videoGame.setCheckedOut(true);
                videoGameDatabase.videoGamedao.updateVideoGame(videoGame);
                VideoGameAdapter.updateList(videoGameDatabase.videoGameDao().getVideoGames());
                Toast.makeText(videoGame.getGameTitle(), )
            }
        }).setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        videoGameDatabase.videoGameDao().updateVideoGame(videoGame);
        adapter.updateList(videoGame.videoGameDao().getVideoGames());
    }



}
