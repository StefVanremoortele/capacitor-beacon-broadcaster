package com.nineengineering.capacitor.plugins.model;
import com.nineengineering.capacitor.plugins.AdvertiseDataGenerator;

public abstract class Eddystone implements AdvertiseDataGenerator {

    protected final static String EDDYSTONE_SERVICE_UUID = "0000FEAA-0000-1000-8000-00805F9B34FB";
    protected final static byte FRAME_TYPE_UID = 0x00;
    protected final static byte FRAME_TYPE_URL = 0x10;
    protected final static byte FRAME_TYPE_TLM = 0x20;
    protected final static byte FRAME_TYPE_EID = 0x30;

    protected final static byte TLM_VERSION_STANDARD = 0x00;
    protected final static byte TLM_VERSION_ENCRYPTED = 0x01;

}
