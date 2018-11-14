package com.linearbd.automation.ui.main;

import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;

import com.linearbd.automation.database.dao.ZoneDao;
import com.linearbd.automation.database.entity.Zone;

import java.util.List;

public class MainPresenter implements MainContract.Presenter {

    private MainContract.View mView;
    private final ZoneDao zoneDao;

    public MainPresenter(MainContract.View mView, ZoneDao zoneDao) {
        this.mView = mView;
        this.zoneDao = zoneDao;
    }

    @Override
    public void populateZones() {
        zoneDao.findAllZones().observeForever(new Observer<List<Zone>>() {
            @Override
            public void onChanged(@Nullable List<Zone> zones) {
                mView.setZones(zones);

                if (zones == null || zones.size() < 1) {
                    mView.showEmptyMessage();
                }
            }
        });

    }

    @Override
    public void alertNegativeClick() {
        mView.dismissAlertDialog();
    }

    @Override
    public void addNewZone() {
        mView.openAddZoneActivity();
    }

    @Override
    public void deleteZone(Zone zone) {
        zoneDao.deleteZone(zone);
        mView.removeItem(zone);
    }
}
