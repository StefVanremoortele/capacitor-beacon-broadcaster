package com.nineengineering.capacitor.plugins.model;

import android.bluetooth.le.AdvertiseData;
import android.os.Parcel;
import android.os.ParcelUuid;
import android.os.Parcelable;

//import net.alea.beaconsimulator.bluetooth.ByteTools;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

import com.nineengineering.capacitor.plugins.ByteTools;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.UUID;

public class EddystoneUID extends Eddystone implements Parcelable {

//    private static final Logger sLogger = LoggerFactory.getLogger(EddystoneUID.class);

    private String namespace;
    private String instance;
    private int power;

    public EddystoneUID(String namespace, String instance) {
        this.namespace = namespace;
        this.instance = instance;
        this.power = -65;
    }

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public String getInstance() {
        return instance;
    }

    public void setInstance(String instance) {
        this.instance = instance;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public static String generateUidNamespace() {
//        TODO: remove hard-coded-code :)
        return "f46e91e35cd7d2c66369";
//        String randomUUID = UUID.randomUUID().toString();
//        return randomUUID.subSequence(0, 8)+randomUUID.substring(24, 36);
    }

    @Override
    public AdvertiseData generateAdvertiseData() {
        byte txPower = (byte)power;
        byte[] namespaceBytes = ByteTools.toByteArray(namespace);
        byte[] instanceBytes = ByteTools.toByteArray(instance);
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            os.write(new byte[]{FRAME_TYPE_UID, txPower});
            os.write(namespaceBytes);
            os.write(instanceBytes);
        } catch (IOException e) {
//            sLogger.warn("Error while generating ADData", e);
            System.out.print("Error while generating ADData");
            return null;
        }

        byte[] serviceData = os.toByteArray();

        final ParcelUuid parcelUuid = ParcelUuid.fromString(Eddystone.EDDYSTONE_SERVICE_UUID);

        return new AdvertiseData.Builder()
                .addServiceUuid(parcelUuid)
                .addServiceData(parcelUuid, serviceData)
                .setIncludeDeviceName(false)
                .setIncludeTxPowerLevel(false)
                .build();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.namespace);
        dest.writeString(this.instance);
        dest.writeInt(this.power);
    }

    protected EddystoneUID(Parcel in) {
        this.namespace = in.readString();
        this.instance = in.readString();
        this.power = in.readInt();
    }

    public static final Parcelable.Creator<EddystoneUID> CREATOR = new Parcelable.Creator<EddystoneUID>() {
        @Override
        public EddystoneUID createFromParcel(Parcel source) {
            return new EddystoneUID(source);
        }

        @Override
        public EddystoneUID[] newArray(int size) {
            return new EddystoneUID[size];
        }
    };
}