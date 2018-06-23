package com.lobot;

import com.lobot.commands.controllers.ContiniusController;
import com.lobot.commands.controllers.Controller;
import com.lobot.commands.domain.CmdEnum;
import com.lobot.connect.Connector;
import tinyb.BluetoothGattCharacteristic;
import tinyb.BluetoothManager;

public class Application {

    public static Config config = new Config();

    public static void main(String[] args) throws InterruptedException {
        Connector connector = new Connector(BluetoothManager.getBluetoothManager(), 4000L);
        BluetoothGattCharacteristic controlPoint = connector.getControlCharacteristic();
        ContiniusController c = new ContiniusController().of(controlPoint);
        c.start();
        c.add(CmdEnum.GRAB_TILT, 20);
    }
}
