package com.linearbd.automation.ui.addSwitch;

import com.linearbd.automation.database.entity.Switch;

public interface SwitchContract {

    interface Presenter{
        boolean validate(Switch swi);
        void saveSwitch(Switch swtc);
    }

    interface View{
        void showErrorMessage(int field);
        void clearPreErrors();
        void complete();
    }
}
