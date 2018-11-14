package com.linearbd.automation.ui.editSwitch;

import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.linearbd.automation.R;
import com.linearbd.automation.database.AppDatabase;
import com.linearbd.automation.database.entity.Switch;
import com.linearbd.automation.ui.addSwitch.SwitchPresenter;
import com.linearbd.automation.utils.BaseActivity;
import com.linearbd.automation.utils.Constant;

public class EditSwitchActivity extends BaseActivity implements EditSwitchContract.View,View.OnClickListener {

    private TextInputLayout tilName,tilUrl;
    private EditText etName,etUrl;
    private Button btnAdd;

    private EditSwitchPresenter mPresenter;

    private Switch aSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_switch);

        aSwitch = (Switch) getIntent().getSerializableExtra(Constant.SWITCH);

        setupToolbar();
        initView();

        AppDatabase db = AppDatabase.getInstance(getApplicationContext());
        mPresenter = new EditSwitchPresenter(this,db.switchModel());
    }

    private void initView() {
        tilName = findViewById(R.id.switchNameTIL);
        tilUrl = findViewById(R.id.switchUrlTIL);
        etName = findViewById(R.id.etName);
        etUrl = findViewById(R.id.etUrl);
        btnAdd = findViewById(R.id.btn_add);
        btnAdd.setOnClickListener(this);

        etName.setText(aSwitch.name);
        etUrl.setText(aSwitch.url);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getSupportActionBar().setTitle(R.string.edit_switch);
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

    @Override
    public void onClick(View view) {

        String name = etName.getText().toString().trim();
        String url = etUrl.getText().toString().trim();

        aSwitch.name = name;
        aSwitch.url = url;

        boolean valid = mPresenter.validate(aSwitch);

        if(!valid){
            return;
        }

        mPresenter.editSwitch(aSwitch);

    }
}
