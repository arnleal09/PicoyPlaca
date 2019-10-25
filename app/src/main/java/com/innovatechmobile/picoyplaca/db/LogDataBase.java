package com.innovatechmobile.picoyplaca.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.innovatechmobile.picoyplaca.db.dao.LogDao;
import com.innovatechmobile.picoyplaca.db.entity.Log;

@Database(entities = {Log.class}, version = 1, exportSchema = false)
public abstract class LogDataBase extends RoomDatabase {

    public abstract LogDao logDao();

    private static volatile LogDataBase instance;

    static LogDataBase getDatabase(final Context context) {
        if (instance == null) {
            synchronized (LogDataBase.class) {
                if (instance == null) {
                    instance = Room.databaseBuilder(context.getApplicationContext(),
                            LogDataBase.class, "log_database")
                            .build();
                }
            }
        }
        return instance;
    }
}