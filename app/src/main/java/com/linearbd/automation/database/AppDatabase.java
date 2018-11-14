package com.linearbd.automation.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.linearbd.automation.database.dao.SwitchDao;
import com.linearbd.automation.database.dao.ZoneDao;
import com.linearbd.automation.database.entity.Switch;
import com.linearbd.automation.database.entity.Zone;


@Database(entities = {Zone.class, Switch.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    private static final String DB_NAME = "automation.db";
    private static volatile AppDatabase instance;

    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = create(context);
        }
        return instance;
    }

    private static AppDatabase create(final Context context) {
        return Room.databaseBuilder(
                context,
                AppDatabase.class,
                DB_NAME)
                .allowMainThreadQueries()
                .build();
    }


    public abstract ZoneDao zoneModel();
    public abstract SwitchDao switchModel();

    public static void destroyInstance() {
        instance = null;
    }
}
