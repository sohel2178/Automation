package com.linearbd.automation.ui.zone;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.linearbd.automation.R;
import com.linearbd.automation.database.AppDatabase;
import com.linearbd.automation.database.entity.Switch;
import com.linearbd.automation.database.entity.Zone;
import com.linearbd.automation.ui.addSwitch.AddSwitchActivity;
import com.linearbd.automation.ui.editSwitch.EditSwitchActivity;
import com.linearbd.automation.utils.BaseActivity;
import com.linearbd.automation.utils.Constant;

import java.util.List;

public class ZoneActivity extends BaseActivity implements ZoneContract.View {

    private Zone zone;

    private TextView tvEmptyText;
    private LinearLayout mContainer;

    private RecyclerView rvSwitch;

    private SwitchAdapter adapter;

    private ZonePresenter mPresenter;

    private AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zone);

        setupToolbar();

        zone = (Zone) getIntent().getSerializableExtra(Constant.ZONE);

        Log.d("HHHH",zone.id+"");

        adapter = new SwitchAdapter(this);

        initView();

        AppDatabase db = AppDatabase.getInstance(getApplicationContext());
        mPresenter = new ZonePresenter(this,db.switchModel());
    }

    private void initView() {
        tvEmptyText = findViewById(R.id.emptyTextView);
        mContainer = findViewById(R.id.container);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.requestToOpenAddSwitchActivity();
            }
        });

        rvSwitch = findViewById(R.id.rvSwitch);
        rvSwitch.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(getApplicationContext());
        rvSwitch.setLayoutManager(manager);
        rvSwitch.addItemDecoration(new DividerItemDecoration(getApplicationContext(),DividerItemDecoration.VERTICAL));
        rvSwitch.setAdapter(adapter);
    }


    @Override
    protected void onStart() {
        super.onStart();
        mPresenter.requestForAllSwitch(zone);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if(zone!=null){
            getSupportActionBar().setTitle(zone.name);
        }
    }

    @Override
    public void showEmptyView() {
        tvEmptyText.setVisibility(View.VISIBLE);
        mContainer.setVisibility(View.GONE);
    }

    @Override
    public void showDataView(List<Switch> switchList) {
        tvEmptyText.setVisibility(View.GONE);
        mContainer.setVisibility(View.VISIBLE);

        adapter.setData(switchList);
    }

    @Override
    public void openAddSwitchActivity() {

        Bundle bundle = new Bundle();
        bundle.putSerializable(Constant.ZONE,zone);

        Intent intent = new Intent(this, AddSwitchActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void deleteSwitch(Switch sw) {
        //mPresenter.deleteSwitch(sw);
        showDeleteDialog(sw);
    }

    @Override
    public void startEditSwitchActivity(Switch sw) {
        Intent intent = new Intent(getApplicationContext(), EditSwitchActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constant.SWITCH,sw);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void removeItem(Switch sw) {
        adapter.removeSwitch(sw);
    }

    @Override
    public void switchChangeState(Switch sw, boolean state) {
        mPresenter.updateState(sw,state);
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void dismissAlertDialog() {
        if(alertDialog!=null &&alertDialog.isShowing()){
            alertDialog.dismiss();
        }

        Toast.makeText(this, "Dialog Dismiss", Toast.LENGTH_SHORT).show();
    }


    public void showDeleteDialog(final Switch sw) {
        String titleText ="Alert";

        // Initialize a new foreground color span instance
        ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(Color.WHITE);

        // Initialize a new spannable string builder instance
        SpannableStringBuilder ssBuilder = new SpannableStringBuilder(titleText);

        // Apply the text color span
        ssBuilder.setSpan(
                foregroundColorSpan,
                0,
                titleText.length(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        );

        alertDialog = new AlertDialog.Builder(this,R.style.CustomDialogTheme).create();
        alertDialog.setTitle(ssBuilder);
        alertDialog.setMessage("Do you Really want to Delete this Switch??");
        Drawable drawable = ContextCompat.getDrawable(this,R.drawable.delete);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            drawable.setTint(getResources().getColor(R.color.red));
        }
        alertDialog.setIcon(drawable);
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mPresenter.alertNegativeClick();
                //dialogInterface.dismiss();

            }
        });

        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.indicator_4));
                alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.indicator_4));
            }
        });



        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mPresenter.deleteSwitch(sw);

            }
        });

        alertDialog.show();
    }
}
