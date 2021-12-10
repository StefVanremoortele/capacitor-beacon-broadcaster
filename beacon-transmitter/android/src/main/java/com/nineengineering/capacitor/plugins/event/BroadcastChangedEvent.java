package com.nineengineering.capacitor.plugins.event;
import java.util.UUID;

public class BroadcastChangedEvent {

    private final UUID mId;
    private final boolean mIsBroadcasting;
    private final int mActiveBroadcast;
    private final boolean mIsFailure;

    public BroadcastChangedEvent(UUID id, boolean isBroadcasting, int activeBroadcast) {
        this(id, isBroadcasting, activeBroadcast, false);
    }

    public BroadcastChangedEvent(UUID id, boolean isBroadcasting,int activeBroadcast, boolean isFailure) {
        mId = id;
        mIsBroadcasting = isBroadcasting;
        mActiveBroadcast = activeBroadcast;
        mIsFailure = isFailure;
    }

    public UUID getBeaconId() {
        return mId;
    }

    public boolean isBroadcasting() {
        return mIsBroadcasting;
    }

    public boolean isFailure() {
        return mIsFailure;
    }

    public int getActiveBroadcast() {
        return mActiveBroadcast;
    }
}
