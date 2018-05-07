package com.ubs.opsit.interviews;

import java.util.Arrays;

/**
 *
 * Created by jeen on 5/7/2018.
 */
public class BerlinTimeConverter implements TimeConverter {

    enum LampColor {
        YELLOW('Y'), RED('R'), NONE('O');

        LampColor(final char signal){
            this.signal = signal;
        }
        private char signal;

        public char getSignal() {
            return this.signal;
        }
    }

    @Override
    public String convertTime(final String timeToDisplay) {
        final String[] value = timeToDisplay.split(":");

        return blinkFromSeconds(Integer.valueOf(value[2])) + System.lineSeparator() +
               getHoursSignal(Integer.valueOf(value[0])) + System.lineSeparator() +
               getMinutesSignal(Integer.valueOf(value[1]));
    }

    private String getHoursSignal(final int hour){
        final StringBuilder hoursSignal = new StringBuilder();

        hoursSignal.append(getLampSignal((new char[4]), (hour / 5), LampColor.RED.getSignal()));
        hoursSignal.append(System.lineSeparator());
        hoursSignal.append(getLampSignal((new char[4]), (hour % 5), LampColor.RED.getSignal()));

        return hoursSignal.toString();
    }

    private String getLampSignal(final char[] lampsInRow,final int noOfLampsOn,final char lampColor) {
        if(noOfLampsOn > 0) {
            Arrays.fill(lampsInRow, 0, noOfLampsOn, lampColor);
            if(noOfLampsOn < lampsInRow.length) {
                Arrays.fill(lampsInRow, noOfLampsOn, lampsInRow.length, LampColor.NONE.getSignal());
            }
        } else {
            Arrays.fill(lampsInRow, LampColor.NONE.getSignal());
        }

        return String.copyValueOf(lampsInRow);
    }

    private String getMinutesSignal(final int minute){
        final StringBuilder minutesSignal = new StringBuilder();
        final StringBuilder topRowMinutesSignal = new StringBuilder(
                getLampSignal((new char[11]), (minute / 5), LampColor.YELLOW.getSignal()));

        if(topRowMinutesSignal.charAt(2) == LampColor.YELLOW.getSignal()){
            topRowMinutesSignal.setCharAt(2, LampColor.RED.getSignal());

            if(topRowMinutesSignal.charAt(5) == LampColor.YELLOW.getSignal()){
                topRowMinutesSignal.setCharAt(5, LampColor.RED.getSignal());

                if(topRowMinutesSignal.charAt(8) == LampColor.YELLOW.getSignal()){
                    topRowMinutesSignal.setCharAt(8, LampColor.RED.getSignal());
                }
            }
        }
        minutesSignal.append(topRowMinutesSignal);
        minutesSignal.append(System.lineSeparator());
        minutesSignal.append(getLampSignal((new char[4]), (minute % 5), LampColor.YELLOW.getSignal()));

        return minutesSignal.toString();
    }

    private String blinkFromSeconds(final int second){
        if(second % 2 == 0) {
            return String.valueOf(LampColor.YELLOW.getSignal());
        }
        return String.valueOf(LampColor.NONE.getSignal());
    }
}

