package com.lobot.commands;

import com.lobot.commands.domain.BotCommandEnum;
import org.junit.Assert;
import org.junit.Test;
public class BuilderUtilTest {

    @Test
    public void toBytes_grip_tilt() {
        BuilderUtil bu = new BuilderUtil();
        byte[] actual = bu.toBytes(BotCommandEnum.GRAB_TILT, 10);
        Assert.assertArrayEquals(new byte[]{0x55, 0x55, 0x05, 0x02, 0x02, 0x4B, 0x06}, actual);
    }
}