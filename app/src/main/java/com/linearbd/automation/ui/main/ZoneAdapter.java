package com.linearbd.automation.ui.main;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.linearbd.automation.R;
import com.linearbd.automation.database.entity.Zone;

import java.util.ArrayList;
import java.util.List;

public class ZoneAdapter extends RecyclerView.Adapter<ZoneAdapter.ZoneHolder>{

    private MainContract.View mView;

    private LayoutInflater inflater;
    private List<Zone> zoneList;

    public ZoneAdapter(Activity activity) {
        this.inflater = LayoutInflater.from(activity);
        this.mView = (MainContract.View) activity;
        this.zoneList = new ArrayList<>();
    }

    @NonNull
    @Override
    public ZoneHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_zone,parent,false);

        return new ZoneHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ZoneHolder holder, int position) {
        Zone zone = zoneList.get(position);
        holder.tvName.setText(zone.name);
    }

    @Override
    public int getItemCount() {
        return zoneList.size();
    }

    public void setData(List<Zone> zoneList){
        this.zoneList = zoneList;
        notifyDataSetChanged();
    }

    public void removeItem(Zone zone){
        int pos = zoneList.indexOf(zone);
        this.zoneList.remove(pos);
        notifyItemRemoved(pos);
    }

    class ZoneHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView tvName;
        ImageView mEdit,mDelete;

        public ZoneHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.name);
            mEdit = itemView.findViewById(R.id.edit);
            mDelete = itemView.findViewById(R.id.delete);
            mEdit.setOnClickListener(this);
            mDelete.setOnClickListener(this);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if(view==itemView){
                if(mView!=null){
                    mView.openZoneActivity(zoneList.get(getAdapterPosition()));
                }
            }else if(view==mEdit){
                mView.startEditZoneActivity(zoneList.get(getAdapterPosition()));
            }else if(view==mDelete){
                mView.deleteZone(zoneList.get(getAdapterPosition()));
            }

        }
    }
}
