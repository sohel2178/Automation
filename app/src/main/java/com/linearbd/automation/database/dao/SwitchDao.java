package com.linearbd.automation.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.linearbd.automation.database.entity.Switch;

import java.util.List;

@Dao
public interface SwitchDao {

    @Insert
    void insert(Switch repo);

    @Update
    void update(Switch sw);

    @Update
    void update(Switch... repos);

    @Delete
    void delete(Switch... repos);

    @Delete
    void deleteSwitch(Switch sw);

    @Query("SELECT * FROM switch")
    List<Switch> getAllSwitches();

    @Query("SELECT * FROM switch WHERE zoneId=:zoneId")
    List<Switch> findAllSwitchesByZoneID(final long zoneId);
}
