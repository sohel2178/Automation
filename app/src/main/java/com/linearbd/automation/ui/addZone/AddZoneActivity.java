package com.linearbd.automation.ui.addZone;

import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.linearbd.automation.R;
import com.linearbd.automation.database.AppDatabase;
import com.linearbd.automation.database.entity.Zone;
import com.linearbd.automation.utils.BaseActivity;
import com.linearbd.automation.utils.Constant;

public class AddZoneActivity extends BaseActivity implements View.OnClickListener,AddZoneContract.View {

    private TextInputLayout tilZone;
    private EditText etZone;
    private Button btnAdd;

    private AddZoneContract.Presenter mPresenter;

    private Zone zone;
    private boolean isUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_zone);

        zone = (Zone) getIntent().getSerializableExtra(Constant.ZONE);

        setupToolbar();

        tilZone = findViewById(R.id.zoneTIL);
        etZone = findViewById(R.id.etZone);
        btnAdd = findViewById(R.id.btn_add);
        btnAdd.setOnClickListener(this);

        if(zone!=null){
            isUpdate=true;
            etZone.setText(zone.name);
            btnAdd.setText(R.string.update);
        }

        AppDatabase db = AppDatabase.getInstance(getApplicationContext());

        mPresenter = new AddZonePresenter(this,db.zoneModel());

    }


    @Override
    protected void onResume() {
        super.onResume();

        if(zone!=null){
            getSupportActionBar().setTitle(R.string.edit_zone);
        }else {
            getSupportActionBar().setTitle(R.string.add_new_zone);
        }


    }

    @Override
    public void onClick(View view) {
        if(zone==null){
            zone = new Zone();
        }
        String zoneName = etZone.getText().toString().trim();
        zone.name= zoneName;

        boolean valid = mPresenter.validate(zone);

        if(!valid){
           return;
        }

        if(isUpdate){
            mPresenter.updateZone(zone);
        }else {
            mPresenter.saveZone(zone);
        }





    }

    @Override
    public void showErrorMessage(int field) {
        tilZone.setError(getString(R.string.zone_name_empty));
        etZone.requestFocus();
    }

    @Override
    public void clearPreErrors() {
        tilZone.setErrorEnabled(false);
    }

    @Override
    public void complete() {
        finish();
    }
}
