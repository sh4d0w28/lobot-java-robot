package com.lobot.commands;

import com.lobot.commands.domain.BotCommand;
import com.lobot.commands.domain.CmdEnum;
import lombok.extern.java.Log;
import tinyb.BluetoothGattCharacteristic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Log(topic = "cmdlog")
public class Controller {

    private List<BotCommand> commands;
    private BluetoothGattCharacteristic characteristic;
    private BuilderUtil builderUtil = new BuilderUtil();

    private static final String LOG_WAIT = "WAIT %d";
    private static final String LOG_CMD = "%s %d";
    private static final String LOG_INTERRUPT = "WAIT INTERRUPTED";

    public HashMap<CmdEnum, Integer> status = null;

    private boolean debugMode = false;

    public Controller of(BluetoothGattCharacteristic characteristic) {
        return of(characteristic, false);
    }

    public Controller of(BluetoothGattCharacteristic characteristic, boolean debugMode) {
        initStatus();
        this.commands = new ArrayList<>();
        this.characteristic = characteristic;
        this.debugMode = debugMode;
        return this;
    }

    private void initStatus() {
        this.status = new HashMap<>();
        this.status.put(CmdEnum.GRAB, 0);
        this.status.put(CmdEnum.GRAB_TILT,0);
        this.status.put(CmdEnum.ARM_CTRL,0);
        this.status.put(CmdEnum.ELBOW_CTRL,0);
        this.status.put(CmdEnum.HAND_TURN,0);
        this.status.put(CmdEnum.HAND_CTRL,0);
    }

    public Controller add(CmdEnum cmd, int param) {
        commands.add(new BotCommand(cmd, param, 0L));
        return this;
    }

    public Controller pause(long delay) {
        commands.add(new BotCommand(CmdEnum.NOP, null, delay));
        return this;
    }

    public void execute() {
        for (BotCommand command : commands) {
            byte[] bytes = builderUtil.toBytes(command.getCommand(), command.getValue());
            if(command.getDelayMs() > 0) {
                log.info(String.format(LOG_WAIT, command.getDelayMs()));
                try {
                    Thread.sleep(command.getDelayMs());
                } catch (InterruptedException ex) {
                    log.warning(LOG_INTERRUPT);
                }
                continue;
            } else {
                log.info(String.format(LOG_CMD, command.getCommand(), command.getValue()));
            }
            status.put(command.getCommand(), command.getValue());
            if (!debugMode) {
                characteristic.writeValue(bytes);
            }
        }
    }
}
