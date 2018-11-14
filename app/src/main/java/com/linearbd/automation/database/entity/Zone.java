package com.linearbd.automation.database.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;
import java.util.Date;

@Entity
public class Zone implements Serializable {
    @PrimaryKey(autoGenerate = true)
    public long id;
    public String name;


    @Ignore
    public Zone() {
        this.name = "";
    }

    public Zone(String name) {
        this.name = name;
    }
}
