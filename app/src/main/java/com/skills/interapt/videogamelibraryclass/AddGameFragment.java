package com.skills.interapt.videogamelibraryclass;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;

public class AddGameFragment extends Fragment {

    private AddGameCallback addGameCallback;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_game, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    public static AddGameFragment newInstance() {
        Bundle args = new Bundle();

        AddGameFragment fragment = new AddGameFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public void attachParent(AddGameCallback addGameCallback) {
        this.addGameCallback = addGameCallback;
    }

    public interface AddGameCallback {
        void addGame();
    }
}
