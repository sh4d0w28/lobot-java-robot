package com.lobot;

import com.lobot.commands.Controller;
import com.lobot.commands.domain.BotCommandEnum;
import com.lobot.connect.Connector;
import tinyb.BluetoothGattCharacteristic;

public class Application {

    public static void main(String[] args) throws InterruptedException {
        Connector connector = new Connector();
        BluetoothGattCharacteristic controlPoint = connector.getControlCharacteristic();

        new Controller().of(controlPoint)
                .add(BotCommandEnum.GRAB_OPEN, 0 )
                .add(BotCommandEnum.GRAB_TILT, 90 )
                .execute();
    }
}
