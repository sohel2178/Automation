package com.linearbd.automation.ui.zone;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.linearbd.automation.R;
import com.linearbd.automation.database.entity.Switch;
import com.linearbd.automation.ui.addSwitch.SwitchContract;

import java.util.ArrayList;
import java.util.List;

public class SwitchAdapter extends RecyclerView.Adapter<SwitchAdapter.SwitchHolder> {

    private LayoutInflater inflater;
    private List<Switch> switchList;
    private ZoneContract.View mView;

    public SwitchAdapter(Activity activity) {
        this.inflater = LayoutInflater.from(activity);
        this.mView = (ZoneContract.View) activity;
        this.switchList = new ArrayList<>();
    }

    @NonNull
    @Override
    public SwitchHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_switch,parent,false);

        return new SwitchHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SwitchHolder holder, int position) {
        Switch sw = switchList.get(position);

        holder.tvName.setText(sw.name);

        if(sw.state==0){
            holder.scState.setChecked(false);
        }else{
            holder.scState.setChecked(true);
        }
    }

    @Override
    public int getItemCount() {
        return switchList.size();
    }

    public void removeSwitch(Switch sw){
        int pos = switchList.indexOf(sw);
        switchList.remove(pos);
        notifyItemRemoved(pos);
    }

    public void setData(List<Switch> switchList){
        this.switchList= switchList;
        notifyDataSetChanged();
    }

    class SwitchHolder extends RecyclerView.ViewHolder implements View.OnClickListener,CompoundButton.OnCheckedChangeListener{

        TextView tvName;
        SwitchCompat scState;

        ImageView mEdit,mDelete;

        public SwitchHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.name);
            scState = itemView.findViewById(R.id.state);

            mEdit = itemView.findViewById(R.id.edit);
            mDelete = itemView.findViewById(R.id.delete);
            mEdit.setOnClickListener(this);
            mDelete.setOnClickListener(this);

            scState.setOnCheckedChangeListener(this);
        }

        @Override
        public void onClick(View view) {
            if(view==mEdit){
                mView.startEditSwitchActivity(switchList.get(getAdapterPosition()));
            }else if(view==mDelete){
                mView.deleteSwitch(switchList.get(getAdapterPosition()));
            }
        }

        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            mView.switchChangeState(switchList.get(getAdapterPosition()),b);
        }
    }
}
