package com.app;

import com.lobot.commands.Controller;
import com.lobot.connect.Connector;
import tinyb.BluetoothGattCharacteristic;
import tinyb.BluetoothManager;

import javax.swing.*;

public class Application {

    public static void main(String[] args) {

        Connector connector = new Connector(BluetoothManager.getBluetoothManager(), 4000L);
        BluetoothGattCharacteristic controlPoint = connector.getControlCharacteristic();

        Controller controller = new Controller().of(controlPoint, true);

        MainFrame mainFrame = new MainFrame(controller);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setVisible(true);
    }
}
