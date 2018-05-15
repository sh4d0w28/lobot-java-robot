package com.lobot.commands;

import com.lobot.commands.domain.BotCommand;
import com.lobot.commands.domain.BotCommandEnum;
import tinyb.BluetoothGattCharacteristic;

import java.util.ArrayList;
import java.util.List;

public class Controller {

    private int handCtrlState = 0;


    private List<BotCommand> commands;
    private BluetoothGattCharacteristic characteristic;

    private BuilderUtil builderUtil = new BuilderUtil();

    public Controller of(BluetoothGattCharacteristic characteristic) {
        this.commands = new ArrayList<>();
        this.characteristic = characteristic;
        return this;
    }

    public Controller add(BotCommandEnum cmd, int param) {
        return add(cmd, param, 0L);
    }

    public Controller add(BotCommandEnum cmd, int param, long delay) {
        commands.add(new BotCommand(cmd, param, delay));
        return this;
    }

    public void execute() {
        for (BotCommand command : commands) {
            System.out.println(">> " + command.getCommand() + " (" + command.getValue() + ")");
            byte[] bytes = builderUtil.toBytes(command.getCommand(), command.getValue());
            try {
                Thread.sleep(command.getDelayMs());
            } catch (InterruptedException ex ) {
                System.out.println("NO DELAY");
            }
            characteristic.writeValue(bytes);
        }
    }
}
