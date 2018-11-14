package com.linearbd.automation.ui.addZone;

import com.linearbd.automation.database.dao.ZoneDao;
import com.linearbd.automation.database.entity.Zone;

public class AddZonePresenter implements AddZoneContract.Presenter {
    private AddZoneContract.View mView;
    private ZoneDao zoneDao;

    public AddZonePresenter(AddZoneContract.View mView, ZoneDao zoneDao) {
        this.mView = mView;
        this.zoneDao = zoneDao;
    }

    @Override
    public boolean validate(Zone zone) {
        mView.clearPreErrors();
        if(zone.name.equals("")){
            mView.showErrorMessage(1);
            return false;
        }
        return true;
    }

    @Override
    public void saveZone(Zone zone) {
        long id = zoneDao.insertZone(zone);
        mView.complete();
    }

    @Override
    public void updateZone(Zone zone) {
        zoneDao.updateZone(zone);
        mView.complete();
    }
}
