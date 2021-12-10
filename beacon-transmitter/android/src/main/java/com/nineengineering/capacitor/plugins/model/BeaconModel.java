
package com.nineengineering.capacitor.plugins.model;

import android.bluetooth.le.AdvertiseSettings;
import android.os.Parcel;
import android.os.Parcelable;
//import android.support.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import com.nineengineering.capacitor.plugins.AdvertiseDataGenerator;
import com.nineengineering.capacitor.plugins.ExtendedAdvertiseData;



import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

public class BeaconModel implements Parcelable {

//    private static final Logger sLogger = LoggerFactory.getLogger(BeaconModel.class);


    private UUID id;
    private String name;
    private Settings settings;

    private EddystoneUID eddystoneUID;


    public BeaconModel(String namespace, String instance) {
        this("", UUID.randomUUID(), new Settings());
        this.eddystoneUID = new EddystoneUID(namespace, instance);
    }

    private BeaconModel(String name, UUID id, Settings settings) {
        this.name = name;
        this.id = id;
        this.settings = settings;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Settings getSettings() {
        return settings;
    }

    public void setSettings(Settings settings) {
        this.settings = settings;
    }

    public EddystoneUID getEddystoneUID() {
        return eddystoneUID;
    }

    public void setEddystoneUID(EddystoneUID eddystoneUID) {
        this.eddystoneUID = eddystoneUID;
    }

    public String generateBeaconName() {
        final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        return "eddy-" + dateFormat.format(new Date());
    }

    public String serializeToJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public static BeaconModel parseFromJson(String json) {
        Gson gson = new Gson();
        BeaconModel beacon = null;
        try {
            beacon = gson.fromJson(json, BeaconModel.class);
        }
        catch (JsonSyntaxException e) {
            System.out.println(e);
            // sLogger.warn("Error while parsing JSON", e);
        }
        return beacon;
    }

    @Override
    public String toString() {
        return serializeToJson();
    }

    public AdvertiseSettings generateADSettings() {
        if (settings != null) {
            return settings.generateADSettings();
        }
        else {
            return null;
        }
    }

//    @Nullable
    public ExtendedAdvertiseData generateADData() {
        AdvertiseDataGenerator conversion = eddystoneUID;
        if (conversion != null) {
            return new ExtendedAdvertiseData(conversion.generateAdvertiseData());
        }
        else {
            return null;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeSerializable(this.id);
        dest.writeString(this.name);
        dest.writeParcelable(this.settings, flags);
        dest.writeParcelable(this.eddystoneUID, flags);
    }

    protected BeaconModel(Parcel in) {
        this.id = (UUID) in.readSerializable();
        this.name = in.readString();
        this.settings = in.readParcelable(Settings.class.getClassLoader());
        this.eddystoneUID = in.readParcelable(EddystoneUID.class.getClassLoader());
    }

    public static final Creator<BeaconModel> CREATOR = new Creator<BeaconModel>() {
        @Override
        public BeaconModel createFromParcel(Parcel source) {
            return new BeaconModel(source);
        }

        @Override
        public BeaconModel[] newArray(int size) {
            return new BeaconModel[size];
        }
    };
}