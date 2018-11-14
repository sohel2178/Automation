package com.linearbd.automation.ui.main;

import com.linearbd.automation.database.entity.Zone;

import java.util.List;

public interface MainContract {

    interface Presenter{
        void populateZones();
        void addNewZone();
        void alertNegativeClick();
        void deleteZone(Zone zone);
    }

    interface View{
        void setZones(List<Zone> zoneList);

        void showEmptyMessage();

        void openAddZoneActivity();
        void openZoneActivity(Zone zone);
        void startEditZoneActivity(Zone zone);
        void deleteZone(Zone zone);
        void removeItem(Zone zone);
        void dismissAlertDialog();
    }
}
