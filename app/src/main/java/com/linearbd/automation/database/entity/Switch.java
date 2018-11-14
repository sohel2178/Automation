package com.linearbd.automation.database.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;

import static android.arch.persistence.room.ForeignKey.CASCADE;


@Entity(foreignKeys = @ForeignKey(entity = Zone.class,
        parentColumns = "id",
        childColumns = "zoneId",
        onDelete = CASCADE))

public class Switch implements Serializable {
    @PrimaryKey(autoGenerate = true)
    public long id;
    public String name;
    public String url;
    public int state;
    public long zoneId;


    public Switch(String name,String url, long zoneId) {
        this.name = name;
        this.url = url;
        this.state = 0;
        this.zoneId = zoneId;
    }


}
