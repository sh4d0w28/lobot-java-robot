package com.lobot.commands;

import com.lobot.commands.domain.CmdEnum;
import org.junit.Assert;
import org.junit.Test;
public class BuilderUtilTest {

    BuilderUtil bu = new BuilderUtil();
    byte[] actual = new byte[]{};

    @Test
    public void toBytes_grip_tilt() {

        actual = bu.toBytes(CmdEnum.GRAB_OPEN, null);
        Assert.assertArrayEquals(new byte[]{0x55, 0x55, 0x05, 0x02, 0x01, 0x52, 0x03}, actual);

        actual = bu.toBytes(CmdEnum.GRAB_CLOSE, null);
        Assert.assertArrayEquals(new byte[]{0x55, 0x55, 0x05, 0x02, 0x01, -104, 0x8}, actual);

        actual = bu.toBytes(CmdEnum.GRAB, 800);
        Assert.assertArrayEquals(new byte[]{0x55, 0x55, 0x05, 0x02, 0x01, 0x14, 0x05}, actual);
    }

    @Test
    public void toBytes_arm() {
        actual = bu.toBytes(CmdEnum.ARM_CTRL, -90);
        Assert.assertArrayEquals(new byte[]{0x55, 0x55, 0x05, 0x02, 0x03, -60, 0x09}, actual);
    }

    @Test
    public void toBytes_elbow() {
        actual = bu.toBytes(CmdEnum.ELBOW_CTRL, -90);
        Assert.assertArrayEquals(new byte[]{0x55, 0x55, 0x05, 0x02, 0x04, -48, 0x07}, actual);
    }

    @Test
    public void toBytes_hand() {
        actual = bu.toBytes(CmdEnum.HAND_CTRL, -90);
        Assert.assertArrayEquals(new byte[]{0x55, 0x55, 0x05, 0x02, 0x05, 0x2B, 0x02}, actual);
    }

    @Test
    public void toBytes_hand_turn() {
        actual = bu.toBytes(CmdEnum.HAND_TURN, -90);
        Assert.assertArrayEquals(new byte[]{0x55, 0x55, 0x05, 0x02, 0x06, -24, 0x03}, actual);
    }

    @Test
    public void toBytes_grip() {

        actual = bu.toBytes(CmdEnum.GRAB_TILT, 10);
        Assert.assertArrayEquals(new byte[]{0x55, 0x55, 0x05, 0x02, 0x02, 0x4B, 0x06}, actual);
    }

    @Test
    public void toBytes_move() {
        actual = bu.toBytes(CmdEnum.MOVE_FW, 10);
        Assert.assertArrayEquals(new byte[]{0x55, 0x55, 0x04, 0x01, 0x01, 0x0A}, actual);

        actual = bu.toBytes(CmdEnum.MOVE_BW, 10);
        Assert.assertArrayEquals(new byte[]{0x55, 0x55, 0x04, 0x01, 0x02, 0x0A}, actual);

        actual = bu.toBytes(CmdEnum.STOP, null);
        Assert.assertArrayEquals(new byte[]{0x55, 0x55, 0x04, 0x01, 0x00, 0x00}, actual);
    }

    @Test
    public void toBytes_turn() {
        actual = bu.toBytes(CmdEnum.TURN_LFT, 10);
        Assert.assertArrayEquals(new byte[]{0x55, 0x55, 0x04, 0x01, 0x03, 0x0A}, actual);

        actual = bu.toBytes(CmdEnum.TURN_RGT, 10);
        Assert.assertArrayEquals(new byte[]{0x55, 0x55, 0x04, 0x01, 0x04, 0x0A}, actual);
    }

}