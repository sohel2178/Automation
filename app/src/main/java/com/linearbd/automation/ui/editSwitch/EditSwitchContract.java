package com.linearbd.automation.ui.editSwitch;

import com.linearbd.automation.database.entity.Switch;

public interface EditSwitchContract {

    interface Presenter{
        boolean validate(Switch swi);
        void editSwitch(Switch swtc);
    }

    interface View{
        void showErrorMessage(int field);
        void clearPreErrors();
        void complete();
    }
}
