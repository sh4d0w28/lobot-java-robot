package com.lobot.commands;

import com.lobot.commands.controllers.Controller;
import com.lobot.commands.domain.CmdEnum;
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
        ctrllr.of(characteristic).add(CmdEnum.GRAB_TILT, 0).execute();

        Mockito.verify(characteristic).writeValue(Mockito.any());
        Mockito.verifyNoMoreInteractions(characteristic);
    }

    @Test
    public void test_of_ActionAndWait_1() {
        Controller ctrllr = new Controller();
        ctrllr.of(characteristic).add(CmdEnum.GRAB_TILT, 0).pause(2000).add(CmdEnum.HAND_CTRL, 20).execute();

        Mockito.verify(characteristic).writeValue(new byte[]{0x55,0x55,0x05,0x02,0x02,-36,0x5});
        Mockito.verify(characteristic).writeValue(new byte[]{0x55,0x55,0x05,0x02,0x05,-70,0x06});
        Mockito.verifyNoMoreInteractions(characteristic);
    }
}