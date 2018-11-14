package com.linearbd.automation.ui.zone;

import com.linearbd.automation.database.entity.Switch;
import com.linearbd.automation.database.entity.Zone;

import java.util.List;

public interface ZoneContract {

    interface Presenter{
        void requestForAllSwitch(Zone zone);
        void requestToOpenAddSwitchActivity();
        void deleteSwitch(Switch sw);
        void updateState(Switch sw,boolean state);
        void alertNegativeClick();
    }

    interface View{
        void showEmptyView();
        void showDataView(List<Switch> switchList);
        void openAddSwitchActivity();
        void deleteSwitch(Switch sw);
        void startEditSwitchActivity(Switch sw);
        void removeItem(Switch sw);
        void switchChangeState(Switch sw,boolean state);
        void showToast(String message);

        void dismissAlertDialog();

    }
}
