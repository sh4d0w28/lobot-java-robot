package com.lobot.connect;

import com.lobot.Config;
import lombok.Value;
import lombok.extern.java.Log;
import tinyb.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;

@Log(topic = "connector")
public class Connector {

    private BluetoothManager manager;
    private Long triesInterval;

    private Config config = new Config();

    private boolean running = true;

    public Connector(BluetoothManager manager, Long triesInterval) {
        this.manager = manager;
        this.triesInterval = triesInterval;
    }

    private BluetoothDevice getDevice(String address) throws Exception {

        BluetoothDevice desired = null;
        boolean discoveryStarted = manager.startDiscovery();
        log.info("The discovery started: " + (discoveryStarted ? "true" : "false"));

        for (int i = 0; (i < 15) && running; ++i) {
            List<BluetoothDevice> list = manager.getDevices();
            if (list == null) {
                log.warning("Attempt ["+i+" / 15] - no devices around");
            }

            for (BluetoothDevice device : list) {
                if (device.getAddress().equals(address))
                    desired = device;
            }

            if (desired != null) {
                log.info("Found desired device");
                return desired;
            }
            Thread.sleep(triesInterval);
        }

        try {
            manager.stopDiscovery();
        } catch (BluetoothException e) {
            log.log(Level.SEVERE, "Discovery could not be stopped", e);
        }

        throw new Exception("No desired device found");
    }

    private BluetoothGattService getService(BluetoothDevice device, String UUID) throws Exception {
        log.info("Services exposed by device:");
        BluetoothGattService tempService = null;
        List<BluetoothGattService> bluetoothServices;
        do {
            bluetoothServices = device.getServices();
            if (bluetoothServices == null) {
                throw new Exception("No services available");
            }
            for (BluetoothGattService service : bluetoothServices) {
                log.info("UUID: " + service.getUUID());
                if (service.getUUID().equals(UUID))
                    tempService = service;
            }
            Thread.sleep(4000);
        } while (bluetoothServices.isEmpty() && running);

        if (tempService == null) {
            throw new Exception("No desired service found");
        }
        log.info("Found desired service");

        return tempService;
    }

    private BluetoothGattCharacteristic getCharacteristic(BluetoothGattService service, String UUID) throws Exception {
        List<BluetoothGattCharacteristic> characteristics = service.getCharacteristics();
        if (characteristics == null) {
            throw new Exception("No characteristics available");
        }

        for (BluetoothGattCharacteristic characteristic : characteristics) {
            if (characteristic.getUUID().equals(UUID))
                log.info("Found the desired characteristic");
                return characteristic;
        }

        throw new Exception("No desired service found");
    }

    public BluetoothGattCharacteristic getControlCharacteristic() {

        BluetoothDevice robot = null;
        try {
            robot = getDevice(config.getTargetMac());

            if (robot.connect())
                log.info("Desired device connected");
            else {
                throw new Exception("Could not connect device.");
            }

            Lock lock = new ReentrantLock();
            Condition cv = lock.newCondition();
            Runtime.getRuntime().addShutdownHook(new Thread() {
                public void run() {
                    running = false;
                    lock.lock();
                    try {
                        cv.signalAll();
                    } finally {
                        lock.unlock();
                    }
                }
            });

            BluetoothGattService tempService = getService(robot, config.getTargetServiceControlUUIID());
            BluetoothGattCharacteristic tempChar = getCharacteristic(tempService, config.getTargetCharacteristicControlUUID());
            return tempChar;
        } catch (Exception ex) {
            if(robot != null) {
                robot.disconnect();
            }
            log.log(Level.SEVERE, "Error on device connection", ex);
        }
        return null;
    }
}
