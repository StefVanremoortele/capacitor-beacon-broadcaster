package com.nineengineering.capacitor.plugins;

import android.bluetooth.le.AdvertiseData;

public interface AdvertiseDataGenerator {

    /**
     * The only purpose of this method is to generate an AdvertiseData object from
     * a data model being in the class implementing this interface.
     *
     * @return
     */
    AdvertiseData generateAdvertiseData();

}
