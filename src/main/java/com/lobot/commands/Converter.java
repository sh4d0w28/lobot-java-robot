package com.lobot.commands;

class Converter {

    static final int OPENED = 350;
    static final int CLOSED = 1700;


    static int convertAndValidateGrab(int grab) {
        if(grab < OPENED) {
            grab = OPENED;
        }
        if(grab > CLOSED) {
            grab = CLOSED;
        }
        return grab + 500;
    }

    static int convertAndValidateGrabTitleAngle(int angle) {
        if(angle < -90) {
            angle = -90;
        }
        if(angle > 90) {
            angle = 90;
        }
        return Math.round((angle + 90)*100 / 9) + 500; // 0---2000

    }

    static int convertAndValidateArmAngle(int angle) {
        angle = -angle;
        if(angle < -45) {
            angle = -45;
        }
        if(angle > 90) {
            angle = 90;
        }

        return Math.round((angle + 90)*100 / 9) + 500; // 0---2000
    }

    static int convertAndValidateElbowAngle(int angle) {
        angle = -angle;
        if(angle < -90) {
            angle = -90;
        }
        if(angle > 45) {
            angle = 45;
        }

        return Math.round((angle + 90)*100 / 9) + 500; // 0---2000
    }

    static int convertAndValidateHandAngle(int angle) {
        if(angle < -85) {
            angle = -85;
        }
        if(angle > 85) {
            angle = 85
            ;
        }

        return Math.round((angle + 90)*100 / 9) + 500; // 0---2000
    }

    static int convertAndValidateHandTurnAngle(int angle) {
        if(angle < -45) {
            angle = -45;
        }
        if(angle > 45) {
            angle = 45;
        }

        return Math.round((angle + 90)*100 / 9) + 500; // 0---2000
    }

    static int convertAndValidateAheadSpeed(int aheadSpeed) {
        if (aheadSpeed < 0) {
            aheadSpeed = 0;
        }
        if (aheadSpeed > 10) {
            aheadSpeed = 10;
        }
        return aheadSpeed;
    }

    static int convertAndValidateCornerSpeed(int cornerSpeed) {
        if (cornerSpeed < 0) {
            cornerSpeed = 0;
        }
        if (cornerSpeed > 10) {
            cornerSpeed = 10;
        }
        return cornerSpeed;
    }
}
