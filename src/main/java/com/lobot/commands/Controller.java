package com.lobot.commands;

import com.lobot.commands.domain.BotCommand;
import com.lobot.commands.domain.CmdEnum;
import lombok.extern.apachecommons.CommonsLog;
import lombok.extern.java.Log;
import tinyb.BluetoothGattCharacteristic;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Log(topic = "cmdlog")
public class Controller {

    private List<BotCommand> commands;
    private BluetoothGattCharacteristic characteristic;

    private BuilderUtil builderUtil = new BuilderUtil();

    public Controller of(BluetoothGattCharacteristic characteristic) {
        this.commands = new ArrayList<>();
        this.characteristic = characteristic;
        return this;
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
                log.info("WAIT "  + command.getDelayMs());
                try {
                    Thread.sleep(command.getDelayMs());
                } catch (InterruptedException ex) {
                    log.warning("WAIT INTERRUPT");
                }
                continue;
            } else {
                log.info(command.getCommand() + " (" + command.getValue() + ")");
            }
            characteristic.writeValue(bytes);
        }
    }
}
