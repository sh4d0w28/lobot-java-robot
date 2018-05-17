package com.lobot;

import com.lobot.commands.Controller;
import com.lobot.commands.domain.CmdEnum;
import com.lobot.connect.Connector;
import tinyb.BluetoothGattCharacteristic;
import tinyb.BluetoothManager;

public class Application {

    public static Config config = new Config();

    public static void main(String[] args) throws InterruptedException {
        Connector connector = new Connector(BluetoothManager.getBluetoothManager(), 4000L);

        BluetoothGattCharacteristic controlPoint = connector.getControlCharacteristic();

        new Controller().of(controlPoint)
                .add(CmdEnum.GRAB_OPEN, 0 )
                .add(CmdEnum.GRAB_TILT, 90 )
                .add(CmdEnum.HAND_CTRL, 20)
                .pause(2000)
                .add(CmdEnum.HAND_CTRL, 0)
                .add(CmdEnum.GRAB_TILT, 0)
                .add(CmdEnum.GRAB_CLOSE, 0)
                .execute();
    }
}
