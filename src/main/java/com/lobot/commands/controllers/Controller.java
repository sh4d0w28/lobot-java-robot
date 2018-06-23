package com.lobot.commands.controllers;

import com.lobot.commands.domain.BotCommand;
import com.lobot.commands.domain.CmdEnum;
import lombok.extern.java.Log;
import tinyb.BluetoothGattCharacteristic;

import java.util.ArrayList;
import java.util.List;

@Log(topic = "cmdlog")
public class Controller extends BaseController {

    private List<BotCommand> commands;

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

    private void addCommand(CmdEnum cmd, int param) {
        commands.add(new BotCommand(cmd, param, 0L));
    }

    public Controller add(CmdEnum cmd, int param) {
        addCommand(cmd, param);
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
