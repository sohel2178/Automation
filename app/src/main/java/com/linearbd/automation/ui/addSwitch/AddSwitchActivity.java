package com.linearbd.automation.ui.addSwitch;

import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.linearbd.automation.R;
import com.linearbd.automation.database.AppDatabase;
import com.linearbd.automation.database.entity.Switch;
import com.linearbd.automation.database.entity.Zone;
import com.linearbd.automation.utils.BaseActivity;
import com.linearbd.automation.utils.Constant;

public class AddSwitchActivity extends BaseActivity implements View.OnClickListener,SwitchContract.View{

    private TextInputLayout tilName,tilUrl;
    private EditText etName,etUrl;
    private Button btnAdd;

    private SwitchPresenter mPresenter;

    private Zone zone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_switch);

        //getIntent().getIntExtra()

        zone = (Zone) getIntent().getSerializableExtra(Constant.ZONE);

        setupToolbar();
        initView();

        AppDatabase db = AppDatabase.getInstance(getApplicationContext());

        mPresenter = new SwitchPresenter(this,db.switchModel());
    }

    private void initView() {
        tilName = findViewById(R.id.switchNameTIL);
        tilUrl = findViewById(R.id.switchUrlTIL);
        etName = findViewById(R.id.etName);
        etUrl = findViewById(R.id.etUrl);
        btnAdd = findViewById(R.id.btn_add);
        btnAdd.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        getSupportActionBar().setTitle(R.string.add_new_switch);
    }

    @Override
    public void onClick(View view) {
        String name = etName.getText().toString().trim();
        String url = etUrl.getText().toString().trim();

        if(zone.id!=-1){
            Switch swi = new Switch(name,url,zone.id);

            boolean valid = mPresenter.validate(swi);

            if(!valid){
                return;
            }

            mPresenter.saveSwitch(swi);
        }


    }

    @Override
    public void showErrorMessage(int field) {
        switch (field){
            case 1:
                tilName.setError("Empty Field not Acceptable");
                etName.requestFocus();
                break;
            case 2:
                tilUrl.setError("Empty Field not Acceptable");
                etUrl.requestFocus();
                break;
        }
    }

    @Override
    public void clearPreErrors() {
        tilName.setErrorEnabled(false);
        tilUrl.setErrorEnabled(false);
    }

    @Override
    public void complete() {
        finish();
    }
}
