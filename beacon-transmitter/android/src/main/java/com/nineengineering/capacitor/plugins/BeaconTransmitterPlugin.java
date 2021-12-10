package com.nineengineering.capacitor.plugins;

import static android.content.Context.BLUETOOTH_SERVICE;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.AdvertiseCallback;
import android.bluetooth.le.AdvertiseData;
import android.bluetooth.le.AdvertiseSettings;
import android.bluetooth.le.BluetoothLeAdvertiser;
import android.os.ParcelUuid;

import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import com.nineengineering.capacitor.plugins.event.BroadcastChangedEvent;
import com.nineengineering.capacitor.plugins.model.BeaconModel;
import com.nineengineering.capacitor.plugins.model.EddystoneUID;

import java.util.ArrayList;
import java.util.UUID;

@CapacitorPlugin(name = "BeaconTransmitter")
public class BeaconTransmitterPlugin extends Plugin {

    private static final String ADV_SERVICE_UUID = "f46e91e35cd7d2c66369";
    private final BeaconTransmitter implementation = new BeaconTransmitter();
    private BluetoothAdapter mBtAdapter;
    private BluetoothLeAdvertiser mBtAdvertiser;
    private BeaconService bService;

    @PluginMethod
    public void initialize(PluginCall call) {
//        TODO: validate input. Should have both parameters set. if not raise error
        String namespace = call.getString("namespace");
        String instance = call.getString("instance");
        this.bService = new BeaconService();
        this.bService.init(this.getBridge().getContext(), namespace, instance);
        System.out.println("Init..");
        System.out.println("Shoudl set namespace to " + namespace);
        System.out.println("Shoudl set instance to " + instance);
    }

    @PluginMethod
    public void setNamespace(PluginCall call) {
        String namespace = call.getString("namespace");
        this.bService.setNamespace(namespace);
    }

    @PluginMethod
    public void setInstance(PluginCall call) {
        String instance = call.getString("instance");
        this.bService.setInstance(instance);
    }

    @PluginMethod
    public void startBroadcast(PluginCall call) {
        this.bService.startBroadcast();
    }

    @PluginMethod
    public void stopBroadcast(PluginCall call) {
        this.bService.stopBroadcast();
    }
}
