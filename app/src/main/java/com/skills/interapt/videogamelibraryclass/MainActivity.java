package com.skills.interapt.videogamelibraryclass;

import android.app.AlertDialog;
import android.arch.persistence.db.SupportSQLiteOpenHelper;
import android.arch.persistence.room.DatabaseConfiguration;
import android.arch.persistence.room.InvalidationTracker;
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
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements Adapter.AdapterCallback, VideoGameDao, AddGameFragment.AddGameCallback {

    private RecyclerView recyclerView;
    private List<VideoGame> videoGameList;
    private Adapter adapter;
    public VideoGameDatabase videoGameDatabase;
    private AddGameFragment addGameFragment;

    @BindView(R.id.add_game_button)
    protected Button addGameButton;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        videoGameDatabase = VideoGameDatabase.getDatabase(this);
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
//        videoGame.isCheckedOut() ? checkGameBackIn(videoGame) : checkGameOut(videoGame);
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
                videoGameDatabase.videoGameDao().updateVideoGame(videoGame);
                adapter.updateList(videoGameDatabase.videoGameDao().getVideoGames());
                //Toast.makeText(videoGame.getGameTitle(), );
            }
        }).setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        videoGameDatabase.videoGameDao().updateVideoGame(videoGame);
        adapter.updateList(videoGameDatabase.videoGameDao().getVideoGames());
    }

    private void rowLongClicked(final VideoGame videoGame) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete Game?").setMessage("Are you sure you want to delete this game?").setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                videoGameList.remove(videoGame);

            }
        }).setNegativeButton("no", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).setIcon(android.R.drawable.ic_dialog_alert).show();
    }

    @Override
    public List<VideoGame> getVideoGames() {
        return videoGameDatabase.videoGameDao().getVideoGames();
    }

    @Override
    public void addVideoGame(VideoGame videoGame) {
        videoGameDatabase.videoGameDao().addVideoGame(videoGame);
    }

    @Override
    public void updateVideoGame(VideoGame videoGame) {
        videoGameDatabase.videoGameDao().updateVideoGame(videoGame);
    }

    @Override
    public void deleteVideoGame(VideoGame videoGame) {
        videoGameDatabase.videoGameDao().deleteVideoGame(videoGame);
    }

    @Override
    public void addGame(VideoGame videoGame) {
        //logic
    }

    @OnClick(R.id.add_game_button)
    protected void addGameClicked() {
        addGameFragment = AddGameFragment.newInstance();
        addGameFragment.attachParent(this);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_holder, addGameFragment).commit();
    }

    @Override
    public void addGame() {
        
    }
}



