package com.lobot.commands;

class Converter {

    static final int OPENED = 350;
    static final int CLOSED = 1700;

    static int toServo(int angle) {
        // 0 to 1500, 90 to 2500, -90 to 500
        return Math.round((angle + 90)*100 / 9) + 500; // 0---2000
    }

    static int checkBorders(int min, int angle, int max) {
        return Math.min(Math.max(min, angle), max);
    }

    static int convertAndValidateGrab(int grab) {
        grab = checkBorders(OPENED, grab, CLOSED);
        return grab + 500;
    }

    static int convertAndValidateGrabTitleAngle(int angle) {
        angle = checkBorders(-90, angle, 90);
        return toServo(angle);
    }

    static int convertAndValidateArmAngle(int angle) {
        angle = -angle;
        angle = checkBorders(-45, angle, 90);
        return toServo(angle);
    }

    static int convertAndValidateElbowAngle(int angle) {
        angle = -angle;
        angle = checkBorders(-90, angle, 45);
        return toServo(angle);
    }

    static int convertAndValidateHandAngle(int angle) {
        angle = checkBorders(-85, angle, 85);
        return toServo(angle);
    }

    static int convertAndValidateHandTurnAngle(int angle) {
        angle = checkBorders(-45, angle, 45);
        return toServo(angle);
    }

    static int convertAndValidateAheadSpeed(int aheadSpeed) {
        return checkBorders(0, aheadSpeed, 10);
    }

    static int convertAndValidateCornerSpeed(int cornerSpeed) {
        return checkBorders(0, cornerSpeed, 10);
    }
}
