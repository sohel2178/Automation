package com.linearbd.automation.ui.zone;

import android.util.Log;

import com.linearbd.automation.api.DeviceClient;
import com.linearbd.automation.api.ServiceGenerator;
import com.linearbd.automation.database.dao.SwitchDao;
import com.linearbd.automation.database.entity.Switch;
import com.linearbd.automation.database.entity.Zone;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ZonePresenter implements ZoneContract.Presenter {
    private ZoneContract.View mView;
    private SwitchDao mSwitchDao;

    public ZonePresenter(ZoneContract.View mView, SwitchDao mSwitchDao) {
        this.mView = mView;
        this.mSwitchDao = mSwitchDao;
    }

    @Override
    public void requestForAllSwitch(Zone zone) {

        List<Switch> switchList = mSwitchDao.findAllSwitchesByZoneID(zone.id);

        if(switchList !=null && switchList.size()>0){
            mView.showDataView(switchList);
        }else {
            mView.showEmptyView();
        }

    }

    @Override
    public void requestToOpenAddSwitchActivity() {
        mView.openAddSwitchActivity();
    }

    @Override
    public void deleteSwitch(Switch sw) {
        mSwitchDao.deleteSwitch(sw);
        mView.removeItem(sw);
    }

    @Override
    public void alertNegativeClick() {
        mView.dismissAlertDialog();
    }

    @Override
    public void updateState(final Switch sw, boolean state) {
        DeviceClient deviceClient = ServiceGenerator.createService(DeviceClient.class);
        String url = sw.url;

        if(state){
            url= url+"/1";
        }else {
            url=url+"/0";
        }

        Call<String> call = deviceClient.setState(url);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                // Url Test
                String value = response.body();
                if(value.equals("ON")){
                    sw.state=1;
                }else{
                    sw.state=0;
                }

                mSwitchDao.update(sw);

                mView.showToast(value);
                Log.d("HHHHHH","HHHHHH");
                Log.d("HHHHHH",value);
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                // Handle Failure
                Log.d("HHHHHH","Failed");
            }
        });
    }
}
