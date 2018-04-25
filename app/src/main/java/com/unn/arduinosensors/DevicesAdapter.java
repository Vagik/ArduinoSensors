package com.unn.arduinosensors;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.unn.arduinosensors.Activities.EditActivity;
import com.unn.arduinosensors.Activities.SensorsActivity;
import com.unn.arduinosensors.ClickListeners.DeviceClickListener;

import java.util.List;

public class DevicesAdapter extends RecyclerView.Adapter<DevicesAdapter.DeviceViewHolder> {
    private List<Device> devices;
    private Context context;

    public DevicesAdapter(List<Device> devices, Context context) {
        this.devices = devices;
        this.context = context;
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
        personViewHolder.deviceName.setOnClickListener(new DeviceClickListener(devices.get(position), context, SensorsActivity.class));
        personViewHolder.deviceButton.setOnClickListener(new DeviceClickListener(devices.get(position), context, EditActivity.class));
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    class DeviceViewHolder extends RecyclerView.ViewHolder {
        TextView deviceName;
        Button deviceButton;

        DeviceViewHolder(View itemView) {
            super(itemView);
            deviceName = (TextView) itemView.findViewById(R.id.deviceNameView);
            deviceButton = (Button) itemView.findViewById(R.id.deviceEditButton);
        }
    }
}
