package com.nineengineering.capacitor.plugins;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.AdvertiseCallback;
import android.bluetooth.le.AdvertiseData;
import android.bluetooth.le.AdvertiseSettings;
import android.bluetooth.le.BluetoothLeAdvertiser;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;

import androidx.annotation.Nullable;

import com.getcapacitor.Bridge;
import com.nineengineering.capacitor.plugins.event.BroadcastChangedEvent;
import com.nineengineering.capacitor.plugins.model.BeaconModel;
import com.nineengineering.capacitor.plugins.model.EddystoneUID;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

public class BeaconService extends Service{


    private static BeaconService sInstance;

//    public static final int NOTIFICATION_ID = 1;
//    public static final String CHANNEL_ID = "status";

    private AlarmManager mAlarmManager;
    private BluetoothAdapter mBtAdapter;
    private BluetoothLeAdvertiser mBtAdvertiser;
    private Map<UUID, AdvertiseCallback> mAdvertiseCallbacks;
    private Map<UUID, Long> mAdvertiseStartTimestamp;
    private Context context;
    private BeaconModel beaconModel;

    public void setContext(Context context) {
        this.context = context;
    }

    public void init(Context context, String namespace, String instance) {
        if (mAdvertiseCallbacks.size() > 0) {
            System.out.println("Shoudl stop broadcasting first");
            return;
        }
//        TODO: validate input parameter Bridge
        setContext(context);
        mAlarmManager = (AlarmManager)context.getSystemService(ALARM_SERVICE);
        mBtAdapter = ((BluetoothManager)context.getSystemService(BLUETOOTH_SERVICE)).getAdapter();
        mAdvertiseCallbacks = new TreeMap<>();
        mAdvertiseStartTimestamp = new HashMap<>();
        sInstance = this;

        this.beaconModel = new BeaconModel(namespace, instance);
    }

    public void setNamespace(String namespace) {
        beaconModel.getEddystoneUID().setNamespace(namespace);
    }

    public void setInstance(String instance) {
        beaconModel.getEddystoneUID().setInstance(instance);
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public void startBroadcast() {
        if (mAdvertiseCallbacks.size() > 0) {
            System.out.println("We already having a beacon transmitting...");
            return;
        }
        mBtAdvertiser = mBtAdapter.getBluetoothLeAdvertiser();
        if (mBtAdvertiser == null || !mBtAdapter.isEnabled()) {
            System.out.println("Bluetooth is off, doing nothing");
            EventBus.getDefault().post(new BroadcastChangedEvent(beaconModel.getId(), false, 1));
            return;
        }
        final AdvertiseSettings settings = beaconModel.getSettings().generateADSettings();
        final ExtendedAdvertiseData exAdvertiseData =  beaconModel.generateADData();
        final AdvertiseData advertiseData = exAdvertiseData.getAdvertiseData();
        final String localName = exAdvertiseData.getLocalName();
        mBtAdvertiser.startAdvertising(settings, advertiseData, new MyAdvertiseCallback(1, beaconModel.getId(), false));

//        if (exAdvertiseData.getAdvertiseData().getIncludeDeviceName()) {
//            final String backupName = mBtAdapter.getName();
//            if (localName != null) {
//                mBtAdapter.setName(localName);
//            }
//            mBtAdvertiser.startAdvertising(settings, advertiseData, new MyAdvertiseCallback(1, bModel.getId(), false));
//            mBtAdapter.setName(backupName);
//        }
//        else {
//            mBtAdvertiser.startAdvertising(settings, advertiseData, new MyAdvertiseCallback(1, bModel.getId(), false));
//        }

    }

    public void stopBroadcast() {
        final AdvertiseCallback adCallback = mAdvertiseCallbacks.get(beaconModel.getId());
        if (adCallback != null) {
            try {
                if (mBtAdvertiser != null) {
                    mBtAdvertiser.stopAdvertising(adCallback);
                    mAdvertiseCallbacks.clear();
                }
                else {
                    System.out.println("Not able to stop broadcast - bt advertiser is null");
                }
            }
            catch(RuntimeException e) { // Can happen if BT adapter is not in ON state
//                sLogger.warn("Not able to stop broadcast; BT state: {}", mBtAdapter.isEnabled(), e);
                System.out.println("Not able to stop broadcast; BT state: {TODO}");
            }
//            removeScheduledUpdate(id);
//            mAdvertiseCallbacks.remove(.beaconModel.getId());
//            long timestamp = mAdvertiseStartTimestamp.get(beaconModel.getId());
//            long totalTime = (SystemClock.elapsedRealtime() - ) / 1000;
//            System.out.println(String.format("Total broadcast time for beacon %s: %,ds", beaconModel.getId(), totalTime));
//            this.beaconModel = null;
//            if (! isRestart) {
//                EventBus.getDefault().post(new BroadcastChangedEvent(id, false, mAdvertiseCallbacks.size()));
//                // For Fabric Answers
//                long totalTime = (SystemClock.elapsedRealtime() - mAdvertiseStartTimestamp.get(id)) / 1000;
//                mAdvertiseStartTimestamp.remove(id);
//                BeaconModel beaconModel = this.beacon(id);
////                if (beaconModel != null) {
////                    System.out.println(String.format("Total broadcast time for beacon %s: %,ds", beaconModel.getId(), totalTime));
////                }
//            }
        }
//        if (isRestart) {
//            return;
//        }
//        if (mAdvertiseCallbacks.size() == 0) {
//            stopForeground(true);
//            if (! ignoreServiceStartId) {
//                stopSelf(serviceStartId);
//            }
//            else {
//                stopSelf();
//            }
//            sLogger.info("No more broadcast, stopping simulator service");
//        }
//        else {
//            updateNotification();
//        }
    }

    private class MyAdvertiseCallback extends AdvertiseCallback {
        private final UUID _id;
        private final int _serviceStartId;
        private final boolean _isRestart;
        public MyAdvertiseCallback(int serviceStartId, UUID id, boolean isRestart) {
            _id = id;
            _serviceStartId = serviceStartId;
            _isRestart = isRestart;
        }
        public void onStartSuccess(AdvertiseSettings settingsInEffect) {
            mAdvertiseCallbacks.put(_id, this);
            mAdvertiseStartTimestamp.put(_id, SystemClock.elapsedRealtime());

            if (! _isRestart) {
                mAdvertiseStartTimestamp.put(_id, SystemClock.elapsedRealtime());
//                EventBus.getDefault().post(new BroadcastChangedEvent(_id, true, 1));
                // Prepare and display notification
            }
            int amount =  mAdvertiseCallbacks.size();
            System.out.println("Success in starting broadcast, currently active: {}" + Integer.toString(amount));
//            System.out.println(settingsInEffect.getMode());
        }
    }
}
