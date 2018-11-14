package com.linearbd.automation.ui.editSwitch;

import com.linearbd.automation.database.dao.SwitchDao;
import com.linearbd.automation.database.entity.Switch;

public class EditSwitchPresenter implements EditSwitchContract.Presenter {

    private EditSwitchContract.View mView;
    private SwitchDao mSwitchDao;

    public EditSwitchPresenter(EditSwitchContract.View mView, SwitchDao mSwitchDao) {
        this.mView = mView;
        this.mSwitchDao = mSwitchDao;
    }



    @Override
    public boolean validate(Switch swi) {
        mView.clearPreErrors();

        if(swi.name.equals("")){
            mView.showErrorMessage(1);
            return false;
        }

        if(swi.url.equals("")){
            mView.showErrorMessage(2);
            return false;
        }
        return true;
    }

    @Override
    public void editSwitch(Switch swtc) {
        mSwitchDao.update(swtc);
        mView.complete();
    }
}
