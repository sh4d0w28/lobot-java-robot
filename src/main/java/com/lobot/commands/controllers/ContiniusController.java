package com.lobot.commands.controllers;

import com.lobot.commands.domain.BotCommand;
import com.lobot.commands.domain.CmdEnum;
import lombok.extern.java.Log;
import tinyb.BluetoothGattCharacteristic;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

@Log(topic = "continiusLog")
public class ContiniusController extends BaseController {

    Queue<BotCommand> commandQueue;

    public ContiniusController of(BluetoothGattCharacteristic characteristic) {
        return of(characteristic, false);
    }

    public ContiniusController of(BluetoothGattCharacteristic characteristic, boolean debugMode) {
        this.initStatus();
        this.commandQueue = new LinkedList<>();
        this.characteristic = characteristic;
        this.debugMode = debugMode;
        return this;
    }

    public void start() {
        ExecutorService executor = Executors.newFixedThreadPool(1);
        Runnable backgroundTask = () -> {
            for(;;) {
                try {
                    Thread.sleep(200L);
                } catch(InterruptedException ex) {
                    ;
                }
                if(commandQueue.peek() == null) {
                    dummy();
                }
                BotCommand command = commandQueue.poll();
                if(command == null) {
                    continue;
                }
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
        };
        executor.execute(backgroundTask);
    }

    public ContiniusController add(CmdEnum cmd, int param) {
        this.commandQueue.add(new BotCommand(cmd, param, 0L));
        return this;
    }

    private void dummy() {
        this.commandQueue.add(new BotCommand(CmdEnum.GRAB, this.status.get(CmdEnum.GRAB),0L));
        this.commandQueue.add(new BotCommand(CmdEnum.GRAB_TILT, this.status.get(CmdEnum.GRAB_TILT),0L));
        this.commandQueue.add(new BotCommand(CmdEnum.ARM_CTRL, this.status.get(CmdEnum.ARM_CTRL),0L));
        this.commandQueue.add(new BotCommand(CmdEnum.ELBOW_CTRL, this.status.get(CmdEnum.ELBOW_CTRL),0L));
        this.commandQueue.add(new BotCommand(CmdEnum.HAND_CTRL, this.status.get(CmdEnum.HAND_CTRL),0L));
        this.commandQueue.add(new BotCommand(CmdEnum.HAND_TURN, this.status.get(CmdEnum.HAND_TURN),0L));
    }
}
