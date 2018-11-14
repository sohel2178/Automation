package com.linearbd.automation.ui.addZone;

import com.linearbd.automation.database.entity.Zone;

public interface AddZoneContract {

    interface Presenter{
        boolean validate(Zone zone);
        void saveZone(Zone zone);
        void updateZone(Zone zone);
    }

    interface View{
        void showErrorMessage(int field);
        void clearPreErrors();
        void complete();
    }


}
