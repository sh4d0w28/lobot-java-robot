package com.lobot.commands;

import com.lobot.commands.domain.CmdEnum;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BuilderUtil {

    private static byte CMD_MOVE = 0x01;
    private static byte CMD_SERV = 0x02;
    private static byte CMD_BATT = 0x03;
    private static byte CMD_DIST = 0x04;

    private final Byte[] magic = new Byte[]{0x55, 0x55};

    private byte[] buildCmd(Byte ...args) {
        List<Byte> both = new ArrayList<>(Arrays.asList(magic.clone()));
        Byte length = (byte)(args.length + 1);
        both.add(length);

        for (int i = 0; i < args.length; i++) {
            both.add(args[i]);
        }
        Byte[] resultB = both.toArray(new Byte[args.length + 3]);
        int j=0;
        byte[] result = new byte[resultB.length];
        for(Byte b: resultB) {
            result[j++] = b.byteValue();
        }
        return result;
    }

    private byte[] getMoveAhead(int aHeadSpeed) {
        int value = Converter.convertAndValidateAheadSpeed(aHeadSpeed);
        return buildCmd(CMD_MOVE, (byte)0x01, (byte)value);
    }

    private byte[] getMoveBack(int aHeadSpeed) {
        int value = Converter.convertAndValidateAheadSpeed(aHeadSpeed);
        return buildCmd(CMD_MOVE, (byte)0x02, (byte)value);
    }

    private byte[] getTurnLeft(int cornerSpeed) {
        int value = Converter.convertAndValidateCornerSpeed(cornerSpeed);
        return buildCmd(CMD_MOVE, (byte)0x03, (byte)value);
    }

    private byte[] getTurnRight(int cornerSpeed) {
        int value = Converter.convertAndValidateCornerSpeed(cornerSpeed);
        return buildCmd(CMD_MOVE, (byte)0x04, (byte)value);
    }

    private byte[] getStop() {
        return buildCmd(CMD_MOVE, (byte)0x00, (byte)0x00);
    }

    private byte[] setGrip(int seekbarPositon) {
        int value = Converter.convertAndValidateGrab(seekbarPositon);
        byte Low = (byte) (value & 0x00ff);
        byte High = (byte) ((value >> 8) & 0x00ff);
        return buildCmd(CMD_SERV, (byte)0x01, Low, High);
    }

    private byte[] setGripTilt(int angle) {
        int value = Converter.convertAndValidateGrabTitleAngle(angle);
        byte Low = (byte) (value & 0x00ff);
        byte High = (byte) ((value >> 8) & 0x00ff);
        return buildCmd(CMD_SERV, (byte)0x02, Low, High);
    }

    private byte[] setArmUpDown(int angle) {
        int value = Converter.convertAndValidateArmAngle(angle);
        byte Low = (byte) (value & 0x00ff);
        byte High = (byte) ((value >> 8) & 0x00ff);
        return buildCmd(CMD_SERV, (byte)0x03, Low, High);
    }

    private byte[] setElbowUpDown(int angle) {
        int value = Converter.convertAndValidateElbowAngle(angle);
        byte Low = (byte) (value & 0x00ff);
        byte High = (byte) ((value >> 8) & 0x00ff);
        return buildCmd(CMD_SERV, (byte)0x04, Low, High);
    }

    private byte[] setHandUpDown(int angle) {
        int value = Converter.convertAndValidateHandAngle(angle);
        byte Low = (byte) (value & 0x00ff);
        byte High = (byte) ((value >> 8) & 0x00ff);
        return buildCmd(CMD_SERV, (byte)0x05, Low, High);
    }

    private byte[] setHandLeftRight(int angle) {
        int value = Converter.convertAndValidateHandTurnAngle(angle);
        byte Low = (byte) (value & 0x00ff);
        byte High = (byte) ((value >> 8) & 0x00ff);
        return buildCmd(CMD_SERV, (byte)0x06, Low, High);
    }

    byte[] toBytes(CmdEnum command, Integer value) {

        switch (command) {
            case GRAB:
                return setGrip(value);
            case GRAB_OPEN:
                return setGrip(350);
            case GRAB_CLOSE:
                return setGrip(1700);
            case GRAB_TILT:
                return setGripTilt(value);
            case ARM_CTRL:
                return setArmUpDown(value);
            case ELBOW_CTRL:
                return setElbowUpDown(value);
            case HAND_CTRL:
                return setHandUpDown(value);
            case HAND_TURN:
                return setHandLeftRight(value);

            case STOP:
                return getStop();
            case MOVE_FW:
                return getMoveAhead(value);
            case MOVE_BW:
                return getMoveBack(value);
            case TURN_LFT:
                return getTurnLeft(value);
            case TURN_RGT:
                return getTurnRight(value);

            default:
                return new byte[0];
        }
    }

    /*public byte[] getBattery(){return buildCmd(CMD_BATT);}public byte[] getDistance(){return buildCmd(CMD_DIST);}*/
}
