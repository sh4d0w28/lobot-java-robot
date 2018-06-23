package com.lobot.commands.controllers;

import com.lobot.commands.BuilderUtil;
import com.lobot.commands.domain.CmdEnum;
import tinyb.BluetoothGattCharacteristic;

import java.util.HashMap;

public class BaseController {

    protected BluetoothGattCharacteristic characteristic;
    protected BuilderUtil builderUtil = new BuilderUtil();
    protected boolean debugMode = false;

    protected static final String LOG_WAIT = "WAIT %d";
    protected static final String LOG_CMD = "%s %d";
    protected static final String LOG_INTERRUPT = "WAIT INTERRUPTED";

    public HashMap<CmdEnum, Integer> status = null;

    protected void initStatus() {
        this.status = new HashMap<>();
        this.status.put(CmdEnum.GRAB, 0);
        this.status.put(CmdEnum.GRAB_TILT,0);
        this.status.put(CmdEnum.ARM_CTRL,0);
        this.status.put(CmdEnum.ELBOW_CTRL,0);
        this.status.put(CmdEnum.HAND_TURN,0);
        this.status.put(CmdEnum.HAND_CTRL,0);
    }
}
