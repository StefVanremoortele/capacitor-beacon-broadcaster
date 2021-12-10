package com.nineengineering.capacitor.plugins;



import android.bluetooth.le.AdvertiseData;
import android.os.Parcel;
import android.os.Parcelable;

public class ExtendedAdvertiseData implements Parcelable {

    private final AdvertiseData mAdData;
    private String mLocalName;

    public ExtendedAdvertiseData(AdvertiseData adData) {
        mAdData = adData;
    }

    public AdvertiseData getAdvertiseData() {
        return mAdData;
    }

    public String getLocalName() {
        return mLocalName;
    }

    public void setLocalName(String localName) {
        this.mLocalName = localName;
    }


    protected ExtendedAdvertiseData(Parcel in) {
        mAdData = in.readParcelable(AdvertiseData.class.getClassLoader());
        mLocalName = in.readString();
    }

    public static final Creator<ExtendedAdvertiseData> CREATOR = new Creator<ExtendedAdvertiseData>() {
        @Override
        public ExtendedAdvertiseData createFromParcel(Parcel in) {
            return new ExtendedAdvertiseData(in);
        }

        @Override
        public ExtendedAdvertiseData[] newArray(int size) {
            return new ExtendedAdvertiseData[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(mAdData, flags);
        dest.writeString(mLocalName);
    }
}
