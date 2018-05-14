package com.skills.interapt.videogamelibraryclass;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

@Database(version = 1, entities = VideoGame.class)
@TypeConverters(DateConverter.class)
abstract class VideoGameDatabase extends RoomDatabase {
    private static VideoGameDatabase INSTANCE;


    public abstract VideoGameDao videoGameDao();


    /** Singleton pattern so that this object can only be made one time */
    public static VideoGameDatabase getDatabase(Context context) {
        if(INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), VideoGameDatabase.class, "data_base")
                    .allowMainThreadQueries()
                    .build();
        }

        return INSTANCE;
    }

}
