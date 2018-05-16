package com.lobot.connect;

import org.junit.Test;
import tinyb.BluetoothDevice;
import tinyb.BluetoothGattCharacteristic;
import tinyb.BluetoothGattService;
import tinyb.BluetoothManager;

import java.util.Collection;
import java.util.Collections;

import static org.mockito.Mockito.*;

public class ConnectorTest {

    @Test
    public void name() {

        BluetoothManager manager = BluetoothManager.getBluetoothManager();
        BluetoothManager man = spy(manager);

        BluetoothGattCharacteristic controlCharacteristic = mock(BluetoothGattCharacteristic.class);
        when(controlCharacteristic.getUUID()).thenReturn("0000ffe1-0000-1000-8000-00805f9b34fb");

        BluetoothGattService controlService = mock(BluetoothGattService.class);
        when(controlService.getUUID()).thenReturn("0000ffe0-0000-1000-8000-00805f9b34fb");
        when(controlService.getCharacteristics()).thenReturn(Collections.singletonList(controlCharacteristic));

        BluetoothDevice device = mock(BluetoothDevice.class);
        when(device.getAddress()).thenReturn("3C:A3:08:AC:4A:03");
        when(device.connect()).thenReturn(true);
        when(device.getServices()).thenReturn(Collections.singletonList(controlService));

        when(man.startDiscovery()).thenReturn(true);
        when(man.getDevices()).thenReturn(Collections.singletonList(device));



        Connector cn = new Connector(man, 0L);
        cn.getControlCharacteristic();
    }
}