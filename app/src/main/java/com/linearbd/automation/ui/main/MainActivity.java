package com.linearbd.automation.ui.main;

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
import android.widget.TextView;
import android.widget.Toast;

import com.linearbd.automation.R;
import com.linearbd.automation.api.DeviceClient;
import com.linearbd.automation.api.ServiceGenerator;
import com.linearbd.automation.database.AppDatabase;
import com.linearbd.automation.database.entity.Zone;
import com.linearbd.automation.ui.addZone.AddZoneActivity;
import com.linearbd.automation.ui.zone.ZoneActivity;
import com.linearbd.automation.utils.BaseActivity;
import com.linearbd.automation.utils.Constant;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;



public class MainActivity extends BaseActivity implements MainContract.View {

    private TextView mEmptyTextView;
    private MainPresenter mPresenter;

    private ZoneAdapter adapter;

    private AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupToolbar();
        adapter = new ZoneAdapter(this);


        mEmptyTextView =  findViewById(R.id.emptyTextView);

        FloatingActionButton fab =  findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.addNewZone();
            }
        });

        RecyclerView recyclerView =  findViewById(R.id.list);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager manage = new LinearLayoutManager(getApplicationContext());

        recyclerView.setLayoutManager(manage);
        recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(),DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(adapter);

        AppDatabase db = AppDatabase.getInstance(getApplicationContext());
        mPresenter = new MainPresenter(this, db.zoneModel());
    }

    @Override
    protected void onStart() {
        super.onStart();
        mPresenter.populateZones();
    }

    @Override
    protected void onResume() {
        super.onResume();

        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setTitle(R.string.zone_list);

        /*DeviceClient client = ServiceGenerator.createService(DeviceClient.class);

        client.test().enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.d("HHHH",response.body());
                Toast.makeText(MainActivity.this, response.body(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.d("HHHH","NNNNNNNNNNNNNN");
                Log.d("HHHH","NNNNNNNNNNNNNN"+t.getMessage());
            }
        });*/
    }


    @Override
    public void setZones(List<Zone> zoneList) {
        mEmptyTextView.setVisibility(View.GONE);
        adapter.setData(zoneList);
    }

    @Override
    public void showEmptyMessage() {

        mEmptyTextView.setVisibility(View.VISIBLE);
    }

    @Override
    public void openAddZoneActivity() {
        Intent intent = new Intent(getApplicationContext(), AddZoneActivity.class);
        startActivity(intent);
    }

    @Override
    public void openZoneActivity(Zone zone) {
        Bundle bundle = new Bundle();
        Log.d("HHHHH",zone.id+"");
        bundle.putSerializable(Constant.ZONE,zone);
        Intent intent = new Intent(getApplicationContext(), ZoneActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void startEditZoneActivity(Zone zone) {
        Intent intent = new Intent(getApplicationContext(), AddZoneActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constant.ZONE,zone);

        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void deleteZone(Zone zone) {
        showDeleteDialog(zone);
    }

    @Override
    public void removeItem(Zone zone) {
        adapter.removeItem(zone);
    }

    @Override
    public void dismissAlertDialog() {
        if(alertDialog!=null &&alertDialog.isShowing()){
            alertDialog.dismiss();
        }

        Toast.makeText(this, "Cancel Click", Toast.LENGTH_SHORT).show();

    }


    public void showDeleteDialog(final Zone zone) {
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
        alertDialog.setMessage("Do you Really want to Delete this Zone??");
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
                mPresenter.deleteZone(zone);

            }
        });

        alertDialog.show();
    }
}
