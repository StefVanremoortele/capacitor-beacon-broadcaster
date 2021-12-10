package com.nineengineering.capacitor.plugins.model;

import android.bluetooth.le.AdvertiseSettings;
import android.os.Parcel;
import android.os.Parcelable;

public class Settings implements Parcelable {

    private boolean connectable;
    private PowerLevel powerLevel;
    private AdvertiseMode advertiseMode;

    public Settings() {
        this.connectable = false;
        this.powerLevel = PowerLevel.medium;
        this.advertiseMode = AdvertiseMode.balanced;
    }


    public boolean isConnectable() {
        return connectable;
    }

    public void setConnectable(boolean connectable) {
        this.connectable = connectable;
    }

    public PowerLevel getPowerLevel() {
        return powerLevel;
    }

    public void setPowerLevel(PowerLevel powerLevel) {
        this.powerLevel = powerLevel;
    }

    public AdvertiseMode getAdvertiseMode() {
        return advertiseMode;
    }

    public void setAdvertiseMode(AdvertiseMode advertiseMode) {
        this.advertiseMode = advertiseMode;
    }

    public AdvertiseSettings generateADSettings() {
        AdvertiseSettings.Builder builder = new AdvertiseSettings.Builder();
        builder.setConnectable(connectable);
        switch (powerLevel) {
            case high:
                builder.setTxPowerLevel(AdvertiseSettings.ADVERTISE_TX_POWER_HIGH);
                break;
            case medium:
                builder.setTxPowerLevel(AdvertiseSettings.ADVERTISE_TX_POWER_MEDIUM);
                break;
            case low:
                builder.setTxPowerLevel(AdvertiseSettings.ADVERTISE_TX_POWER_LOW);
                break;
            case ultraLow:
            default:
                builder.setTxPowerLevel(AdvertiseSettings.ADVERTISE_TX_POWER_ULTRA_LOW);
                break;
        }
        switch (advertiseMode) {
            case lowLatency:
                builder.setAdvertiseMode(AdvertiseSettings.ADVERTISE_MODE_LOW_LATENCY);
                break;
            case balanced:
                builder.setAdvertiseMode(AdvertiseSettings.ADVERTISE_MODE_BALANCED);
                break;
            case lowPower:
            default:
                builder.setAdvertiseMode(AdvertiseSettings.ADVERTISE_MODE_LOW_POWER);
                break;
        }
        return builder.build();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte(this.connectable ? (byte) 1 : (byte) 0);
        dest.writeInt(this.powerLevel == null ? -1 : this.powerLevel.ordinal());
        dest.writeInt(this.advertiseMode == null ? -1 : this.advertiseMode.ordinal());
    }

    protected Settings(Parcel in) {
        this.connectable = in.readByte() != 0;
        int tmpPowerLevel = in.readInt();
        this.powerLevel = tmpPowerLevel == -1 ? null : PowerLevel.values()[tmpPowerLevel];
        int tmpAdvertiseMode = in.readInt();
        this.advertiseMode = tmpAdvertiseMode == -1 ? null : AdvertiseMode.values()[tmpAdvertiseMode];
    }

    public static final Parcelable.Creator<Settings> CREATOR = new Parcelable.Creator<Settings>() {
        @Override
        public Settings createFromParcel(Parcel source) {
            return new Settings(source);
        }

        @Override
        public Settings[] newArray(int size) {
            return new Settings[size];
        }
    };
}
