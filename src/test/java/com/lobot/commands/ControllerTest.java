package com.lobot.commands;

import com.lobot.commands.domain.BotCommandEnum;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import tinyb.BluetoothGattCharacteristic;


public class ControllerTest {

    @Before
    public void setUp() throws Exception {
        PowerMockito.when(characteristic.writeValue(new byte[]{})).then(i -> true);
    }

    BluetoothGattCharacteristic characteristic = PowerMockito.mock(BluetoothGattCharacteristic.class);;

    @Test
    public void test_of_singleAction() {
        Controller ctrllr = new Controller();
        ctrllr.of(characteristic).add(BotCommandEnum.GRAB_TILT, 0).execute();

        Mockito.verify(characteristic).writeValue(Mockito.any());
        Mockito.verifyNoMoreInteractions(characteristic);
    }
}