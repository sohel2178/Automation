package com.linearbd.automation.database.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.linearbd.automation.database.entity.Zone;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;

@Dao
public interface ZoneDao {

    @Query("SELECT * FROM zone ORDER BY name ASC")
    LiveData<List<Zone>> findAllZones();

    @Query("SELECT * FROM zone WHERE id=:id")
    Zone findZoneById(long id);

    @Insert(onConflict = IGNORE)
    long insertZone(Zone zone);

    @Update
    int updateZone(Zone zone);

    @Update
    void updateZones(List<Zone> zones);

    @Delete
    void deleteZone(Zone zone);

    @Query("DELETE FROM zone")
    void deleteAll();
}
