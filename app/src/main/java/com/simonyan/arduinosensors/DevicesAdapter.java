package com.simonyan.arduinosensors;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.simonyan.arduinosensors.Activities.EditActivity;
import com.simonyan.arduinosensors.Activities.SensorsActivity;
import com.simonyan.arduinosensors.ClickListeners.DeleteClickListener;
import com.simonyan.arduinosensors.ClickListeners.DeviceClickListener;

import java.util.List;

public class DevicesAdapter extends RecyclerView.Adapter<DevicesAdapter.DeviceViewHolder> {
    private List<Device> devices;
    private Activity activity;

    public DevicesAdapter(List<Device> devices, Activity activity) {
        this.devices = devices;
        this.activity = activity;
    }

    @Override
    public int getItemCount() {
        return devices.size();
    }

    @Override
    public DeviceViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.devices_list, viewGroup, false);
        return new DeviceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DeviceViewHolder personViewHolder, int position) {
        personViewHolder.deviceName.setText(devices.get(position).getName());
        personViewHolder.deviceName.setOnClickListener(new DeviceClickListener(devices.get(position), activity, SensorsActivity.class));
        personViewHolder.deviceButton.setOnClickListener(new DeviceClickListener(devices.get(position), activity, EditActivity.class));
        personViewHolder.deleteButton.setOnClickListener(new DeleteClickListener(devices.get(position), activity));
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    class DeviceViewHolder extends RecyclerView.ViewHolder {
        TextView deviceName;
        Button deviceButton;
        Button deleteButton;

        DeviceViewHolder(View itemView) {
            super(itemView);
            deviceName = (TextView) itemView.findViewById(R.id.deviceNameView);
            deviceButton = (Button) itemView.findViewById(R.id.deviceEditButton);
            deleteButton = (Button) itemView.findViewById(R.id.deviceDeleteButton);
        }
    }
}
