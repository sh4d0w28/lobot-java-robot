package com.lobot.commands;

import com.lobot.commands.domain.CmdEnum;

public class Validator {

    static final int OPENED = 350;
    static final int CLOSED = 1700;

    public static final int GRAB_TILT_MIN = -90;
    public static final int GRAB_TILT_MAX = 90;

    public static final int HAND_ANGLE_MIN = -85;
    public static final int HAND_ANGLE_MAX = 85;

    public static int validateFor(CmdEnum cmd, int value) {
        switch (cmd) {
            case GRAB:
                return validateGrab(value);
            case HAND_CTRL:
                return validateHandAngle(value);
            case ELBOW_CTRL:
                return validateElbowAngle(value);
            case ARM_CTRL:
                return validateArmAngle(value);
            case GRAB_TILT:
                return validateGrabTitleAngle(value);
            case HAND_TURN:
                return validateHandTurnAngle(value);
            default:
                return 0;
        }
    }

    public static int checkBorders(int min, int angle, int max) {
        return Math.min(Math.max(min, angle), max);
    }

    public static int validateGrab(int grab) {
        return checkBorders(OPENED, grab, CLOSED);
    }

    public static int validateGrabTitleAngle(int angle) {
        return checkBorders(GRAB_TILT_MIN, angle, GRAB_TILT_MAX);
    }

    public static int validateArmAngle(int angle) {
        return checkBorders(-45, angle, 90);
    }

    public static int validateElbowAngle(int angle) {
        return checkBorders(-90, angle, 45);
    }

    public static int validateHandAngle(int angle) {
        return checkBorders(HAND_ANGLE_MIN, angle, HAND_ANGLE_MAX);
    }

    public static int validateHandTurnAngle(int angle) {
        return checkBorders(-45, angle, 45);
    }

    public static int validateAheadSpeed(int aheadSpeed) {
        return checkBorders(0, aheadSpeed, 10);
    }

    public static int validateCornerSpeed(int cornerSpeed) {
        return checkBorders(0, cornerSpeed, 10);
    }
}
