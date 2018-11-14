package com.linearbd.automation.ui.addSwitch;

import com.linearbd.automation.database.dao.SwitchDao;
import com.linearbd.automation.database.entity.Switch;

public class SwitchPresenter implements SwitchContract.Presenter {

    private SwitchContract.View mView;
    private SwitchDao mSwitchDao;

    public SwitchPresenter(SwitchContract.View mView, SwitchDao mSwitchDao) {
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
    public void saveSwitch(Switch swtc) {
        mSwitchDao.insert(swtc);
        mView.complete();
    }
}
