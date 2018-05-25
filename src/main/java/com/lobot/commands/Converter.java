package com.lobot.commands;

class Converter {

    static int toServo(int angle) {
        // 0 to 1500, 90 to 2500, -90 to 500
        return Math.round((angle + 90)*100 / 9) + 500; // 0---2000
    }

    static int convertAndValidateGrab(int grab) {
        grab = Validator.validateGrab(grab);
        return grab + 500;
    }

    static int convertAndValidateGrabTitleAngle(int angle) {
        angle = Validator.validateGrabTitleAngle(angle);
        return toServo(angle);
    }

    static int convertAndValidateArmAngle(int angle) {
        angle = -angle;
        angle = Validator.validateArmAngle(angle);
        return toServo(angle);
    }

    static int convertAndValidateElbowAngle(int angle) {
        angle = -angle;
        angle = Validator.validateElbowAngle(angle);
        return toServo(angle);
    }

    static int convertAndValidateHandAngle(int angle) {
        angle = Validator.validateHandAngle(angle);
        return toServo(angle);
    }

    static int convertAndValidateHandTurnAngle(int angle) {
        angle = Validator.validateHandTurnAngle(angle);
        return toServo(angle);
    }

    static int convertAndValidateAheadSpeed(int aheadSpeed) {
        return Validator.validateAheadSpeed(aheadSpeed);
    }

    static int convertAndValidateCornerSpeed(int cornerSpeed) {
        return Validator.validateCornerSpeed(cornerSpeed);
    }
}
